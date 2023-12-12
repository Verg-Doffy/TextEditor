package com.fane.packageV1; 
import com.fane.packageV0.*;



public class InsertCommand implements Command {
    private Engine engine;
    private Invoker inv;
    // private String textToInsert;

    public InsertCommand(Engine engine, Invoker inv) {
        this.engine = engine;
        this.inv = inv;
    }

    @Override
    public void execute() {
        engine.insert(inv.getText());
    }
}