import com.fane.Back_End.packageV0.*; 
import com.fane.Back_End.packageV1.*; 
import com.fane.Back_End.packageV2.*; 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Test_V2_CutCommandReplaying {
    private Engine engine;
    private Recorder recorder;
    private Invoker invoker;

    @BeforeEach
    void setUp() {
        // Initialize the main components of the editor
        engine = new EngineImpl();
        recorder = new Recorder(engine);
        invoker = new Invoker(recorder);

        // Register commands with the invoker
        invoker.addCommand("insert", new InsertCommand(engine, invoker, recorder));
        invoker.addCommand("changeSelection", new ChangeSelectionCommand(engine, invoker, recorder));
        invoker.addCommand("copy", new CopyCommand(engine, recorder));
        invoker.addCommand("cut", new CutCommand(engine, recorder));
        invoker.addCommand("paste", new PasteCommand(engine, recorder));
        invoker.addCommand("delete", new DeleteCommand(engine, recorder));
        invoker.addCommand("start", new StartCommand(recorder));
        invoker.addCommand("stop", new StopCommand(recorder));
        invoker.addCommand("replay", new ReplayCommand(recorder));
    }

    @Test
    public void testCutCommandAndReplay() {
        // Insert initial text and verify
        invoker.setText("Blableblubliblag");
        invoker.executeCommand("insert");
        assertEquals("Blableblubliblag", engine.getBufferContents(),
                "Buffer should contain 'Blableblubliblag' after initial insertion");

        // Start recording
        invoker.executeCommand("start");

        // Perform first cut operation and verify buffer and clipboard states
        invoker.setBeginIndex(0);
        invoker.setEndIndex(3); // Select "Bla"
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("cut");
        assertEquals("bleblubliblag", engine.getBufferContents(),
                "Buffer should contain 'bleblubliblag' after first cut");
        assertEquals("Bla", engine.getClipboardContents(), "Clipboard should contain 'Bla' after first cut");

        // Perform second cut operation and verify buffer and clipboard states
        invoker.setBeginIndex(0);
        invoker.setEndIndex(3); // Select "ble"
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("cut");
        assertEquals("blubliblag", engine.getBufferContents(), "Buffer should contain 'blubliblag' after second cut");
        assertEquals("ble", engine.getClipboardContents(), "Clipboard should contain 'ble' after second cut");

        // Stop recording
        invoker.executeCommand("stop");

        // Replay recorded actions and verify final buffer and clipboard states
        invoker.executeCommand("replay");
        assertEquals("blag", engine.getBufferContents(), "Buffer should contain 'blag' after replay");
        assertEquals("bli", engine.getClipboardContents(), "Clipboard should contain 'bli' after replay");
    }
}
