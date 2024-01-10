package com.fane.Back_End.packageV2;
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV3.*;


/**
 * The {@code ChangeSelectionMemento} class represents a memento object used to store the state of a selection change
 * in the context of an {@link Engine}. It implements the {@link Memento} interface.
 *
 * The memento captures the beginning and ending indices of a selection in an engine at a specific point in time.
 * It is typically used in the context of the command pattern to support undo and redo operations.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 3.0
 */
public class ChangeSelectionMemento implements Memento {

    private final int beginIndex;
    private final int endIndex;

    /**
     * Constructs a {@code ChangeSelectionMemento} with the specified beginning and ending indices.
     *
     * @param beginIndex The starting index of the selection.
     * @param endIndex   The ending index of the selection.
     */
    public ChangeSelectionMemento(int beginIndex, int endIndex) {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    /**
     * Gets the beginning index of the selection stored in this memento.
     *
     * @return The beginning index of the selection.
     */
    public int getBeginIndex() {
        return beginIndex;
    }

    /**
     * Gets the ending index of the selection stored in this memento.
     *
     * @return The ending index of the selection.
     */
    public int getEndIndex() {
        return endIndex;
    }
}
