package com.fane.Back_End.packageV1;

import java.util.Stack;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV2.*;
import com.fane.Back_End.packageV3.*;


/**
 * The {@code InsertCommand} class represents a command for inserting text into an {@link Engine}.
 * It implements the {@link Recordable} interface to support recording of its execution.
 *
 * The {@code InsertCommand} class inserts text from an associated {@link Invoker} into the engine,
 * performs the insert operation, and saves itself using a {@link Recorder}. It also supports memento
 * functionality to store and retrieve the state of the text to be inserted during the execution
 * of the insert command.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 3.0
 */
public class InsertCommand implements Recordable {

    private Engine engine;
    private Invoker inv;
    private Recorder recorder;
    private Stack<ChangeSelectionMemento> selectionStack; // Utiliser une pile pour stocker les mementos
    private Stack<String> clipboardStack; // Utiliser une pile pour stocker les états précédents du presse-papiers
    private Stack<String> textToInsertStack; // Utiliser une pile pour stocker les textes à insérer

    /**
     * Constructs an {@code InsertCommand} with references to the specified {@link Engine}, {@link Invoker},
     * and {@link Recorder}.
     *
     * @param engine   The engine responsible for text operations.
     * @param inv      The invoker providing the text to be inserted.
     * @param recorder The recorder used to save the command execution.
     */
    public InsertCommand(Engine engine, Invoker inv, Recorder recorder) {
        this.engine = engine;
        this.inv = inv;
        this.recorder = recorder;
        this.selectionStack = new Stack<>();
        this.clipboardStack = new Stack<>();
        this.textToInsertStack = new Stack<>();
    }

    /**
     * Executes the insert command, capturing the current state before insertion, performing the insert operation,
     * and saving the command for undo and redo functionalities.
     */
    @Override
    public void execute() {
        if (!recorder.isReplaying() && !recorder.isUndoing() && !recorder.isRedoing() && !recorder.isRecording()) {
            // Save the current state before insertion
            ChangeSelectionMemento currentSelection = new ChangeSelectionMemento(
                    engine.getSelection().getBeginIndex(),
                    engine.getSelection().getEndIndex());
            selectionStack.push(currentSelection); // Push the memento
            clipboardStack.push(engine.getClipboardContents()); // Push the current clipboard state
            textToInsertStack.push(inv.getText()); // Push the text to be inserted
        }

        // Perform the insertion
        engine.insert(inv.getText());

        // Save the state for replay functionality
        recorder.saveForReplay(this);

        // Save the state for undo and redo functionalities
        recorder.save(this);
    }

    /**
     * Undoes the insert command by restoring the previous state and deleting the inserted text.
     */
    @Override
    public void undo() {

        if (!selectionStack.isEmpty() && !clipboardStack.isEmpty() && !textToInsertStack.isEmpty()) {
            // Restore the previous state
            ChangeSelectionMemento previousSelection = selectionStack.pop();
            String previousClipboardContent = clipboardStack.pop();
            String previousTextToInsert = textToInsertStack.pop();

            // Restore the clipboard state
            engine.setClipboardContents(previousClipboardContent);

            // Perform the deletion
            int insertPosition = previousSelection.getBeginIndex();
            int bufferLength = engine.getBufferContents().length();
            int insertedTextLength = previousTextToInsert.length();
            int endPosition = Math.min(insertPosition + insertedTextLength, bufferLength);

            engine.getSelection().setBeginIndex(insertPosition);
            engine.getSelection().setEndIndex(endPosition);
            engine.delete();

            // Restore the selection state
            engine.getSelection().setBeginIndex(previousSelection.getBeginIndex());
            engine.getSelection().setEndIndex(previousSelection.getEndIndex());
        }
    }

    /**
     * Retrieves a memento representing the state of the text to be inserted.
     *
     * @return A memento capturing the text to be inserted during the insert operation.
     */
    @Override
    public Memento getMemento() {
        return new InsertMemento(inv.getText());
    }

    /**
     * Sets the state of the associated invoker based on the provided memento.
     * Specifically, updates the text to be inserted based on the information stored in the memento.
     *
     * @param memento The memento containing information about the text to be inserted.
     */
    @Override
    public void setMemento(Memento memento) {
        if (memento instanceof InsertMemento) {
            InsertMemento insertMemento = (InsertMemento) memento;
            inv.setText(insertMemento.getTextToInsert());
        }
    }
}
