package com.fane.packageV1; 
import com.fane.packageV0.*;

/**
 * The {@code PasteCommand} class represents a command to paste the contents of the clipboard into the buffer.
 * 
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 1.0
 */

public class PasteCommand implements Command {
    private Engine engine;

    /**
     * Constructs a {@code PasteCommand} with the specified engine.
     *
     * @param engine The engine associated with the command.
     */
    public PasteCommand(Engine engine) {
        this.engine = engine;
    }

    /**
     * Executes the paste command, pasting the contents of the clipboard into the buffer.
     */
    @Override
    public void execute() {
        engine.pasteClipboard();
    }
}
