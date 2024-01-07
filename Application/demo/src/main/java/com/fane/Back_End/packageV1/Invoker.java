package com.fane.Back_End.packageV1;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV2.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code Invoker} class represents an invoker that manages and executes commands.
 *
 * The invoker maintains a collection of commands mapped to unique keys and allows the execution
 * of specific commands identified by their keys. It also provides functionality to manage text,
 * including setting and getting text content as well as manipulating text indices.
 *
 * The class supports integration with a {@link Recorder} for recording and saving commands that
 * implement the {@link Recordable} interface.
 *
 * @author Mohamed AL AFTAN & Djakaridja FANE
 * @version 1.0
 * @see com.fane.Back_End.packageV0
 * @see com.fane.Back_End.packageV2
 * @see Command
 * @see Recorder
 * @see Recordable
 */
public class Invoker {

    private Map<String, Command> commands = new HashMap<>();
    private Recorder recorder; // we need a reference to the Recorder
    private String text;
    private int newBeginIndex;
    private int newEndIndex;

    /**
     * Constructs an {@code Invoker} with a reference to the provided {@link Recorder}.
     *
     * @param recorder The recorder to be associated with this invoker.
     */
    public Invoker(Recorder recorder) {
        this.recorder = recorder;
    }

    /**
     * Adds a command to the invoker's collection with the specified key.
     *
     * @param key     The unique key associated with the command.
     * @param command The command to be added.
     */
    public void addCommand(String key, Command command) {
        commands.put(key, command);
    }

    /**
     * Executes the command associated with the specified key.
     * If the command is {@link Recordable} and the recorder is in recording state,
     * the command is saved using the recorder.
     *
     * @param key The key associated with the command to be executed.
     */
    public void executeCommand(String key) {
        Command command = commands.get(key);
        if (command != null) {
            if (command instanceof Recordable && recorder.isRecording()) {
                recorder.save((Recordable) command);
            }
            command.execute();
        }
    }

    /**
     * Gets the current text content managed by the invoker.
     *
     * @return The current text content.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text content to be managed by the invoker.
     *
     * @param text The new text content.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the current beginning index for text manipulation.
     *
     * @return The current beginning index.
     */
    public int getBeginIndex() {
        return newBeginIndex;
    }

    /**
     * Gets the current ending index for text manipulation.
     *
     * @return The current ending index.
     */
    public int getEndIndex() {
        return newEndIndex;
    }

    /**
     * Sets the beginning index for text manipulation.
     *
     * @param beginIndex The new beginning index.
     */
    public void setBeginIndex(int beginIndex) {
        this.newBeginIndex = beginIndex;
    }

    /**
     * Sets the ending index for text manipulation.
     *
     * @param endIndex The new ending index.
     */
    public void setEndIndex(int endIndex) {
        this.newEndIndex = endIndex;
    }
}
