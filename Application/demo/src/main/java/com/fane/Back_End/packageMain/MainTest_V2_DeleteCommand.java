package com.fane.Back_End.packageMain;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;


public class MainTest_V2_DeleteCommand {
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

