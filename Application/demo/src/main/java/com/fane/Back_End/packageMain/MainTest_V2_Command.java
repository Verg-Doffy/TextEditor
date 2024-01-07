package com.fane.Back_End.packageMain;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;

/**
 * The MainTest_V2_Command class serves as a test program for evaluating the functionality
 * of the command pattern implementation in the back-end text editing application.
 * It demonstrates the usage of various commands such as insert, changeSelection, copy,
 * cut, paste, delete, start, stop, and replay.
 * 
 * The test scenario includes initializing the engine, recorder, and invoker components,
 * adding commands to the invoker, and performing actions like text insertion, cutting,
 * pasting, copying, and deletion. The program outputs the buffer contents at different
 * stages to verify the effectiveness of the commands.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 1.0
 */

public class MainTest_V2_Command {
    /**
     * The main method that serves as the entry point for the test program.
     *
     * @param args The command-line arguments (unused in this context).
     */
    public static void main(String[] args) {
        // Create the main components

        Engine engine = new EngineImpl();
        Recorder recorder = new Recorder(engine);
        Invoker invoker = new Invoker(recorder);

        // Add commands to the invoker
        invoker.addCommand("insert", new InsertCommand(engine, invoker, recorder));
        invoker.addCommand("changeSelection", new ChangeSelectionCommand(engine, invoker, recorder));
        invoker.addCommand("copy", new CopyCommand(engine, recorder));
        invoker.addCommand("cut", new CutCommand(engine, recorder));
        invoker.addCommand("paste", new PasteCommand(engine, recorder));
        invoker.addCommand("delete", new DeleteCommand(engine, recorder));
        invoker.addCommand("start", new StartCommand(recorder));
        invoker.addCommand("stop", new StopCommand(recorder));
        invoker.addCommand("replay", new ReplayCommand(recorder));

        // Insérer un texte initial
        invoker.setText("Bonjour, Comment ça va?");
        invoker.executeCommand("insert");
        System.out.println(engine.getBufferContents()); // Devrait afficher "Bonjour, Comment ça va?

        // Couper du texte
        invoker.setBeginIndex(9);
        invoker.setEndIndex(16);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("cut");

        // Vérification
        System.out.println(engine.getBufferContents()); // Devrait afficher "Bonjour, ça va?"

        // Coller le texte précédemment coupé à la fin
        invoker.setBeginIndex(engine.getBufferContents().length());
        invoker.setEndIndex(engine.getBufferContents().length());
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("paste");

        // Vérification
        System.out.println(engine.getBufferContents()); // Devrait afficher "Bonjour, ça va?Comment"

        // Test pour CopyCommand
        invoker.setBeginIndex(0);
        invoker.setEndIndex(7);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("copy");
        System.out.println(engine.getClipboardContents()); // Devrait afficher "Bonjour"

        // Test pour PasteCommand
        invoker.setBeginIndex(engine.getBufferContents().length());
        invoker.setEndIndex(engine.getBufferContents().length());
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("paste");
        System.out.println(engine.getBufferContents()); // Devrait afficher "Bonjour, ça va?CommentBonjour"

        // Test pour DeleteCommand
        invoker.setBeginIndex(16);
        invoker.setEndIndex(engine.getBufferContents().length());
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("delete");
        System.out.println(engine.getBufferContents()); // Devrait afficher "Bonjour, ça va?"

    }
}

