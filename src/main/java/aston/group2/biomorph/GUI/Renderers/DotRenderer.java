package aston.group2.biomorph.GUI.Renderers;

import aston.group2.biomorph.GUI.BiomorphRenderer;
import aston.group2.biomorph.GUI.Coordinate;
import aston.group2.biomorph.Model.Genes.Dot;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * @author Antoine
 */
public class DotRenderer extends Renderer<Dot> {
    public DotRenderer(Dot dot)
    {
        super(dot);
    }

    @Override
    public void draw(BiomorphRenderer.RenderState renderState, Graphics2D g) {
        Coordinate origin = getParentAttachPoint();

//        System.err.println(String.format("Drawing oval at (%d,%d) width: %d height: %d", origin.x, origin.y, getGene().width, getGene().height));

        Dot d = getGene();

        AffineTransform ot = g.getTransform();

        g.rotate(d.angle, origin.x, origin.y);

        g.setColor(renderState.fillColour);
        g.fillOval(origin.x - (d.width / 2), origin.y - (d.height / 2), d.width, d.height);

        g.setStroke(new BasicStroke(renderState.lineWidth));

        g.setColor(renderState.lineColour);
        g.drawOval(origin.x - (d.width / 2), origin.y - (d.height / 2), d.width, d.height);

        g.setTransform(ot);

//        g2d.rotate(-d.angle, origin.x, origin.y);
    }

    @Override
    public Coordinate getAttachPoint() {
        return getParentAttachPoint();
    }
}
