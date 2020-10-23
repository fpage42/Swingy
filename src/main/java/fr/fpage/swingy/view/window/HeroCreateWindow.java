package fr.fpage.swingy.view.window;

import fr.fpage.swingy.controller.Game;
import fr.fpage.swingy.model.Hero;
import fr.fpage.swingy.model.HeroClass;
import fr.fpage.swingy.view.console.ConsoleView;
import fr.fpage.swingy.view.console.HeroCreateSelectClassConsole;
import org.hibernate.validator.internal.engine.path.PathImpl;

import javax.swing.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Set;

public class HeroCreateWindow extends WindowView {
    public HeroCreateWindow(Game game) {
        super(game);
        this.game.getWindow().setSize(550, 320);
        this.game.getWindow().setJPanel(this.getContainer());
    }

    public JPanel getContainer() {
        JPanel panel = new JPanel();
        String[] classDescription = new String[]{"Guerrier | Degats moyen, Chance de toucher haute, Chance de fuite basse",
                "Archer   | Degats haut, Chance de toucher moyenne, chance de fuite moyenne",
                "Sorcier  | Degats bas, Chance de toucher haute, Chance de fuite haute"};
        final JList<String> heroClassList = new JList<String>(classDescription);
        JScrollPane heroClassScrollPane = new JScrollPane(heroClassList);

        JPanel selectHeroClassPanel = new JPanel();
        selectHeroClassPanel.setBorder(BorderFactory.createTitledBorder("Classe"));
        BoxLayout selectHeroLayout = new BoxLayout(selectHeroClassPanel, BoxLayout.Y_AXIS);
        selectHeroClassPanel.setLayout(selectHeroLayout);
        JPanel heroNamePanel = new JPanel();
        JLabel heroNameLabel = new JLabel("Nom du hero");
        final JTextField heroName = new JTextField();
        heroName.setPreferredSize(new Dimension(150, 20));
        heroNamePanel.add(heroNameLabel);
        heroNamePanel.add(heroName);
        JButton validationButton = new JButton("Valider");
        validationButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Hero hero = new Hero(heroName.getText(), HeroClass.values()[heroClassList.getSelectedIndex()]);
                Set<ConstraintViolation<Hero>> constraintViolation = Validation.buildDefaultValidatorFactory().getValidator().validate(hero);
                if (constraintViolation.size() > 0) {
                    for (ConstraintViolation<Hero> constraints : constraintViolation) {
                        if (((PathImpl)constraints.getPropertyPath()).getLeafNode().getName().equalsIgnoreCase("name")) {
                            heroName.setBackground(Color.RED);
                            heroName.setForeground(Color.WHITE);
                        }
                        if (((PathImpl)constraints.getPropertyPath()).getLeafNode().getName().equalsIgnoreCase("heroClass")) {
                            heroClassList.setBackground(Color.RED);
                            heroClassList.setForeground(Color.WHITE);
                        }
                    }
                } else {
                    hero.save(game.getDatabase());
                    game.addHero(hero);
                    game.openView(new MainMenuWindow(game));
                }
            }
        });
        JButton backButton = new JButton("Retour");
        backButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.openView(new MainMenuWindow(game));
            }
        });
        selectHeroClassPanel.add(heroClassScrollPane);
        selectHeroClassPanel.add(heroNamePanel);
        panel.add(selectHeroClassPanel);
        panel.add(validationButton);
        panel.add(backButton);
        return panel;
    }

    public ConsoleView getConsoleView() {
        return new HeroCreateSelectClassConsole(this.game);
    }
}
