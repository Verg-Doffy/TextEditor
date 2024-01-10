package com.fane.packageV1; 
import com.fane.packageV0.*;

/**
 * The {@code CutCommand} class implements the {@link Command} interface and represents a command
 * for cutting the selected text in the {@link Engine}.
 * 
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 1.0
 */

public class CutCommand implements Command {
    private Engine engine;

    /**
     * Constructs a {@code CutCommand} with the specified {@link Engine}.
     *
     * @param e The engine on which the command operates.
     */
    public CutCommand(Engine e) {
        this.engine = e;
    }

    /**
     * Executes the cut command by calling the {@link Engine#cutSelectedText()} method.
     */
    @Override
    public void execute() {
        engine.cutSelectedText();
    }
}
