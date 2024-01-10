package com.fane.packageV1; 
import com.fane.packageV0.*;

/**
 * The {@code DeleteCommand} class implements the {@link Command} interface and represents a command
 * for deleting the selected text in the {@link Engine}.
 * 
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 1.0
 */

public class DeleteCommand implements Command {
    private Engine engine;

    /**
     * Constructs a {@code DeleteCommand} with the specified {@link Engine}.
     *
     * @param e The engine on which the command operates.
     */
    public DeleteCommand(Engine e) {
        this.engine = e;
    }

    /**
     * Executes the delete command by calling the {@link Engine#delete()} method.
     */
    @Override
    public void execute() {
        engine.delete();
    }
}
