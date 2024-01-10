package com.fane.Back_End.packageV1;

import java.util.Stack;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV2.*;
import com.fane.Back_End.packageV3.*;


/**
 * The {@code PasteCommand} class represents a command for pasting content from the clipboard into an {@link Engine}.
 * It implements the {@link Recordable} interface to support recording of its execution.
 *
 * The {@code PasteCommand} class pastes content from the clipboard into the engine, performs the paste operation,
 * and saves itself using a {@link Recorder}. It also supports memento functionality to store and retrieve
 * the state of the selection during the execution of the paste command.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 3.0
 */
public class PasteCommand implements Recordable {

    private Engine engine;
    private Recorder recorder;
    private Stack<ChangeSelectionMemento> selectionStack;
    private Stack<String> clipboardBeforePasteStack;
    private Stack<Integer> pastedTextLengthStack;

    /**
     * Constructs a {@code PasteCommand} with references to the specified {@link Engine} and {@link Recorder}.
     *
     * @param engine   The engine responsible for text operations.
     * @param recorder The recorder used to save the command execution.
     */
    public PasteCommand(Engine engine, Recorder recorder) {
        this.engine = engine;
        this.recorder = recorder;
        this.selectionStack = new Stack<>();
        this.clipboardBeforePasteStack = new Stack<>();
        this.pastedTextLengthStack = new Stack<>();
    }

    /**
     * Executes the paste command, capturing the current state before pasting, performing the paste operation,
     * and saving the command for undo and redo functionalities.
     */
    @Override
    public void execute() {
        // Save the current state before pasting unless replaying, undoing, redoing, or
        // recording
        if (!recorder.isReplaying() && !recorder.isUndoing() && !recorder.isRedoing() && !recorder.isRecording()) {
            Selection selection = engine.getSelection();
            selectionStack.push(new ChangeSelectionMemento(selection.getBeginIndex(), selection.getEndIndex()));
            clipboardBeforePasteStack.push(engine.getClipboardContents());
        }

        // Perform the paste operation
        int selectionStart = engine.getSelection().getBeginIndex();
        engine.pasteClipboard();
        int selectionEnd = engine.getSelection().getEndIndex();
        pastedTextLengthStack.push(selectionEnd - selectionStart);

        recorder.saveForReplay(this);
        recorder.save(this);
    }

    /**
     * Undoes the paste command by restoring the previous state and deleting the pasted text.
     */
    @Override
    public void undo() {
        if (!selectionStack.isEmpty() && !clipboardBeforePasteStack.isEmpty() && !pastedTextLengthStack.isEmpty()) {
            ChangeSelectionMemento prevSelection = selectionStack.pop();
            engine.getSelection().setBeginIndex(prevSelection.getBeginIndex());
            engine.getSelection().setEndIndex(prevSelection.getEndIndex());

            int pastedTextLength = pastedTextLengthStack.pop();
            int pasteStartPosition = prevSelection.getBeginIndex();
            engine.getSelection().setBeginIndex(pasteStartPosition);
            engine.getSelection().setEndIndex(pasteStartPosition + pastedTextLength);
            engine.delete();

            engine.setClipboardContents(clipboardBeforePasteStack.pop());
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
