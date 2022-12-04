package org.example;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.apis.Rule;
import org.example.apis.RuleReader;
import org.example.apis.RulesClassLoader;
import org.example.apis.domain.Rules;
import org.example.rules.RuleEvaluator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Slf4j
public class DataLossPreventionApp implements Runnable {
    private final int availableProcessors;
    private final ExecutorService executor;
    private final List<Rule> rules;
    private final RuleEvaluator evaluator;
    private final BufferedReader dataReader;
    private final Writer writer;

    public DataLossPreventionApp(
            Reader configReader,
            BufferedReader dataReader,
            RuleReader ruleReader,
            RulesClassLoader classLoader,
            RuleEvaluator evaluator,
            Writer writer
    ) {
        availableProcessors = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(availableProcessors);
        this.dataReader = dataReader;
        this.evaluator = evaluator;
        this.writer = writer;

        val loadedRules = classLoader.load(readRules(configReader, ruleReader));
        rules = new ArrayList<>(loadedRules);
    }

    @SneakyThrows
    private static Rules readRules(Reader configReader, RuleReader ruleReader) {
        log.debug("reading rules from reader");
        @Cleanup val temporaryConfigReader = configReader;
        return ruleReader.readRules(temporaryConfigReader);
    }

    @Override
    @SneakyThrows
    public void run() {
        long start = System.currentTimeMillis();
        {
            @Cleanup val stream = dataReader.lines().onClose(executor::shutdown);
            stream.forEach(line -> {
                rules.forEach(rule -> {
                    CompletableFuture.runAsync(() -> {
                        try {
                            evaluator.evaluate(rule, line, writer);
                        } catch (IOException e) {
                            log.error(e.toString());
                            executor.shutdown();
                        }
                    }, executor);
                });
            });
        }
        executor.awaitTermination(10, TimeUnit.MINUTES);
        long totalTime = System.currentTimeMillis() - start;
        log.info("total time: {} milliseconds", totalTime);
    }
}
