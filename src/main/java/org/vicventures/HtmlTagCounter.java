package org.vicventures;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Collector;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static Map<String, Long> countAllTagsFromString(String htmlString) {

        Document document = Jsoup.parse(htmlString);
        Map<String, Long> counts = countAllTagsInDoc(document);
        return counts;
    }

    public static Map<String, Long> countAllTagsInDoc(Document doc){
        List<String> tags = new ArrayList<String>();

        for(Element e : doc.getAllElements()){
            tags.add(e.tagName().toLowerCase());
        }

        Map<String, Long> counts = tags.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        return counts;
    }

    public static Map<String, Long> countTagsInFilesFromDir(String dir,String filetype) throws IOException {
        List<String> files = DataLoader.getListOfFilesWithSpecificTypeFromDir(dir, filetype);

        for (String file: files) {
            Document doc = DataLoader.loadHtmlFromPath(file);
            Map<String, Long> counts = countAllTagsInDoc(doc);
            System.out.println(counts);
        }
        return null;
    }

}
