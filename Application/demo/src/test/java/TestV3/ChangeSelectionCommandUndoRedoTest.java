package TestV3;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;
import com.fane.Back_End.packageV3.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChangeSelectionCommandUndoRedoTest {
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
        invoker.addCommand("changeSelection", new ChangeSelectionCommand(engine, invoker, recorder));
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
    void testchangeSelectionCommandUndoRedo() {
        // verify the starting state of the test environment.(if buffer and clipboard
        // are empty at beginning )
        assertEquals("", engine.getBufferContents(), "Buffer should be empty at beginning");
        assertEquals("", engine.getClipboardContents(), "Clipboard should be empty at beginning");
        assertEquals(0, engine.getSelection().getBeginIndex(), "begin index should be at 0");
        assertEquals(0, engine.getSelection().getEndIndex(), "end index should be at 0");

        // Executing a insert command.
        invoker.setText("Merci mon grand Beaucoup");
        invoker.executeCommand("insert");

        // Verifying the state after executing the commands.
        assertEquals("Merci mon grand Beaucoup", engine.getBufferContents(),
                "Buffer should contain 'Merci mon grand Beaucoup'");
        assertEquals(0, engine.getSelection().getBeginIndex(), "begin index should be at 0");
        assertEquals(24, engine.getSelection().getEndIndex(), "end index should be at 24");

        // Testing the undo functionality.
        invoker.executeCommand("undo");
        assertEquals("", engine.getBufferContents(),
                "Buffer should contain ''");
        assertEquals(0, engine.getSelection().getBeginIndex(), "begin index should be at 0");
        assertEquals(0, engine.getSelection().getEndIndex(), "end index should be at 0");

        // Testing the redo functionality.
        invoker.executeCommand("redo");
        assertEquals("Merci mon grand Beaucoup", engine.getBufferContents(),
                "Buffer should contain 'Merci mon grand Beaucoup'");
        assertEquals(0, engine.getSelection().getBeginIndex(), "begin index should be at 0");
        assertEquals(24, engine.getSelection().getEndIndex(), "end index should be at 24");

    }
}
