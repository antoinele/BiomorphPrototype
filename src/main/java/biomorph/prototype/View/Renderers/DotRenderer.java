package biomorph.prototype.View.Renderers;

import biomorph.prototype.Model.Genes.Dot;
import biomorph.prototype.View.Coordinate;
import biomorph.prototype.View.Renderable;

import java.awt.*;

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
        Coordinate origin = ((Renderable)getGene().getParent()).getRenderer().getAttachPoint();

//        System.err.println(String.format("Drawing oval at (%d,%d) width: %d height: %d", origin.x, origin.y, getGene().width, getGene().height));

        Dot d = getGene();

        g.rotate(d.angle, origin.x, origin.y);

        g.drawOval(origin.x - (d.width / 2), origin.y - (d.height / 2), d.width, d.height);

//        g2d.rotate(-d.angle, origin.x, origin.y);
    }

    @Override
    public Coordinate getAttachPoint() {
        return getParentAttachPoint();
    }
}
