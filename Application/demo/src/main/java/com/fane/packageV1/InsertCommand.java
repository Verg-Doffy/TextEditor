package com.fane.packageV1; 
import com.fane.packageV0.*;

/**
 * The {@code InsertCommand} class implements the {@link Command} interface and represents a command
 * for inserting text into the {@link Engine}.
 * 
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 1.0
 */

public class InsertCommand implements Command {
    private Engine engine;
    private Invoker inv;

    /**
     * Constructs an {@code InsertCommand} with the specified {@link Engine} and {@link Invoker}.
     *
     * @param engine The engine on which the command operates.
     * @param inv    The invoker associated with the command.
     */
    public InsertCommand(Engine engine, Invoker inv) {
        this.engine = engine;
        this.inv = inv;
    }

    /**
     * Executes the insert command by calling the {@link Engine#insert(String)} method
     * with the text provided by the associated {@link Invoker}.
     */
    @Override
    public void execute() {
        engine.insert(inv.getText());
    }
}
