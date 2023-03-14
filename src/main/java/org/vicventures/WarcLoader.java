package org.vicventures;

import org.netpreserve.jwarc.*;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class WarcLoader {

    /**
     * Get list of warc records, might not be used for anything
     * @param path
     * @return
     * @throws IOException
     */
    public static List<WarcRecord> getListOfWarcs(String path) throws IOException {
        Path inputPath = Path.of(path);
        WarcReader reader = new WarcReader(inputPath);

        Stream<WarcRecord> records = reader.records();

        return records.toList();
    }

    /**
     * Returns all HTML responses from input warc
     * @param path
     * @return
     * @throws IOException
     */
    public static List<String> listOfHtmlsFromWarcS(String path) throws IOException{
        List<String> allHtmlStrings = new ArrayList<>();
        try (WarcReader reader = new WarcReader(FileChannel.open(Paths.get(path)))) {

            // Analyse all websites in input warc file.
            for (WarcRecord record : reader) {
                if (record instanceof WarcResponse && record.contentType().base().equals(MediaType.HTTP)) {
                    WarcResponse response = (WarcResponse) record;
                    //System.out.println(response.http().status() + " " + response.target());

                    byte[] responseBodyAsBytes = response.body().stream().readAllBytes();
                    String responseAsString = new String(responseBodyAsBytes, StandardCharsets.UTF_8);
                    allHtmlStrings.add(responseAsString);


                }
            }
        }
        return allHtmlStrings;
    }

    /**
     * Load single warc entry for testing
     * @param path
     * @return
     * @throws IOException
     */
    public static String loadFirstHtmlWarcEntry(String path) throws IOException {
        String responseAsString = "";
        try (WarcReader reader = new WarcReader(FileChannel.open(Paths.get(path)))) {
            // Single result
            for (WarcRecord record : reader) {
                for (int count = 0; count < 2; count++) {
                    if (record instanceof WarcResponse && record.contentType().base().equals(MediaType.HTTP)) {
                        WarcResponse response = (WarcResponse) record;
                        //System.out.println(response.http().status() + " " + response.target());

                        byte[] responseBodyAsBytes = response.body().stream().readAllBytes();
                        responseAsString = new String(responseBodyAsBytes, StandardCharsets.UTF_8);
                    }
                }
            }
        }
        return responseAsString;
    }
}
