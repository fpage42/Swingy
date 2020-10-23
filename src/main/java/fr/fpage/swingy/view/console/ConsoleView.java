package fr.fpage.swingy.view.console;

import fr.fpage.swingy.controller.Game;
import fr.fpage.swingy.view.View;
import fr.fpage.swingy.view.window.WindowView;

public abstract class ConsoleView extends View {

    public ConsoleView(Game game) {
        super(game);
    }

    public abstract WindowView getWindowView();
    public abstract void receiveCommand(String command);
}
