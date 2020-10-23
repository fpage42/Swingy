package fr.fpage.swingy.view.window;

import fr.fpage.swingy.controller.Game;
import fr.fpage.swingy.view.console.ConsoleView;
import fr.fpage.swingy.view.console.ResumeHeroConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ResumeHeroWindow extends WindowView {

    public ResumeHeroWindow(Game game) {
        super(game);
        this.game.getWindow().setJPanel(this.getContainer());
    }

    public JPanel getContainer() {
        JPanel jPanel = new JPanel();
        JPanel resumePanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        resumePanel.setBorder(BorderFactory.createTitledBorder("Resume du hero"));
        resumePanel.setLayout(new FlowLayout());
        JLabel resumeTitleLabel = new JLabel("");
        resumeTitleLabel.setText("<html><b>Nom du hero</b>: " + "<br>" +
                "Classe du hero: <br>" +
                "Niveau du hero: <br>" +
                "Statistiques: <br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;Vie: <br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;Defense: <br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;Degats: <br>" +
                "Equipement: <br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;Casque: <br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;Armure: <br>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;Arme: <br>" +
                "</html>");
        JLabel resumeValueLabel = new JLabel("");
        resumeValueLabel.setText("<html>" + this.game.getSelectedHero().getName() + "<br>" +
                this.game.getSelectedHero().getHeroClass() + "<br>" +
                this.game.getSelectedHero().getLevel() + "<br>" +
                "<br>" +
                this.game.getSelectedHero().getHp() + "<br>" +
                this.game.getSelectedHero().getDefense() + "<br>" +
                this.game.getSelectedHero().getAttack() + "<br>" +
                "<br>" +
                (this.game.getSelectedHero().getHelm()==null?"0":this.game.getSelectedHero().getHelm().getValue()) + "<br>" +
                (this.game.getSelectedHero().getArmor()==null?"0":this.game.getSelectedHero().getArmor().getValue()) + "<br>" +
                (this.game.getSelectedHero().getWeapon()==null?"0":this.game.getSelectedHero().getWeapon().getValue()) + "<br>" +
                "</html>");
        JPanel buttonGroup = new JPanel();
        JButton start = new JButton("Demarrer une partie");
        start.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.startMap();
            }
        });
        JButton back = new JButton("Retour");
        back.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.getSelectedHero().save(game.getDatabase());
                game.setSelectedHero(null);
                game.openView(new MainMenuWindow(game));
            }
        });
        buttonGroup.add(start);
        buttonGroup.add(back);
        resumePanel.add(resumeTitleLabel);
        resumePanel.add(resumeValueLabel);
        jPanel.add(resumePanel);
        jPanel.add(buttonGroup);
        return jPanel;
    }

    public ConsoleView getConsoleView() {
        return new ResumeHeroConsole(this.game);
    }
}
