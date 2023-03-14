package org.vicventures;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

//TODO: Add documentation for all methods
public class DataLoader {
    public static final String odderData = "src/main/resources/data/odder";
    public static final String oddernettetData = "src/main/resources/data/oddernettet";

    public static Map<String, Integer> countNumberOfSnapshotsPerYear(String website){
        String[] datesFromDirectory = createDateArrayFromDirectoryNames(website);
        List<String> filteredListOnlySnapshotDates = removeNonSnapshots(datesFromDirectory);
        int[] allYears = getYears(filteredListOnlySnapshotDates);
        Map<Integer, Integer> snapshotsPerYear = countSnapshotsPerYear(allYears);
        Map<Integer, Integer> snapshotsPerYearSorted = sortHashMap(snapshotsPerYear);
        Map<String, Integer> snapshotsPerYearString = convertMapKeysToStrings(snapshotsPerYearSorted);

        return snapshotsPerYearString;
    }
    /**
     * Create string array of files and directories in input directory
     * @param directory to extract file- and directory names from
     * @return an array of all file- and directory names in given input directory
     */
    private static String[] createDateArrayFromDirectoryNames(String directory){
        File inputDirectoryPath = new File(directory);
        return inputDirectoryPath.list();
    }

    /**
     * Adds all strings from input array that has the form of an Internet Archive snapshot timestamp.
     * @param dates an array of file/directory names, that contains some snapshot timestamps
     * @return a list of timestamps
     */
    private static List<String> removeNonSnapshots(String[] dates){
        List<String> listOfSnapshots = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d{14}");
        for (String s:dates) {
            Matcher matcher = pattern.matcher(s);
            boolean matchFound = matcher.find();
            if (matchFound){
                listOfSnapshots.add(s);
            }
        }
        return listOfSnapshots;
    }

    /**
     * Extracts years from input timestamps
     * @param listOfSnapshots containing strings of 14 digit timestamps of the format YYYYMMDDHHMMSS
     * @return an array of years converted to integers
     */
    private static int[] getYears(List<String> listOfSnapshots){
        String[] stringYears = new String[listOfSnapshots.size()];
        for (int i = 0; i<listOfSnapshots.size(); i++) {
            stringYears[i] = listOfSnapshots.get(i).substring(0,4);
        }

        int[] years = new int[stringYears.length];

        for (int i = 0; i < stringYears.length; i++) {
            years[i] = Integer.parseInt(stringYears[i]);
        }
        return years;
    }

    /**
     * Calculate how many times a single year is present in the input array
     * @param years contains multiple years as values
     * @return a map containing every single year as keys and the value is a count of how many times the key was present in the ipnut array
     */
    private static Map<Integer, Integer> countSnapshotsPerYear(int[] years){
        // Initialize map with years from 1998 until today
        Map<Integer, Integer> snapshotsPerYear = new HashMap<>();
        for (int year : years) {
            if (snapshotsPerYear.containsKey(year)) {
                int newCount = snapshotsPerYear.get(year) + 1;
                snapshotsPerYear.put(year, newCount);
            } else {
                snapshotsPerYear.put(year, 1);
            }
        }
        return snapshotsPerYear;
    }

    private static Map<Integer, Integer> sortHashMap(Map<Integer, Integer> inputMap){
        Map<Integer, Integer> snapshotsPerYearSorted = new TreeMap<>(inputMap);
        return snapshotsPerYearSorted;
    }

    private static Map<String, Integer> convertMapKeysToStrings(Map<Integer, Integer> inputMap){
        Map<String, Integer> snapshotsPerYearString = new TreeMap<>();

        for (Map.Entry<Integer,Integer> pair : inputMap.entrySet()) {
            snapshotsPerYearString.put(pair.getKey().toString(),pair.getValue());
        }
        return snapshotsPerYearString;
    }

