import com.fane.Back_End.packageV0.*; 
import com.fane.Back_End.packageV1.*; 
import com.fane.Back_End.packageV2.*; 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Test_V2_InsertCommandReplayingTest {

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
    }

    @Test
    void testInsertCommand() {
        // Insert text into the buffer before recording starts
        invoker.setText("Merci");
        invoker.executeCommand("insert");
        assertEquals("Merci", engine.getBufferContents(), "Buffer should contain 'Merci' after initial insert.");

        // Start recording
        invoker.executeCommand("start");

        // Insert while recording
        invoker.setText(" Beaucoup");
        invoker.setBeginIndex(5);
        invoker.setEndIndex(5);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("insert");
        assertEquals("Merci Beaucoup", engine.getBufferContents(),
                "Buffer should contain 'Merci Beaucoup' after insertion during recording.");

        // Stop recording
        invoker.executeCommand("stop");

        // Insert after recording stops
        invoker.setText(" FANE");
        invoker.setBeginIndex(engine.getBufferContents().length());
        invoker.setEndIndex(engine.getBufferContents().length());
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("insert");
        assertEquals("Merci Beaucoup FANE", engine.getBufferContents(),
                "Buffer should contain 'Merci Beaucoup FANE' after insertion post-recording.");

        // Replay the recorded actions
        invoker.executeCommand("replay");
        assertEquals("Merci Beaucoup Beaucoup FANE", engine.getBufferContents(),
                "Buffer should contain 'Merci Beaucoup Beaucoup FANE' after replay.");
    }
}
