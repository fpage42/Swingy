package fr.fpage.swingy.view.window;

import fr.fpage.swingy.controller.Game;
import fr.fpage.swingy.view.View;
import fr.fpage.swingy.view.console.ConsoleView;

import javax.swing.*;
import java.awt.*;

public abstract class WindowView extends View {

    public WindowView(Game game) {
        super(game);
    }

    public abstract JPanel getContainer();
    public abstract ConsoleView getConsoleView();
}
