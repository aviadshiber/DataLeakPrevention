package org.example;

import lombok.Cleanup;
import lombok.val;
import org.example.apis.RuleReader;
import org.example.apis.RulesClassLoader;
import org.example.rules.RuleEvaluator;
import org.example.rules.RuleJsonReader;
import org.example.rules.RulesImplLoader;
import org.example.rules.utils.FileHelper;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    private static final String POLICY_CONFIG_FILE = "policy.json";

    //private static final String FILE_TO_SCAN = "ssn-example.csv";
    private static final String FILE_TO_SCAN_URL = "https://www.protecto.ai/wp-content/uploads/2020/08/Book1.xlsx-us-social-security-22-cvs.csv";


    public static void main(String[] args) throws IOException, URISyntaxException {
        //TODO: add Command line view and load paths from args
        //init all resources needed
        val dataTempPath = Files.createTempFile(null, "_downloaded_data");
        val dataTempFile = dataTempPath.toFile();
        val configFilePath = Path.of(Objects.requireNonNull(Main.class.getClassLoader().getResource(POLICY_CONFIG_FILE)).toURI());
        val configReader = FileHelper.readFile(configFilePath);

        //val dataPath = Path.of(Objects.requireNonNull(Main.class.getClassLoader().getResource(FILE_TO_SCAN)).toURI());
        downloadFile(FILE_TO_SCAN_URL, dataTempFile);
        @Cleanup val dataReader = FileHelper.readFile(dataTempPath);


        val app = new DataLossPreventionApp(configReader, dataReader, ruleReader, classLoader, evaluator, writer);
        app.run();
    }


}