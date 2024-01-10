
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

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


import java.io.IOException;
import java.util.Optional;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;
import com.fane.Back_End.packageV3.RedoCommand;
import com.fane.Back_End.packageV3.UndoCommand;

/**
 * The {@code SceneController} class manages the interaction between the graphical user interface (GUI) components
 * and the underlying logic of the Text Editor application. It handles various actions triggered by user interactions
 * and updates the state of the application accordingly.
 *
 * This class is part of the Front_End package and serves as the controller for the GUI scenes.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 3.0
 */

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

    /**
     * Switches the current scene to the "Edit File" scene.
     * Loads the "/fxml/Graphic.fxml" file and sets it as the new scene.
     *
     * @param event The ActionEvent that triggered the method.
     * @throws IOException If an I/O error occurs during loading the new scene.
     */
    public void switchToEditFile(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/Graphic.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches the current scene to the "Main Menu" scene.
     * Loads the "/fxml/Main_Menu.fxml" file and sets it as the new scene.
     *
     * @param event The ActionEvent that triggered the method.
     * @throws IOException If an I/O error occurs during loading the new scene.
     */
    public void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/Main_Menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches the current scene to the "Credits" scene.
     * Loads the "/fxml/Credits.fxml" file and sets it as the new scene.
     *
     * @param event The ActionEvent that triggered the method.
     * @throws IOException If an I/O error occurs during loading the new scene.
     */
    public void switchToCredits(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/Credits.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Adds predefined commands to the invoker for handling text editing operations.
     * The commands include "insert", "changeSelection", "copy", "cut", "paste", "delete",
     * "start", "stop", and "replay," each associated with a specific command implementation.
     * 
     * The commands are added to the invoker, which is responsible for executing them
     * based on user interactions or programmatic actions.
     * 
     * Note: Make sure to call this method before attempting to execute any of the added commands.
     */
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
        invoker.addCommand("undo", new UndoCommand(recorder));
        invoker.addCommand("redo", new RedoCommand(recorder));
    }

    /**
     * Checks the validity of input values by attempting to parse them as integers
     * and ensuring the first value is less than or equal to the second value.
     *
     * @param value1 The first input value as a string.
     * @param value2 The second input value as a string.
     * @return {@code true} if the values are valid (parseable as integers and first <= second),
     *         {@code false} otherwise.
     */
    private boolean isValidInput(String value1, String value2) {
        try {
            int intValue1 = Integer.parseInt(value1);
            int intValue2 = Integer.parseInt(value2);
            return intValue1 <= intValue2;
        } catch (NumberFormatException e) {
            return false; 
        }
    }

    private String showInputDialog(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Action request");
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);

        // Add the "OK" button to the dialog box
        dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK);

        // Add application logo to dialog box
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(appIcon);

        // Get dialog result
        Optional<String> result = dialog.showAndWait();

        // Return value if present, otherwise an empty string
        return result.orElse("");
    }

    /**
     * Displays a dialog box prompting the user for input and returns the entered value.
     *
     * @param prompt The message displayed to the user in the dialog box.
     * @return The value entered by the user or an empty string if no value is entered.
     */
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

    /**
     * Updates the content of the associated TextArea with the provided text.
     *
     * @param text The text to set in the TextArea.
     */
    public void updateTextField(String text) {
        // Set the content of the TextArea
        textArea.setText(text);
        
        // Enable automatic line wrapping
        textArea.setWrapText(true);
        
        // Set the preferred number of rows in the TextArea
        textArea.setPrefRowCount(10);
    }

    /**
     * Displays input dialogs to obtain two numerical values from the user, ensuring the validity of the inputs.
     *
     * @param messageDialog1 The prompt for the first value.
     * @param messageDialog2 The prompt for the second value.
     * @param messageError   The error message displayed if the inputs are invalid.
     * @return An array containing the validated numerical input values.
     */
    public String[] SelectAction(String messageDialog1, String messageDialog2, String messageError) {
        String value1, value2;

        do {
            // Show input dialogs to obtain values from the user
            value1 = showInputDialog(messageDialog1);
            value2 = showInputDialog(messageDialog2);

            // Check the validity of the inputs
            if (!isValidInput(value1, value2)) {
                // Show an error alert if inputs are invalid
                showAlert("Error", messageError);
            }
        } while (!isValidInput(value1, value2));

        // Return the validated numerical input values as an array
        return new String[]{value1, value2};
    }

    
    /**
     * Executes the insert action, prompting the user for input and updating the editor accordingly.
     *
     * The method adds the insert command to the invoker, obtains the start and end indices for insertion,
     * and the text to insert from the user through input dialogs. It then executes the insert command
     * on the invoker and updates the editor's state.
     *
     * @param event The ActionEvent triggering the insert action.
     */
    public void InsertAction(ActionEvent event) {

        // Add the insert command to the invoker
        addCommand();

        int intValue1 = 0;
        int intValue2 = 0;

        String value1 = "0";
        String value2 = "0";

        if(engine.getBufferContents().length() != 0) {
            // Obtain user input for start and end indices
            String[] valeursIndex = SelectAction("From which index do you want to start to insert the text? (Enter a number)", "From which index do you want to finish to insert the text? (Enter a number)", "The first value of the insert must be less than or equal to the second value of the insert.");
            value1 = valeursIndex[0];
            value2 = valeursIndex[1];
        }

        // Obtain user input for the text to insert
        String valueText = showInputDialog("Enter the text to insert");

        try {
            // Convert input values to integers
            intValue1 = Integer.parseInt(value1);
            intValue2 = Integer.parseInt(value2);

            // Set the begin and end indices and execute changeSelection command
            invoker.setBeginIndex(intValue1);
            invoker.setEndIndex(intValue2);
            invoker.executeCommand("changeSelection");

            // Set the text to insert and execute insert command
            invoker.setText(valueText);
            invoker.executeCommand("insert");

            // Display information about the insert operation
            System.out.println("--> Case of Insert");
            System.out.println("Clipboard Content : " + engine.getClipboardContents());
            System.out.println("Buffer Content    : " + engine.getBufferContents());

            // Update the editor's text field with the buffer contents
            updateTextField(engine.getBufferContents());

        } catch (NumberFormatException e) {
            // Handle conversion errors
            e.printStackTrace();
        }
    }


    /**
     * Executes the copy action, prompting the user for input and updating the editor accordingly.
     *
     * The method adds the copy command to the invoker, obtains the start and end indices for copying,
     * and then executes the changeSelection and copy commands on the invoker. It displays information
     * about the copy operation and updates the editor's state.
     *
     * @param event The ActionEvent triggering the copy action.
     */
    public void CopyAction(ActionEvent event) {

        // Add the copy command to the invoker
        addCommand();

        int intValue1 = 0;
        int intValue2 = 0;

        String value1 = "0";
        String value2 = "0";

        if(engine.getBufferContents().length() != 0) {
            // Obtain user input for start and end indices
            String[] valeursIndex = SelectAction("From which index do you want to start to copy the text? (Enter a number)", "From which index do you want to finish to insert the text? (Enter a number)", "The first value of the insert must be less than or equal to the second value of the insert.");
            value1 = valeursIndex[0];
            value2 = valeursIndex[1];
        }

        try {
            // Convert input values to integers
            intValue1 = Integer.parseInt(value1);
            intValue2 = Integer.parseInt(value2);

            // Set the begin and end indices and execute changeSelection command
            invoker.setBeginIndex(intValue1);
            invoker.setEndIndex(intValue2);
            invoker.executeCommand("changeSelection");

            // Execute the copy command
            invoker.executeCommand("copy");

            // Display information about the copy operation
            System.out.println("--> Case of Copy");
            System.out.println("Clipboard Content : " + engine.getClipboardContents());
            System.out.println("Buffer Content    : " + engine.getBufferContents());

            // Update the editor's text field with the buffer contents
            updateTextField(engine.getBufferContents());

        } catch (NumberFormatException e) {
            // Handle conversion errors
            e.printStackTrace();
        }
    }


    /**
     * Executes the paste action, prompting the user for input and updating the editor accordingly.
     *
     * The method adds the paste command to the invoker, obtains the start and end indices for pasting,
     * and then executes the changeSelection and paste commands on the invoker. It displays information
     * about the paste operation and updates the editor's state.
     *
     * @param event The ActionEvent triggering the paste action.
     */
    public void PasteAction(ActionEvent event) {

        // Add the paste command to the invoker
        addCommand();

        int intValue1 = 0;
        int intValue2 = 0;

        String value1 = "0";
        String value2 = "0";

        if(engine.getBufferContents().length() != 0) {
            // Obtain user input for start and end indices
            String[] valeursIndex = SelectAction("From which index do you want to start to paste the text? (Enter a number)", "From which index do you want to finish to insert the text? (Enter a number)", "The first value of the insert must be less than or equal to the second value of the insert.");
            value1 = valeursIndex[0];
            value2 = valeursIndex[1];
        }

        try {
            // Convert input values to integers
            intValue1 = Integer.parseInt(value1);
            intValue2 = Integer.parseInt(value2);

            // Set the begin and end indices and execute changeSelection command
            invoker.setBeginIndex(intValue1);
            invoker.setEndIndex(intValue2);
            invoker.executeCommand("changeSelection");

            // Execute the paste command
            invoker.executeCommand("paste");

            // Display information about the paste operation
            System.out.println("--> Case of Paste");
            System.out.println("Clipboard Content : " + engine.getClipboardContents());
            System.out.println("Buffer Content    : " + engine.getBufferContents());

            // Update the editor's text field with the buffer contents
            updateTextField(engine.getBufferContents());

        } catch (NumberFormatException e) {
            // Handle conversion errors
            e.printStackTrace();
        }
    }

    /**
     * Executes the cut action, prompting the user for input and updating the editor accordingly.
     *
     * The method adds the cut command to the invoker, obtains the start and end indices for cutting,
     * and then executes the changeSelection and cut commands on the invoker. It displays information
     * about the cut operation and updates the editor's state.
     *
     * @param event The ActionEvent triggering the cut action.
     */
    public void CutAction(ActionEvent event) {

        // Add the cut command to the invoker
        addCommand();

        int intValue1 = 0;
        int intValue2 = 0;

        String value1 = "0";
        String value2 = "0";

        if(engine.getBufferContents().length() != 0) {
            // Obtain user input for start and end indices
            String[] valeursIndex = SelectAction("From which index do you want to start to cut the text? (Enter a number)", "From which index do you want to finish to insert the text? (Enter a number)", "The first value of the insert must be less than or equal to the second value of the insert.");
            value1 = valeursIndex[0];
            value2 = valeursIndex[1];
        }

        try {
            // Convert input values to integers
            intValue1 = Integer.parseInt(value1);
            intValue2 = Integer.parseInt(value2);

            // Set the begin and end indices and execute changeSelection command
            invoker.setBeginIndex(intValue1);
            invoker.setEndIndex(intValue2);
            invoker.executeCommand("changeSelection");

            // Execute the cut command
            invoker.executeCommand("cut");

            // Display information about the cut operation
            System.out.println("--> Case of Cut");
            System.out.println("Clipboard Content : " + engine.getClipboardContents());
            System.out.println("Buffer Content    : " + engine.getBufferContents());

            // Update the editor's text field with the buffer contents
            updateTextField(engine.getBufferContents());

        } catch (NumberFormatException e) {
            // Handle conversion errors
            e.printStackTrace();
        }
    }


    /**
     * Executes the delete action, prompting the user for input and updating the editor accordingly.
     *
     * The method adds the delete command to the invoker, obtains the start and end indices for deleting,
     * and then executes the changeSelection and delete commands on the invoker. It displays information
     * about the delete operation and updates the editor's state.
     *
     * @param event The ActionEvent triggering the delete action.
     */
    public void DeleteAction(ActionEvent event) {

        // Add the delete command to the invoker
        addCommand();

        int intValue1 = 0;
        int intValue2 = 0;

        String value1 = "0";
        String value2 = "0";

        if(engine.getBufferContents().length() != 0) {
            // Obtain user input for start and end indices
            String[] valeursIndex = SelectAction("From which index do you want to start to delete the text? (Enter a number)", "From which index do you want to finish to insert the text? (Enter a number)", "The first value of the insert must be less than or equal to the second value of the insert.");
            value1 = valeursIndex[0];
            value2 = valeursIndex[1];
        }

        try {
            // Convert input values to integers
            intValue1 = Integer.parseInt(value1);
            intValue2 = Integer.parseInt(value2);

            // Set the begin and end indices and execute changeSelection command
            invoker.setBeginIndex(intValue1);
            invoker.setEndIndex(intValue2);
            invoker.executeCommand("changeSelection");

            // Execute the delete command
            invoker.executeCommand("delete");

            // Display information about the delete operation
            System.out.println("--> Case of Delete");
            System.out.println("Clipboard Content : " + engine.getClipboardContents());
            System.out.println("Buffer Content    : " + engine.getBufferContents());

            // Update the editor's text field with the buffer contents
            updateTextField(engine.getBufferContents());

        } catch (NumberFormatException e) {
            // Handle conversion errors
            e.printStackTrace();
        }
    }

    /**
     * Displays a confirmation popup with the given message.
     *
     * The method creates an Alert with CONFIRMATION type, sets the title, header text,
     * and content text using the provided confirmation message. It adds the application
     * logo to the dialog box, sets the OK button, and waits for user confirmation.
     *
     * @param confirmation The message to display in the confirmation popup.
     */
    private void ConfirmationPopup(String confirmation) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Execution succeeded!");
        alert.setContentText(confirmation);

        // Add the application logo to the dialog box
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(appIcon);

        // Set the OK button for the dialog
        alert.getButtonTypes().setAll(ButtonType.OK);

        // Wait for user confirmation
        alert.showAndWait();
    }

    
    /**
     * Initiates the recording of user actions in the editor.
     *
     * The method adds the start command to the invoker and executes it. It displays a confirmation popup
     * indicating that the editor has started recording user actions.
     *
     * @param event The ActionEvent triggering the start action.
     */
    public void StartAction(ActionEvent event) {

        // Add the start command to the invoker
        addCommand();

        try {
            if(recorder.isRecording() == false) {
                // Execute the start command
                invoker.executeCommand("start");
                // Display a confirmation popup for the user
                ConfirmationPopup("The editor starts recording your actions");
            }
            else{
                // Display a alrt popup for the user
                showAlert("Error", "The editor has already started saving the actions");
            }

        } catch (NumberFormatException e) {
            // Handle conversion errors
            e.printStackTrace();
        }
    }

    /**
     * Stops the recording of user actions in the editor.
     *
     * The method adds the stop command to the invoker and executes it. It displays a confirmation popup
     * indicating that the editor has stopped recording user actions.
     *
     * @param event The ActionEvent triggering the stop action.
     */
    public void StopAction(ActionEvent event) {

        // Add the stop command to the invoker
        addCommand();

        try {
            if(recorder.isRecording() == true) {
                // Execute the stop command
                invoker.executeCommand("stop");
                // Display a confirmation popup for the user
                ConfirmationPopup("The editor stops recording your actions");
            }
            else{
                // Display a alert popup for the user
                showAlert("Error", "The editor doesn't recording your actions");
            }

        } catch (NumberFormatException e) {
            // Handle conversion errors
            e.printStackTrace();
        }
    }

    /**
     * Replays the recorded actions saved in the editor.
     *
     * The method adds the replay command to the invoker and executes it. It displays a confirmation popup
     * indicating that the saved actions are being replayed. Additionally, it updates the editor's text field
     * with the current buffer contents after replaying.
     *
     * @param event The ActionEvent triggering the replay action.
     */
    public void ReplayAction(ActionEvent event) {

        // Add the replay command to the invoker
        addCommand();

        try {
            // Execute the replay command
            invoker.executeCommand("replay");

            // Display a confirmation popup for the user
            ConfirmationPopup("The save is replayed");

            // Update the editor's text field with the buffer contents
            updateTextField(engine.getBufferContents());

        } catch (NumberFormatException e) {
            // Handle conversion errors
            e.printStackTrace();
        }
    }

    /**
     * Handles the action triggered by the undo button. Adds the replay command to the invoker,
     * executes the "undo" command, and updates the editor's text field with the current buffer contents.
     *
     * @param event The ActionEvent triggered by the undo button.
     */
    public void UndoAction(ActionEvent event) {

        // Add the undo command to the invoker
        addCommand();

        try {
            // Execute the undo command (one for the selection command, and the other for the action command)
            invoker.executeCommand("undo");
            invoker.executeCommand("undo");

            // Update the editor's text field with the buffer contents
            updateTextField(engine.getBufferContents());

        } catch (NumberFormatException e) {
            // Handle conversion errors
            e.printStackTrace();
        }
    }

    /**
     * Handles the action triggered by the redo button. Adds the replay command to the invoker,
     * executes the "redo" command, and updates the editor's text field with the current buffer contents.
     *
     * @param event The ActionEvent triggered by the redo button.
     */
    public void RedoAction(ActionEvent event) {

        // Add the redo command to the invoker
        addCommand();

        try {
            // Execute the redo command twice (one for the selection command, and the other for the action command)
            invoker.executeCommand("redo");
            invoker.executeCommand("redo");

            // Update the editor's text field with the buffer contents
            updateTextField(engine.getBufferContents());

        } catch (NumberFormatException e) {
            // Handle conversion errors
            e.printStackTrace();
        }
    }
}
