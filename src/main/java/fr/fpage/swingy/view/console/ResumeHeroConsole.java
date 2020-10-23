package fr.fpage.swingy.view.console;

import fr.fpage.swingy.controller.Game;
import fr.fpage.swingy.view.window.MainMenuWindow;
import fr.fpage.swingy.view.window.ResumeHeroWindow;
import fr.fpage.swingy.view.window.WindowView;

public class ResumeHeroConsole extends ConsoleView {
    public ResumeHeroConsole(Game game) {
        super(game);
        System.out.println("Nom du hero: " + game.getSelectedHero().getName());
        System.out.println("Classe du hero: " + game.getSelectedHero().getHeroClass());
        System.out.println("Niveau du hero: " + game.getSelectedHero().getLevel());
        System.out.println("Statistiques:");
        System.out.println("    Vie: " + game.getSelectedHero().getHp());
        System.out.println("    Defense: " + game.getSelectedHero().getDefense());
        System.out.println("    Degats: " + game.getSelectedHero().getAttack());
        System.out.println("Equipement:");
        System.out.println("    Casque: " + (this.game.getSelectedHero().getHelm()==null?"0":this.game.getSelectedHero().getHelm().getValue()));
        System.out.println("    Armure: " + (this.game.getSelectedHero().getArmor()==null?"0":this.game.getSelectedHero().getArmor().getValue()));
        System.out.println("    Arme: " + (this.game.getSelectedHero().getWeapon()==null?"0":this.game.getSelectedHero().getWeapon().getValue()));

        System.out.println("1. Lancer une partie avec ce hero");
        System.out.println("2. Retour");
    }

    public WindowView getWindowView() {
        return new ResumeHeroWindow(this.game);
    }

    public void receiveCommand(String command) {
        try {
            int choice = Integer.parseInt(command);
            switch (choice) {
                case 1:
                    this.game.startMap();
                    break;
                case 2:
                    this.game.getSelectedHero().save(this.game.getDatabase());
                    this.game.setSelectedHero(null);
                    this.game.openView(new MainMenuConsole(this.game));
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Format invalide");
        }
    }
}
