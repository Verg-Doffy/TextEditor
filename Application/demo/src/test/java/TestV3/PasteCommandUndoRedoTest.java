package TestV3;

import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;
import com.fane.Back_End.packageV3.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasteCommandUndoRedoTest {
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
    void testpasteCommandUndoRedo() {
        // verify the starting state of the test environment.(if buffer and clipboard
        // are empty at beginning )
        assertEquals("", engine.getBufferContents(), "Buffer should be empty at beginning");
        assertEquals("", engine.getClipboardContents(), "Clipboard should be empty at beginning");
        assertEquals(0, engine.getSelection().getBeginIndex(), "begin index should be at 0");
        assertEquals(0, engine.getSelection().getEndIndex(), "end index should be at 0");

        // Insertion
        invoker.setText("Merci mon grand Beaucoup");
        invoker.executeCommand("insert");
        assertEquals("Merci mon grand Beaucoup", engine.getBufferContents(),
                "Buffer should contain 'Merci mon grand Beaucoup'");

        // Change the selection before copying
        invoker.setBeginIndex(0);
        invoker.setEndIndex(5);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("copy");

        // Verify the state after commands execution
        assertEquals("Merci mon grand Beaucoup", engine.getBufferContents(),
                "Buffer should contain 'Merci mon grand Beaucoup'");
        assertEquals("Merci", engine.getClipboardContents(), "Clipboard should contain 'Merci'");
        assertEquals(0, engine.getSelection().getBeginIndex(), "begin index should be at 0");
        assertEquals(5, engine.getSelection().getEndIndex(), "end index should be at 5");

        // Change the selection before pasting
        invoker.setBeginIndex(5);
        invoker.setEndIndex(5);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("paste");

        // Verify the state after commands execution
        assertEquals("MerciMerci mon grand Beaucoup", engine.getBufferContents(),
                "Buffer should contain 'MerciMerci mon grand Beaucoup'");
        assertEquals("Merci", engine.getClipboardContents(), "Clipboard should contain 'Merci'");
        assertEquals(5, engine.getSelection().getBeginIndex(), "begin index should be at 0");
        assertEquals(10, engine.getSelection().getEndIndex(), "end index should be at 5");

        // Testing undo functionality
        invoker.executeCommand("undo");
        invoker.executeCommand("undo");
        assertEquals("Merci mon grand Beaucoup", engine.getBufferContents(),
                "Buffer should contain 'Merci mon grand Beaucoup'");
        assertEquals("Merci", engine.getClipboardContents(), "Clipboard should contain'Merci'");
        assertEquals(0, engine.getSelection().getBeginIndex(), "begin index should be at 0");
        assertEquals(5, engine.getSelection().getEndIndex(), "end index should be at 5");

        // Testing redo functionality
        invoker.executeCommand("redo");
        invoker.executeCommand("redo");
        assertEquals("MerciMercigrand Beaucoup", engine.getBufferContents(),
                "Buffer should contain 'MerciMerci mon grand Beaucoup'");
        assertEquals("Merci", engine.getClipboardContents(), "Clipboard should contain'Merci'");
        assertEquals(5, engine.getSelection().getBeginIndex(), "begin index should be at 0");
        assertEquals(10, engine.getSelection().getEndIndex(), "end index should be at 0");

    }
}
