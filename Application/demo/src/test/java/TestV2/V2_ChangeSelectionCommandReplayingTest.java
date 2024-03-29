package TestV2;
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** 
 * Test class for version 2 (V2) of the text editor application.
 * This class focuses on testing the behavior of the ChangeSelectionCommand and its replaying functionality
 * in coordination with the Invoker, Engine, and Recorder components.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 3.0
 */

public class V2_ChangeSelectionCommandReplayingTest {
    private Engine engine;
    private Recorder recorder;
    private Invoker invoker;

    /**
     * Set up the test environment by creating the main components - Engine, Recorder, and Invoker.
     * Add commands to the invoker for testing.
     */
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

    /**
     * Test the ChangeSelectionCommand and its replaying functionality.
     * It covers scenarios where the selection changes before recording, during recording, after recording,
     * and replays to verify the correct selection indices.
     */
    @Test
    void testChangeSelectionAndReplay() {
        // Insert text
        invoker.setText("Merci Beaucoup");
        invoker.executeCommand("insert");

        // Change selection before recording start
        invoker.setBeginIndex(3);
        invoker.setEndIndex(8);
        invoker.executeCommand("changeSelection");

        // Start recording, change selection, and stop recording
        invoker.executeCommand("start");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(5);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("stop");

        // Change selection after recording stop
        invoker.setBeginIndex(6);
        invoker.setEndIndex(10);
        invoker.executeCommand("changeSelection");

        // Replay and verify selection indices
        invoker.executeCommand("replay");
        assertEquals(0, engine.getSelection().getBeginIndex(), "Begin index should match after replay.");
        assertEquals(5, engine.getSelection().getEndIndex(), "End index should match after replay.");
    }
}
