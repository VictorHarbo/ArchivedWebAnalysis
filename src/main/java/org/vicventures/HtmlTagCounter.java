package org.vicventures;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * This class contains methods to count the occurrences of different HTML tags in input HTML strings.
 */
public class HtmlTagCounter {
    /**
     * Count number of H1 tags in HTML document.
     * @param htmlString string representation of HTML file.
     * @return number of H1 tags from input doc.
     */
    public static long findH1Tags(String htmlString){
        Document doc = Jsoup.parse(htmlString);
        Elements headings = doc.select("h1");

        return headings.size();
    }

    /**
     * Count number of H2 tags in HTML document.
     * @param htmlString string representation of HTML file.
     * @return number of H2 tags from input doc.
     */
    public static long findH2Tags(String htmlString){
        Document doc = Jsoup.parse(htmlString);
        Elements headings = doc.select("h2");

        return headings.size();
    }

    /**
     * Count number of H3 tags in HTML document.
     * @param htmlString string representation of HTML file.
     * @return number of H3 tags from input doc.
     */
    public static long findH3Tags(String htmlString){
        Document doc = Jsoup.parse(htmlString);
        Elements headings = doc.select("h3");

        return headings.size();
    }

    /**
     * Count number of H4 tags in HTML document.
     * @param htmlString string representation of HTML file.
     * @return number of H4 tags from input doc.
     */
    public static long findH4Tags(String htmlString){
        Document doc = Jsoup.parse(htmlString);
        Elements headings = doc.select("h4");

        return headings.size();
    }

    /**
     * Count number of H5 tags in HTML document.
     * @param htmlString string representation of HTML file.
     * @return number of H5 tags from input doc.
     */
    public static long findH5Tags(String htmlString){
        Document doc = Jsoup.parse(htmlString);
        Elements headings = doc.select("h5");

        return headings.size();
    }

    /**
     * Count number of a tags in HTML document.
     * @param htmlString string representation of HTML file.
     * @return number of a tags from input doc.
     */
    public static long findATags(String htmlString){
        Document doc = Jsoup.parse(htmlString);
        Elements aTags = doc.select("a");

        return aTags.size();
    }

    /**
     * Count number of abbr tags in HTML document.
     * @param htmlString string representation of HTML file.
     * @return number of abbr tags from input doc.
     */
    public static long findAbbrTags(String htmlString){
        Document doc = Jsoup.parse(htmlString);
        Elements abbrTags = doc.select("abbr");

        return abbrTags.size();
    }

    /**
     * Count number of aside tags in HTML document.
     * @param htmlString string representation of HTML file.
     * @return number of aside tags from input doc.
     */
    public static long findAsideTags(String htmlString){
        Document doc = Jsoup.parse(htmlString);
        Elements asideTags = doc.select("aside");

        return asideTags.size();
    }

    /**
     * Count number of audio tags in HTML document.
     * @param htmlString string representation of HTML file.
     * @return number of audio tags from input doc.
     */
    public static long findAudioTags(String htmlString){
        Document doc = Jsoup.parse(htmlString);
        Elements audioTags = doc.select("tag");

        return audioTags.size();
    }

    /**
     * Count number of b tags in HTML document.
     * @param htmlString string representation of HTML file.
     * @return number of b tags from input doc.
     */
    public static long findBTags(String htmlString){
        Document doc = Jsoup.parse(htmlString);
        Elements bTags = doc.select("tag");

        return bTags.size();
    }

    /**
     * Count number of basefont tags in HTML document.
     * @param htmlString string representation of HTML file.
     * @return number of basefont tags from input doc.
     */
    public static long findBasefontTags(String htmlString){
        Document doc = Jsoup.parse(htmlString);
        Elements basefontTags = doc.select("basefont");

        return basefontTags.size();
    }

    /**
     * Count number of big tags in HTML document.
     * @param htmlString string representation of HTML file.
     * @return number of big tags from input doc.
     */
    public static long findBigTags(String htmlString){
        Document doc = Jsoup.parse(htmlString);
        Elements bigTags = doc.select("tag");

        return bigTags.size();
    }

    /*
    public static long findTags(String htmlString){
        Document doc = Jsoup.parse(htmlString);
        Elements tags = doc.select("tag");

        return tags.size();
    }
     */
}
