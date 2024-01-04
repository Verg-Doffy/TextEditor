package com.fane.Back_End.packageV1; 
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV2.*;

public class InsertCommand implements Recordable {
    private Engine engine;
    private Invoker inv;
    private Recorder recorder;

    public InsertCommand(Engine engine, Invoker inv, Recorder recorder) {
        this.engine = engine;
        this.inv = inv;
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        engine.insert(inv.getText());
        recorder.save(this);
    }

    @Override
    public Memento getMemento() {
        return new InsertMemento(inv.getText());
    }

    @Override
    public void setMemento(Memento memento) {
        if (memento instanceof InsertMemento) {
            InsertMemento insertMemento = (InsertMemento) memento;
            inv.setText(insertMemento.getTextToInsert());
        }
    }
}
