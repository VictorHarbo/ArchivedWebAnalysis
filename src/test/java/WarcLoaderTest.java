import org.junit.Test;
import org.vicventures.WarcLoader;

import java.io.IOException;

public class WarcLoaderTest {

    @Test
    public void streamPartOfWarcTest() throws  IOException{
        WarcLoader.listOfHtmlsFromWarcS("src/test/resources/IAH-20080430204825-00000-blackbook.warc");
    }
}
