package com.fane;

import java.util.HashMap;
import java.util.Map;

public class Invoker {
    private Map<String, Command> commands = new HashMap<>();

    public void addCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }

    public void executeCommand(Command command) {
        if (command != null) {
            command.execute();
        } else {
            System.out.println("Command is not valid.");
        }
    }
}
