package com.fane.Back_End.packageV2;
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV3.*;


/**
 * The {@code StartCommand} class represents a command that triggers the start of recording
 * using a provided {@link Recorder}. It implements the {@link Command} interface.
 *
 * The start command executes the start recording operation on the associated recorder,
 * allowing the recorder to capture and store subsequent commands in its history.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 3.0
 */
public class StartCommand implements Command {

    private final Recorder recorder;

    /**
     * Constructs a {@code StartCommand} with the specified recorder.
     *
     * @param recorder The recorder associated with this start command.
     */
    public StartCommand(Recorder recorder) {
        this.recorder = recorder;
    }

    /**
     * Executes the start recording operation on the associated recorder,
     * allowing the recorder to capture and store subsequent commands in its history.
     */
    @Override
    public void execute() {
        recorder.start();
    }
}
