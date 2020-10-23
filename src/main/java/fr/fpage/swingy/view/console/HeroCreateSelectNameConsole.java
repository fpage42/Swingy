package fr.fpage.swingy.view.console;

import fr.fpage.swingy.controller.Game;
import fr.fpage.swingy.model.Hero;
import fr.fpage.swingy.model.HeroClass;
import fr.fpage.swingy.view.window.HeroCreateWindow;
import fr.fpage.swingy.view.window.WindowView;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

public class HeroCreateSelectNameConsole extends ConsoleView {

    private final HeroClass selectedHeroClass;

    public HeroCreateSelectNameConsole(Game game, HeroClass heroClass) {
        super(game);
        this.selectedHeroClass = heroClass;
        System.out.println("Entrez le nom de votre hero (entre 3 et 20 caracteres)");
    }

    public WindowView getWindowView() {
        return new HeroCreateWindow(this.game);
    }

    public void receiveCommand(String command) {
        Hero hero = new Hero(command, this.selectedHeroClass);
        Set<ConstraintViolation<Hero>> constraintViolation = Validation.buildDefaultValidatorFactory().getValidator().validate(hero);
        if (constraintViolation.size() > 0) {
            System.out.println("Données invalide: ");
            for (ConstraintViolation<Hero> constraints : constraintViolation) {
                System.out.println(constraints.getPropertyPath() + " " + constraints.getMessage());
            }
        } else {
            hero.save(this.game.getDatabase());
            this.game.addHero(hero);
            this.game.openView(new MainMenuConsole(this.game));
        }
    }
}
