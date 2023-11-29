package com.fane;

import java.util.HashMap;
import java.util.Map;

public class Invoker {
    private Map<String, Command> commands = new HashMap<>();
    private String text;
    private int newBeginIndex;
    private int newEndIndex;

    public void addCommand(String key, Command command) {
        commands.put(key, command);
    }

    public void executeCommand(String key) {
        Command command = commands.get(key);
        if (command != null) {
            command.execute();
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBeginIndex() {
        return newBeginIndex;
    }

    public int getEndIndex() {
        return newEndIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.newBeginIndex = beginIndex;
    }

    public void setEndIndex(int endIndex) {
        this.newEndIndex = endIndex;
    }
}
