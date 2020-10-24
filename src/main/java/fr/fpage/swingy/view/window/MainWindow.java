package fr.fpage.swingy.view.window;

import fr.fpage.swingy.DisplayMode;
import fr.fpage.swingy.controller.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {

    private final Game game;
    private JPanel currentPanel;

    public MainWindow(Game game) {
        super();
        this.game = game;
        build();
    }

    private void build() {
        setSize(480, 320);
        setTitle("Swingy");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container panel = this.getContentPane();
        panel.setLayout(new BorderLayout());
        JButton consoleButton = new JButton("Console mode");
        consoleButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.changeDisplayMode(DisplayMode.CONSOLE);
            }
        });
        panel.add(consoleButton, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public void setJPanel(JPanel component) {
        this.setVisible(false);
        if (this.currentPanel != null)
            this.getContentPane().remove(this.currentPanel);
        this.currentPanel = component;
        this.getContentPane().add(this.currentPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public void reOpen() {
        this.setVisible(true);
    }
}
