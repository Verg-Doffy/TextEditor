package com.fane.Back_End.packageMain;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;

/**
 * The {@code MainTest_V1} class serves as a commented-out test program for the functionality
 * of the Back_End module, including the use of {@link EngineImpl},
 * {@link Invoker}, and various command implementations (e.g., {@link CutCommand}, {@link PasteCommand}).
 *
 * This class demonstrates the setup of an engine, invoker, and several commands, followed by simulated
 * operations such as text insertion, cutting, pasting, copying, and deleting.
 *
 * @author Mohamed AL AFTAN  Djakaridja FANE
 * @version 1.0
 */


public class MainTest_V1 {

        /**
         * The main method that serves as a test program for the functionality of the Version 1 of the project.
         *         *
         * @param args The command-line arguments (unused in this context).
         */
        /**
         * public static void main(String[] args) {
         * // Création de l'engine et de l'invocateur
         * Engine engine = new EngineImpl();
         * Invoker invoker = new Invoker();
         * 
         * // Ajout de quelques commandes à l'invocateur
         * invoker.addCommand("cut", new CutCommand(engine));
         * invoker.addCommand("paste", new PasteCommand(engine));
         * invoker.addCommand("copy", new CopyCommand(engine));
         * invoker.addCommand("insert", new InsertCommand(engine, invoker));
         * invoker.addCommand("delete", new DeleteCommand(engine));
         * invoker.addCommand("changeSelection", new ChangeSelectionCommand(engine,
         * invoker));
         * 
         * // Simulons quelques opérations
         * 
         * // Insérer un texte initial
         * invoker.setText("Bonjour, Comment ça va?");
         * invoker.executeCommand("insert");
         * System.out.println(engine.getBufferContents()); // Devrait afficher "Bonjour,
         * Comment ça va?"
         * 
         * // Couper du texte
         * invoker.setBeginIndex(9);
         * invoker.setEndIndex(16);
         * invoker.executeCommand("changeSelection");
         * invoker.executeCommand("cut");
         * 
         * // Vérification
         * System.out.println(engine.getBufferContents()); // Devrait afficher "Bonjour,
         * ça va?"
         * 
         * // Coller le texte précédemment coupé à la fin
         * invoker.setBeginIndex(engine.getBufferContents().length());
         * invoker.setEndIndex(engine.getBufferContents().length());
         * invoker.executeCommand("changeSelection");
         * invoker.executeCommand("paste");
         * 
         * // Vérification
         * System.out.println(engine.getBufferContents()); // Devrait afficher "Bonjour,
         * ça va?Comment"
         * 
         * // Test pour CopyCommand
         * invoker.setBeginIndex(0);
         * invoker.setEndIndex(7);
         * invoker.executeCommand("changeSelection");
         * invoker.executeCommand("copy");
         * System.out.println(engine.getClipboardContents()); // Devrait afficher
         * "Bonjour"
         * 
         * // Test pour PasteCommand
         * invoker.setBeginIndex(engine.getBufferContents().length());
         * invoker.setEndIndex(engine.getBufferContents().length());
         * invoker.executeCommand("changeSelection");
         * invoker.executeCommand("paste");
         * System.out.println(engine.getBufferContents()); // Devrait afficher "Bonjour,
         * ça va?CommentBonjour"
         * 
         * // Test pour DeleteCommand
         * invoker.setBeginIndex(16);
         * invoker.setEndIndex(engine.getBufferContents().length());
         * invoker.executeCommand("changeSelection");
         * invoker.executeCommand("delete");
         * System.out.println(engine.getBufferContents()); // Devrait afficher "Bonjour,
         * ça va?"
         * 
         * }
         */
}
