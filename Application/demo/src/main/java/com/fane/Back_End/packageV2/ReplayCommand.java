package com.fane.Back_End.packageV2;
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;

/**
 * The {@code ReplayCommand} class represents a command that triggers the replay of recorded commands
 * using a provided {@link Recorder}. It implements the {@link Command} interface.
 *
 * The replay command executes the replay operation on the associated recorder, updating the state of the engine
 * based on the recorded history of commands.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 2.0
 */
public class ReplayCommand implements Command {

    private final Recorder recorder;

    /**
     * Constructs a {@code ReplayCommand} with the specified recorder.
     *
     * @param recorder The recorder associated with this replay command.
     */
    public ReplayCommand(Recorder recorder) {
        this.recorder = recorder;
    }

    /**
     * Executes the replay operation on the associated recorder, updating the state of the engine
     * based on the recorded history of commands.
     */
    @Override
    public void execute() {
        recorder.replay();
    }
}
