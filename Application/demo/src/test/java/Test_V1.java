import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.fane.Back_End.packageV0.*; 
import com.fane.Back_End.packageV1.*; 
import com.fane.Back_End.packageV2.*; 

public class Test_V1 {

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
    public void testMainCommandsV2() {
        invoker.executeCommand("start");

        invoker.setText("Helloworld");
        invoker.executeCommand("insert");
        assertEquals("Helloworld", engine.getBufferContents(), "Buffer should contain 'Helloworld'");

        invoker.setBeginIndex(0);
        invoker.setEndIndex(5);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("cut");
        assertEquals("world", engine.getBufferContents(), "Buffer should contain 'world'");
        assertEquals("Hello", engine.getClipboardContents(), "clipboard should contain 'Hello'");

        invoker.setBeginIndex(5);
        invoker.setEndIndex(5);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("paste");
        assertEquals("worldHello", engine.getBufferContents(), "Buffer should contain 'worldHello'");
        assertEquals("Hello", engine.getClipboardContents(), "clipboard should contain 'Hello'");

        invoker.setBeginIndex(0);
        invoker.setEndIndex(5);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("delete");
        assertEquals("Hello", engine.getBufferContents(), "Buffer should contain 'Hello'");
        assertEquals("Hello", engine.getClipboardContents(), "clipboard should contain 'Hello'");

        invoker.executeCommand("stop");

        assertEquals("Hello", engine.getBufferContents(), "Buffer should contain 'Hello' before replaying");
        assertEquals("Hello", engine.getClipboardContents(), "clipboard should contain 'Hello' before replaying");

        invoker.executeCommand("replay");

        // After replay, the buffer should contain "HelloHello"
        assertEquals("HelloHello", engine.getBufferContents(), "Buffer should contain 'HelloHello' after replaying");
        assertEquals("Hello", engine.getClipboardContents(), "clipboard should contain 'Hello' after replaying");

    }

}