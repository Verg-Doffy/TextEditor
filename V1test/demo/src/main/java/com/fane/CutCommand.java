package com.fane;

public class CutCommand implements Command {
    private Engine engine;

    public CutCommand(Engine e) {
        this.engine = e;
    }

    @Override
    public void execute() {
        engine.cutSelectedText();
    }
}