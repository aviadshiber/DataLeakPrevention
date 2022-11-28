package org.example.view;

import org.apache.commons.cli.*;

import java.util.Optional;

public class CommandLineView {
    static final String CONFIG_OPTION_KEY = "config";
    static final String URL_OPTION_KEY = "url";
    static final String HELP_OPTION_KEY = "help";
    Options options;
    CommandLine cmd;

    public CommandLineView(String[] args) throws IllegalArgumentException {
        this.options = new Options();
        options.addOption(new Option("cr",CONFIG_OPTION_KEY,true,"Path to a configuration file, representing the list of policy rules"));
        options.addOption(new Option("u", URL_OPTION_KEY,true,"url to the file which you wish to scan"));
        options.addOption(new Option("h", HELP_OPTION_KEY,false,"helps how to work with the tool"));
        CommandLineParser parser = new DefaultParser();

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            showHelp();
            throw new IllegalArgumentException(e);
        }
        if(cmd.hasOption(HELP_OPTION_KEY)){
            showHelp();
        }
        checkForMissingOption(URL_OPTION_KEY);
    }

    private void showHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ant", options);
    }

    public Optional<String> getConfigPath(){
        if(!cmd.hasOption(CONFIG_OPTION_KEY)) return Optional.empty();
        return Optional.ofNullable(cmd.getOptionValue(CONFIG_OPTION_KEY));
    }
    public String getUrl(){
        return cmd.getOptionValue(URL_OPTION_KEY);
    }

    private void checkForMissingOption(String optionKey) {
        if (!cmd.hasOption(optionKey)) {
            String errMessage = "Missing `--" + optionKey + "` option, type --help for more information";
            throw new IllegalArgumentException(errMessage);
        }
    }
}
