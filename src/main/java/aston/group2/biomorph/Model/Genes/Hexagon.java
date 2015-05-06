package aston.group2.biomorph.Model.Genes;

import aston.group2.biomorph.GUI.Renderers.HexagonRenderer;
import aston.group2.biomorph.GUI.Renderers.Renderer;
import aston.group2.biomorph.GUI.Renderable;

/**
 * @author Antoine
 * creates a gene in the form of a hexagon
 */
public class Hexagon extends Gene implements Renderable<Hexagon> {
    public short sideLength;
    public short angle;

    public Hexagon() {
        super('H', 0.4f);
    }

    @Override
    public int maxValues() { return 2; }

    @Override
    protected void parseValues() {
        short[] values = getValues();
        sideLength = values[0];
        angle      = values[1];
    }

    private transient HexagonRenderer r = null;

    @Override
    public Renderer<Hexagon> getRenderer() {
        if(r == null) r = new HexagonRenderer(this);
        return r;
    }
}
