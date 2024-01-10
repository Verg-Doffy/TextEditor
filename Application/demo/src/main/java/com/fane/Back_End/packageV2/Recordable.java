package com.fane.Back_End.packageV2;
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;

/**
 * The {@code Recordable} interface represents an entity that can be recorded, undoable, and redoable.
 * It extends both the {@link Command} and {@link Originator} interfaces, inheriting their functionalities.
 *
 * The {@code Recordable} interface combines the ability to execute a command (as specified by {@link Command})
 * with the ability to capture and restore its internal state (as specified by {@link Originator}). This is typically
 * used in the context of the command pattern to support undo and redo operations.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 2.0
 */
public interface Recordable extends Command, Originator {

    // This interface will inherit execute from Command
    // and getMemento, setMemento from Originator.

}
