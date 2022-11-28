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


@Slf4j
public class DataLossPreventionApp implements Runnable {
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
        val loadedRules = classLoader.load(readRules(configReader, ruleReader));
        rules = new ArrayList<>(loadedRules);
        this.dataReader = dataReader;
        this.evaluator = evaluator;
        this.writer = writer;
    }

    @SneakyThrows
    private static Rules readRules(Reader configReader, RuleReader ruleReader) {
        log.debug("reading rules from reader");
        @Cleanup val temporaryConfigReader = configReader;
        return ruleReader.readRules(temporaryConfigReader);
    }

    @Override
    public void run() {

        dataReader.lines().forEach(line -> {
            rules.forEach(rule -> {
                try {
                    evaluator.evaluate(rule, line, writer);
                } catch (IOException e) {
                    log.error(e.toString());
                }
            });
        });
    }
}
