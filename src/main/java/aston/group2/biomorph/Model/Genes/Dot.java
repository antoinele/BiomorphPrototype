package aston.group2.biomorph.Model.Genes;

import aston.group2.biomorph.GUI.Renderers.DotRenderer;
import aston.group2.biomorph.GUI.Renderable;

/**
 * Created by antoine on 29/10/14.
 */
public class Dot extends Gene implements Renderable<Dot> {
    public short width;
    public short height;
    public short angle;

    public Dot()
    {
        super('D');
    }

    @Override
    protected void parseValues() {
        short[] values = this.getValues();
        width  = values[0];
        height = values[1];
        angle  = values[2];
    }

    private DotRenderer r = null;

    @Override
    public DotRenderer getRenderer()
    {
        if(r == null) r = new DotRenderer(this);
        return r;
    }

	@Override
	protected int maxValues() {
		// TODO Auto-generated method stub
		return 3;
	}
}
