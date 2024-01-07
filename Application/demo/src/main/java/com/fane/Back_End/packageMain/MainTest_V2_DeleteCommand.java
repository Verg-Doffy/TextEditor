package com.fane.Back_End.packageMain;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;

/**
 * The MainTest_V2_DeleteCommand class serves as a test program for evaluating the functionality
 * of the DeleteCommand in the back-end text editing application using the command pattern.
 * It demonstrates the usage of the delete command, recording actions, and replaying them.
 *
 * The test scenario includes inserting initial text, starting recording, performing a delete operation,
 * stopping recording, and replaying the recorded actions. The program outputs the buffer contents at
 * different stages to verify the effectiveness of the DeleteCommand.
 *
 * @author Mohamed AL AFTAN & Djakaridja FANE
 * @version 1.0
 * @see com.fane.Back_End.packageV0
 * @see com.fane.Back_End.packageV1
 * @see com.fane.Back_End.packageV2
 */

public class MainTest_V2_DeleteCommand {
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
        invoker.setText("MerciMerciMerci Beaucoup");
        invoker.executeCommand("insert");
        System.out.println("Buffer after initial insert: " + engine.getBufferContents()); // expected output :
                                                                                          // MerciMerciMerci Beaucoup

        // Step 2: Start Recording
        invoker.executeCommand("start");

        // Step 3: Perform Actions While Recording
        invoker.setBeginIndex(0);
        invoker.setEndIndex(5);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("delete");
        System.out.println("Buffer content after delete: " + engine.getBufferContents()); // expected output :
                                                                                          // MerciMerci Beaucoup

        // Step 4: Stop Recording
        invoker.executeCommand("stop");

        // Step 5: Replay Recorded Actions
        invoker.executeCommand("replay"); // expected output : Merci Beaucoup

    }
}

