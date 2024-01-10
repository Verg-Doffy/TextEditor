package com.fane.Back_End.packageV1;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV2.*;

/**
 * The {@code Command} interface represents a generic command that can be executed.
 * Implementing classes should provide specific behavior in the {@link #execute()} method.
 * So that for each action of our text editor, it is called by the {@link #execute()} method.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 2.0
 */
public interface Command {

    /**
     * Executes the specific behavior associated with this command.
     * Implementing classes should define the actions to be taken when the command is executed.
     */
    void execute();
}
