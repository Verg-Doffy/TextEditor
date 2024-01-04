import com.fane.Back_End.packageV0.*; 
import com.fane.Back_End.packageV1.*; 
import com.fane.Back_End.packageV2.*; 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Test_V2_DeleteCommandReplaying {
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
    void testDeleteAndReplay() {
        // Insert text into the buffer
        invoker.setText("MerciMerciMerci Beaucoup");
        invoker.executeCommand("insert");
        assertEquals("MerciMerciMerci Beaucoup", engine.getBufferContents(),
                "Buffer should contain initial text after insert.");

        // Start recording
        invoker.executeCommand("start");

        // Delete a portion of the text while recording
        invoker.setBeginIndex(0);
        invoker.setEndIndex(5);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("delete");
        assertEquals("MerciMerci Beaucoup", engine.getBufferContents(),
                "Buffer should contain updated text after delete.");

        // Stop recording
        invoker.executeCommand("stop");

        // Modify the buffer content after recording
        invoker.setText("carte");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(0);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("insert");
        assertEquals("carteMerciMerci Beaucoup", engine.getBufferContents(),
                "Buffer should contain updated text after insert.");

        // Replay the recorded actions
        invoker.executeCommand("replay");
        assertEquals("MerciMerci Beaucoup", engine.getBufferContents(),
                "Buffer should contain final text after replay.");
    }

}
