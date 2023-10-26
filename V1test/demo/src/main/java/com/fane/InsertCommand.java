package com.fane;

public class InsertCommand implements Command {
    private Engine engine;
    private String textToInsert;

    public InsertCommand(Engine engine, String textToInsert) {
        this.engine = engine;
        this.textToInsert = textToInsert;
    }

    @Override
    public void execute() {
        int beginIndex = engine.getSelection().getBeginIndex();
        engine.getBuffer().delete(beginIndex, engine.getSelection().getEndIndex()); // Remove the selected text from buffer
        engine.getBuffer().insert(beginIndex, textToInsert); // Insert string at the selection
        engine.getSelection().setEndIndex(beginIndex + textToInsert.length()); // Move selection to the end of the inserted text
    }
}
