package com.fane;

public class CopyCommand implements Command {
    private Engine engine;

    public CopyCommand(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void execute() {
        int beginIndex = engine.getSelection().getBeginIndex();
        int endIndex = engine.getSelection().getEndIndex();
        engine.setClipboardContents(engine.getBufferContents().substring(beginIndex, endIndex));
    }
}

