package com.fane.packageV1; 
//import com.fane.packageV0.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code Invoker} class represents an invoker that manages commands and their execution.
 * 
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 1.0
 */

public class Invoker {
    private Map<String, Command> commands = new HashMap<>();
    private String text;
    private int newBeginIndex;
    private int newEndIndex;

    /**
     * Adds a command with the specified key to the invoker.
     *
     * @param key     The key associated with the command.
     * @param command The command to be added.
     */
    public void addCommand(String key, Command command) {
        commands.put(key, command);
    }

    /**
     * Executes the command associated with the given key.
     *
     * @param key The key of the command to be executed.
     */
    public void executeCommand(String key) {
        Command command = commands.get(key);
        if (command != null) {
            command.execute();
        }
    }

    /**
     * Gets the text associated with the invoker.
     *
     * @return The text.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text associated with the invoker.
     *
     * @param text The text to be set.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the beginning index associated with the invoker.
     *
     * @return The beginning index.
     */
    public int getBeginIndex() {
        return newBeginIndex;
    }

    /**
     * Gets the ending index associated with the invoker.
     *
     * @return The ending index.
     */
    public int getEndIndex() {
        return newEndIndex;
    }

    /**
     * Sets the beginning index associated with the invoker.
     *
     * @param beginIndex The beginning index to be set.
     */
    public void setBeginIndex(int beginIndex) {
        this.newBeginIndex = beginIndex;
    }

    /**
     * Sets the ending index associated with the invoker.
     *
     * @param endIndex The ending index to be set.
     */
    public void setEndIndex(int endIndex) {
        this.newEndIndex = endIndex;
    }
}