package fr.fpage.swingy.model.map;

import fr.fpage.swingy.model.Hero;

import javax.validation.constraints.NotNull;

public abstract class MapObject {

    @NotNull
    protected Position position;
    @NotNull
    protected Map map;

    public MapObject(Map map, Position position) {
        this.map = map;
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public abstract Character getSymbol();

    public abstract boolean interact(Hero hero);

    public void setPosition(Position position) {
        this.position = position;
    }
}
