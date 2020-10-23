package fr.fpage.swingy.view;

import fr.fpage.swingy.controller.Game;

public abstract class View {

    protected Game game;

    public View(Game game) {
        this.game = game;
    }
}
