package com.fane;

public class EngineImpl implements Engine {

    private Selection selection;
    private String clipboard;
    private StringBuilder buffer = new StringBuilder();
    private Invoker invoker;

    // Constructors
    public EngineImpl(Invoker invoker) {
        this.selection = new SelectionImpl(buffer);
        this.clipboard = "";
        this.invoker = invoker;
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

    // Implémentez la méthode setClipboardContents
    @Override
    public void setClipboardContents(String contents) {
        clipboard = contents;
    }

    // Implémentez la méthode getBuffer
    @Override
    public StringBuilder getBuffer() {
        return buffer;
    }

    /**
     * Removes the text within the interval
     * specified by the selection control object,
     * from the buffer.
     */
    @Override
    public void cutSelectedText() {
        // Utilisez l'invoker pour exécuter la commande CutCommand
        invoker.executeCommand(new CutCommand(this));
    }

    /**
     * Copies the text within the interval
     * specified by the selection control object
     * into the clipboard.
     */
    @Override
    public void copySelectedText() {
        // Utilisez l'invocateur pour exécuter la commande CopyCommand
        invoker.executeCommand(new CopyCommand(this));
    }

    /**
     * Replaces the text within the interval specified by the selection object with
     * the contents of the clipboard.
     */
    @Override
    public void pasteClipboard() {
        // Utilisez l'invocateur pour exécuter la commande CopyCommand
        invoker.executeCommand(new PasteCommand(this));
    }

    /**
     * Inserts a string in the buffer, which replaces the contents of the selection
     *
     * @param s the text to insert
     */
    @Override
    public void insert(String s) {
        // Utilisez l'invocateur pour exécuter la commande InsertCommand
        invoker.executeCommand(new InsertCommand(this, s));
    }

    /**
     * Removes the contents of the selection in the buffer
     */
    @Override
    public void delete() {
        // Utilisez l'invocateur pour exécuter la commande DeleteCommand
        invoker.executeCommand(new DeleteCommand(this));
    }
}
