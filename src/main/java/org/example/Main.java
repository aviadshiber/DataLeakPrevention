package org.example;

import lombok.Cleanup;
import lombok.val;
import org.example.apis.RuleReader;
import org.example.apis.RulesClassLoader;
import org.example.rules.RuleEvaluator;
import org.example.rules.RuleJsonReader;
import org.example.rules.RulesImplLoader;
import org.example.view.CommandLineView;
import org.example.rules.utils.FileHelper;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.example.rules.utils.WebHelper.downloadFile;

public class Main {

    //Application IOC (All "Beans" are defined here):
    private static final RuleEvaluator evaluator =
            new RuleEvaluator(
                    ((timestamp, context, data, args) ->
                            String.format("[%s] - found [rule:%s] given \"%s\"%n", timestamp, context.name(), data))
            );
    private static final Writer writer = new PrintWriter(System.out);
    private static final RuleReader ruleReader = new RuleJsonReader();
    private static final RulesClassLoader classLoader = new RulesImplLoader();
    private static final String DEFAULT_POLICY_CONFIG_FILE = "policy.json";

    public static void main(String[] args) throws IOException, URISyntaxException {
        val cmd = new CommandLineView(args);
        val configFilePath = cmd.getConfigPath().map(Paths::get).orElse(defaultConfigPath());
        val urlToDownload = cmd.getUrl();
        //init all resources needed
        val dataTempPath = Files.createTempFile(null, "_downloaded_data");
        val dataTempFile = dataTempPath.toFile();
        dataTempFile.deleteOnExit();
        val configReader = FileHelper.readFile(configFilePath);


        downloadFile(urlToDownload, dataTempFile);
        @Cleanup val dataReader = FileHelper.readFile(dataTempPath);


        val app = new DataLossPreventionApp(configReader, dataReader, ruleReader, classLoader, evaluator, writer);
        app.run();
    }

    private static Path defaultConfigPath() throws URISyntaxException {
        return Path.of(Objects.requireNonNull(Main.class.getClassLoader().getResource(DEFAULT_POLICY_CONFIG_FILE)).toURI());
    }


}