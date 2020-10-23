package fr.fpage.swingy.view.window;

import fr.fpage.swingy.controller.Game;
import fr.fpage.swingy.model.Hero;
import fr.fpage.swingy.view.console.ConsoleView;
import fr.fpage.swingy.view.console.MainMenuConsole;
import fr.fpage.swingy.view.console.ResumeHeroConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenuWindow extends WindowView {

    private JList<Hero> heroList;

    public MainMenuWindow(Game game) {
        super(game);
        this.game.getWindow().setSize(480, 320);
        this.game.getWindow().setJPanel(this.getContainer());
    }

    public JPanel getContainer() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.PAGE_AXIS));
        JPanel selectHeroPanel = new JPanel();
        selectHeroPanel.setBorder(BorderFactory.createTitledBorder("Selection"));
        BoxLayout selectHeroLayout = new BoxLayout(selectHeroPanel, BoxLayout.X_AXIS);
        selectHeroPanel.setLayout(selectHeroLayout);
        this.heroList = new JList<Hero>(this.game.getHeroes().toArray(new Hero[0]));
        this.heroList.setLayoutOrientation(JList.VERTICAL);
        final JScrollPane heroScrollPane = new JScrollPane(this.heroList);
        JButton selectButton = new JButton("Selectionner ce hero");
        selectButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (heroList.getSelectedValue() == null) {
                    heroList.setBackground(Color.RED);
                    heroList.setForeground(Color.WHITE);
                }
                else {
                    game.setSelectedHero(heroList.getSelectedValue());
                    game.openView(new ResumeHeroWindow(game));
                }
            }
        });
        JPanel newHeroPanel = new JPanel();
        JButton newHeroButton = new JButton("Creer un hero");
        newHeroButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.openView(new HeroCreateWindow(game));
            }
        });
        newHeroPanel.add(newHeroButton);
        selectHeroPanel.add(heroScrollPane);
        selectHeroPanel.add(selectButton);
        jPanel.add(selectHeroPanel);
        jPanel.add(newHeroPanel);
        return jPanel;
    }

    @Override
    public ConsoleView getConsoleView() {
        return new MainMenuConsole(this.game);
    }

}
