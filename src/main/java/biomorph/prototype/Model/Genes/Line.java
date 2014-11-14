package biomorph.prototype.Model.Genes;

import biomorph.prototype.View.Renderable;
import biomorph.prototype.View.Renderers.LineRenderer;
import biomorph.prototype.View.Renderers.Renderer;

/**
 * Created by antoine on 30/10/14.
 */
public class Line extends Gene implements Renderable<Line> {
    public short length;
    public short thickness;
    public short angle;

    public Line()
    {
        super('L');
    }

    @Override
    protected int maxValues()
    {
        return 3;
    }

    @Override
    protected void parseValues() {
        short[] values = this.getValues();
        length    = values[0];
        thickness = values[1];
        angle     = values[2];
    }

    private LineRenderer r = null;

    @Override
    public LineRenderer getRenderer()
    {
        if(r == null)
        {
            r = new LineRenderer(this);
        }

        return r;
    }
}
