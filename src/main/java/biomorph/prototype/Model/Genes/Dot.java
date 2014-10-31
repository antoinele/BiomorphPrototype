package biomorph.prototype.Model.Genes;

import biomorph.prototype.View.DotRenderer;
import biomorph.prototype.View.Renderable;
import biomorph.prototype.View.Renderer;

/**
 * Created by antoine on 29/10/14.
 */
public class Dot extends Gene implements Renderable {
    private short width;
    private short height;
    private short angle;

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

    @Override
    public Renderer getRenderer() {
        return new DotRenderer(this);
    }
}
