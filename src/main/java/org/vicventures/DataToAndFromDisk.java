package org.vicventures;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DataToAndFromDisk {

    /**
     * Save map to disk.
     * @param output nested map to save to disk.
     */
    public static void mapToDisk(Map<String, Map<String, Integer>> output){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("src/main/resources/data/mapOfOdddernettetData");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(output);
            out.close();
            fileOut.close();
            System.out.printf("src/main/resources/data/mapOfOdddernettetData");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Load map from disk.
     * @return nested map, loaded from disk.
     */
    public static Map<String, Map<String, Integer>> mapFromDisk(){
        Map<String, Map<String, Integer>> loadedMap = null;
        try {
            FileInputStream fileIn = new FileInputStream("src/main/resources/data/mapOfOdddernettetData");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            loadedMap = (Map<String, Map<String, Integer>>) in.readObject();
            in.close();
            fileIn.close();
            return loadedMap;
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
        return loadedMap;
    }
}
