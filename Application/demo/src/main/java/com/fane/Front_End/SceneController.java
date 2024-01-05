
package com.fane.Front_End;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Node;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


import java.io.IOException;
import java.util.Optional;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;

public class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Image appIcon = new Image(getClass().getResourceAsStream("/img/logo_app.png"));

    private Engine engine = new EngineImpl();
    private Recorder recorder = new Recorder(engine);
    private Invoker invoker = new Invoker(recorder);

    @FXML
    private TextArea textArea;

    public void switchToEditFile(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/Graphic.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/Main_Menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToCredits(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/Credits.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void addCommand(){
        invoker.addCommand("insert", new InsertCommand(engine, invoker, recorder));
        invoker.addCommand("changeSelection", new ChangeSelectionCommand(engine, invoker, recorder));
        invoker.addCommand("copy", new CopyCommand(engine, recorder));
        invoker.addCommand("cut", new CutCommand(engine, recorder));
        invoker.addCommand("paste", new PasteCommand(engine, recorder));
        invoker.addCommand("delete", new DeleteCommand(engine, recorder));
        invoker.addCommand("start", new StartCommand(recorder));
        invoker.addCommand("stop", new StopCommand(recorder));
        invoker.addCommand("replay", new ReplayCommand(recorder));
    }

    private boolean isValidInput(String value1, String value2) {
        try {
            int intValue1 = Integer.parseInt(value1);
            int intValue2 = Integer.parseInt(value2);
            return intValue1 <= intValue2;
        } catch (NumberFormatException e) {
            return false; // Gérer les erreurs de conversion
        }
    }

    private String showInputDialog(String prompt) {
        // Créer une boîte de dialogue pour obtenir la valeur de l'utilisateur
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Action request");
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);

        // Ajoutez le bouton "OK" à la boîte de dialogue
        dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK);

        // Ajouter le logo de l'application à la boîte de dialogue
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(appIcon);

        // Obtenir le résultat de la boîte de dialogue
        Optional<String> result = dialog.showAndWait();

        // Retourner la valeur si présente, sinon une chaîne vide
        return result.orElse("");
    }

    private void showAlert(String title, String content) {
        // Afficher une boîte de dialogue d'alerte
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        // Ajouter le bouton "OK" à la boîte de dialogue
        alert.getButtonTypes().setAll(ButtonType.OK);

        // Ajouter le logo de l'application à la boîte de dialogue
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(appIcon);

        alert.showAndWait();
    }

    public void updateTextField(String text) {
        textArea.setText(text);
        textArea.setWrapText(true); // Permet le saut de ligne automatique
        textArea.setPrefRowCount(10); // Définir le nombre de lignes préférées
    }

    public String[] SelectAction( String messageDialog1, String messageDialog2, String messageError) {
        String value1, value2;

        do {
            value1 = showInputDialog(messageDialog1);
            value2 = showInputDialog(messageDialog2);

            // Vérifier la validité des entrées
            if (!isValidInput(value1, value2)) {
                showAlert("Error", messageError);
            }
        } while (!isValidInput(value1, value2));

        return new String[]{value1, value2};
    }
    
    public void InsertAction(ActionEvent event) {

        addCommand();

        String[] valeursIndex = SelectAction("From which index do you want to start to insert the text? (Enter a number)", "From which index do you want to finish to insert the text? (Enter a number)", "The first value of the insert must be less than or equal to the second value of the insert.");
        String value1 = valeursIndex[0];
        String value2 = valeursIndex[1];

        String valueText = showInputDialog("Enter the text to insert");

        // Traiter les valeurs
        try {
            int intValue1 = Integer.parseInt(value1);
            int intValue2 = Integer.parseInt(value2);
            
            invoker.setBeginIndex(intValue1);
            invoker.setEndIndex(intValue2);
            invoker.executeCommand("changeSelection");
            invoker.setText(valueText);
            invoker.executeCommand("insert");

            System.out.println("--> Case of Insert");
            System.out.println("Clipboard Content : " + engine.getClipboardContents()); 
            System.out.println("Buffer Content    : " + engine.getBufferContents()); 

            updateTextField(engine.getBufferContents());

        } catch (NumberFormatException e) {
            // Gérer les erreurs de conversion
            e.printStackTrace();
        }
    }

    public void CopyAction(ActionEvent event) {

        addCommand();

        String[] valeursIndex = SelectAction("From which index do you want to start to copy the text? (Enter a number)", "From which index do you want to finish to copy the text? (Enter a number)", "The first value of the copy must be less than or equal to the second value of the copy.");
        String value1 = valeursIndex[0];
        String value2 = valeursIndex[1];

        // Traiter les valeurs
        try {
            int intValue1 = Integer.parseInt(value1);
            int intValue2 = Integer.parseInt(value2);
            
            invoker.setBeginIndex(intValue1);
            invoker.setEndIndex(intValue2);
            invoker.executeCommand("changeSelection");
            invoker.executeCommand("copy");

            System.out.println("--> Case of Copy");
            System.out.println("Clipboard Content : " + engine.getClipboardContents()); 
            System.out.println("Buffer Content    : " + engine.getBufferContents()); 

            updateTextField(engine.getBufferContents());

        } catch (NumberFormatException e) {
            // Gérer les erreurs de conversion
            e.printStackTrace();
        }
    }

    public void PasteAction(ActionEvent event) {

        addCommand();

        String[] valeursIndex = SelectAction("From which index do you want to start to past the text? (Enter a number)", "From which index do you want to finish to past the text? (Enter a number)", "The first value of the past must be less than or equal to the second value of the past.");
        String value1 = valeursIndex[0];
        String value2 = valeursIndex[1];

        // Traiter les valeurs
        try {
            int intValue1 = Integer.parseInt(value1);
            int intValue2 = Integer.parseInt(value2);
            
            invoker.setBeginIndex(intValue1);
            invoker.setEndIndex(intValue2);
            invoker.executeCommand("changeSelection");
            invoker.executeCommand("paste");

            System.out.println("--> Case of Paste");
            System.out.println("Clipboard Content : " + engine.getClipboardContents()); 
            System.out.println("Buffer Content    : " + engine.getBufferContents()); 
            
            updateTextField(engine.getBufferContents());

        } catch (NumberFormatException e) {
            // Gérer les erreurs de conversion
            e.printStackTrace();
        }
    }

    public void CutAction(ActionEvent event) {

        addCommand();

        String[] valeursIndex = SelectAction("From which index do you want to start to cut the text? (Enter a number)", "From which index do you want to finish to cut the text? (Enter a number)", "The first value of the cut must be less than or equal to the second value of the cut.");
        String value1 = valeursIndex[0];
        String value2 = valeursIndex[1];

        // Traiter les valeurs
        try {
            int intValue1 = Integer.parseInt(value1);
            int intValue2 = Integer.parseInt(value2);
            
            invoker.setBeginIndex(intValue1);
            invoker.setEndIndex(intValue2);
            invoker.executeCommand("changeSelection");
            invoker.executeCommand("cut");

            System.out.println("--> Case of Cut");
            System.out.println("Clipboard Content : " + engine.getClipboardContents()); 
            System.out.println("Buffer Content    : " + engine.getBufferContents()); 
            
            updateTextField(engine.getBufferContents());

        } catch (NumberFormatException e) {
            // Gérer les erreurs de conversion
            e.printStackTrace();
        }
    }

    public void DeleteAction(ActionEvent event) {

        addCommand();

        String[] valeursIndex = SelectAction("From which index do you want to start to delete the text? (Enter a number)", "From which index do you want to finish to delete the text? (Enter a number)", "The first value of the delete must be less than or equal to the second value of the delete.");
        String value1 = valeursIndex[0];
        String value2 = valeursIndex[1];

        // Traiter les valeurs
        try {
            int intValue1 = Integer.parseInt(value1);
            int intValue2 = Integer.parseInt(value2);
            
            invoker.setBeginIndex(intValue1);
            invoker.setEndIndex(intValue2);
            invoker.executeCommand("changeSelection");
            invoker.executeCommand("delete");

            System.out.println("--> Case of Delete");
            System.out.println("Clipboard Content : " + engine.getClipboardContents()); 
            System.out.println("Buffer Content    : " + engine.getBufferContents()); 
            
            updateTextField(engine.getBufferContents());

        } catch (NumberFormatException e) {
            // Gérer les erreurs de conversion
            e.printStackTrace();
        }
    }

    private void ConfirmationPopup(String confirmation) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("L'exécution a réussi !");
        alert.setContentText(confirmation);

        // Ajouter le logo de l'application à la boîte de dialogue
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(appIcon);
    
        // Ajoutez le bouton "OK" à la boîte de dialogue
        alert.getButtonTypes().setAll(ButtonType.OK);

        // Attendre la confirmation de l'utilisateur
        alert.showAndWait();
    }
    
    public void StartAction(ActionEvent event) {

        addCommand();

        // Traiter les valeurs
        try {
            invoker.executeCommand("start");
            ConfirmationPopup("The editor starts recording your actions");

        } catch (NumberFormatException e) {
            // Gérer les erreurs de conversion
            e.printStackTrace();
        }
    }

    public void StopAction(ActionEvent event) {

        addCommand();

        // Traiter les valeurs
        try {
            invoker.executeCommand("stop");
            ConfirmationPopup("The editor stops recording your actions");

        } catch (NumberFormatException e) {
            // Gérer les erreurs de conversion
            e.printStackTrace();
        }
    }

    public void ReplyAction(ActionEvent event) {

        addCommand();

        // Traiter les valeurs
        try {
            invoker.executeCommand("reply");
            ConfirmationPopup("The save is replayed");
            updateTextField(engine.getBufferContents());

        } catch (NumberFormatException e) {
            // Gérer les erreurs de conversion
            e.printStackTrace();
        }
    }

}
