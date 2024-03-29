package com.fane.Back_End.packageV1;

import java.util.Stack;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV2.*;
import com.fane.Back_End.packageV3.*;


/**
 * The {@code DeleteCommand} class represents a command for deleting selected text using an {@link Engine}.
 * It implements the {@link Recordable} interface to support recording of its execution.
 *
 * The {@code DeleteCommand} class captures the current selection in the engine, performs the delete operation,
 * and saves itself using a {@link Recorder}. It also supports memento functionality to store and retrieve
 * the state of the selection during the execution of the delete command.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 3.0
 */
public class DeleteCommand implements Recordable {

    private Engine engine;
    private Recorder recorder;
    private Stack<ChangeSelectionMemento> selectionStack;
    private Stack<String> deletedTextStack;

    /**
     * Constructs a {@code DeleteCommand} with references to the specified {@link Engine} and {@link Recorder}.
     *
     * @param engine   The engine responsible for text operations.
     * @param recorder The recorder used to save the command execution.
     */
    public DeleteCommand(Engine engine, Recorder recorder) {
        this.engine = engine;
        this.recorder = recorder;
        this.selectionStack = new Stack<>();
        this.deletedTextStack = new Stack<>();
    }

    /**
     * Executes the delete command, capturing the current selection in the engine, performing the delete operation,
     * and saving the command for undo and redo functionalities.
     */
    @Override
    public void execute() {
        if (!recorder.isReplaying() && !recorder.isUndoing() && !recorder.isRedoing() && !recorder.isRecording()) {
            ChangeSelectionMemento currentSelection = new ChangeSelectionMemento(
                    engine.getSelection().getBeginIndex(),
                    engine.getSelection().getEndIndex());
            selectionStack.push(currentSelection);
            deletedTextStack.push(engine.getBufferContents().substring(engine.getSelection().getBeginIndex(),
                    engine.getSelection().getEndIndex()));
        }
        engine.delete();
        recorder.saveForReplay(this);
        recorder.save(this);
    }

    /**
     * Undoes the delete command by restoring the selection and inserting the deleted text back to the buffer.
     */
    @Override
    public void undo() {
        if (!selectionStack.isEmpty() && !deletedTextStack.isEmpty()) {
            ChangeSelectionMemento prevSelection = selectionStack.pop();
            engine.getSelection().setBeginIndex(prevSelection.getBeginIndex());
            engine.getSelection().setEndIndex(prevSelection.getBeginIndex());

            engine.insert(deletedTextStack.pop());

            // Restore the selection state
            engine.getSelection().setBeginIndex(prevSelection.getBeginIndex());
            engine.getSelection().setEndIndex(prevSelection.getEndIndex());
        }
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
