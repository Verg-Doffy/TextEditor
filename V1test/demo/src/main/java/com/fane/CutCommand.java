package com.fane;

public class CutCommand implements Command {
    private Engine engine;

    public CutCommand(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void execute() {
        int beginIndex = engine.getSelection().getBeginIndex();
        int endIndex = engine.getSelection().getEndIndex();
        engine.setClipboardContents(engine.getBufferContents().substring(beginIndex, endIndex));
        engine.getBuffer().delete(beginIndex, endIndex);
        engine.getSelection().setEndIndex(beginIndex);
    }
}






