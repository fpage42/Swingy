package fr.fpage.swingy.view.console;

import fr.fpage.swingy.controller.Game;
import fr.fpage.swingy.view.window.MainMenuWindow;
import fr.fpage.swingy.view.window.WindowView;

public class MainMenuConsole extends ConsoleView {


    public MainMenuConsole(Game game) {
        super(game);
        System.out.println("1. Creer un hero");
        System.out.println("2. Selectionner un hero");
    }

    @Override
    public void receiveCommand(String command) {
        try {
            int choice = Integer.parseInt(command);
            if (choice == 1) {
                this.game.openView(new HeroCreateSelectClassConsole(this.game));
            } else if (choice == 2) {
                this.game.openView(new HeroSelectConsole(this.game));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Entree invalide");
        }
    }

    @Override
    public WindowView getWindowView() {
        return new MainMenuWindow(this.game);
    }
}
