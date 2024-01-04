package com.fane.Back_End.packageV2;
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;

public interface Recordable extends Command, Originator {
    // This interface will inherit execute from Command
    // and getMemento, setMemento from Originator.
}
