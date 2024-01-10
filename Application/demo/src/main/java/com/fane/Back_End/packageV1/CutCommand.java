package com.fane.Back_End.packageV1;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV2.*;

/**
 * The {@code CutCommand} class represents a command for cutting selected text using an {@link Engine}.
 * It implements the {@link Recordable} interface to support recording of its execution.
 *
 * The {@code CutCommand} class captures the current selection in the engine, performs the cut operation,
 * and saves itself using a {@link Recorder}. It also supports memento functionality to store and retrieve
 * the state of the selection during the execution of the cut command.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 3.0
 */
public class CutCommand implements Recordable {

    private Engine engine;
    private Recorder recorder;
    private ChangeSelectionMemento memento;

    /**
     * Constructs a {@code CutCommand} with references to the specified {@link Engine} and {@link Recorder}.
     *
     * @param engine   The engine responsible for text operations.
     * @param recorder The recorder used to save the command execution.
     */
    public CutCommand(Engine engine, Recorder recorder) {
        this.engine = engine;
        this.recorder = recorder;
    }

    /**
     * Executes the cut operation by invoking the {@code cutSelectedText()} method on the associated engine.
     * Additionally, saves the command using the recorder to support recording functionality.
     */
    @Override
    public void execute() {
        engine.cutSelectedText();
        recorder.save(this);
    }

    /**
     * Retrieves the stored memento representing the state of the selection during the execution of the cut command.
     *
     * @return A memento capturing the selection state during the cut operation.
     */
    @Override
    public Memento getMemento() {
        // Return the stored memento
        return this.memento;
    }

    /**
     * Sets the state of the associated engine based on the provided memento.
     * Specifically, adjusts the selection indices based on the information stored in the memento.
     *
     * @param memento The memento containing information about the selection state.
     */
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
