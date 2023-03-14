import org.junit.Test;
import org.vicventures.DataLoader;

import java.util.List;
import java.util.Map;

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
        System.out.println(result.entrySet());
        //DataLoader.getListOfAllYears(DataLoader.oddernettetData);
    }
}
