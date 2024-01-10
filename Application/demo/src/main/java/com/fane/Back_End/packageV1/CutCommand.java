package com.fane.Back_End.packageV1;

import java.util.Stack;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV2.*;
import com.fane.Back_End.packageV3.*;

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
    private Stack<ChangeSelectionMemento> selectionStack;
    private Stack<String> removedTextStack;
    private Stack<String> clipboardBeforeCutStack;

    /**
     * Constructs a {@code CutCommand} with references to the specified {@link Engine} and {@link Recorder}.
     *
     * @param engine   The engine responsible for text operations.
     * @param recorder The recorder used to save the command execution.
     */
    public CutCommand(Engine engine, Recorder recorder) {
        this.engine = engine;
        this.recorder = recorder;
        this.selectionStack = new Stack<>();
        this.removedTextStack = new Stack<>();
        this.clipboardBeforeCutStack = new Stack<>();
    }

    /**
     * Executes the cut command, capturing the current selection in the engine, performing the cut operation,
     * and saving the command for undo and redo functionalities.
     */
    @Override
    public void execute() {
        if (!recorder.isReplaying() && !recorder.isUndoing() && !recorder.isRedoing() && !recorder.isRecording()) {
            Selection selection = engine.getSelection();
            selectionStack.push(new ChangeSelectionMemento(selection.getBeginIndex(), selection.getEndIndex()));
            removedTextStack
                    .push(engine.getBufferContents().substring(selection.getBeginIndex(), selection.getEndIndex()));
            clipboardBeforeCutStack.push(engine.getClipboardContents());
        }
        engine.cutSelectedText();
        recorder.saveForReplay(this);
        recorder.save(this);
    }

    /**
     * Undoes the cut command by restoring the selection, inserting the removed text back to the buffer,
     * and restoring the clipboard to its state before the cut operation.
     */
    @Override
    public void undo() {
        if (!selectionStack.isEmpty() && !removedTextStack.isEmpty() && !clipboardBeforeCutStack.isEmpty()) {
            ChangeSelectionMemento prevSelection = selectionStack.pop();
            engine.getSelection().setBeginIndex(prevSelection.getBeginIndex());
            engine.getSelection().setEndIndex(prevSelection.getEndIndex());

            engine.insert(removedTextStack.pop());
            engine.setClipboardContents(clipboardBeforeCutStack.pop());

            // Restore the selection state
            engine.getSelection().setBeginIndex(prevSelection.getBeginIndex());
            engine.getSelection().setEndIndex(prevSelection.getEndIndex());
        }
    }

    /**
     * Retrieves the stored memento representing the state of the selection during the execution of the cut command.
     *
     * @return A memento capturing the selection state during the cut operation.
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
