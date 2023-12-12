package com.fane.packageV1; 
import com.fane.packageV0.*;



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
