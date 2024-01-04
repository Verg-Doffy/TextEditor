import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class V2_ChangeSelectionCommandReplayingTest {
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
