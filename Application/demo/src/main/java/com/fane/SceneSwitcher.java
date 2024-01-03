
package com.fane;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    public static void switchScene(Stage stage, String resourcePath) {
        try {
            // Charger le nouveau fichier FXML
            Parent nouvellePageParent = FXMLLoader.load(SceneSwitcher.class.getResource(resourcePath));

            // Créer une nouvelle scène avec le contenu de la nouvelle page
            Scene nouvellePageScene = new Scene(nouvellePageParent);

            // Mettre à jour la scène du stage
            stage.setScene(nouvellePageScene);

            // Montrer la nouvelle scène
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
        }
    }
}
