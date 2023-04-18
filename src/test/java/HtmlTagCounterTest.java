import org.junit.Test;
import org.vicventures.DataLoader;
import org.vicventures.HtmlTagCounter;
import org.vicventures.WarcLoader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HtmlTagCounterTest {
    @Test
    public void h1TagTest(){
        String multipleH1String ="<h1>Big heading</h1>" +
                "<h1>Big heading2</h1>" +
                "<h1>Big heading3</h1>" +
                "<h1>Big heading4</h1>";

        long result = HtmlTagCounter.findTags(multipleH1String, "h1");

        assertEquals( 4, result);
    }

    @Test
    public void tagsFromFileTest() throws IOException {
        List<String> htmlStrings = WarcLoader.listOfHtmlsFromWarcS("src/test/resources/IAH-20080430204825-00000-blackbook.warc");
        long h1Tags = HtmlTagCounter.findTags(htmlStrings.get(11), "h1");

        assertEquals(2, h1Tags);
    }

    @Test
    public void allTagsTest() throws IOException{
        List<String> htmlStrings = WarcLoader.listOfHtmlsFromWarcS("src/test/resources/IAH-20080430204825-00000-blackbook.warc");
        Map<String, Long> result = HtmlTagCounter.countAllTagsFromString(htmlStrings.get(11));

        assertEquals(26, result.size());
    }

    @Test
    public void allTagsFromFilesInDirTest() throws IOException {
        HtmlTagCounter.countTagsInFilesFromDir(DataLoader.oddernettetData, ".html");
    }



}
