package fr.fpage.swingy.model;

import fr.fpage.swingy.Database;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Hero {

    @NotNull
    private Integer databaseId = -1;
    @NotNull
    @Size(max = 20, min = 3)
    private final String name;
    @NotNull
    private final HeroClass heroClass;
    @NotNull
    @Min(0)
    private Integer level;
    @NotNull
    @Min(0)
    private Integer experience;
    @NotNull
    @Min(0)
    private Integer attack = 5;
    @NotNull
    @Min(0)
    private Integer defense = 5;
    @NotNull
    @Min(0)
    private Integer hp = 50;
    private Artefact weapon;
    private Artefact armor;
    private Artefact helm;

    public Hero(@NotNull Integer databaseId, @NotNull @Size(max = 20, min = 3) String name, @NotNull HeroClass heroClass, @NotNull @Min(0) Integer level,
                @NotNull @Min(0) Integer experience, @NotNull @Min(0) Integer attack, @NotNull @Min(0) Integer defense,
                @NotNull @Min(0) Integer hp, @Min(0) Artefact weapon, @Min(0) Artefact armor,
                @Min(0) Artefact helm) {
        this.databaseId = databaseId;
        this.name = name;
        this.heroClass = heroClass;
        this.level = level;
        this.experience = experience;
        this.attack = attack;
        this.defense = defense;
        this.hp = hp;
        this.weapon = weapon;
        this.armor = armor;
        this.helm = helm;
    }

    public Hero(@NotNull @Size(max = 20, min = 3) String name, @NotNull HeroClass heroClass) {
        this.name = name;
        this.heroClass = heroClass;
        this.level = 1;
        this.experience = 0;
    }

    public String getName() {
        return this.name;
    }

    public HeroClass getHeroClass() {
        return this.heroClass;
    }

    public Integer getLevel() {
        return this.level;
    }

    public Integer getExperience() {
        return this.experience;
    }

    public Integer getAttack() {
        return this.attack + (this.weapon==null?0:this.weapon.getValue());
    }

    public Integer getDefense() {
        return this.defense + (this.armor==null?0:this.armor.getValue());
    }

    public Integer getHp() {
        return this.hp + (this.helm==null?0:this.helm.getValue());
    }

    public Artefact getWeapon() {
        return this.weapon;
    }

    public Artefact getArmor() {
        return this.armor;
    }

    public Artefact getHelm() {
        return this.helm;
    }

    public boolean addExp(Integer exp) {
        this.experience += exp;
        if (this.experience >= this.level * 1000 + Math.pow(this.level - 1, 2) * 450) {
            this.experience -= ((int) (this.level * 1000 + Math.pow(this.level - 1, 2) * 450));
            this.level++;
            return true;
        }
        return false;
    }

    public void incAttack() {
        this.attack++;
    }

    public void incDefense() {
        this.defense++;
    }

    public void incHp() {
        this.hp++;
    }

    private void setWeapon(Artefact weapon) {
        this.weapon = weapon;
    }

    private void setArmor(Artefact armor) {
        this.armor = armor;
    }

    private void setHelm(Artefact helm) {
        this.helm = helm;
    }

    public void save(Database database) {
        try {
            PreparedStatement preparedStatement;
            if (this.databaseId == -1)
            {
                preparedStatement = database.getConnection().prepareStatement("INSERT INTO  heroes " +
                        "(name, type, level, experience, attack, defense, hp, weaponAttackValue, armorDefenseValue, helmHpValue)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            } else {
                preparedStatement = database.getConnection().prepareStatement("UPDATE heroes SET name=?, type=?, level=?, experience=?, attack=?, defense=?, hp=?, weaponAttackValue=?, armorDefenseValue=?, helmHpValue=? WHERE id=?");
                preparedStatement.setInt(11, this.databaseId);
            }
            preparedStatement.setString(1, this.getName());
            preparedStatement.setString(2, this.getHeroClass().toString());
            preparedStatement.setInt(3, this.getLevel());
            preparedStatement.setInt(4, this.getExperience());
            preparedStatement.setInt(5, this.getAttack() - (this.weapon==null?0:this.weapon.getValue()));
            preparedStatement.setInt(6, this.getDefense() - (this.armor==null?0:this.armor.getValue()));
            preparedStatement.setInt(7, this.getHp() - (this.helm==null?0:this.helm.getValue()));
            preparedStatement.setInt(8, this.getWeapon()==null?-1:this.getWeapon().getValue());
            preparedStatement.setInt(9, this.getArmor()==null?-1:this.getArmor().getValue());
            preparedStatement.setInt(10, this.getHelm()==null?-1:this.getHelm().getValue());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public String toString() {
        String className = "";
        switch (this.heroClass) {
            case WARRIOR:
                className = "Guerrier";
                break;
            case ARCHER:
                className = "Archer";
                break;
            case WIZARD:
                className = "Sorcier";
                break;
        }
        return this.name + " | Class: " + className + " | Level: " + this.level;
    }

    public void giveArtefact(Artefact artefact) {
        switch (artefact.getArtefactTypes()) {
            case HELM:
                this.setHelm(artefact);
                break;
            case ARMOR:
                this.setArmor(artefact);
                break;
            case WEAPON:
                this.setWeapon(artefact);
                break;
        }
    }
}
