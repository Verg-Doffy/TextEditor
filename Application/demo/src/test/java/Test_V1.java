import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.fane.packageV0.*; 
import com.fane.packageV1.*; 

/**
 * The {@code Test_V1} class contains JUnit tests for various commands
 * using the {@link EngineImpl} and {@link Invoker} with different command implementations.
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 1.0
 */

public class Test_V1 {
    private EngineImpl engine;
    private Invoker invoker;

    /**
     * Set up the test environment by creating an EngineImpl and an Invoker
     * with various command implementations.
     */
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

    /**
     * Test the InsertCommand by inserting a specific text and checking if the buffer contents match.
     */
    @Test
    public void testInsertCommand() {
        invoker.setText("Bonjour, Comment ça va?");
        invoker.executeCommand("insert");
        assertEquals("Bonjour, Comment ça va?", engine.getBufferContents());
    }

    /**
     * Test the CopyCommand by copying a specific range of text and checking if the clipboard contents match.
     */
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

    /**
     * Test the PasteCommand by simulating a cut operation and pasting the contents at a specific index.
     */
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

    /**
     * Test the PasteCommand by simulating a cut operation and pasting the contents at the end of the buffer.
     */
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

    /**
     * Test the DeleteCommand by deleting a specific range of text and checking if the buffer contents match.
     */
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

    /**
     * Test the CutCommand by cutting a specific range of text and checking if the buffer and clipboard contents match.
     */
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
