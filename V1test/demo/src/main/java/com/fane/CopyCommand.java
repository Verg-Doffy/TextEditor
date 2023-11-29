package com.fane;

public class CopyCommand implements Command {
    private Engine engine;

    public CopyCommand(Engine e) {
        this.engine = e;
    }

    @Override
    public void execute() {
        engine.copySelectedText();
    }
}