package com.fane.Back_End.packageMain;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;

/**
 * The MainTest_V2_CopyCommand class serves as a test program for evaluating the functionality
 * of the CopyCommand in the back-end text editing application using the command pattern.
 * It demonstrates the usage of the copy command, recording actions, and replaying them.
 *
 * The test scenario includes putting initial text in the buffer, performing a non-recorded copy,
 * starting recording, performing a recorded copy, stopping recording, performing another non-recorded
 * copy, and finally replaying the recorded actions. The program outputs the clipboard contents
 * at different stages to verify the effectiveness of the CopyCommand.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 3.0
 */

public class MainTest_V2_CopyCommand {
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

                // Step 1: Put something in the buffer
                invoker.setText("GlablagBlag");
                invoker.executeCommand("insert");
                System.out.println("Buffer after initial insert: " + engine.getBufferContents()); // expected output:
                                                                                                  // GlablagBlag

                invoker.setBeginIndex(0);
                invoker.setEndIndex(3);
                invoker.executeCommand("changeSelection");
                invoker.executeCommand("copy");
                System.out.println("Clipboard content after not recorded copy: " +
                                engine.getClipboardContents());

                // Step 2: Start Recording
                invoker.executeCommand("start");

                // Step 3: Perform Actions While Recording
                invoker.setBeginIndex(7);
                invoker.setEndIndex(engine.getBufferContents().length());
                invoker.executeCommand("changeSelection");
                invoker.executeCommand("copy");
                /*
                 * System.out.println("Buffer content after copy: " +
                 * engine.getBufferContents());
                 */
                System.out.println("Clipboard content after recorded copy: " +
                                engine.getClipboardContents());

                // Step 4: Stop Recording
                invoker.executeCommand("stop");

                invoker.setBeginIndex(3);
                invoker.setEndIndex(7);
                invoker.executeCommand("changeSelection");
                invoker.executeCommand("copy");
                System.out.println("Clipboard content after not recorded copy: " +
                                engine.getClipboardContents());

                // Step 5: Replay Recorded Actions
                invoker.executeCommand("replay");
                System.out.println("Clipboard content after replay: " +
                                engine.getClipboardContents());

        }

}

