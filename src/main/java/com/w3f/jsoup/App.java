package com.w3f.jsoup;

import org.apache.commons.cli.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws IOException {

        CommandLine commandLine;

        Option url = Option.builder("u")
                .argName("url")
                .hasArg()
                .desc("Fetch html from url")
                .longOpt("url")
                .build();
        Option html = Option.builder("h")
                .argName("html")
                .hasArg()
                .desc("Provide raw html")
                .longOpt("html")
                .build();
        Option selector = Option.builder("s")
                .argName("selector")
                .hasArg()
                .desc("Jsoup HTML selector")
                .longOpt("selector")
                .build();
        Option help = Option.builder()
                .argName("help")
                .longOpt("help")
                .build();

        Options options = new Options()
                .addOption(url)
                .addOption(selector)
                .addOption(html)
                .addOption(help);

        CommandLineParser commandLineParser = new DefaultParser();

        try {
            commandLine = commandLineParser.parse(options, args);
            Document doc = null;

            if (commandLine.hasOption(help.getArgName())) {
                HelpFormatter helpFormatter = new HelpFormatter();
                helpFormatter.printHelp("[option]", options);
            }
            if (commandLine.hasOption(url.getOpt())) {
                doc = Jsoup.connect(commandLine.getOptionValue(url.getArgName())).get();
            }
            if (commandLine.hasOption(html.getOpt())) {
                doc = Jsoup.parse(commandLine.getOptionValue(html.getArgName()));
            }
            if (commandLine.hasOption(selector.getOpt())) {
                String collect = doc.select(commandLine.getOptionValue(selector.getArgName())).stream()
                        .map(Element::text)
                        .collect(Collectors.joining("\n"));
                System.out.println(collect);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
