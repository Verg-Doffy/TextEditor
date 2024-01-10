package com.fane.packageV1;

import com.fane.packageV0.*;

/**
 * The {@code ChangeSelectionCommand} class implements the {@link Command} interface and represents a command
 * for changing the selection in the {@link Engine}.
 * 
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 1.0
 */

public class ChangeSelectionCommand implements Command {
    private Engine engine;
    private Invoker inv;

    /**
     * Constructs a {@code ChangeSelectionCommand} with the specified {@link Engine} and {@link Invoker}.
     *
     * @param engine The engine on which the command operates.
     * @param inv    The invoker that controls the command.
     */
    public ChangeSelectionCommand(Engine engine, Invoker inv) {
        this.engine = engine;
        this.inv = inv;
    }

    /**
     * Executes the change selection command.
     * If the end index from the invoker is greater than or equal to the current selection's begin index,
     * set the selection's end index to the invoker's end index and the begin index to the invoker's begin index.
     * Otherwise, set the begin index to the invoker's begin index and the end index to the invoker's end index.
     */
    @Override
    public void execute() {
        if (inv.getEndIndex() >= engine.getSelection().getBeginIndex()) {
            engine.getSelection().setEndIndex(inv.getEndIndex());
            engine.getSelection().setBeginIndex(inv.getBeginIndex());
        } else {
            engine.getSelection().setBeginIndex(inv.getBeginIndex());
            engine.getSelection().setEndIndex(inv.getEndIndex());
        }
    }
}
