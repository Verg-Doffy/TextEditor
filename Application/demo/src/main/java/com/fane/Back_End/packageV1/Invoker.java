package com.fane.Back_End.packageV1; 
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV2.*;

import java.util.HashMap;
import java.util.Map;

public class Invoker {
    private Map<String, Command> commands = new HashMap<>();
    private Recorder recorder; // we need a reference to the Recorder
    private String text;
    private int newBeginIndex;
    private int newEndIndex;

    // Constructor where Recorder is passed as a parameter or initialized
    public Invoker(Recorder recorder) {
        this.recorder = recorder;
    }

    public void addCommand(String key, Command command) {
        commands.put(key, command);
    }

    // Overloaded executeCommand to handle both Command and key-based commands
    public void executeCommand(String key) {
        Command command = commands.get(key);
        if (command != null) {
            if (command instanceof Recordable && recorder.isRecording()) {
                recorder.save((Recordable) command);
            }
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
