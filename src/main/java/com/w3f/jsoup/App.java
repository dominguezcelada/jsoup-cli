package com.w3f.jsoup;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static sun.misc.Version.print;

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
        Option attribute = Option.builder("a")
                .argName("attribute")
                .hasArg()
                .desc("Output attribute value instead of text. E.g --attribute=href")
                .longOpt("attribute")
                .build();
        Option dataset = Option.builder("d")
                .argName("dataset")
                .hasArg()
                .desc("Output dataset value instead of text. E.g --dataset=videoUrl")
                .longOpt("dataset")
                .build();
        Option tags = Option.builder("t")
                .argName("list-tags")
                .hasArg(false)
                .desc("List all children tags in selected query")
                .longOpt("list-tags")
                .build();
        Option help = Option.builder()
                .argName("help")
                .longOpt("help")
                .build();

        Options options = new Options()
                .addOption(url)
                .addOption(selector)
                .addOption(html)
                .addOption(attribute)
                .addOption(dataset)
                .addOption(tags)
                .addOption(help);

        CommandLineParser commandLineParser = new DefaultParser();

        try {
            commandLine = commandLineParser.parse(options, args);
            Document doc = null;

            if (commandLine.hasOption(help.getArgName())) {
                HelpFormatter helpFormatter = new HelpFormatter();
                helpFormatter.printHelp("[options]", options);
            }
            if (commandLine.hasOption(url.getOpt())) {
                doc = Jsoup.connect(commandLine.getOptionValue(url.getArgName())).get();
            }
            if (commandLine.hasOption(html.getOpt())) {
                doc = Jsoup.parse(commandLine.getOptionValue(html.getArgName()));
            }
            if (commandLine.hasOption(selector.getOpt())) {
                String output;
                Stream<Element> elements = doc.select(commandLine.getOptionValue(selector.getArgName())).stream();

                if (commandLine.hasOption(attribute.getOpt())) {
                    output = elements.map(e -> e.attr(commandLine.getOptionValue(attribute.getArgName())))
                            .collect(Collectors.joining("\n"));
                } else if(commandLine.hasOption(dataset.getOpt())) {
                    System.out.println();
                    output = elements.map(e -> {
                        e.dataset().forEach((datasetKey, datasetValue) -> System.out.println(datasetKey + " " + datasetValue));
                        return e.dataset().get(commandLine.getOptionValue(dataset.getArgName()));
                    }).collect(Collectors.joining("\n"));
                } else if(commandLine.hasOption(tags.getOpt())) {
                    output = elements.map(e -> e.getAllElements()
                            .stream()
                            .map(Element::tagName)
                            .collect(Collectors.joining("\n")))
                            .collect(Collectors.joining("\n"));
                } else {
                    output = elements
                            .map(Element::text)
                            .collect(Collectors.joining("\n"));
                }
                System.out.println(output);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
