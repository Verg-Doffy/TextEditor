package com.fane;

public class MainTest {
    public static void main(String[] args) {
        // Crée une instance de EngineImpl
        Invoker invoker = new Invoker();
        Engine engine = new EngineImpl(invoker);

        // Insère du texte dans le buffer
        engine.insert("Hello, World!");
        System.out.println("Buffer Contents: " + engine.getBufferContents());

        // Sélectionne une partie du texte
        Selection selection = engine.getSelection();
        selection.setBeginIndex(1);
        selection.setEndIndex(5);

        // Copie le texte sélectionné dans le clipboard
        engine.copySelectedText();
        System.out.println("Clipboard Contents: " + engine.getClipboardContents());

        // Coupe le texte sélectionné
        engine.cutSelectedText();
        System.out.println("Buffer Contents after Cut: " + engine.getBufferContents());
        System.out.println("Clipboard Contents after Cut: " + engine.getClipboardContents());

        // Insère du texte depuis le clipboard
        engine.pasteClipboard();
        System.out.println("Buffer Contents after Paste: " + engine.getBufferContents());

        // Supprime le texte sélectionné
        engine.delete();
        System.out.println("Buffer Contents after Delete: " + engine.getBufferContents());

        /****************************************************
         * Testing of Exception *
         **************************************************** *
         */

        // Crée une 2è instance de EngineImpl
        Invoker invoker2 = new Invoker();
        Engine engine2 = new EngineImpl(invoker2);

        // Créez une instance de CutCommand et ajoutez-la à l'invocateur
        String textToInsert = "";
        CutCommand cutCommand = new CutCommand(engine);
        CopyCommand copyCommand = new CopyCommand(engine);
        PasteCommand pasteCommand = new PasteCommand(engine);
        DeleteCommand deleteCommand = new DeleteCommand(engine);
        InsertCommand insertCommand = new InsertCommand(engine, textToInsert);
        invoker.addCommand("cut", cutCommand);
        invoker.addCommand("copy", copyCommand);
        invoker.addCommand("paste", pasteCommand);
        invoker.addCommand("delete", deleteCommand);
        invoker.addCommand("insert", insertCommand);

        // Insère du texte dans le buffer
        engine2.insert("Hello, World!");
        System.out.println("Buffer Contents: " + engine.getBufferContents());

        // Sélectionne une partie du texte
        Selection selection2 = engine2.getSelection();
        selection2.setBeginIndex(6);
        selection2.setEndIndex(9);

        // Copie le texte sélectionné dans le clipboard
        engine2.copySelectedText();
        System.out.println("Clipboard Contents: " + engine2.getClipboardContents());

    }

}
