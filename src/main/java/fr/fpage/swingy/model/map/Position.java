package fr.fpage.swingy.model.map;

import javax.validation.constraints.NotNull;

public class Position implements Cloneable {

    @NotNull
    private Integer x;
    @NotNull
    private Integer y;

    public Position(@NotNull Integer x, @NotNull Integer y) {
        this.x = x;
        this.y = y;
    }

    @NotNull
    public Integer getX() {
        return x;
    }

    @NotNull
    public Integer getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != null ? !x.equals(position.x) : position.x != null) return false;
        return y != null ? y.equals(position.y) : position.y == null;
    }

    @Override
    public int hashCode() {
        int result = x != null ? x.hashCode() : 0;
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Position clone() {
        try {
            return (Position) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
