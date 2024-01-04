package com.fane.Back_End.packageV2;
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;

public class StopCommand implements Command {
    private final Recorder recorder;

    public StopCommand(Recorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        recorder.stop();
    }
}

