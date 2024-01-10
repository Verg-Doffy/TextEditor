package com.fane.packageV1; 
//import com.fane.packageV0.*;

/**
 * The {@code Command} interface represents a generic command that can be executed.
 * Implementing classes should provide specific behavior in the {@link #execute()} method.
 * So that for each action of our text editor, it is called by the {@link #execute()} method.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 1.0
 */

public interface Command {

    /**
     * Executes the specific behavior associated with this command.
     * Implementing classes should define the actions to be taken when the command is executed.
     */
    void execute();
}
