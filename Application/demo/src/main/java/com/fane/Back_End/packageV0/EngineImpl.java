package com.fane.Back_End.packageV0;

public class EngineImpl implements Engine {

    private Selection selection;
    private String clipboard;
    private StringBuilder buffer = new StringBuilder();

    // Constructors
    public EngineImpl() {
        this.selection = new SelectionImpl(buffer);
        this.clipboard = "";
    }

    /**
     * Provides access to the selection control object
     *
     * @return the selection object
     */
    @Override
    public Selection getSelection() {
        return selection;
    }

    /**
     * Provides the whole contents of the buffer, as a string
     *
     * @return a copy of the buffer's contents
     */
    @Override
    public String getBufferContents() {
        return buffer.toString();
    }

    /**
     * Provides the clipboard contents
     *
     * @return a copy of the clipboard's contents
     */
    @Override
    public String getClipboardContents() {
        return clipboard;
    }

    /**
     * Removes the text within the interval
     * specified by the selection control object,
     * from the buffer.
     */
    @Override
    public void cutSelectedText() {
        int beginIndex = selection.getBeginIndex();
        int endIndex = selection.getEndIndex();
        if (beginIndex != endIndex) {
            clipboard = buffer.substring(beginIndex, endIndex); // Copy selected text to clipboard
            buffer.delete(beginIndex, endIndex); // Remove selected text from buffer
            selection.setEndIndex(beginIndex); // Move selection to the beginning of the removed text
        }
    }

    /**
     * Copies the text within the interval
     * specified by the selection control object
     * into the clipboard.
     */
    @Override
    public void copySelectedText() {
        int beginIndex = selection.getBeginIndex();
        int endIndex = selection.getEndIndex();
        clipboard = buffer.substring(beginIndex, endIndex); // Copy selected text to clipboard

    }

    /**
     * Replaces the text within the interval specified by the selection object with
     * the contents of the clipboard.
     */
    @Override
    public void pasteClipboard() {
        int beginIndex = selection.getBeginIndex();
        int endIndex = selection.getEndIndex();

        buffer.delete(beginIndex, endIndex); // Remove the selected text from buffer
        buffer.insert(beginIndex, clipboard); // Insert clipboard contents
        selection.setEndIndex(beginIndex + clipboard.length()); // Move selection to the end of the pasted text

    }

    /**
     * Inserts a string in the buffer, which replaces the contents of the selection
     *
     * @param s the text to insert
     */
    @Override
    public void insert(String s) {
        int beginIndex = selection.getBeginIndex();
        buffer.delete(beginIndex, selection.getEndIndex()); // Remove the selected text from buffer
        buffer.insert(beginIndex, s); // Insert string at the selection
        selection.setEndIndex(beginIndex + s.length()); // Move selection to the end of the inserted text
    }

    /**
     * Removes the contents of the selection in the buffer
     */
    @Override
    public void delete() {
        int beginIndex = selection.getBeginIndex();
        int endIndex = selection.getEndIndex();
        if (beginIndex != endIndex) {
            buffer.delete(beginIndex, endIndex); // Remove selected text from buffer
            selection.setEndIndex(beginIndex); // Move selection to the beginning of the removed text
        }
    }

}
