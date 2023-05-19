package org.vicventures;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import tech.tablesaw.api.Table;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class DataVisualisation extends Application {
    Map<String, Map<String, Integer>> values = DataToAndFromDisk.mapFromDisk();
    Map<String, Map<String, Integer>> valuesNoHtml = DataLoader.removeHtmlFilesFromMapOfMap(values);
    Table frequenciesData = DataTransformer.convertToFrequencies(valuesNoHtml);


    /**
     * Create figures that visualise the data analysed in other classes of this project.
     */
    @Override public void start(Stage stage) throws IOException {
        //TODO: Divide different visualisations into own methods
        stage.setTitle("Fordeling af arkiverede websites");

        createFigure1(stage);

        createFigure2(stage);

        createFigure3(stage);

    }
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Create figure that visualises how archived versions are dispersed per year.
     */
    private void createFigure1(Stage stage) throws IOException {
        //defining the axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("År");
        yAxis.setLabel("Antal");

        //creating the chart
        final LineChart<String,Number> lineChart =
                new LineChart<String,Number>(xAxis,yAxis);

        lineChart.setTitle("Figur 1: Fordeling af arkiverede versioner per år (1998-2023)");

        // Load odder.dk data
        Map<String, Integer> odderSnapshotsPerYear = DataLoader.countNumberOfSnapshotsPerYear(DataLoader.odderData);
        // define data series for odder.dk
        XYChart.Series<String, Number> odderSeries = new XYChart.Series<>();
        odderSeries.setName("odder.dk");
        // Add data from odder.dk map to dataseries
        for (Map.Entry<String, Integer> pair : odderSnapshotsPerYear.entrySet() ) {
            odderSeries.getData().add(new XYChart.Data(pair.getKey(), pair.getValue()));
        }

        // Load oddernettet.dk data
        Map<String, Integer> oddernettetSnapshotsPerYear = DataLoader.countNumberOfSnapshotsPerYear(DataLoader.oddernettetData);
        // Define data series for oddernettet.dk
        XYChart.Series oddernettetSeries = new XYChart.Series();
        oddernettetSeries.setName("oddernettet.dk");
        // Add data from oddernettet.dk map to dataseries
        for (Map.Entry<String, Integer> pair : oddernettetSnapshotsPerYear.entrySet() ) {
            oddernettetSeries.getData().add(new XYChart.Data(pair.getKey(), pair.getValue()));
        }

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().addAll(oddernettetSeries, odderSeries);

        scene.getStylesheets().add("stylesheet.css");
        stage.setScene(scene);

        //Saving the scene as image
        WritableImage image = scene.snapshot(null);
        File file = new File("src/main/resources/output/figure1_fordelingAfSnapshots.png");
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", file);
        System.out.println("Image1 Saved");
    }

    /**
     * Create figure that visualise how different filetypes have been used over time
     * on the website www.oddernettet.dk.
     */
    private void createFigure2(Stage stage) throws IOException {
        // Second visualisation
        final CategoryAxis xAxis2 = new CategoryAxis();
        final NumberAxis yAxis2 = new NumberAxis();

        xAxis2.setLabel("År");
        yAxis2.setLabel("Antal");

        //creating the chart
        final LineChart<String,Number> lineChart2 =
                new LineChart<String,Number>(xAxis2,yAxis2);

        lineChart2.setTitle("Figur 2: Fordeling af filtyper over tid på www.oddernettet.dk");

        //Map<String, Map<String, Integer>> values = DataToAndFromDisk.mapFromDisk();
        values.keySet().stream().forEach(filetype -> {
            XYChart.Series data = new XYChart.Series();
            data.setName(filetype);
            values.get(filetype).entrySet().stream().forEach(pair -> {
                data.getData().add(new XYChart.Data(pair.getKey(), pair.getValue()));
            });

            // and add the data set to the chart
            lineChart2.getData().add(data);
        });

        Scene scene  = new Scene(lineChart2,800,600);
        scene.getStylesheets().add("stylesheet.css");
        stage.setScene(scene);
        //Saving the third scene as image
        WritableImage image = scene.snapshot(null);
        File file = new File("src/main/resources/output/figure2_fordelingAfFiltyper.png");
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", file);
        System.out.println("Image2 Saved");
    }

    /**
     * Create figure that visualise how different filetypes are dispersed over time.
     * Measured in relative frequencies and without HTML files.
     */
    private void createFigure3(Stage stage) throws IOException {
        // Visualisation 3
        final CategoryAxis xAxis3 = new CategoryAxis();
        final NumberAxis yAxis3 = new NumberAxis(0,100,10);

        xAxis3.setLabel("År");
        yAxis3.setLabel("Procent");

        //creating the chart
        final AreaChart<String,Number> lineChart3 =
                new AreaChart<String,Number>(xAxis3,yAxis3);

        lineChart3.setTitle("Figur 3: Fordeling af filtyper over tid på www.oddernettet.dk uden HTML-filer. Målt i relativ frekvens.");
        lineChart3.setId("figur3");

        List<String> columnNames = frequenciesData.columnNames();
        columnNames.remove(0);
        columnNames.remove(0);

        columnNames.stream().forEach(filetype -> {
            XYChart.Series data = new XYChart.Series();
            data.setName(filetype);
            frequenciesData.stream().forEach(row -> {
                if (row.getInt("total_files") != 0) {
                    data.getData().add(new XYChart.Data(row.getString(0), row.getDouble(filetype)));
                }
            });

            lineChart3.getData().add(data);
        });

        Scene scene  = new Scene(lineChart3,800,600);
        scene.getStylesheets().add("stylesheet.css");
        stage.setScene(scene);
        //Saving the third scene as image
        WritableImage image = scene.snapshot(null);
        File file = new File("src/main/resources/output/figure3_frequencies.png");
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", file);
        System.out.println("Image3 Saved");
    }


}

