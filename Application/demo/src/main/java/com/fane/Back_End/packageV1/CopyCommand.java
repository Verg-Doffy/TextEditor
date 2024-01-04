package com.fane.Back_End.packageV1; 
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV2.*;


public class CopyCommand implements Recordable {
    private Engine engine;
    private Recorder recorder;

    public CopyCommand(Engine engine, Recorder recorder) {
        this.engine = engine;
        this.recorder = recorder;
    }

    @Override
    public void execute() {

        engine.copySelectedText();
        recorder.save(this);

    }

    @Override
    public Memento getMemento() {
        Selection selection = engine.getSelection();
        return new ChangeSelectionMemento(selection.getBeginIndex(), selection.getEndIndex());
    }

    @Override
    public void setMemento(Memento memento) {
        if (memento instanceof ChangeSelectionMemento) {
            ChangeSelectionMemento selectionMemento = (ChangeSelectionMemento) memento;
            int currentBufferLength = engine.getBufferContents().length();

            if ((selectionMemento.getEndIndex() >= engine.getSelection().getBeginIndex())
                    && (selectionMemento.getEndIndex() <= currentBufferLength)) {
                engine.getSelection().setEndIndex(selectionMemento.getEndIndex());
                engine.getSelection().setBeginIndex(selectionMemento.getBeginIndex());
            } else if (selectionMemento.getEndIndex() < engine.getSelection().getBeginIndex()) {
                engine.getSelection().setBeginIndex(selectionMemento.getBeginIndex());
                engine.getSelection().setEndIndex(selectionMemento.getEndIndex());
            }
        }
    }
}