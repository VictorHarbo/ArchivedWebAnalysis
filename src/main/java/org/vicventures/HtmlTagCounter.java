package org.vicventures;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * This class contains methods to count the occurrences of different HTML tags in input HTML strings.
 */
public class HtmlTagCounter {

    /**
     * Count number of input tag in HTML document.
     * @param htmlString string representation of HTML file.
     * @param tag to search for in the HTML document.
     * @return number of given tag in input doc.
     */
    public static long findTags(String htmlString, String tag){
        Document doc = Jsoup.parse(htmlString);
        Elements tags = doc.select(tag);

        return tags.size();
    }

}
