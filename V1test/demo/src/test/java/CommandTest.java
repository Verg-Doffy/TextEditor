import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.fane.*;

public class CommandTest {
    private EngineImpl engine;
    private Invoker invoker;

    @BeforeEach
    public void setUp() {
        engine = new EngineImpl();
        invoker = new Invoker();

        // Add commands to invoker
        invoker.addCommand("copy", new CopyCommand(engine));
        invoker.addCommand("paste", new PasteCommand(engine));
        invoker.addCommand("insert", new InsertCommand(engine, "Bonjour, Comment ça va?"));
        invoker.addCommand("delete", new DeleteCommand(engine));
        invoker.addCommand("cut", new CutCommand(engine));
    }

    @Test
    public void testCopyCommand() {
        engine.insert("Hello World");
        engine.changeSelection(0, 5);
        invoker.executeCommand("copy");
        assertEquals("Hello", engine.getClipboardContents());
        assertEquals("Hello World", engine.getBufferContents());
    }

    @Test
    public void testPasteCommand1() {
        engine.insert("Hello World");
        engine.changeSelection(6, 11);
        invoker.executeCommand("cut");
        engine.changeSelection(0, 0);
        invoker.executeCommand("paste");
        assertEquals("WorldHello ", engine.getBufferContents());
    }

    @Test
    public void testPasteCommand2() {
        invoker.executeCommand("insert");
        engine.changeSelection(9, engine.getBufferContents().length());
        invoker.executeCommand("cut");
        engine.changeSelection(0, 0);
        invoker.executeCommand("paste");
        assertEquals("Comment ça va?Bonjour, ", engine.getBufferContents());
    }

    @Test
    public void testInsertCommand() {
        engine.changeSelection(0, 0);
        invoker.executeCommand("insert");
        assertEquals("Bonjour, Comment ça va?", engine.getBufferContents());
    }

    @Test
    public void testDeleteCommand() {
        invoker.executeCommand("insert");
        engine.changeSelection(0, 9);
        invoker.executeCommand("delete");
        assertEquals("Comment ça va?", engine.getBufferContents());
    }

    @Test
    public void testCutCommand() {
        invoker.executeCommand("insert");
        engine.changeSelection(9, 16);
        invoker.executeCommand("cut");
        assertEquals("Bonjour,  ça va?", engine.getBufferContents());
        assertEquals("Comment", engine.getClipboardContents());
    }
}
