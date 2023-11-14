package com.fane;

public class ChangeSelectionCommand implements Command {
    private Engine engine;
    private Invoker inv;

    public ChangeSelectionCommand(Engine engine, Invoker inv) {
        this.engine = engine;
        this.inv = inv;
    }

    @Override
    public void execute() {
        engine.getSelection().setBeginIndex(inv.getBeginIndex());
        engine.getSelection().setEndIndex(inv.getEndIndex());
    }
}
