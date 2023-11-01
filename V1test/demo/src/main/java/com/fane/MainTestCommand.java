package com.fane;

public class MainTestCommand {
    public static void main(String[] args) {
        Invoker invoker = new Invoker();
        EngineImpl engine = new EngineImpl(invoker);

        // Ajoutez les commandes au invocateur
        CutCommand cutCommand = new CutCommand(engine);
        CopyCommand copyCommand = new CopyCommand(engine);
        PasteCommand pasteCommand = new PasteCommand(engine);
        InsertCommand insertCommand = new InsertCommand(engine, "Text to insert");
        DeleteCommand deleteCommand = new DeleteCommand(engine);

        invoker.addCommand("cut", cutCommand);
        invoker.addCommand("copy", copyCommand);
        invoker.addCommand("paste", pasteCommand);
        invoker.addCommand("insert", insertCommand);
        invoker.addCommand("delete", deleteCommand);

        // Testez les commandes
        engine.insert("This is a test string.");
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(9);

        engine.cutSelectedText();;
        System.out.println("Clipboard contents: " + engine.getClipboardContents());
        System.out.println("Buffer contents: " + engine.getBufferContents());

        engine.copySelectedText();
        System.out.println("Clipboard contents: " + engine.getClipboardContents());

        engine.pasteClipboard();;
        System.out.println("Buffer contents after paste: " + engine.getBufferContents());

        engine.insert("HELLO");
        System.out.println("Buffer contents after insert: " + engine.getBufferContents());

        engine.delete();
        System.out.println("Buffer contents after delete: " + engine.getBufferContents());
    }
}
