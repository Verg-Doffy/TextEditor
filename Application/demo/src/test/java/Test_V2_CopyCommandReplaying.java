import com.fane.Back_End.packageV0.*; 
import com.fane.Back_End.packageV1.*; 
import com.fane.Back_End.packageV2.*; 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Test_V2_CopyCommandReplaying {
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
    void testCopyCommand() {
        // Insert some text into the buffer
        invoker.setText("GlablagBlag");
        invoker.executeCommand("insert");

        // Select a portion of the text and copy it
        invoker.setBeginIndex(0);
        invoker.setEndIndex(3);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("copy");

        // Check if the clipboard contains the copied text
        assertEquals("Gla", engine.getClipboardContents(), "Clipboard should contain 'Gla' after copy.");

        // Start recording
        invoker.executeCommand("start");

        // Copy another portion of the text during recording
        invoker.setBeginIndex(7);
        invoker.setEndIndex(engine.getBufferContents().length());
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("copy");

        // Stop recording
        invoker.executeCommand("stop");

        // Check if the clipboard contains the newly copied text
        assertEquals("Blag", engine.getClipboardContents(),
                "Clipboard should contain 'Blag' after copy during recording.");

        // Copy another portion of text after recording
        invoker.setBeginIndex(3);
        invoker.setEndIndex(7);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("copy");
        // Check if the clipboard contains the newly copied text
        assertEquals("blag", engine.getClipboardContents(),
                "Clipboard should contain 'Blag' after copy during recording.");

        // Replay recorded actions
        invoker.executeCommand("replay");

        // Verify clipboard content after replay
        assertEquals("Blag", engine.getClipboardContents(), "Clipboard should contain 'Blag' after replay.");
    }
}
