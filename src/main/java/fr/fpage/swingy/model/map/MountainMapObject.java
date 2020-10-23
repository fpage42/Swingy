package fr.fpage.swingy.model.map;

import fr.fpage.swingy.model.Hero;

public class MountainMapObject extends MapObject {

    public MountainMapObject(Map map, Position position) {
        super(map, position);
    }

    public Character getSymbol() {
        return '^';
    }

    public boolean interact(Hero hero) {
        return false;
    }
}
