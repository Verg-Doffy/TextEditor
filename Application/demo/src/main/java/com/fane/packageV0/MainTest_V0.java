package com.fane.packageV0;

public class MainTest_V0 {
    public static void main(String[] args) {
        // Crée une instance de EngineImpl
        Engine engine = new EngineImpl();

        // Insère du texte dans le buffer
        engine.insert("Hello, World!");
        System.out.println("Buffer Contents: " + engine.getBufferContents());

        // Sélectionne une partie du texte
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(5);

        // Copie le texte sélectionné dans le clipboard
        engine.copySelectedText();
        System.out.println("Clipboard Contents: " + engine.getClipboardContents());

        // Coupe le texte sélectionné
        engine.cutSelectedText();
        System.out.println("Buffer Contents after Cut: " + engine.getBufferContents());
        System.out.println("Clipboard Contents after Cut: " + engine.getClipboardContents());

        // Insère du texte depuis le clipboard
        System.out.println(selection.getBeginIndex());
        System.out.println(selection.getEndIndex());
        selection.setEndIndex(1);
        selection.setBeginIndex(1);
        engine.pasteClipboard();
        System.out.println("Buffer Contents after Paste: " + engine.getBufferContents());

        // Supprime le texte sélectionné
        engine.delete();
        System.out.println("Buffer Contents after Delete: " + engine.getBufferContents());

        /****************************************************
         * Testing of Exception *
         **************************************************** *
         */

        System.out.println("Buffer Contents: " + engine.getBufferContents());

        // Sélectionne une partie du texte
        Selection selection2 = engine.getSelection();
        selection2.setBeginIndex(6);
        selection2.setEndIndex(3);

        // Copie le texte sélectionné dans le clipboard
        engine.copySelectedText();
        System.out.println("Clipboard Contents: " + engine.getClipboardContents());

    }

}
