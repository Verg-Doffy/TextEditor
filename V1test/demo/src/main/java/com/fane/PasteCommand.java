package com.fane;

public class PasteCommand implements Command {
    private Engine engine;

    public PasteCommand(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void execute() {
        int beginIndex = engine.getSelection().getBeginIndex();
        int endIndex = engine.getSelection().getEndIndex();

        engine.getBuffer().delete(beginIndex, endIndex); // Remove the selected text from buffer
        engine.getBuffer().insert(beginIndex, engine.getClipboardContents()); // Insert clipboard contents
        engine.getSelection().setEndIndex(beginIndex + engine.getClipboardContents().length()); // Move selection to the end of the pasted text
    }
}
