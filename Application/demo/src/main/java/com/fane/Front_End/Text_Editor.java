package com.fane.Front_End;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The {@code Text_Editor} class represents the main entry point for the Text Editor application.
 * It extends the JavaFX {@link Application} class and initializes the graphical user interface (GUI).
 *
 * This class is part of the Front_End package and is responsible for launching the Text Editor application.
 *
 * The Text Editor application provides a graphical user interface for editing and manipulating text.
 * It initializes the main menu GUI using JavaFX and sets up the application window with specified properties.
 *
 * @author Mohamed AL AFTAN & Djakaridja FANE
 * @version 1.0
 * @see javafx.application.Application
 * @see javafx.fxml.FXMLLoader
 * @see javafx.scene.Parent
 * @see javafx.scene.Scene
 * @see javafx.scene.image.Image
 * @see javafx.stage.Stage
 */
public class Text_Editor extends Application {

    /**
     * The main entry point for the Text Editor application.
     *
     * @param primaryStage The primary stage for the application window.
     */
    @Override
    public void start(Stage primaryStage){
        try{
            Parent principal = FXMLLoader.load(getClass().getResource("/fxml/Main_Menu.fxml"));
            Scene scene = new Scene(principal);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Text-Editor");
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/logo_app.png")));

            primaryStage.setResizable(false);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method that launches the Text Editor application.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
