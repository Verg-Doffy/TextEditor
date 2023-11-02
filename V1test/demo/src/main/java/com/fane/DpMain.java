package com.fane;

public class DpMain {
    public static void main(String[] args) {
        // Création de l'engine et de l'invocateur
        Engine engine = new EngineImpl();
        Invoker invoker = new Invoker();
        String text = "Bonjour, Comment ça va?";
        int newBeginIndex = engine.getBufferContents().length();
        int newEndIndex = engine.getBufferContents().length();

        // Ajout de quelques commandes à l'invocateur
        invoker.addCommand("cut", new CutCommand(engine));
        invoker.addCommand("paste", new PasteCommand(engine));
        invoker.addCommand("copy", new CopyCommand(engine));
        invoker.addCommand("insert", new InsertCommand(engine, text));
        invoker.addCommand("delete", new DeleteCommand(engine));
        invoker.addCommand("changeSelection", new ChangeSelectionCommand(engine, newBeginIndex, newEndIndex));

        // ... Vous pouvez ajouter autant de commandes que vous le souhaitez ici

        // Simulons quelques opérations

        // Insérer un texte initial
        invoker.executeCommand("insert");
        System.out.println(engine.getBufferContents()); // Devrait afficher "Bonjour, Comment ça va?"

        // Sélectionner et couper le texte "Comment"
        engine.changeSelection(9, 16);
        invoker.executeCommand("cut");

        // Vérification
        System.out.println(engine.getBufferContents()); // Devrait afficher "Bonjour, ça va?"

        // Coller le texte précédemment coupé à la fin
        engine.changeSelection(engine.getBufferContents().length(), engine.getBufferContents().length());
        invoker.executeCommand("paste");

        // Vérification
        System.out.println(engine.getBufferContents()); // Devrait afficher "Bonjour, ça va?Comment"

        // Test pour CopyCommand
        engine.changeSelection(0, 7);
        invoker.executeCommand("copy");
        System.out.println(engine.getClipboardContents()); // Devrait afficher "Bonjour"

        engine.changeSelection(engine.getBufferContents().length(), engine.getBufferContents().length());
        invoker.executeCommand("paste");
        System.out.println(engine.getBufferContents()); // Devrait afficher "Bonjour, ça va?CommentBonjour"

        // Test pour DeleteCommand
        engine.changeSelection(16, engine.getBufferContents().length());
        invoker.executeCommand("delete");
        System.out.println(engine.getBufferContents()); // Devrait afficher "Bonjour, ça va?"

    }
}