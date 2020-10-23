package fr.fpage.swingy.view.window;

import fr.fpage.swingy.controller.Game;
import fr.fpage.swingy.model.Artefact;
import fr.fpage.swingy.model.map.Direction;
import fr.fpage.swingy.model.map.Villains;
import fr.fpage.swingy.view.console.ConsoleView;
import fr.fpage.swingy.view.console.MainGameConsole;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainGameWindow extends WindowView {

    private final JTextPane map = new JTextPane();
    private final JLabel hp = new JLabel();
    private final JLabel items = new JLabel();
    private final JLabel hpVillain = new JLabel();
    private final JPanel buttonPanel = new JPanel();

    private Villains villains;

    public MainGameWindow(Game game) {
        super(game);
        this.game.getWindow().setJPanel(this.getContainer());
    }

    public JPanel getContainer() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        this.map.setEditable(false);
        this.map.setBackground(Color.WHITE);
        this.map.setText(this.getDisplayMap());
        StyledDocument doc = this.map.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        this.map.setFont(new Font(this.map.getFont().getName(), Font.PLAIN, 20));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JPanel stats = new JPanel();
        stats.setLayout(new BoxLayout(stats, BoxLayout.PAGE_AXIS));
        stats.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.reloadStatsPanel();
        this.loadMoveButton();
        rightPanel.add(buttonPanel);
        stats.add(this.hp);
        stats.add(this.items);
        stats.add(this.hpVillain);
        rightPanel.add(stats);
        jPanel.add(map, BorderLayout.CENTER);
        jPanel.add(rightPanel, BorderLayout.EAST);
        return jPanel;
    }

    private String getDisplayMap() {
        return "\n" + game.getMap().getDisplayMap();

    }

    private void reloadStatsPanel() {
        if (this.game.getMap() != null) {
            this.hp.setText("hp: " + this.game.getMap().getHeroMapObject().getHp());
            this.items.setText("Casque: " + (this.game.getSelectedHero().getHelm() == null ? 0 : this.game.getSelectedHero().getHelm().getValue()) +
                    " | Armure: " + (this.game.getSelectedHero().getArmor() == null ? 0 : this.game.getSelectedHero().getArmor().getValue()) +
                    " | Arme: " + (this.game.getSelectedHero().getWeapon() == null ? 0 : this.game.getSelectedHero().getWeapon().getValue()));
            if (this.villains != null) {
                this.hpVillain.setText("Vie du villans: " + this.villains.getHp());
            }
        }
    }

    private void loadFightButton() {
        this.buttonPanel.removeAll();
        JButton fight = new JButton("Combattre");
        JButton run = new JButton("Fuire");

        fight.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (villains != null)
                    villains.fight();
                reloadStatsPanel();
            }
        });
        run.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (villains != null)
                    villains.run();
                reloadStatsPanel();
            }
        });
        this.hpVillain.setVisible(true);
        this.buttonPanel.add(fight);
        this.buttonPanel.add(run);
        this.game.getWindow().setVisible(false);
        this.game.getWindow().setVisible(true);
    }

    private void loadMoveButton() {
        this.buttonPanel.removeAll();
        JButton north = new JButton("Nord");
        JButton east = new JButton("Est");
        JButton south = new JButton("Sud");
        JButton west = new JButton("Ouest");
        north.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.getMap().move(Direction.NORTH);
                if (game.getMap() != null)
                {
                    map.setText(getDisplayMap());
                    reloadStatsPanel();
                }
            }
        });
        east.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.getMap().move(Direction.EAST);
                if (game.getMap() != null) {
                    map.setText(getDisplayMap());
                    reloadStatsPanel();
                }
            }
        });
        south.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.getMap().move(Direction.SOUTH);
                if (game.getMap() != null) {
                    map.setText(getDisplayMap());
                    reloadStatsPanel();
                }
            }
        });
        west.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.getMap().move(Direction.WEST);
                if (game.getMap() != null) {
                    map.setText(getDisplayMap());
                    reloadStatsPanel();
                }
            }
        });
        this.buttonPanel.add(north);
        this.buttonPanel.add(east);
        this.buttonPanel.add(south);
        this.buttonPanel.add(west);
        this.game.getWindow().setVisible(false);
        this.game.getWindow().setVisible(true);
    }

    public ConsoleView getConsoleView() {
        return new MainGameConsole(this.game);
    }

    public void dropItem(Artefact artefact) {
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
        int response = JOptionPane.showConfirmDialog(null, "Vous trouvez " + itemType + " +" + artefact.getValue() +
                        "\nSouhaitez vous l'equiper ? (item actuel +" + actualValue + ")",
                "Satisfaction", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION)
            this.game.getSelectedHero().giveArtefact(artefact);
    }

    public void lvlUp() {
        Object[] options = {"Vie",
                "Armure",
                "Degats"};
        int response = JOptionPane.showOptionDialog(null,
                "Vous avez gagné un niveau, choississez " +
                        "dans quoi vous souhaitez placer vos points.",
                "Gain de niveau",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);
        switch (response) {
            case 0:
                this.game.getSelectedHero().incHp();
                break;
            case 1:
                this.game.getSelectedHero().incDefense();
                break;
            case 2:
                this.game.getSelectedHero().incAttack();
                break;
        }
    }

    public void startFight(Villains villains) {
        this.villains = villains;
        this.loadFightButton();
    }

    public void stopFight() {
        this.loadMoveButton();
        this.villains = null;
        this.hpVillain.setVisible(false);
    }
}
