package com.fane;

public class DeleteCommand implements Command {
    private Engine engine;

    public DeleteCommand(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void execute() {
        int beginIndex = engine.getSelection().getBeginIndex();
        int endIndex = engine.getSelection().getEndIndex();

        engine.getBuffer().delete(beginIndex, endIndex); // Remove selected text from buffer
        engine.getSelection().setEndIndex(beginIndex);
    }
}