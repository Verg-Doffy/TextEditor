package com.fane.Back_End.packageV3;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;

/**
 * Implementation of the Command interface representing the redo operation in the text editor application.
 * This command is responsible for executing the redo functionality by interacting with the associated Recorder.
 *
 * @author [Your Name]
 * @version 1.0
 */
public class RedoCommand implements Command {
    private final Recorder recorder;

    /**
     * Constructs a RedoCommand with the specified Recorder.
     *
     * @param recorder The Recorder instance associated with the text editor.
     */
    public RedoCommand(Recorder recorder) {
        this.recorder = recorder;
    }

    /**
     * Executes the redo operation by invoking the redo method of the associated Recorder.
     */
    @Override
    public void execute() {
        recorder.redo();
    }
}
