package fr.fpage.swingy.model.map;

import fr.fpage.swingy.model.Hero;

import javax.validation.constraints.NotNull;

public class HeroMapObject extends MapObject {

    @NotNull
    private final Hero hero;

    @NotNull
    private Integer hp;

    public HeroMapObject(@NotNull Map map, @NotNull Position position, @NotNull Hero hero) {
        super(map, position);
        this.hero = hero;
        this.hp = hero.getHp();
    }

    public Hero getHero() {
        return hero;
    }

    public Character getSymbol() {
        return 'H';
    }

    public boolean interact(Hero hero) {
        return true;
    }

    public boolean damage(int damage) {
        this.hp -= damage;
        return this.hp <= 0;
    }

    public Integer getHp() {
        return hp;
    }

    public void heal(int hp) {
        this.hp += hp;
        if (this.hp > this.hero.getHp())
            this.hp = this.hero.getHp();
    }
}
