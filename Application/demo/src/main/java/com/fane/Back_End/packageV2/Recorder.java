package com.fane.Back_End.packageV2;
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Recorder} class is responsible for recording and replaying commands executed on an {@link Engine}.
 * It works in conjunction with classes implementing the {@link Recordable} interface and mementos
 * that implement the {@link Memento} interface.
 *
 * The recorder maintains a history of executed commands along with their corresponding mementos during recording.
 * It also provides functionality to start and stop recording, replay the recorded commands, and save commands during recording.
 *
 * @author Mohamed AL AFTAN & Djakaridja FANE
 * @version 1.0
 * @see com.fane.Back_End.packageV0.Engine
 * @see com.fane.Back_End.packageV0.Recordable
 * @see com.fane.Back_End.packageV0.Memento
 */
public class Recorder {

    private List<Pair<Recordable, Memento>> history;
    private boolean isRecording = false;
    private boolean isReplaying = false;
    private Engine engine;

    /**
     * Constructs a {@code Recorder} with the specified {@link Engine}.
     *
     * @param engine The engine to which commands are applied and recorded.
     */
    public Recorder(Engine engine) {
        this.history = new ArrayList<>();
        this.engine = engine;
    }

    /**
     * Starts recording commands. Clears any previous recordings.
     */
    public void start() {
        if (!isRecording) {
            history.clear(); // Clear any previous recordings
            isRecording = true;
            System.out.println("Recording started");
        }
    }

    /**
     * Stops recording commands.
     */
    public void stop() {
        if (isRecording) {
            isRecording = false;
            System.out.println("Recording stopped");
        }
    }

    /**
     * Checks if the recorder is currently in recording mode.
     *
     * @return {@code true} if recording, {@code false} otherwise.
     */
    public boolean isRecording() {
        return isRecording;
    }

    /**
     * Checks if the recorder is currently in replaying mode.
     *
     * @return {@code true} if replaying, {@code false} otherwise.
     */
    public boolean isReplaying() {
        return isReplaying;
    }

    /**
     * Replays the recorded commands and updates the state of the engine accordingly.
     */
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

    /**
     * Saves the provided command along with its memento to the history during recording.
     *
     * @param command The command to be saved.
     */
    public void save(Recordable command) {
        if (isRecording) {
            history.add(Pair.of(command, command.getMemento()));
        }
    }
}
