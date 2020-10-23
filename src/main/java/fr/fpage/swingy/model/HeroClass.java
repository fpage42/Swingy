package fr.fpage.swingy.model;

public enum HeroClass {

    WARRIOR(1., 75, 35),
    ARCHER(1.2, 60, 50),
    WIZARD(0.8, 65, 65);

    private final Double attackMultiplier;
    private final Integer touchingChance;
    private final Integer escapeChance;

    HeroClass(Double attackMultiplier, Integer touchingChance, Integer escapeChance) {
        this.attackMultiplier = attackMultiplier;
        this.touchingChance = touchingChance;
        this.escapeChance = escapeChance;
    }

    public Double getAttackMultiplier() {
        return attackMultiplier;
    }

    public Integer getTouchingChance() {
        return touchingChance;
    }

    public Integer getEscapeChance() {
        return escapeChance;
    }
}
