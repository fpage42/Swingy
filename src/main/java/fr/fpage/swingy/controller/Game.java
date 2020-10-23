package fr.fpage.swingy.controller;

import fr.fpage.swingy.Database;
import fr.fpage.swingy.DisplayMode;
import fr.fpage.swingy.model.Artefact;
import fr.fpage.swingy.model.ArtefactTypes;
import fr.fpage.swingy.model.Hero;
import fr.fpage.swingy.model.HeroClass;
import fr.fpage.swingy.model.map.Map;
import fr.fpage.swingy.model.map.Villains;
import fr.fpage.swingy.view.View;
import fr.fpage.swingy.view.console.ConsoleView;
import fr.fpage.swingy.view.console.MainGameConsole;
import fr.fpage.swingy.view.console.MainMenuConsole;
import fr.fpage.swingy.view.console.ResumeHeroConsole;
import fr.fpage.swingy.view.window.*;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private final Database database;
    private DisplayMode displayMode;
    private MainWindow window;
    private View activeView;

    private final List<Hero> heroes = new ArrayList<Hero>();
    private Hero selectedHero;
    private Map map;

    public Game(DisplayMode displayMode) {
        this.displayMode = displayMode;
        this.database = new Database(new File("").getAbsolutePath() + "/game.db");
        this.init();
        this.start();
    }

    private void start() {
        if (this.displayMode.equals(DisplayMode.WINDOW))
        {
            window = new MainWindow(this);
            this.activeView = this.getWindowMainMenu();
        } else {
            this.activeView = this.getConsoleMainMenu();
            this.consoleLoop();
        }
    }

    private void consoleLoop() {
        Scanner scanner = new Scanner(System.in);
        while (this.getDisplayMode().equals(DisplayMode.CONSOLE)) {
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("window"))
                this.changeDisplayMode(DisplayMode.WINDOW);
            else if (command.equalsIgnoreCase("quit"))
                break;
            else
                ((ConsoleView)this.activeView).receiveCommand(command);
        }
    }

    private View getConsoleMainMenu() {
        return new MainMenuConsole(this);
    }

    private View getWindowMainMenu() {
        return new MainMenuWindow(this);
    }

    public void changeDisplayMode(DisplayMode mode) {
        this.displayMode = mode;
        if (this.displayMode.equals(DisplayMode.CONSOLE)) {
            this.window.setVisible(false);
            this.openView(((WindowView) this.activeView).getConsoleView());
            this.consoleLoop();
        } else {
            window = new MainWindow(this);
            this.window.setVisible(true);
            this.openView(((ConsoleView) this.activeView).getWindowView());
        }
    }

    private void init() {
        try {
            Connection connection = this.database.getConnection();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS `heroes` " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name VARCHAR(20)," +
                    "type VARCHAR(15)," +
                    "level INTEGER," +
                    "experience INTEGER," +
                    "attack INTEGER," +
                    "defense INTEGER," +
                    "hp INTEGER," +
                    "weaponAttackValue INTEGER," +
                    "armorDefenseValue INTEGER," +
                    "helmHpValue INTEGER)").execute();
            ResultSet resultSet = connection.prepareStatement("SELECT * FROM `heroes`").executeQuery();
            while (resultSet.next()) {
                Artefact weapon = this.loadArtefact(resultSet.getInt(9), ArtefactTypes.WEAPON);
                Artefact armor = this.loadArtefact(resultSet.getInt(9), ArtefactTypes.ARMOR);
                Artefact helm = this.loadArtefact(resultSet.getInt(9), ArtefactTypes.HELM);
                this.addHero(new Hero(resultSet.getInt(1), resultSet.getString(2), HeroClass.valueOf(resultSet.getString(3)),
                        resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6),
                        resultSet.getInt(7), resultSet.getInt(8), weapon, armor, helm));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Artefact loadArtefact(int value, ArtefactTypes type) {
        if (value >= 0)
            return new Artefact(value, type);
        return null;
    }

    public void addHero(Hero hero) {
        this.heroes.add(hero);
    }

    public Database getDatabase() {
        return database;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public DisplayMode getDisplayMode() {
        return displayMode;
    }

    public MainWindow getWindow() {
        return window;
    }

    public Hero getSelectedHero() {
        return selectedHero;
    }

    public void setSelectedHero(Hero selectedHero) {
        this.selectedHero = selectedHero;
    }

    public Map getMap() {
        return map;
    }

    public void openView(View view) {
        this.activeView = view;
    }

    public void startMap() {
        this.map = new Map(this);
        if (this.getDisplayMode().equals(DisplayMode.CONSOLE))
            this.openView(new MainGameConsole(this));
        else
            this.openView(new MainGameWindow(this));
    }

    public void endMap() {
        this.map = null;
        if (this.getDisplayMode().equals(DisplayMode.CONSOLE))
            this.openView(new ResumeHeroConsole(this));
        else
            this.openView(new ResumeHeroWindow(this));
        this.getSelectedHero().save(this.database);
    }

    public void dropItem(Artefact artefact) {
        if (this.activeView instanceof MainGameWindow)
            ((MainGameWindow) this.activeView).dropItem(artefact);
        else if (this.activeView instanceof MainGameConsole)
            ((MainGameConsole) this.activeView).dropItem(artefact);
    }

    public void lvlUp() {
        if (this.activeView instanceof MainGameWindow)
            ((MainGameWindow) this.activeView).lvlUp();
        else if (this.activeView instanceof MainGameConsole)
            ((MainGameConsole) this.activeView).lvlUp();
    }

    public void startFight(Villains villains) {
        if (this.activeView instanceof MainGameWindow)
            ((MainGameWindow) this.activeView).startFight(villains);
        else if (this.activeView instanceof MainGameConsole)
            ((MainGameConsole) this.activeView).startFight(villains);
    }

    public void stopFight() {
        if (this.activeView instanceof MainGameWindow)
            ((MainGameWindow) this.activeView).stopFight();
        else if (this.activeView instanceof MainGameConsole)
            ((MainGameConsole) this.activeView).stopFight();
    }
}
