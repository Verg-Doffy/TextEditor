package TestV3;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;
import com.fane.Back_End.packageV3.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InsertCommandUndoredoFailureTest {
    private Engine engine;
    private Recorder recorder;
    private Invoker invoker;

    @BeforeEach
    void setUp() {

        // Create the main components
        engine = new EngineImpl();
        recorder = new Recorder(engine);
        invoker = new Invoker(recorder);

        // Add commands to the invoker
        invoker.addCommand("insert", new InsertCommand(engine, invoker, recorder));
        invoker.addCommand("changeSelection", new ChangeSelectionCommand(engine,
                invoker, recorder));
        invoker.addCommand("copy", new CopyCommand(engine, recorder));
        invoker.addCommand("cut", new CutCommand(engine, recorder));
        invoker.addCommand("paste", new PasteCommand(engine, recorder));
        invoker.addCommand("delete", new DeleteCommand(engine, recorder));
        invoker.addCommand("start", new StartCommand(recorder));
        invoker.addCommand("stop", new StopCommand(recorder));
        invoker.addCommand("replay", new ReplayCommand(recorder));
        invoker.addCommand("undo", new UndoCommand(recorder));
        invoker.addCommand("redo", new RedoCommand(recorder));

    }

    @Test
    void testinsertCommandUndoRedo() {
        // verify the starting state of the test environment.(if buffer and clipboard
        // are empty at beginning )
        assertEquals("", engine.getBufferContents(), "Buffer should be empty at beginning");
        assertEquals("", engine.getClipboardContents(), "Clipboard should be empty at beginning");
        assertEquals(0, engine.getSelection().getBeginIndex(), "begin index should be at 0");
        assertEquals(0, engine.getSelection().getEndIndex(), "end index should be at 0");

        // Insertion
        invoker.setText("Merci");
        invoker.executeCommand("insert");
        assertEquals("Merci", engine.getBufferContents(), "Buffer should contain 'Merci'");

        // Change the selection before insertion
        invoker.setText(" mon grand");
        invoker.setBeginIndex(5);
        invoker.setEndIndex(5);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("insert");
        // verify the buffer state
        assertEquals("Merci mon grand", engine.getBufferContents(), "Buffer should contain 'Merci mon grand'");

        // Change the selection before insertion
        invoker.setText(" Beaucoup");
        invoker.setBeginIndex(15);
        invoker.setEndIndex(15);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("insert");
        // verify the buffer state
        assertEquals("Merci mon grand Beaucoup", engine.getBufferContents(),
                "Buffer should contain 'Merci mon grand Beaucoup'");

        // Testing undo functionality
        invoker.executeCommand("undo");
        invoker.executeCommand("undo");
        assertEquals("Merci mon grand", engine.getBufferContents(), "Buffer should contain 'Merci mon grand'");
        assertEquals("", engine.getClipboardContents(), "Clipboard should be empty");
        assertEquals(5, engine.getSelection().getBeginIndex(), "begin index should be at 5");
        assertEquals(15, engine.getSelection().getEndIndex(), "end index should be at 15");

        invoker.executeCommand("undo");
        invoker.executeCommand("undo");
        assertEquals("Merci", engine.getBufferContents(), "Buffer should contain 'Merci'");
        assertEquals("", engine.getClipboardContents(), "Clipboard should be empty");
        assertEquals(0, engine.getSelection().getBeginIndex(), "begin index should be at 0");
        assertEquals(5, engine.getSelection().getEndIndex(), "end index should be at 5");

        invoker.executeCommand("undo");
        invoker.executeCommand("undo");
        assertEquals("", engine.getBufferContents(), "Buffer should be empty");
        assertEquals("", engine.getClipboardContents(), "Clipboard should be empty");
        assertEquals(0, engine.getSelection().getBeginIndex(), "begin index should be at 0");
        assertEquals(0, engine.getSelection().getEndIndex(), "end index should be at 0");

        // Testing redo functionality
        invoker.executeCommand("redo");
        invoker.executeCommand("redo");
        assertEquals("Merci", engine.getBufferContents(), "Buffer should contain 'Merci'");
        assertEquals("", engine.getClipboardContents(), "Clipboard should be empty");
        assertEquals(5, engine.getSelection().getBeginIndex(), "begin index should be at 5");
        assertEquals(5, engine.getSelection().getEndIndex(), "end index should be at 5");

        invoker.executeCommand("redo");
        invoker.executeCommand("redo");
        assertEquals("Merci mon grand", engine.getBufferContents(), "Buffer should contain 'Merci mon grand'");
        assertEquals("", engine.getClipboardContents(), "Clipboard should be empty");
        assertEquals(15, engine.getSelection().getBeginIndex(), "begin index should be at 5");
        assertEquals(15, engine.getSelection().getEndIndex(), "end index should be at 15");

        invoker.executeCommand("redo");
        invoker.executeCommand("redo");
        assertEquals("Merci mon grand Beaucoup", engine.getBufferContents(),
                "Buffer should contain 'Merci mon grand Beaucoup'");
        assertEquals("", engine.getClipboardContents(), "Clipboard should be empty");
        assertEquals(15, engine.getSelection().getBeginIndex(), "begin index should be at 15");
        assertEquals(24, engine.getSelection().getEndIndex(), "end index should be at 24");

        invoker.executeCommand("undo");
        invoker.executeCommand("undo");
        assertEquals("Merci mon grand", engine.getBufferContents(), "Buffer should contain 'Merci mon grand'");
        assertEquals("", engine.getClipboardContents(), "Clipboard should be empty");
        assertEquals(15, engine.getSelection().getBeginIndex(), "begin index should be at 5");
        assertEquals(15, engine.getSelection().getEndIndex(), "end index should be at 15");

    }

}
