package com.fane;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Text_Editor extends Application {
    
	private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        
        AnchorPane principal = FXMLLoader.load(getClass().getResource("/fxml/Main_Menu.fxml"));
        this.scene = new Scene(principal);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Text-Editor");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/logo_app.png")));

        primaryStage.setResizable(false);
        primaryStage.show();

    }

    
    @FXML
    private void switchToEditFile(ActionEvent event) {
        // Appeler la méthode switchScene avec le stage, le chemin du nouveau fichier FXML et le titre
        SceneSwitcher.switchScene((Stage) scene.getWindow(), "/fxml/Graphics.fxml");
    }
    

    public static void main(String[] args) {
        Application.launch(args);
    }

}