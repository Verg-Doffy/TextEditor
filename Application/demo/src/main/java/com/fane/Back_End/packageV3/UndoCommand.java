package com.fane.Back_End.packageV3;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;

/**
 * Implementation of the Command interface representing the undo operation in the text editor application.
 * This command is responsible for executing the undo functionality by interacting with the associated Recorder.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 3.0
 */
public class UndoCommand implements Command {
    private final Recorder recorder;

    /**
     * Constructs an UndoCommand with the specified Recorder.
     *
     * @param recorder The Recorder instance associated with the text editor.
     */
    public UndoCommand(Recorder recorder) {
        this.recorder = recorder;
    }

    /**
     * Executes the undo operation by invoking the undo method of the associated Recorder.
     */
    @Override
    public void execute() {
        recorder.undo();
    }
}
