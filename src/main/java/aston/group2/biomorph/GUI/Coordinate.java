package aston.group2.biomorph.GUI;

import java.io.Serializable;

/**
 * Created by antoine on 31/10/14.
 */
public class Coordinate implements Serializable {
    public Coordinate(int x, int y)
    {
        this.x = x;
        this.y =y;
    }

    public int x;
    public int y;
}
