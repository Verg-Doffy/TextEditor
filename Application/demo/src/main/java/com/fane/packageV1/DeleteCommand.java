package com.fane.packageV1; 
import com.fane.packageV0.*;



public class DeleteCommand implements Command {
    private Engine engine;

    public DeleteCommand(Engine e) {
        this.engine = e;
    }

    @Override
    public void execute() {
        engine.delete();
    }
}