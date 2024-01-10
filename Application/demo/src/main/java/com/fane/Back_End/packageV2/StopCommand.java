package com.fane.Back_End.packageV2;
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;

/**
 * The {@code StopCommand} class represents a command that triggers the stop of recording
 * using a provided {@link Recorder}. It implements the {@link Command} interface.
 *
 * The stop command executes the stop recording operation on the associated recorder,
 * indicating that recording should cease, and subsequent commands will not be captured.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 2.0
 */
public class StopCommand implements Command {

    private final Recorder recorder;

    /**
     * Constructs a {@code StopCommand} with the specified recorder.
     *
     * @param recorder The recorder associated with this stop command.
     */
    public StopCommand(Recorder recorder) {
        this.recorder = recorder;
    }

    /**
     * Executes the stop recording operation on the associated recorder,
     * indicating that recording should cease, and subsequent commands will not be captured.
     */
    @Override
    public void execute() {
        recorder.stop();
    }
}
