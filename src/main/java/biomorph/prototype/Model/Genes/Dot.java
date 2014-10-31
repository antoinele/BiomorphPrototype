package biomorph.prototype.Model.Genes;

import biomorph.prototype.View.Renderers.DotRenderer;
import biomorph.prototype.View.Renderable;
import biomorph.prototype.View.Renderers.Renderer;

/**
 * Created by antoine on 29/10/14.
 */
public class Dot extends Gene implements Renderable {
    public short width;
    public short height;
    public short angle;

    public Dot()
    {
        super('D');
    }

    @Override
    protected void parseValues() {
        byte[] values = this.getValues();
        width  = (short)(values[0] & 0xff); // Converting a signed byte into an unsigned short
        height = (short)(values[1] & 0xff);
        angle  = (short)(values[2] & 0xff);
    }

    private DotRenderer r = null;

    @Override
    public Renderer getRenderer()
    {
        if(r == null) r = new DotRenderer(this);
        return r;
    }
}
