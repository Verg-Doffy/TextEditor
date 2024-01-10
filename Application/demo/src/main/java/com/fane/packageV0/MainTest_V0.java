package com.fane.packageV0;


/**
 * The {@code MainTest_V0} class serves as a test program for the functionality of {@link EngineImpl}
 * and related classes in the Back_End module for The V0 of the project.
 *
 * This class demonstrates the usage of an {@link EngineImpl} instance to perform various text operations,
 * such as inserting, selecting, copying, cutting, pasting, and deleting text.
 *
 * Additionally, it includes a section for testing exceptions by attempting to perform operations
 * with invalid selection indices.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 0.0
 */

public class MainTest_V0 {

    /**
     * The main method that serves as the entry point for the test program.
     *
     * @param args The command-line arguments (unused in this context).
     */
    public static void main(String[] args) {
        // Create an instance of EngineImpl
        Engine engine = new EngineImpl();

        // Insert text into the buffer
        engine.insert("Hello, World!");
        System.out.println("Buffer Contents: " + engine.getBufferContents());

        // Select a part of the text
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(5);

        // Copy the selected text to the clipboard
        engine.copySelectedText();
        System.out.println("Clipboard Contents: " + engine.getClipboardContents());

        // Cut the selected text
        engine.cutSelectedText();
        System.out.println("Buffer Contents after Cut: " + engine.getBufferContents());
        System.out.println("Clipboard Contents after Cut: " + engine.getClipboardContents());

        // Insert text from the clipboard
        System.out.println(selection.getBeginIndex());
        System.out.println(selection.getEndIndex());
        selection.setEndIndex(1);
        selection.setBeginIndex(1);
        engine.pasteClipboard();
        System.out.println("Buffer Contents after Paste: " + engine.getBufferContents());

        // Delete the selected text
        engine.delete();
        System.out.println("Buffer Contents after Delete: " + engine.getBufferContents());

        /****************************************************
         * Testing of Exception *
         **************************************************** *
         */

        System.out.println("Buffer Contents: " + engine.getBufferContents());

        // Select a part of the text
        Selection selection2 = engine.getSelection();
        selection2.setBeginIndex(6);
        selection2.setEndIndex(3);

        // Copy the selected text to the clipboard
        engine.copySelectedText();
        System.out.println("Clipboard Contents: " + engine.getClipboardContents());

    }


}
