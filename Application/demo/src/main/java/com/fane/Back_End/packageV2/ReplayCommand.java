package com.fane.Back_End.packageV2;
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;

public class ReplayCommand implements Command {
    private final Recorder recorder;

    public ReplayCommand(Recorder recorder) {
        this.recorder = recorder;

    }

    @Override
    public void execute() {
        recorder.replay();
    }
}