    private static List<String> getListOfAllFilesFromDirectory(String startDirectory){
        List<String> fileStrings = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(Paths.get(startDirectory))) {
            List<Path> fileendings = stream.filter(Files::isRegularFile)
                    .toList();
            for (Path p: fileendings) {
                fileStrings.add(String.valueOf(p));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileStrings;
    }

    /**
     * Return a list of all file formats present in the directories given as input
     * @param paths to directories that are to be searched for filetypes
     * @return a list of all file types present in input directories
     */
    private static List<String> getCleanFileFormatFromPaths(List<String> paths){
        List<String> fileFormats = new ArrayList<>();
        for (String path : paths) {
            if (path.contains(".")){
                int index = path.indexOf(".");
                fileFormats.add(path.substring(index));
            } else {
                fileFormats.add(path);
            }
        }

        List<String> fileFormatsNoQuery = new ArrayList<>();
        for (String s: fileFormats) {
            if (s.contains("?")){
                int index = s.indexOf("?");
                fileFormatsNoQuery.add(s.substring(0, index));
            } else {
                fileFormatsNoQuery.add(s);
            }
        }

        List<String> fileFormatsNoAnd = new ArrayList<>();
        for (String s : fileFormatsNoQuery){
            if (s.contains("&")){
                int index = s.indexOf("&");
                fileFormatsNoAnd.add(s.substring(0, index));
            } else {
                fileFormatsNoAnd.add(s);
            }
        }

        List<String> fileFormatsNoDoubleDot = new ArrayList<>();
        for (String s : fileFormatsNoAnd){
            if (s.contains(".")){
                int index = s.lastIndexOf(".");
                fileFormatsNoDoubleDot.add(s.substring(index));
            } else {
                fileFormatsNoDoubleDot.add(s);
            }
        }

        List<String> fileFormatClean = new ArrayList<>();
        for (String s : fileFormatsNoDoubleDot) {
            if (!s.contains("DS_Store")){
                fileFormatClean.add(s.toLowerCase());
            }
        }

        List<String> fileFormatManualClean = new ArrayList<>();
        for ( String s : fileFormatClean){
            if (s.equalsIgnoreCase(".aspx/")){
                fileFormatManualClean.add(".aspx");
            } else if (s.equalsIgnoreCase(".doc")) {
                fileFormatManualClean.add(".docx");
            } else if (s.equalsIgnoreCase(".htm")) {
                fileFormatManualClean.add(".html");
            } else if (s.equalsIgnoreCase(".jpeg")) {
                fileFormatManualClean.add(".jpg");
            } else if (s.equalsIgnoreCase(".dk")) {
            } else {
                fileFormatManualClean.add(s);
            }
        }
        return fileFormatManualClean;
    }

    private static List<String> getFiletypesFromSpecificYear(String year, String startDirectory){
        List<String> fileStrings = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(Paths.get(startDirectory))) {
            List<Path> fileendings = stream.filter(Files::isRegularFile)
                    .toList();
            for (Path p: fileendings) {
                fileStrings.add(String.valueOf(p));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<String> shortenedPaths = new ArrayList<>();
        for (String s : fileStrings){
            shortenedPaths.add(s.replace(startDirectory, ""));
        }

        List<String> filteredPaths = new ArrayList<>();
        for (String s : shortenedPaths){
            if (s.startsWith("/"+ year)){
                filteredPaths.add(s);
            }
        }
        return filteredPaths;
    }

    /**
     * TODO: Write doc
     * @return
     */
    public static Map<String, Map<String, Integer>> getAllFiletypesPerYear(){
        // Create list containing all formats
        List<String> fileStrings = getListOfAllFilesFromDirectory(oddernettetData);
        List<String> allCleanFormats = getCleanFileFormatFromPaths(fileStrings);
        List<String> allDistinctFormats = allCleanFormats.stream().distinct().toList();
        List<String> allYears = getListOfAllYears(oddernettetData);

        Map<String, Map<String, Integer>> filetypesPerYearCount = new HashMap<>();
        for (int i = 0; i < allDistinctFormats.size(); i++) {
            filetypesPerYearCount.put(allDistinctFormats.get(i), createInnerMaps(allYears, allDistinctFormats, i));
        }
        return filetypesPerYearCount;
    }

    private static Map<String, Integer> createInnerMaps(List<String> allYears, List<String> allDistinctFormats, int i){
        Map<String, Integer> innerMap = new TreeMap<>();

        for (int j = 0; j < allYears.size(); j++){
            int[] countOfFormatsForYear = new int[allDistinctFormats.size()];
            List<String> filetypesForYear = getFiletypesFromSpecificYear(allYears.get(j), oddernettetData);
            List<String> cleanFormatsForYear = getCleanFileFormatFromPaths(filetypesForYear);
            List<String> distinctFormatsForYear = cleanFormatsForYear.stream().distinct().toList();

            for (int k = 0; k < distinctFormatsForYear.size(); k++) {
                for (String format : cleanFormatsForYear) {
                    if (format.equals(distinctFormatsForYear.get(k))) {
                        countOfFormatsForYear[k]++;
                    }
                }
            }

            innerMap.put(allYears.get(j), countOfFormatsForYear[i]);
        }
        return innerMap;
    }

    private static List<String> getListOfAllYears(String website){
        String[] datesFromDirectory = createDateArrayFromDirectoryNames(website);
        List<String> filteredListOnlySnapshotDates = removeNonSnapshots(datesFromDirectory);
        List<String> allYears = getDistinctStringYears(filteredListOnlySnapshotDates);
        return allYears;
    }

    private static List<String> getDistinctStringYears(List<String> listOfSnapshots) {
        String[] stringYears = new String[listOfSnapshots.size()];
        for (int i = 0; i < listOfSnapshots.size(); i++) {
            stringYears[i] = listOfSnapshots.get(i).substring(0, 4);
        }
        List<String> distinctYears = Arrays.stream(stringYears).distinct().toList();
        return distinctYears;
    }


}