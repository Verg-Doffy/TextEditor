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
        invoker.addCommand("cut", new CutCommand(engine));
        invoker.addCommand("paste", new PasteCommand(engine));
        invoker.addCommand("copy", new CopyCommand(engine));
        invoker.addCommand("insert", new InsertCommand(engine, invoker));
        invoker.addCommand("delete", new DeleteCommand(engine));
        invoker.addCommand("changeSelection", new ChangeSelectionCommand(engine, invoker));
    }

    @Test
    public void testInsertCommand() {
        invoker.setText("Bonjour, Comment ça va?");
        invoker.executeCommand("insert");
        assertEquals("Bonjour, Comment ça va?", engine.getBufferContents());
    }

    @Test
    public void testCopyCommand() {
        invoker.setText("Hello World");
        invoker.executeCommand("insert");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(5);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("copy");
        assertEquals("Hello", engine.getClipboardContents());
        assertEquals("Hello World", engine.getBufferContents());
    }

    @Test
    public void testPasteCommand1() {
        invoker.setText("Hello World");
        invoker.executeCommand("insert");
        invoker.setBeginIndex(6);
        invoker.setEndIndex(11);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("cut");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(0);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("paste");
        assertEquals("WorldHello ", engine.getBufferContents());
    }

    @Test
    public void testPasteCommand2() {
        invoker.setText("Bonjour, Comment ça va?");
        invoker.executeCommand("insert");
        invoker.setBeginIndex(9);
        invoker.setEndIndex(engine.getBufferContents().length());
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("cut");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(0);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("paste");
        assertEquals("Comment ça va?Bonjour, ", engine.getBufferContents());
    }

    @Test
    public void testDeleteCommand() {
        invoker.setText("Bonjour, Comment ça va?");
        invoker.executeCommand("insert");
        invoker.setBeginIndex(0);
        invoker.setEndIndex(9);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("delete");
        assertEquals("Comment ça va?", engine.getBufferContents());
    }

    @Test
    public void testCutCommand() {
        invoker.setText("Bonjour, Comment ça va?");
        invoker.executeCommand("insert");
        invoker.setBeginIndex(9);
        invoker.setEndIndex(16);
        invoker.executeCommand("changeSelection");
        invoker.executeCommand("cut");
        assertEquals("Bonjour,  ça va?", engine.getBufferContents());
        assertEquals("Comment", engine.getClipboardContents());
    }
}
