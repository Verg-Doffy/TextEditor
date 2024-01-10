package TestV2;
import com.fane.Back_End.packageV0.*;
import com.fane.Back_End.packageV1.*;
import com.fane.Back_End.packageV2.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for version 2 (V2) of the text editor application.
 * This class focuses on testing the behavior of the PasteCommand and its replaying functionality
 * in coordination with the Invoker, Engine, and Recorder components.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 3.0
 */

public class V2_PasteCommandReplayingTest {
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
     * Test the PasteCommand and its replaying functionality.
     * It covers scenarios where text is copied and pasted before, during, and after recording,
     * and verifies the correctness of the buffer content after replay.
     */
    @Test
    void testPasteAndReplay() {
        invoker.setText("Helloworld");
        invoker.executeCommand("insert");
        assertEquals("Helloworld", engine.getBufferContents());

        invoker.executeCommand("start");

        invoker.setBeginIndex(0);
        invoker.setEndIndex(5);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("copy");

        invoker.setBeginIndex(engine.getBufferContents().length());
        invoker.setEndIndex(engine.getBufferContents().length());
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("paste");
        assertEquals("HelloworldHello", engine.getBufferContents());

        invoker.setBeginIndex(5);
        invoker.setEndIndex(10);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("copy");

        invoker.setBeginIndex(engine.getBufferContents().length());
        invoker.setEndIndex(engine.getBufferContents().length());
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("paste");
        assertEquals("HelloworldHelloworld", engine.getBufferContents());

        invoker.executeCommand("stop");

        // Post recording insert and copy-paste
        invoker.setText(" Salut");
        int insertIndex = engine.getBufferContents().length();
        invoker.setBeginIndex(insertIndex);
        invoker.setEndIndex(insertIndex);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("insert");
        assertEquals("HelloworldHelloworld Salut", engine.getBufferContents());

        int startIndex = engine.getBufferContents().indexOf(" Salut");
        int endIndex = startIndex + " Salut".length();
        invoker.setBeginIndex(startIndex);
        invoker.setEndIndex(endIndex);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("copy");

        invoker.setBeginIndex(engine.getBufferContents().length());
        invoker.setEndIndex(engine.getBufferContents().length());
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("paste");
        assertEquals("HelloworldHelloworld Salut Salut", engine.getBufferContents());

        // Replay recorded actions
        invoker.executeCommand("replay");
        assertEquals("HelloworldHelloworldHelloworld Salut Salut", engine.getBufferContents());
    }
}
