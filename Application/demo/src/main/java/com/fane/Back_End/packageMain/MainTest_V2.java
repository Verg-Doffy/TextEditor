package com.fane.Back_End.packageMain;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;

/**
 * The code {@code MainTest_V2} demonstrates the usage of Engine, Recorder, and Invoker
 * components with various commands in a simulated text editor environment.
 * It includes a sequence of actions such as inserting, cutting, pasting, and
 * deleting text, along with starting, stopping, and replaying recording sessions.
 * The example showcases the functionality of the implemented classes in the
 * The expected output of each action is also provided in comments for reference.
 * 
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 1.0
 */

public class MainTest_V2 {
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

        // Example usage
        System.out.println("At beginning, Buffer content: " + engine.getBufferContents()); // Expected output: At
                                                                                           // beginning, Buffer content:
        System.out.println("At beginning, Clipboard content: " + engine.getClipboardContents()); // Expected output: At
                                                                                                 // beginning, Clipboard
                                                                                                 // content:

        invoker.executeCommand("start"); // Start recording

        invoker.setText("Helloworld");
        invoker.executeCommand("insert"); // Insert "Hello World"
        System.out.println("Buffer content after insert: " + engine.getBufferContents());// Expected output: Buffer
                                                                                         // content after insert: Hello
                                                                                         // World

        invoker.setBeginIndex(0);
        invoker.setEndIndex(5);
        invoker.executeCommand("changeSelection"); // Change selection to first 5 chars

        invoker.executeCommand("cut"); // Copy selection
        System.out.println("Clipboard content after cut: " + engine.getClipboardContents());// Expected output:
                                                                                            // Clipboard content after
                                                                                            // cut: Hello

        invoker.setBeginIndex(5);
        invoker.setEndIndex(5);
        invoker.executeCommand("changeSelection"); // Change selection to the buffer length

        invoker.executeCommand("paste"); // Paste the copied text
        System.out.println("Buffer content after paste: " + engine.getBufferContents());// Expected output: Buffer
                                                                                        // content after paste: Hello
                                                                                        // WorldHello

        invoker.setBeginIndex(0);
        invoker.setEndIndex(5);
        invoker.executeCommand("changeSelection"); // Change selection to the buffer length
        invoker.executeCommand("delete");
        System.out.println("Buffer content after delete: " + engine.getBufferContents()); // Expected output: Hello

        invoker.executeCommand("stop"); // Stop recording

        // Print current buffer contents
        System.out.println("Current Buffer: " + engine.getBufferContents());// Expected output: Current Buffer: Hello
                                                                            // WorldHello

        // Replay the recorded actions
        invoker.executeCommand("replay");

        // Explanation: The replay command repeats the actions performed during
        // recording,
        // which in this case is inserting "HelloWorld" then cutting "Hello" and pasting
        // it again at the end and finally
        // deleting "world"
        // When replaying we performed the same actions, so buffer will contain at the
        // end of replaying "HelloHello"

    }
}

