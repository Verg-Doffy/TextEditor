package com.fane.Back_End.packageMain;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;

/**
 * The MainTest_V2_CutCommand class serves as a test program for evaluating the functionality
 * of the CutCommand in the back-end text editing application using the command pattern.
 * It demonstrates the usage of the cut command, recording actions, and replaying them.
 *
 * The test scenario includes inserting initial text, starting recording, performing two cut operations,
 * stopping recording, and replaying the recorded actions. The program outputs the buffer and clipboard
 * contents at different stages to verify the effectiveness of the CutCommand.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 3.0
 */

public class MainTest_V2_CutCommand {
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
        invoker.addCommand("cut", new CutCommand(engine, recorder));
        invoker.addCommand("start", new StartCommand(recorder));
        invoker.addCommand("stop", new StopCommand(recorder));
        invoker.addCommand("replay", new ReplayCommand(recorder));

        // Step 1: Insert initial text
        invoker.setText("Blableblubliblag");
        invoker.executeCommand("insert");
        System.out.println("Buffer after initial insert: " + engine.getBufferContents());

        // Step 2: Start Recording
        invoker.executeCommand("start");

        // Step 3: First Cut Operation
        invoker.setBeginIndex(0);
        invoker.setEndIndex(3); // Select "Bla"
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("cut");
        System.out.println("Buffer content after 1st cut: " + engine.getBufferContents());
        System.out.println("Clipboard content after 1st cut: " + engine.getClipboardContents());

        // Step 3: Second Cut Operation
        invoker.setBeginIndex(0);
        invoker.setEndIndex(3); // Select "ble"
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("cut");
        System.out.println("Buffer content after 2nd cut: " + engine.getBufferContents());
        System.out.println("Clipboard content after 2nd cut: " + engine.getClipboardContents());

        // Step 4: Stop Recording
        invoker.executeCommand("stop");

        // Step 5: Replay Recorded Actions
        invoker.executeCommand("replay");

    }
}

