package org.vicventures;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Map;


public class DataVisualisation extends Application {

    @Override public void start(Stage stage) throws IOException {
        //TODO: Divide different visualisations into own methods
        stage.setTitle("Fordeling af arkiverede websites");
        //defining the axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("År");

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

        // TODO: Make linegraph not show empty nodes from odder.dk
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().addAll(oddernettetSeries, odderSeries);

        stage.setScene(scene);

        //Saving the scene as image
        WritableImage image = scene.snapshot(null);
        File file = new File("src/main/resources/output/fordelingAfSnapshots.png");
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", file);
        System.out.println("Image Saved");

        // Showing the scene on screen
        //stage.show();

        // Second visualisation

        final CategoryAxis xAxis2 = new CategoryAxis();
        final NumberAxis yAxis2 = new NumberAxis();

        xAxis2.setLabel("År");

        //creating the chart
        final LineChart<String,Number> lineChart2 =
                new LineChart<String,Number>(xAxis2,yAxis2);

        lineChart2.setTitle("Figur 2: Fordeling af filtyper over tid på www.oddernettet.dk");

        Map<String, Map<String, Integer>> values = DataLoader.getAllFiletypesPerYear();
        // go through the parties and add them to the chart
        values.keySet().stream().forEach(filetype -> {
            // a different data set for every party
            XYChart.Series data = new XYChart.Series();
            data.setName(filetype);

            // add the party's support numbers to the data set
            values.get(filetype).entrySet().stream().forEach(pair -> {
                data.getData().add(new XYChart.Data(pair.getKey(), pair.getValue()));
            });

            // and add the data set to the chart
            lineChart2.getData().add(data);
        });

        Scene scene2  = new Scene(lineChart2,800,600);
        stage.setScene(scene2);
        //Saving the third scene as image
        WritableImage image2 = scene2.snapshot(null);
        File file2 = new File("src/main/resources/output/figure2.png");
        ImageIO.write(SwingFXUtils.fromFXImage(image2, null), "PNG", file2);
        System.out.println("Image Saved");
        // TODO: Create version of figure 2, that is made without HTML group.
    }
    public static void main(String[] args) {
        launch(args);
    }


}

