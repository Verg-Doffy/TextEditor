package com.fane;

public class InsertCommand implements Command {
    private Engine engine;
    private String textToInsert;

    public InsertCommand(Engine engine, String text) {
        this.engine = engine;
        this.textToInsert = text;
    }

    @Override
    public void execute() {
        engine.insert(textToInsert);
        engine.resetSelection();
    }
}
