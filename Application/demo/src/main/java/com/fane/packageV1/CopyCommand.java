package com.fane.packageV1; 
import com.fane.packageV0.*;

/**
 * The {@code CopyCommand} class implements the {@link Command} interface and represents a command
 * for copying the selected text in the {@link Engine}.
 * 
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 1.0
 */

public class CopyCommand implements Command {
    private Engine engine;

    /**
     * Constructs a {@code CopyCommand} with the specified {@link Engine}.
     *
     * @param e The engine on which the command operates.
     */
    public CopyCommand(Engine e) {
        this.engine = e;
    }

    /**
     * Executes the copy command by calling the {@link Engine#copySelectedText()} method.
     */
    @Override
    public void execute() {
        engine.copySelectedText();
    }
}
