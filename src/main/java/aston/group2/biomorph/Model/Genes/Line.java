package aston.group2.biomorph.Model.Genes;

import aston.group2.biomorph.GUI.Renderers.LineRenderer;
import aston.group2.biomorph.GUI.Renderable;

/**
 * @author Antoine
 * Creates a gene that consists of a line.
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
    public int maxValues()
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

    private transient LineRenderer r = null;

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
