package com.fane.Back_End.packageV2;
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;

/**
 * The {@code InsertMemento} class represents a memento object specifically designed
 * for capturing the state related to an insert operation. It implements the {@link Memento} interface.
 *
 *
 * The memento stores information about the text to be inserted during an insert operation.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 2.0
 */
public class InsertMemento implements Memento {

    private final String textToInsert;

    /**
     * Constructs an {@code InsertMemento} with the specified text to be inserted.
     *
     * @param textToInsert The text to be inserted.
     */
    public InsertMemento(String textToInsert) {
        this.textToInsert = textToInsert;
    }

    /**
     * Retrieves the text that was stored for insertion in this memento.
     *
     * @return The text to be inserted.
     */
    public String getTextToInsert() {
        return textToInsert;
    }
}
