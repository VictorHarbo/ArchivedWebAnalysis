package org.vicventures;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String htmlString = WarcLoader.loadFirstHtmlWarcEntry("src/test/resources/IAH-20080430204825-00000-blackbook.warc");

        HtmlTagCounter.findH1Tags(htmlString);
    }
}