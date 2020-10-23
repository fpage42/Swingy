package fr.fpage.swingy;

import fr.fpage.swingy.controller.Game;

import java.util.logging.Level;

public class Main {


    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        String mode = "console";
        if (args.length > 0)
            mode = args[0];
        try {
            new Game(DisplayMode.valueOf(mode.toUpperCase()));
        } catch (IllegalArgumentException e) {
            System.out.println("Error with display mode: console || window");
            System.exit(0);
        }
    }

}
