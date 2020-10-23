package fr.fpage.swingy.model.map;

import fr.fpage.swingy.controller.Game;

import java.util.HashMap;

public class Map {

    private final Integer LINEOFSIGHT = 3;

    private final Integer mapLevel;
    private final HashMap<Position, MapObject> mapGrid = new HashMap<Position, MapObject>();
    private final HeroMapObject heroMapObject;
    private final Game game;

    public Map(Game game) {
        this.game = game;
        this.mapLevel = this.game.getSelectedHero().getLevel();
        int mapSize = (this.mapLevel - 1) * 5 + 10 - 1;
        this.heroMapObject = new HeroMapObject(this, new Position((mapSize-1)/2, (mapSize-1)/2), this.game.getSelectedHero());
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                int rand = ((int) (Math.random() * 100));
                if (rand < 15)
                    this.addMapObject(new MountainMapObject(this, new Position(x, y)));
                else if (rand < 30)
                    this.addMapObject(new Villains(this, new Position(x, y)));
                else if (rand < 40)
                    this.addMapObject(new Chest(this, new Position(x, y)));
                else if (rand < 45)
                    this.addMapObject(new Fountain(this, new Position(x, y)));
                else
                    this.addMapObject(new VoidCase(this, new Position(x, y)));
            }
        }
    }

    private void addMapObject(MapObject mapObject) {
        this.mapGrid.put(mapObject.getPosition(), mapObject);
    }

    public MapObject getMapObject(Position position) {
        return this.mapGrid.get(position);
    }

    public HeroMapObject getHeroMapObject() {
        return heroMapObject;
    }

    public String getDisplayMap() {
        StringBuilder ret = new StringBuilder();
        for (int i = this.heroMapObject.getPosition().getX() + LINEOFSIGHT;
             i > this.heroMapObject.getPosition().getX() - LINEOFSIGHT - 1; i--) {
            for (int j = this.heroMapObject.getPosition().getY() + LINEOFSIGHT;
                 j > this.heroMapObject.getPosition().getY() - LINEOFSIGHT - 1; j--) {
                if (i == heroMapObject.getPosition().getX() && j == heroMapObject.getPosition().getY()) {
                    ret.append(heroMapObject.getSymbol());
                    ret.append(' ');
                }
                else {
                    MapObject obj = this.mapGrid.get(new Position(i, j));
                    if (obj != null) {
                        ret.append(obj.getSymbol());
                        ret.append(' ');
                    }
                    else
                        ret.append(' ');
                }
            }
            ret.append('\n');
        }
        return ret.toString();
    }

    public void move(Direction direction) {
        Position nPosition;
        try {
            switch (direction) {
                case NORTH:
                    nPosition = this.heroMapObject.getPosition().clone();
                    nPosition.setX(nPosition.getX() + 1);
                    if (this.mapGrid.get(nPosition).interact(this.heroMapObject.getHero()))
                        this.heroMapObject.setPosition(nPosition);
                    break;
                case EAST:
                    nPosition = this.heroMapObject.getPosition().clone();
                    nPosition.setY(nPosition.getY() - 1);
                    if (this.mapGrid.get(nPosition).interact(this.heroMapObject.getHero()))
                        this.heroMapObject.setPosition(nPosition);
                    break;
                case SOUTH:
                    nPosition = this.heroMapObject.getPosition().clone();
                    nPosition.setX(nPosition.getX() - 1);
                    if (this.mapGrid.get(nPosition).interact(this.heroMapObject.getHero()))
                        this.heroMapObject.setPosition(nPosition);
                    break;
                case WEST:
                    nPosition = this.heroMapObject.getPosition().clone();
                    nPosition.setY(nPosition.getY() + 1);
                    if (this.mapGrid.get(nPosition).interact(this.heroMapObject.getHero()))
                        this.heroMapObject.setPosition(nPosition);
                    break;
            }
        } catch (NullPointerException e) {
            this.game.endMap();
        }
    }

    public void removeMapObject(MapObject mapObject) {
        this.mapGrid.put(mapObject.getPosition(), new VoidCase(this, mapObject.getPosition()));
    }

    public Game getGame() {
        return game;
    }

    public Integer getMapLevel() {
        return mapLevel;
    }

}
