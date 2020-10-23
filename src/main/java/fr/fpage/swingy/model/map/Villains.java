package fr.fpage.swingy.model.map;

import fr.fpage.swingy.model.Artefact;
import fr.fpage.swingy.model.ArtefactTypes;
import fr.fpage.swingy.model.Hero;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Villains extends MapObject {

    @NotNull
    private Integer hp;
    @NotNull
    @Min(0)
    private final Integer level;

    public Villains(@NotNull Map map, @NotNull Position position) {
        super(map, position);
        this.level = map.getHeroMapObject().getHero().getLevel();
        this.hp = this.level * 5;
    }

    @NotNull
    @Min(0)
    public Integer getHp() {
        return hp;
    }

    @NotNull
    @Min(0)
    public Integer getLevel() {
        return level;
    }

    public Character getSymbol() {
        return '*';
    }

    public boolean interact(Hero hero) {
        this.map.getGame().startFight(this);
        return false;
    }

    private void generateItem() {
        if (Math.random() * 100 < 30) {
            Artefact artefact = null;
            switch (((int) (Math.random() * 3))) {
                case 0:
                    artefact = new Artefact(((int) (Math.random() * level) + 1), ArtefactTypes.WEAPON);
                    break;
                case 1:
                    artefact = new Artefact(((int) (Math.random() * level) + 1), ArtefactTypes.ARMOR);
                    break;
                case 2:
                    artefact = new Artefact(((int) (Math.random() * level) + 1), ArtefactTypes.HELM);
                    break;
            }
            if (artefact != null)
                this.map.getGame().dropItem(artefact);
        }
    }

    public void fight() {
        int rand = (int) (Math.random() * 100);
        if (rand < this.map.getHeroMapObject().getHero().getHeroClass().getTouchingChance())
            this.hp -= ((int) (this.map.getHeroMapObject().getHero().getAttack() * this.map.getHeroMapObject().getHero().
                    getHeroClass().getAttackMultiplier()));
        if (this.hp < 0)
        {
            if (this.map.getHeroMapObject().getHero().addExp(100 * this.getLevel()))
                this.map.getGame().lvlUp();
            this.generateItem();
            this.map.removeMapObject(this);
            this.map.getGame().stopFight();
        }
        else {
            if (this.map.getHeroMapObject().damage((int) (Math.random() * this.level) * 2 + 1))
                this.map.getGame().endMap();
        }
    }

    public void run() {
        int rand = ((int) (Math.random() * 100));
        int escapeChance = 50 * (1 + this.map.getHeroMapObject().getHero().getHeroClass().getEscapeChance() / 100);
        if (rand < escapeChance) {
            this.map.getGame().stopFight();
        }
        else if (this.map.getHeroMapObject().damage((int) (Math.random() * this.level) * 2 + 1))
            this.map.getGame().endMap();
    }
}
