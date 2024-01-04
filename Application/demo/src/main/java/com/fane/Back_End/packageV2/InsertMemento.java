package com.fane.Back_End.packageV2;
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;

public class InsertMemento implements Memento {
    private final String textToInsert;

    public InsertMemento(String textToInsert) {
        this.textToInsert = textToInsert;
    }

    public String getTextToInsert() {
        return textToInsert;
    }
}
