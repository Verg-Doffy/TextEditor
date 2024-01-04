package com.fane.Back_End.packageV2;
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class Recorder {
    private List<Pair<Recordable, Memento>> history;
    private boolean isRecording = false;
    private Engine engine;
    private boolean isReplaying = false;

    public Recorder(Engine engine) {
        this.history = new ArrayList<>();
        this.engine = engine;
    }

    public void start() {
        if (!isRecording) {
            history.clear(); // Clear any previous recordings
            isRecording = true;
            System.out.println("Recording started");
        }
    }

    public void stop() {
        if (isRecording) {
            isRecording = false;
            System.out.println("Recording stopped");
        }
    }

    public boolean isRecording() {
        return isRecording;
    }

    public boolean isReplaying() {
        return isReplaying;
    }

    public void replay() {
        System.out.println("Current State before Replay: " + engine.getBufferContents());
        isReplaying = true;

        for (Pair<Recordable, Memento> pair : history) {
            Recordable command = pair.getLeft();
            command.setMemento(pair.getRight());
            command.execute();

        }

        isReplaying = false;
        System.out.println("New State after Replay: " + engine.getBufferContents());
        System.out.println("Clipboard content after Replay: " + engine.getClipboardContents());
    }

    public void save(Recordable command) {
        if (isRecording) {
            history.add(Pair.of(command, command.getMemento()));
        }
    }

}
