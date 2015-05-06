package aston.group2.biomorph.GUI;

import java.io.Serializable;

/**
 * @author Antoine
 */
public class Coordinate implements Serializable, Cloneable {
    public Coordinate(int x, int y)
    {
        this.x = x;
        this.y =y;
    }

    public int x;
    public int y;

    @Override
    public Coordinate clone() throws CloneNotSupportedException {
        return (Coordinate) super.clone();
    }
}
