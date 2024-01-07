package com.fane.Back_End.packageV1;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV2.*;

/**
 * The {@code PasteCommand} class represents a command for pasting content from the clipboard into an {@link Engine}.
 * It implements the {@link Recordable} interface to support recording of its execution.
 *
 * The {@code PasteCommand} class pastes content from the clipboard into the engine, performs the paste operation,
 * and saves itself using a {@link Recorder}. It also supports memento functionality to store and retrieve
 * the state of the selection during the execution of the paste command.
 *
 * @author Mohamed AL AFTAN & Djakaridja FANE
 * @version 1.0
 * @see com.fane.Back_End.packageV0
 * @see com.fane.Back_End.packageV2
 * @see Engine
 * @see Recorder
 * @see Recordable
 * @see Memento
 * @see ChangeSelectionMemento
 */
public class PasteCommand implements Recordable {

    private Engine engine;
    private Recorder recorder;

    /**
     * Constructs a {@code PasteCommand} with references to the specified {@link Engine} and {@link Recorder}.
     *
     * @param engine   The engine responsible for text operations.
     * @param recorder The recorder used to save the command execution.
     */
    public PasteCommand(Engine engine, Recorder recorder) {
        this.engine = engine;
        this.recorder = recorder;
    }

    /**
     * Executes the paste operation by invoking the {@code pasteClipboard()} method on the associated engine.
     * Additionally, saves the command using the recorder to support recording functionality.
     */
    @Override
    public void execute() {
        engine.pasteClipboard();
        recorder.save(this);
    }

    /**
     * Retrieves a memento representing the state of the selection in the associated engine.
     *
     * @return A memento capturing the current selection state.
     */
    @Override
    public Memento getMemento() {
        Selection selection = engine.getSelection();
        return new ChangeSelectionMemento(selection.getBeginIndex(), selection.getEndIndex());
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
            ChangeSelectionMemento selectionMemento = (ChangeSelectionMemento) memento;
            int currentBufferLength = engine.getBufferContents().length();

            // Use the memento's begin and end indices to set the selection in the engine
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
