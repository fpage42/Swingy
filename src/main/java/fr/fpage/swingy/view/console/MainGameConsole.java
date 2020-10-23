package fr.fpage.swingy.view.console;

import fr.fpage.swingy.controller.Game;
import fr.fpage.swingy.model.Artefact;
import fr.fpage.swingy.model.map.Direction;
import fr.fpage.swingy.model.map.Villains;
import fr.fpage.swingy.view.window.MainGameWindow;
import fr.fpage.swingy.view.window.WindowView;

public class MainGameConsole extends ConsoleView {

    private Artefact artefact;
    private boolean lvlUp;
    private Villains villains;

    public MainGameConsole(Game game) {
        super(game);
        this.display();
    }

    public WindowView getWindowView() {
        MainGameWindow mainGameWindow = new MainGameWindow(this.game);
        if (this.lvlUp)
            mainGameWindow.lvlUp();
        if (this.artefact != null)
            mainGameWindow.dropItem(this.artefact);
        return mainGameWindow;
    }

    public void receiveCommand(String command) {
        try {
            if (this.lvlUp) {
                switch (Integer.parseInt(command)) {
                    case 1:
                        this.game.getSelectedHero().incHp();
                        break;
                    case 2:
                        this.game.getSelectedHero().incDefense();
                        break;
                    case 3:
                        this.game.getSelectedHero().incAttack();
                        break;
                }
                this.lvlUp = false;
            }
            else if (this.villains != null) {
                switch (Integer.parseInt(command)) {
                    case 1:
                        this.villains.fight();
                        break;
                    case 2:
                        this.villains.run();
                        break;
                }
                }
            else if (this.artefact != null)
                switch (Integer.parseInt(command)) {
                    case 1:
                        this.game.getSelectedHero().giveArtefact(this.artefact);
                    case 2:
                        this.artefact = null;
                        break;
                }
            else
                switch (Integer.parseInt(command))
                {
                    case 1:
                        this.game.getMap().move(Direction.NORTH);
                        break;
                    case 2:
                        this.game.getMap().move(Direction.EAST);
                        break;
                    case 3:
                        this.game.getMap().move(Direction.SOUTH);
                        break;
                    case 4:
                        this.game.getMap().move(Direction.WEST);
                        break;
                    default:
                        System.out.println("Entree invalide");
                }
        } catch (NumberFormatException e) {
            System.out.println("Entree invalide");
        }
        this.display();
    }

    private void display() {
        if (this.game.getMap() != null) {
            if (this.lvlUp) {
                System.out.println("Vous avez gagné un niveau, choississez dans quoi vous souhaitez placer vos points.");
                System.out.println("1. La vie");
                System.out.println("2. L'armure");
                System.out.println("3. Les degats");
            }
            else if (this.villains != null) {
                System.out.println("Combat en cours:");
                System.out.println("Hp: " + this.game.getMap().getHeroMapObject().getHp());
                System.out.println("Casque: " + (this.game.getSelectedHero().getHelm()==null?0:this.game.getSelectedHero().getHelm().getValue()) +
                        " | Armure: " + (this.game.getSelectedHero().getArmor()==null?0:this.game.getSelectedHero().getArmor().getValue()) +
                        " | Arme: " + (this.game.getSelectedHero().getWeapon()==null?0:this.game.getSelectedHero().getWeapon().getValue()));
                System.out.println("Hp du villain: " + this.villains.getHp());
                System.out.println("1. Combattre");
                System.out.println("2. Fuir");
            }
            else if (artefact != null) {
                int actualValue = 0;
                String itemType = "";
                switch (artefact.getArtefactTypes()) {
                    case HELM:
                        itemType = "un casque";
                        actualValue = this.game.getSelectedHero().getHelm()==null?0:this.game.getSelectedHero().getHelm().getValue();
                        break;
                    case ARMOR:
                        itemType = "une armure";
                        actualValue = this.game.getSelectedHero().getArmor()==null?0:this.game.getSelectedHero().getArmor().getValue();
                        break;
                    case WEAPON:
                        itemType = "une arme";
                        actualValue = this.game.getSelectedHero().getWeapon()==null?0:this.game.getSelectedHero().getWeapon().getValue();
                        break;
                }
                System.out.println("Vous trouvez " + itemType + " +" + artefact.getValue() +
                        "\nSouhaitez vous l'equiper ? (item actuel +" + actualValue + ")");
                System.out.println("1. Prendre l'artefact");
                System.out.println("2. Ne pas prendre l'artefact");
            }
            else {
                System.out.println(this.game.getMap().getDisplayMap());
                System.out.println("1. Aller au nord");
                System.out.println("2. Aller a l'est");
                System.out.println("3. Aller au sud");
                System.out.println("4. Aller a l'ouest");
                System.out.println("----------------");
                System.out.println("Hp: " + this.game.getMap().getHeroMapObject().getHp());
                System.out.println("Casque: " + (this.game.getSelectedHero().getHelm()==null?0:this.game.getSelectedHero().getHelm().getValue()) +
                        " | Armure: " + (this.game.getSelectedHero().getArmor()==null?0:this.game.getSelectedHero().getArmor().getValue()) +
                        " | Arme: " + (this.game.getSelectedHero().getWeapon()==null?0:this.game.getSelectedHero().getWeapon().getValue()));
            }
        }
    }

    public void dropItem(Artefact artefact) {
        this.artefact = artefact;
    }

    public void lvlUp() {
        this.lvlUp = true;
    }

    public void startFight(Villains villains) {
        this.villains = villains;
    }

    public void stopFight() {
        this.villains = null;
    }
}
