package com.fane;

public class PasteCommand implements Command {
    private Engine engine;

    public PasteCommand(Engine e) {
        this.engine = e;
    }

    @Override
    public void execute() {
        engine.pasteClipboard();

    }
}
