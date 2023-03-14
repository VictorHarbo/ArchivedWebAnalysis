import org.junit.Test;
import org.vicventures.DataLoader;

import java.util.List;
import java.util.Map;

public class DataLoaderTest {
    @Test
    public void snapshotsPerYearTest(){
       Map<String, Integer> snapshotsPerYear = DataLoader.countNumberOfSnapshotsPerYear(DataLoader.odderData);

       System.out.println(snapshotsPerYear.entrySet());
    }

    @Test
    public void getListOfFileFormatsTest(){
        Map<String, Integer> countOfFormats = DataLoader.getFileTypesFromSite(DataLoader.odderData);

        countOfFormats.entrySet().stream().forEach(System.out::println);
    }

    @Test
    public void randomTest(){
        DataLoader.getFiletypesSortedByYear(DataLoader.odderData);
    }
}
