
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.fane.*;

class CommandTest {

    private TextEditor editor;
    private Command copyCommand;
    private Command cutCommand;
    private Command deleteCommand;
    private Command insertCommand;
    private Command pasteCommand;

    @BeforeEach
    void setUp() {
        editor = new TextEditor(); // Assuming a TextEditor class that these commands operate on
        copyCommand = new CopyCommand(editor);
        cutCommand = new CutCommand(editor);
        deleteCommand = new DeleteCommand(editor);
        insertCommand = new InsertCommand(editor, "InsertedText"); // Assuming InsertCommand takes text to insert as
                                                                   // argument
        pasteCommand = new PasteCommand(editor);
    }

    @Test
    void testCopyCommand() {
        editor.setText("Hello, World!");
        copyCommand.execute();
        assertEquals("Hello, World!", editor.getClipboard());
    }

    @Test
    void testCutCommand() {
        editor.setText("Hello, World!");
        cutCommand.execute();
        assertEquals("Hello, World!", editor.getClipboard());
        assertEquals("", editor.getText());
    }

    @Test
    void testDeleteCommand() {
        editor.setText("Hello, World!");
        deleteCommand.execute();
        assertEquals("", editor.getText());
    }

    @Test
    void testInsertCommand() {
        editor.setText("Hello, ");
        insertCommand.execute();
        assertEquals("Hello, InsertedText", editor.getText());
    }

    @Test
    void testPasteCommand() {
        editor.setClipboard("PastedText"); // Assuming a method to set clipboard content directly
        pasteCommand.execute();
        assertEquals("PastedText", editor.getText());
    }
}
