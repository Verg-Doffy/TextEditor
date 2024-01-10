package com.fane.Back_End.packageV1;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV2.*;

/**
 * The {@code ChangeSelectionCommand} class represents a command for changing the text selection indices in an {@link Engine}.
 * It implements the {@link Recordable} interface to support recording of its execution.
 *
 * The {@code ChangeSelectionCommand} class adjusts the selection indices in the engine based on the indices provided
 * by an associated {@link Invoker}, performs the selection change operation, and saves itself using a {@link Recorder}.
 * It also supports memento functionality to store and retrieve the state of the selection indices during the execution
 * of the selection change command.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 2.0
 */
public class ChangeSelectionCommand implements Recordable {

    private Engine engine;
    private Invoker inv;
    private Recorder recorder;

    /**
     * Constructs a {@code ChangeSelectionCommand} with references to the specified {@link Engine}, {@link Invoker},
     * and {@link Recorder}.
     *
     * @param engine   The engine responsible for text operations.
     * @param inv      The invoker providing the new selection indices.
     * @param recorder The recorder used to save the command execution.
     */
    public ChangeSelectionCommand(Engine engine, Invoker inv, Recorder recorder) {
        this.engine = engine;
        this.inv = inv;
        this.recorder = recorder;
    }

    /**
     * Executes the selection change operation by adjusting the selection indices in the engine
     * based on the indices provided by the invoker. Additionally, saves the command using the recorder
     * to support recording functionality.
     */
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

    /**
     * Retrieves a memento representing the state of the selection indices in the associated invoker.
     *
     * @return A memento capturing the current selection state.
     */
    @Override
    public Memento getMemento() {
        return new ChangeSelectionMemento(inv.getBeginIndex(), inv.getEndIndex());
    }

    /**
     * Sets the state of the associated invoker based on the provided memento.
     * Specifically, updates the selection indices based on the information stored in the memento.
     *
     * @param memento The memento containing information about the selection state.
     */
    @Override
    public void setMemento(Memento memento) {
        if (memento instanceof ChangeSelectionMemento) {
            ChangeSelectionMemento changeSelectionMemento = (ChangeSelectionMemento) memento;
            inv.setBeginIndex(changeSelectionMemento.getBeginIndex());
            inv.setEndIndex(changeSelectionMemento.getEndIndex());
        }
    }
}
