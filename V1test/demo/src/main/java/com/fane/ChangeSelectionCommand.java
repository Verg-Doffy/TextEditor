package com.fane;

public class ChangeSelectionCommand implements Command {
    private Engine engine;
    private int newBeginIndex;
    private int newEndIndex;

    public ChangeSelectionCommand(Engine engine, int newBeginIndex, int newEndIndex) {
        this.engine = engine;
        this.newBeginIndex = newBeginIndex;
        this.newEndIndex = newEndIndex;
    }

    @Override
    public void execute() {
        engine.changeSelection(newBeginIndex, newEndIndex); // update the selection in the Engine
    }
}
