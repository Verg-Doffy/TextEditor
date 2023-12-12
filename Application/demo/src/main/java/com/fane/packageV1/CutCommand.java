package com.fane.packageV1; 
import com.fane.packageV0.*;



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