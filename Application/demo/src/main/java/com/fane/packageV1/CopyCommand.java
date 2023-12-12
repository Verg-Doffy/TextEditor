package com.fane.packageV1; 
import com.fane.packageV0.*;


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