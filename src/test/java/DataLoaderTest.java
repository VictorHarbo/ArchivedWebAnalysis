import org.junit.Test;
import org.vicventures.DataLoader;
import org.vicventures.DataToAndFromDisk;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

// TODO: Add tests that makes sense
public class DataLoaderTest {

    // TODO: rename and make actual test
    @Test
    public void snapshotsPerYearTest(){
       Map<String, Integer> snapshotsPerYear = DataLoader.countNumberOfSnapshotsPerYear(DataLoader.odderData);

       System.out.println(snapshotsPerYear.entrySet());
    }

    // TODO: rename and make actual test
    @Test
    public void randomTest(){
        Map<String, Map<String, Integer>> result = DataLoader.getAllFiletypesPerYear();
        DataToAndFromDisk.mapToDisk(result);
        //DataLoader.getListOfAllYears(DataLoader.oddernettetData);
    }

    @Test
    public void testMapWithoutHtmlValues(){
        Map<String, Map<String, Integer>> result = DataLoader.getAllFiletypesPerYearMinusHtml();
        assertFalse(result.containsKey(".html"));
    }

    @Test
    public void testLoadMap(){
        Map<String, Map<String, Integer>> result = DataToAndFromDisk.mapFromDisk();

        result.entrySet().forEach(System.out::println);
    }
}
