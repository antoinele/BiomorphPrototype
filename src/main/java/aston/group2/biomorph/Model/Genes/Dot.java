package aston.group2.biomorph.Model.Genes;

import aston.group2.biomorph.GUI.Renderers.DotRenderer;
import aston.group2.biomorph.GUI.Renderable;

/**
 * @author Antoine
 * Represents dot genes
 */
public class Dot extends Gene implements Renderable<Dot> {
    public short width;
    public short height;
    public short angle;

    public Dot()
    {
        super('D', 0.8f);
    }

    @Override
    protected void parseValues() {
        short[] values = this.getValues();
        width  = values[0];
        height = values[1];
        angle  = values[2];
    }

    private transient DotRenderer r = null;

    @Override
    public DotRenderer getRenderer()
    {
        if(r == null) r = new DotRenderer(this);
        return r;
    }

	@Override
    public int maxValues() {
		return 3;
	}
}
