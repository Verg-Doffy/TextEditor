package com.fane.Front_End;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Text_Editor extends Application {

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

    public static void main(String[] args) {
        Application.launch(args);
    }

}