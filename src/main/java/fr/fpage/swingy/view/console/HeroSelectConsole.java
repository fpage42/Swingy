package fr.fpage.swingy.view.console;

import fr.fpage.swingy.controller.Game;
import fr.fpage.swingy.model.Hero;
import fr.fpage.swingy.view.window.MainMenuWindow;
import fr.fpage.swingy.view.window.WindowView;

import java.util.List;

public class HeroSelectConsole extends ConsoleView {

    private List<Hero> heroes;

    public HeroSelectConsole(Game game) {
        super(game);
        System.out.println("Liste des heros");
       this.heroes = this.game.getHeroes();
       int i = 0;
        while (i < heroes.size()) {
            System.out.println(i + ". " + heroes.get(i));
            i++;
        }
        System.out.println(i + ". Retour au menu precedent");
    }

    public void receiveCommand(String command) {
        try {
            if (Integer.parseInt(command) > this.heroes.size())
                System.out.println("Selection invalide");
            else if (Integer.parseInt(command) == this.heroes.size())
                this.game.openView(new MainMenuConsole(this.game));
            else {
                if (Integer.parseInt(command) == this.heroes.size())
                    this.game.openView(new MainMenuConsole(this.game));
                this.game.setSelectedHero(heroes.get(Integer.parseInt(command)));
                this.game.openView(new ResumeHeroConsole(this.game));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Entree invalide");
        }
    }

    public WindowView getWindowView() {
        return new MainMenuWindow(this.game);
    }
}
