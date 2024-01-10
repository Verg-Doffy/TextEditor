package com.fane.Back_End.packageV2;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV3.*;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The {@code Recorder} class is responsible for recording and replaying commands executed on an {@link Engine}.
 * It works in conjunction with classes implementing the {@link Recordable} interface and mementos
 * that implement the {@link Memento} interface.
 *
 * The recorder maintains a history of executed commands along with their corresponding mementos during recording.
 * It also provides functionality to start and stop recording, replay the recorded commands, and save commands during recording.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 3.0
 */
public class Recorder {

    private List<Pair<Recordable, Memento>> history;
    private boolean isRecording = false;
    private boolean isReplaying = false;
    private boolean isUndoing = false;
    private boolean isRedoing = false;
    private Engine engine;
    private Stack<Pair<Recordable, Memento>> undoStack;
    private Stack<Pair<Recordable, Memento>> redoStack;

    /**
     * Constructs a {@code Recorder} with the specified {@link Engine}.
     *
     * @param engine The engine to which commands are applied and recorded.
     */
    public Recorder(Engine engine) {
        this.history = new ArrayList<>();
        this.engine = engine;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
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
     * Checks if the recorder is currently in undoing mode.
     *
     * @return {@code true} if undoing, {@code false} otherwise.
     */
    public boolean isUndoing() {
        return isUndoing;
    }

    /**
     * Checks if the recorder is currently in redoing mode.
     *
     * @return {@code true} if redoing, {@code false} otherwise.
     */
    public boolean isRedoing() {
        return isRedoing;
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
    public void saveForReplay(Recordable command) {
        // Only save for replay if we're recording and not undoing, redoing, or
        // replaying
        if (isRecording && !isUndoing && !isRedoing && !isReplaying) {
            history.add(Pair.of(command, command.getMemento()));
        }
    }

    /**
     * Saves the provided command along with its memento to the undo stack.
     *
     * @param command The command to be saved.
     */
    public void save(Recordable command) {
        // Only save if we're not undoing, redoing, or replaying
        if (!isUndoing && !isRedoing && !isReplaying) {
            undoStack.push(Pair.of(command, command.getMemento()));
            redoStack.clear(); // Clear redo stack whenever a new command is executed
        }
    }

    /**
     * Undoes the last recorded command.
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            isUndoing = true; // Set isUndoing to true before undo operation
            Pair<Recordable, Memento> lastCommand = undoStack.pop();
            lastCommand.getLeft().setMemento(lastCommand.getRight());
            lastCommand.getLeft().undo();
            redoStack.push(lastCommand);
            isUndoing = false; // Reset isUndoing after undo operation
        }
    }

    /**
     * Redoes the last undone command.
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            isRedoing = true; // Set isRedoing to true before redo operation
            Pair<Recordable, Memento> lastUndoneCommand = redoStack.pop();
            lastUndoneCommand.getLeft().setMemento(lastUndoneCommand.getRight());
            lastUndoneCommand.getLeft().execute();
            undoStack.push(lastUndoneCommand);
            isRedoing = false; // Reset isRedoing after redo operation
        }
    }
}
