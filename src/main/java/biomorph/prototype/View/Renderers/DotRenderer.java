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
    public void draw(Graphics g) {

    }

    @Override
    public Coordinate getAttachPoint() {
        return ((Renderable)getGene().getParent()).getRenderer().getAttachPoint();
    }
}
