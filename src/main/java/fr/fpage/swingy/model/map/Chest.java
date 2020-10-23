package fr.fpage.swingy.model.map;

import fr.fpage.swingy.model.Artefact;
import fr.fpage.swingy.model.ArtefactTypes;
import fr.fpage.swingy.model.Hero;

public class Chest extends MapObject {
    public Chest(Map map, Position position) {
        super(map, position);
    }

    public Character getSymbol() {
        return 'C';
    }

    public boolean interact(Hero hero) {
        this.generateItem();
        this.map.removeMapObject(this);
        return true;
    }

    private void generateItem() {
        Artefact artefact = null;
        switch (((int) (Math.random() * 3))) {
            case 0:
                artefact = new Artefact(((int) (Math.random() * this.map.getMapLevel()) + 1), ArtefactTypes.WEAPON);
                break;
            case 1:
                artefact = new Artefact(((int) (Math.random() * this.map.getMapLevel()) + 1), ArtefactTypes.ARMOR);
                break;
            case 2:
                artefact = new Artefact(((int) (Math.random() * this.map.getMapLevel()) + 1), ArtefactTypes.HELM);
                break;
        }
        if (artefact != null)
            this.map.getGame().dropItem(artefact);
    }

}
