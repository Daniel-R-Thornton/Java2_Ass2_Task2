package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HelloApplication extends Application {

    public ArrayList<Circle> Points;
    @Override
    public void start(Stage stage)  {
        Pane pane = new Pane();

        Points = new ArrayList<Circle>();

        Scene scene = new Scene(pane, 500, 500);
        stage.setScene(scene);

        //ad label to pane
        Label label = new Label("Hello World");
        label.setFont(new Font("Arial", 20));
        //center align label
        label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        label.setLayoutX(pane.getWidth()/2 );
        label.setLayoutY(pane.getHeight()-100);
        pane.getChildren().add(label);


        // read Points.txt File
        File file = new File("./Points.txt");
        //create scanner to read file
        try {
            Scanner scanner = new Scanner(file);

           for(int i = 0; scanner.hasNext(); i++) {
                String line = scanner.nextLine();
                String[] tokens = line.split(";");
                //get value after X=
                String x = tokens[0].substring(tokens[0].indexOf("=")+1);
                double xd = Double.parseDouble(x);
                //get value after Y=
                String y = tokens[1].substring(tokens[1].indexOf("=")+1);

                double yd = Double.parseDouble(y);
                Points.add(new Circle(xd, yd, 5, Color.BLUE));

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //scan line by line

        //read line by line parsing the x= and y= values
        //create a circle for each point
        for(Circle c : Points) {
            pane.getChildren().add(c);
            //setup clikc event for each circle
            c.setOnMouseClicked(e -> {
                //cahnge the label text to the circle's center x and y values
                label.setText("X: " + c.getCenterX() + " Y: " + c.getCenterY());
            });
            //setup click and drag event for each circle if the circle is dragged
            c.setOnMouseDragged(e -> {
                //move the circle to the new x and y values
                c.setCenterX(e.getX());
                c.setCenterY(e.getY());
            });



        }

        stage.show();
        //hook on exit event to close the application and write the points to the file
        stage.setOnCloseRequest(e -> {
                    try {
                        FileWriter writer = new FileWriter(file);
                        for (Circle c : Points) {
                            writer.write("X=" + c.getCenterX() + ";Y=" + c.getCenterY() + "\n");
                        }
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
        );



    }


    public static void main(String[] args) {
        launch();


    }
}