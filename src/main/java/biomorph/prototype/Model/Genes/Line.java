package biomorph.prototype.Model.Genes;

import biomorph.prototype.View.Renderable;
import biomorph.prototype.View.Renderers.LineRenderer;
import biomorph.prototype.View.Renderers.Renderer;

/**
 * Created by antoine on 30/10/14.
 */
public class Line extends Gene implements Renderable {
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
        byte[] values = this.getValues();
        length    = (short)(values[0] & 0xff);
        thickness = (short)(values[1] & 0xff);
        angle     = (short)(values[2] & 0xff);
    }

    private Renderer<Line> r = null;

    @Override
    public Renderer<Line> getRenderer()
    {
        if(r == null)
        {
            r = new LineRenderer(this);
        }

        return r;
    }
}
