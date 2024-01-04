package com.fane.Back_End.packageV1; 
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV2.*;

public class ChangeSelectionCommand implements Recordable {
    private Engine engine;
    private Invoker inv;
    private Recorder recorder;

    public ChangeSelectionCommand(Engine engine, Invoker inv, Recorder recorder) {
        this.engine = engine;
        this.inv = inv;
        this.recorder = recorder;
    }

    @Override
    public void execute() {

        int currentBufferLength = engine.getBufferContents().length();

        if ((inv.getEndIndex() >= engine.getSelection().getBeginIndex())
                && (inv.getEndIndex() <= currentBufferLength)) {
            engine.getSelection().setEndIndex(inv.getEndIndex());
            engine.getSelection().setBeginIndex(inv.getBeginIndex());
        } else if (inv.getEndIndex() < engine.getSelection().getBeginIndex()) {
            engine.getSelection().setBeginIndex(inv.getBeginIndex());
            engine.getSelection().setEndIndex(inv.getEndIndex());
        }

        recorder.save(this);

    }

    @Override
    public Memento getMemento() {
        return new ChangeSelectionMemento(inv.getBeginIndex(), inv.getEndIndex());
    }

    @Override
    public void setMemento(Memento memento) {
        if (memento instanceof ChangeSelectionMemento) {
            ChangeSelectionMemento changeSelectionMemento = (ChangeSelectionMemento) memento;
            inv.setBeginIndex(changeSelectionMemento.getBeginIndex());
            inv.setEndIndex(changeSelectionMemento.getEndIndex());
        }
    }
}
