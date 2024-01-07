package com.fane.Back_End.packageV2;
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;

/**
 * The {@code Originator} interface represents an object that can create and restore
 * its own memento objects. It is typically used in conjunction with the {@link Memento}
 * interface to support undo and redo operations.
 *
 * Implementations of this interface should provide methods for getting and setting the
 * memento, allowing the object to capture and restore its internal state as needed.
 *
 * @author Mohamed AL AFTAN & Djakaridja FANE
 * @version 1.0
 * @see com.fane.Back_End.packageV0
 * @see com.fane.Back_End.packageV1
 * @see Memento
 */
public interface Originator {

    /**
     * Retrieves a memento representing the current state of the originator.
     *
     * @return A memento capturing the current state of the originator.
     */
    Memento getMemento();

    /**
     * Restores the state of the originator based on the provided memento.
     *
     * @param memento The memento containing information about the desired state.
     */
    void setMemento(Memento memento);
}
