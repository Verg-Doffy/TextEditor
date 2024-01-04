package com.fane.Back_End.packageV1; 
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV2.*;

public class CutCommand implements Recordable {
    private Engine engine;
    private Recorder recorder;
    private ChangeSelectionMemento memento;

    public CutCommand(Engine engine, Recorder recorder) {
        this.engine = engine;
        this.recorder = recorder;
    }

    @Override
    public void execute() {

        engine.cutSelectedText();
        recorder.save(this);
    }

    @Override
    public Memento getMemento() {
        // Return the stored memento
        return this.memento;
    }

    @Override
    public void setMemento(Memento memento) {
        if (memento instanceof ChangeSelectionMemento) {
            this.memento = (ChangeSelectionMemento) memento;
            int currentBufferLength = engine.getBufferContents().length();

            // Use the memento's begin and end indices to set the selection in the engine
            if ((this.memento.getEndIndex() >= engine.getSelection().getBeginIndex())
                    && (this.memento.getEndIndex() <= currentBufferLength)) {
                engine.getSelection().setEndIndex(this.memento.getEndIndex());
                engine.getSelection().setBeginIndex(this.memento.getBeginIndex());
            } else if (this.memento.getEndIndex() < engine.getSelection().getBeginIndex()) {
                engine.getSelection().setBeginIndex(this.memento.getBeginIndex());
                engine.getSelection().setEndIndex(this.memento.getEndIndex());
            }
        }
    }
}
