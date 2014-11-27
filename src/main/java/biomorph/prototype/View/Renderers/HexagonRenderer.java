package biomorph.prototype.View.Renderers;

import biomorph.prototype.Model.Genes.Hexagon;
import biomorph.prototype.View.Coordinate;

import java.awt.*;

/**
 * Created by Antoine on 27/11/2014.
 */
public class HexagonRenderer extends Renderer<Hexagon> {

    public HexagonRenderer(Hexagon hexagon) {
        super(hexagon);
    }

    @Override
    public void draw(Graphics2D g) {
        Coordinate origin = getParentAttachPoint();

        int sideLength = getGene().sideLength;
        double shapeangle = (getGene().angle / 128d) * Math.PI;

        int[] xcoords = new int[6],
              ycoords = new int[6];

        for(int i=0; i<6; i++)
        {
            double angle = (2 * Math.PI) / 6 * (i + shapeangle);
            xcoords[i] = (int)Math.round(origin.x + sideLength * Math.cos(angle));
            ycoords[i] = (int)Math.round(origin.y + sideLength * Math.sin(angle));
        }

        g.drawPolygon(xcoords, ycoords, 6);
    }

    @Override
    public Coordinate getAttachPoint() { return getParentAttachPoint(); }
}
