package org.vicventures;

import tech.tablesaw.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataTransformer {

    /**
     * Convert a nested map to a tablesaw table.
     * @param originalMap to convert.
     * @return tablesaw table of input map.
     */
    public static Table convertToFrequencies(Map<String, Map<String, Integer>> originalMap){

        // Get filetypes from original map of data
        List<String> filetypes = originalMap.keySet().stream().toList();

        // Get all years from one sub-map of original map
        List<String> years = originalMap.get(".css").keySet().stream().toList();

        // Create a column for each filetype and add its value from original Map
        List<IntColumn> filetypeColumns = new ArrayList<>();
        for (String filetype : filetypes) {
            Map<String, Integer> innerMap = originalMap.get(filetype);
            int[] valuesOfInnerMap = innerMap.values().stream().mapToInt(i -> i).toArray();
            int sum = Arrays.stream(valuesOfInnerMap).sum();
            // Only add data if column isn't empty
            if (sum > 0){
                IntColumn columnI = IntColumn.create(filetype, valuesOfInnerMap);
                filetypeColumns.add(columnI);
            }

        }

        // Create table and add single column with all years
        Table ofFiletypes =
                Table.create("Archived filetypes")
                        .addColumns(
                                StringColumn.create("Years", years),
                                IntColumn.create("total_files")
                                //IntColumn.create("rating")
                        );

        // Add all filetype columns to table.
        for (IntColumn column: filetypeColumns) {
            ofFiletypes.addColumns(column);
        }

        // Counting integers in row and adding sum to column total_files
        for (Row row : ofFiletypes){
            List<String> columnNames = row.columnNames();
            List<Integer> numbersInRow = new ArrayList<>();
            for (String column : columnNames){
                if (column.equals("Years") || column.equals("total_files")){
                    continue;
                } else {
                    numbersInRow.add(row.getInt(column));
                }
            }
            int totalNumbersInRow = numbersInRow.stream().reduce(0, Integer::sum);
            row.setInt("total_files", totalNumbersInRow);
        }

        // Copying the table of filetypes to a new table called frequencies
        Table frequencies = Table.create("Frequencies")
                .addColumns(
                        StringColumn.create("Years", years),
                        ofFiletypes.column(1)
                );

        // Add double columns to frequencies
        for (int i = 2; i< ofFiletypes.columnCount(); i++){
            String columnName = ofFiletypes.column(i).name();
            frequencies.addColumns(DoubleColumn.create(columnName));
        }

        // Calculate frequencies for each row
        for (Row row : ofFiletypes) {
            List<String> columnNames = row.columnNames();
            int rowNumber = row.getRowNumber();
            int totalFiles = row.getInt("total_files");
            if (!(totalFiles == 0)) {
                for (String column : columnNames) {
                    if (column.equals("Years") || column.equals("total_files")) {
                        continue;
                    } else {
                        int specificFile = row.getInt(column);
                        double freqOfFile = (double) specificFile/ (double) totalFiles * 100;
                        frequencies.row(rowNumber).setDouble(column, freqOfFile);
                    }
                }
            }
        }

        // Console check to ensure I've got 100% and done calculation correct
        for (Row row : frequencies) {
            int rowNumber = row.getRowNumber();
            int totalFiles = row.getInt("total_files");
            if (!(totalFiles == 0)){
                double totalFreq = 0.0;
                for (int i = 2; i < frequencies.columnCount(); i++) {
                    totalFreq += row.getDouble(i);
                }
                if (totalFreq < 99.99){
                    throw new RuntimeException("Calculation of frequency has gone wrong");
                }

            }
        }

        return frequencies;
    }
}
