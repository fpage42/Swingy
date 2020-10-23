package fr.fpage.swingy.model.map;

import fr.fpage.swingy.model.Hero;

public class Fountain extends MapObject {
    public Fountain(Map map, Position position) {
        super(map, position);
    }

    public Character getSymbol() {
        return 'F';
    }

    public boolean interact(Hero hero) {
        this.map.getHeroMapObject().heal(this.map.getMapLevel() * 5);
        this.map.removeMapObject(this);
        return true;
    }
}
