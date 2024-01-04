
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.fane.Back_End.packageV0.*;

class V0Test {

    private Engine engine;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        engine = new EngineImpl();
    }

    @Test
    @DisplayName("Buffer must be empty after initialisation")
    void getSelection() {
        Selection selection = engine.getSelection();
        assertEquals(selection.getBufferBeginIndex(), selection.getBeginIndex());
        assertEquals("", engine.getBufferContents());
    }

    @Test
    void getBufferContents() {
        engine.insert("Hello, World!");
        assertEquals("Hello, World!", engine.getBufferContents());
    }

    @Test
    void getClipboardContents() {
        // Cut text to clipboard
        engine.insert("Hello");
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(5);
        engine.cutSelectedText();
        assertEquals("Hello", engine.getClipboardContents());
    }

    @Test
    void cutSelectedText() {
        engine.insert("Hello, World!");
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(5);
        engine.cutSelectedText();
        assertEquals(", World!", engine.getBufferContents());
    }

    @Test
    void copySelectedText() {
        engine.insert("Hello, World!");
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(5);
        engine.copySelectedText();
        assertEquals("Hello, World!", engine.getBufferContents()); // Buffer contents shouldn't change after copying
        assertEquals("Hello", engine.getClipboardContents()); // Clipboard should contain copied text
    }

    @Test
    void pasteClipboard() {
        engine.insert("Hello, World!");
        engine.getSelection().setBeginIndex(7);
        engine.getSelection().setEndIndex(12);
        engine.cutSelectedText(); // Clipboard now contains "World"
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(0);
        engine.pasteClipboard();
        assertEquals("WorldHello, !", engine.getBufferContents());
    }

    @Test
    void deleteSelectedText() {
        // Cut text to clipboard
        engine.insert("Hello World");
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(5);
        engine.delete();
        assertEquals(" World", engine.getBufferContents());
        // vérifier que l'index a été mis a jour
    }

    @Test
    @DisplayName("setBeginIndex should throw IndexOutOfBoundsException when set to less than bufferBeginIndex")
    void testSetBeginIndex_LessThanBufferBeginIndex() {
        engine.insert("Hello, World!");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            engine.getSelection().setBeginIndex(-1); // trying to set an invalid index
        });
    }

    @Test
    @DisplayName("setBeginIndex should throw IndexOutOfBoundsException when set to more than endIndex")
    void testSetBeginIndex_MoreThanEndIndex() {
        engine.insert("Hello, World!");
        engine.getSelection().setEndIndex(5);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            engine.getSelection().setBeginIndex(6); // trying to set an invalid index
        });
    }

    @Test
    @DisplayName("setEndIndex should throw IndexOutOfBoundsException when set to less than bufferBeginIndex")
    void testSetEndIndex_LessThanBufferBeginIndex() {
        engine.insert("Hello, World!");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            engine.getSelection().setEndIndex(-1); // trying to set an invalid index
        });
    }

    @Test
    @DisplayName("setEndIndex should throw IndexOutOfBoundsException when set to less than beginIndex")
    void testSetEndIndex_LessThanBeginIndex() {
        engine.insert("Hello, World!");
        engine.getSelection().setBeginIndex(5);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            engine.getSelection().setEndIndex(4); // trying to set an invalid index
        });
    }
}