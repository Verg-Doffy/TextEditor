package com.fane.packageV1; 
import com.fane.packageV0.*;

/**
 * The {@code MainTest_V1} class is a test program demonstrating the usage of commands
 * with the Engine, Invoker, and various command implementations.
 * 
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 1.0
 */

public class MainTest_V1 {
        /**
         * The main method serves as the entry point for the test program.
         *
         * @param args The command-line arguments (unused in this context).
         */
        public static void main(String[] args) {
                // Create the engine and invoker                
                Engine engine = new EngineImpl();
                Invoker invoker = new Invoker();

                // Add some commands to the invoker
                invoker.addCommand("cut", new CutCommand(engine));
                invoker.addCommand("paste", new PasteCommand(engine));
                invoker.addCommand("copy", new CopyCommand(engine));
                invoker.addCommand("insert", new InsertCommand(engine, invoker));
                invoker.addCommand("delete", new DeleteCommand(engine));
                invoker.addCommand("changeSelection", new ChangeSelectionCommand(engine, invoker));

                // Simulate some operations

                // Insert initial text
                invoker.setText("Bonjour, Comment ça va?");
                invoker.executeCommand("insert");
                System.out.println(engine.getBufferContents()); // Should print "Bonjour, Comment ça va?"

                // Cut text
                invoker.setBeginIndex(9);
                invoker.setEndIndex(16);
                invoker.executeCommand("changeSelection");
                invoker.executeCommand("cut");

                // Verification
                System.out.println(engine.getBufferContents()); // Should print "Bonjour, ça va?"

                // Paste the previously cut text at the end
                invoker.setBeginIndex(engine.getBufferContents().length());
                invoker.setEndIndex(engine.getBufferContents().length());
                invoker.executeCommand("changeSelection");
                invoker.executeCommand("paste");

                // Verification
                System.out.println(engine.getBufferContents()); // Should print "Bonjour, ça va?Comment"

                // Test for CopyCommand
                invoker.setBeginIndex(0);
                invoker.setEndIndex(7);
                invoker.executeCommand("changeSelection");
                invoker.executeCommand("copy");
                System.out.println(engine.getClipboardContents()); // Should print "Bonjour"

                // Test for PasteCommand
                invoker.setBeginIndex(engine.getBufferContents().length());
                invoker.setEndIndex(engine.getBufferContents().length());
                invoker.executeCommand("changeSelection");
                invoker.executeCommand("paste");
                System.out.println(engine.getBufferContents()); // Should print "Bonjour, ça va?CommentBonjour"

                // Test for DeleteCommand
                invoker.setBeginIndex(16);
                invoker.setEndIndex(engine.getBufferContents().length());
                invoker.executeCommand("changeSelection");
                invoker.executeCommand("delete");
                System.out.println(engine.getBufferContents()); // Should print "Bonjour, ça va?"

        }
}
