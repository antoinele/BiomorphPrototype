package aston.group2.biomorph.GUI.Renderers;

import aston.group2.biomorph.GUI.Coordinate;
import aston.group2.biomorph.Model.Genes.Dot;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by antoine on 31/10/14.
 */
public class DotRenderer extends Renderer<Dot> {
    public DotRenderer(Dot dot)
    {
        super(dot);
    }

    @Override
    public void draw(Graphics2D g) {
        Coordinate origin = getParentAttachPoint();

//        System.err.println(String.format("Drawing oval at (%d,%d) width: %d height: %d", origin.x, origin.y, getGene().width, getGene().height));

        Dot d = getGene();

        AffineTransform ot = g.getTransform();

        g.rotate(d.angle, origin.x, origin.y);

        g.drawOval(origin.x - (d.width / 2), origin.y - (d.height / 2), d.width, d.height);

        g.setTransform(ot);

//        g2d.rotate(-d.angle, origin.x, origin.y);
    }

    @Override
    public Coordinate getAttachPoint() {
        return getParentAttachPoint();
    }
}
