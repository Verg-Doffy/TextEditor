package com.fane.Back_End.packageMain;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;

/**
 * The MainTest_V2_PasteCommand class serves as a test program for evaluating the functionality
 * of the PasteCommand in the back-end text editing application using the command pattern.
 * It demonstrates the usage of the paste command, including copying, pasting, and replaying recorded actions.
 *
 * The test scenario includes inserting initial text, copying a selection, pasting the copied text,
 * starting recording, copying and pasting while recording, stopping recording, and performing additional insertions
 * and pasting after recording stops. The program outputs the buffer contents and clipboard contents at different stages
 * to verify the effectiveness of the PasteCommand.
 *
 * @author Mohamed AL AFTAN & Djakaridja FANE
 * @version 1.0
 * @see com.fane.Back_End.packageV0
 * @see com.fane.Back_End.packageV1
 * @see com.fane.Back_End.packageV2
 */

public class MainTest_V2_PasteCommand {
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

        // Example usage
        System.out.println("At beginning, Buffer content: " + engine.getBufferContents()); // Expected output: At
                                                                                           // beginning, Buffer content:
        System.out.println("At beginning, Clipboard content: " + engine.getClipboardContents()); // Expected output: At
                                                                                                 // beginning, Clipboard
                                                                                                 // content:
        invoker.setText("Helloworld");
        invoker.executeCommand("insert"); // Insert "Hello World"
        System.out.println("Buffer content after insert: " + engine.getBufferContents());// Expected output: Buffer
                                                                                         // content after insert: Hello
                                                                                         // World
        invoker.executeCommand("start"); // Start recording

        invoker.setBeginIndex(0);
        invoker.setEndIndex(5);
        invoker.executeCommand("changeSelection"); // Change selection to first 5 chars

        invoker.executeCommand("copy"); // Copy selection
        System.out.println("Clipboard content after copy: " + engine.getClipboardContents());// Expected output:
                                                                                             // Clipboard content after
                                                                                             // copy: Hello

        invoker.setBeginIndex(engine.getBufferContents().length());
        invoker.setEndIndex(engine.getBufferContents().length());
        invoker.executeCommand("changeSelection"); // Change selection to the last char

        invoker.executeCommand("paste"); // Paste the copied text
        System.out.println("Buffer content after paste: " + engine.getBufferContents());// Expected output: Buffer
                                                                                        // content after paste: Hello
                                                                                        // WorldHello
        invoker.setBeginIndex(5);
        invoker.setEndIndex(10);
        invoker.executeCommand("changeSelection"); // Change selection from 6th to 10th chars

        invoker.executeCommand("copy"); // Copy selection
        System.out.println("Clipboard content after copy: " + engine.getClipboardContents());// Expected output:
                                                                                             // Clipboard content after
                                                                                             // copy
                                                                                             // world

        invoker.setBeginIndex(engine.getBufferContents().length());
        invoker.setEndIndex(engine.getBufferContents().length());
        invoker.executeCommand("changeSelection"); // Change selection to the last char

        invoker.executeCommand("paste"); // Paste the copied text
        System.out.println("Buffer content after paste: " + engine.getBufferContents());// Expected output: Buffer
                                                                                        // content after paste:
                                                                                        // HelloworldHelloworld

        invoker.executeCommand("stop"); // Stop recording

        // insert text after recording stops

        invoker.setText(" Salut");
        int insertIndex = engine.getBufferContents().length();
        invoker.setBeginIndex(insertIndex);
        invoker.setEndIndex(insertIndex);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("insert");
        System.out.println("Buffer content after post recording insertion: " + engine.getBufferContents()); // expected
                                                                                                            // output:
                                                                                                            // HelloworldHelloworld
                                                                                                            // Salut

        int beginIndex = 20;
        int endIndex = 26;
        invoker.setBeginIndex(beginIndex);
        invoker.setEndIndex(endIndex);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("copy");
        System.out.println("Clipboard content after post recording copy: " + engine.getClipboardContents()); // expected
                                                                                                             // output:
                                                                                                             // Salut

        invoker.setBeginIndex(engine.getBufferContents().length());
        invoker.setEndIndex(engine.getBufferContents().length());
        invoker.executeCommand("changeSelection"); // Change selection to the last char

        invoker.executeCommand("paste"); // Paste the copied text
        System.out.println("Buffer content after paste: " + engine.getBufferContents());// Expected output: Buffer
                                                                                        // content after paste:
                                                                                        // HelloworldHelloworld Salut
                                                                                        // Salut

        // Replay the recorded actions
        invoker.executeCommand("replay"); // HelloworldHelloworldHelloworld SalutSalut

        // Explanation: The replay command repeats the actions performed during
        // recording,
        // which in this case is copying "Hello" and pasting it again at the end(It is
        // end when recording but not end when replaying ) and the same action for
        // "world".

    }
}

