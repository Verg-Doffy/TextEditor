package com.fane.Back_End.packageV1;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV2.*;

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
    }

    /**
     * Executes the insert operation by invoking the {@code insert()} method on the associated engine
     * with the text provided by the invoker. Additionally, saves the command using the recorder
     * to support recording functionality.
     */
    @Override
    public void execute() {
        engine.insert(inv.getText());
        recorder.save(this);
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
