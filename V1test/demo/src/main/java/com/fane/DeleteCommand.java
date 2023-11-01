package com.fane;

public class DeleteCommand implements Command {
    private Engine engine;

    public DeleteCommand(Engine e) {
        this.engine = e;
    }

    @Override
    public void execute() {
        engine.delete();
        engine.resetSelection();
    }
}