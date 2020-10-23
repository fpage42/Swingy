package fr.fpage.swingy.view.console;

import fr.fpage.swingy.controller.Game;
import fr.fpage.swingy.model.HeroClass;
import fr.fpage.swingy.view.window.HeroCreateWindow;
import fr.fpage.swingy.view.window.WindowView;

public class HeroCreateSelectClassConsole extends ConsoleView {
    public HeroCreateSelectClassConsole(Game game) {
        super(game);
        System.out.println("Selectionner une classe");
        System.out.println("1. Guerrier | Degats moyen, Chance de toucher haute, Chance de fuite basse");
        System.out.println("2. Archer   | Degats haut, Chance de toucher moyenne, chance de fuite moyenne");
        System.out.println("3. Sorcier  | Degats bas, Chance de toucher haute, Chance de fuite haute");
        System.out.println("4. Retour au menu precedent");
    }

    public void receiveCommand(String command) {
            try {
                HeroClass heroClass = null;
                int iChoice = Integer.parseInt(command);
                switch (iChoice) {
                    case 1:
                        heroClass = HeroClass.WARRIOR;
                        break;
                    case 2:
                        heroClass = HeroClass.ARCHER;
                        break;
                    case 3:
                        heroClass = HeroClass.WIZARD;
                        break;
                    case 4:
                        this.game.openView(new MainMenuConsole(this.game));
                        break;
                }
                if (heroClass != null) {
                    this.game.openView(new HeroCreateSelectNameConsole(this.game, heroClass));
                }
            } catch (NumberFormatException e) {
                System.out.println("Entree invalide");
            }
    }

    public WindowView getWindowView() {
        return new HeroCreateWindow(this.game);
    }
}
