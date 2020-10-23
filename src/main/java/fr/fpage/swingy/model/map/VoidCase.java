package fr.fpage.swingy.model.map;

import fr.fpage.swingy.model.Hero;

public class VoidCase extends MapObject {
    public VoidCase(Map map, Position position) {
        super(map, position);
    }

    public Character getSymbol() {
        return '*';
    }

    public boolean interact(Hero hero) {
        return true;
    }
}
