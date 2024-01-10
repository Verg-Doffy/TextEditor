package com.fane.Back_End.packageMain;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;
import com.fane.Back_End.packageV3.*;

/**
 * The {@code MainTest_V3} class serves as a test harness for version 3.0 of the text editing application.
 * It demonstrates the usage of various commands such as insert, changeSelection, copy, cut, paste, delete,
 * start, stop, replay, undo, and redo. The class sets up an engine, a recorder, and an invoker to execute
 * and manage commands.
 * <p>
 * The test scenario involves inserting text into the engine's buffer, performing undo and redo operations,
 * and observing the changes in the buffer, clipboard, and selection. The commands are executed and tested
 * sequentially to ensure the correct behavior of the text editing application.
 * <p>
 * Note: The expected outputs in the comments are provided based on the assumptions about the initial state
 * and the execution of commands. Actual outputs may vary based on the specific implementation details.
 * <p>
 * Usage:
 * - Execute the main method to run the test scenario.
 * - Review the console output to observe the effects of various commands.
 * <p>

 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 3.0
 */

public class MainTest_V3 {
        /**
         * The main method that serves as the entry point for executing the test scenario.
         *
         * @param args The command-line arguments (not used in this context).
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
                invoker.addCommand("undo", new UndoCommand(recorder));
                invoker.addCommand("redo", new RedoCommand(recorder));

                // Step 1: Put something in the buffer before recording starts
                invoker.setText("Merci");
                invoker.setBeginIndex(0);
                invoker.setEndIndex(0);
                invoker.executeCommand("changeSelection");
                invoker.executeCommand("insert");
                System.out.println("Buffer after initial insert: " + engine.getBufferContents()); // expected output :
                                                                                                  // Merci

                invoker.setText(" mon grand");
                invoker.setBeginIndex(5);
                invoker.setEndIndex(5);
                invoker.executeCommand("changeSelection");
                invoker.executeCommand("insert");
                System.out.println("Buffer content after 2nd insertion: " + engine.getBufferContents()); // expected
                                                                                                         // output
                                                                                                         // :
                // Merci Beaucoup

                invoker.setText(" Beaucoup");
                invoker.setBeginIndex(15);
                invoker.setEndIndex(15);
                invoker.executeCommand("changeSelection");
                invoker.executeCommand("insert");
                System.out.println("Buffer content after 3rd insertion: " + engine.getBufferContents()); // expected
                                                                                                         // output

                System.out.println("##################################################");
                invoker.executeCommand("undo");
                invoker.executeCommand("undo");
                System.out.println("Buffer content after 1st undo: " + engine.getBufferContents()); // expected: Merci
                                                                                                    // mon grand

                System.out.println("clipboard content after 1st undo: " + engine.getClipboardContents()); // expected:
                                                                                                          // ""
                System.out.println("Selection after 1st undo: " + engine.getSelection().getBeginIndex() + ", "
                                + engine.getSelection().getEndIndex()); // 15,15

                System.out.println("##################################################");
                invoker.executeCommand("undo");
                invoker.executeCommand("undo");
                System.out.println("Buffer content after 2nd undo: " +
                                engine.getBufferContents()); // expected: Merci
                System.out.println("clipboard content after 2nd undo: " +
                                engine.getClipboardContents()); // expected: ""
                System.out.println("Selection after 2nd undo: " +
                                engine.getSelection().getBeginIndex() + ", "
                                + engine.getSelection().getEndIndex()); // 5, 5

                System.out.println("##################################################");
                invoker.executeCommand("undo");
                invoker.executeCommand("undo");
                System.out.println("Buffer content after 3rd undo: " +
                                engine.getBufferContents()); // expected: ""
                System.out.println("clipboard content after 3rd undo: " +
                                engine.getClipboardContents()); // expected: ""
                System.out.println("Selection after 3rd undo: " +
                                engine.getSelection().getBeginIndex() + ", "
                                + engine.getSelection().getEndIndex()); // 0, 0

                System.out.println("##################################################");
                System.out.println("##################################################");
                invoker.executeCommand("redo");
                invoker.executeCommand("redo");
                System.out.println("Buffer content after 1st redo: " +
                                engine.getBufferContents()); // expected: Merci
                System.out.println("clipboard content after 1st redo: " +
                                engine.getClipboardContents()); // expected: ""
                System.out.println("Selection after 1st redo: " +
                                engine.getSelection().getBeginIndex() + ", "
                                + engine.getSelection().getEndIndex());// 0, 5

                System.out.println("##################################################");
                invoker.executeCommand("redo");
                invoker.executeCommand("redo");
                System.out.println("Buffer content after 2nd redo: " +
                                engine.getBufferContents()); // expected: Merci mon grand
                System.out.println("clipboard content after redo: " +
                                engine.getClipboardContents()); // expected: ""
                System.out.println("Selection after redo: " +
                                engine.getSelection().getBeginIndex() + ", "
                                + engine.getSelection().getEndIndex());// 5, 14

                System.out.println("##################################################");
                invoker.executeCommand("redo");
                invoker.executeCommand("redo");
                System.out.println("Buffer content after 3rd redo: " +
                                engine.getBufferContents()); // expected: Merci mon grand Beaucoup
                System.out.println("clipboard content after redo: " +
                                engine.getClipboardContents()); // expected: ""
                System.out.println("Selection after redo: " +
                                engine.getSelection().getBeginIndex() + ", "
                                + engine.getSelection().getEndIndex());// 15, 24

        }

}
