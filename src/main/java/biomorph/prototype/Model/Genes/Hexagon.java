package biomorph.prototype.Model.Genes;

import biomorph.prototype.View.Renderable;
import biomorph.prototype.View.Renderers.HexagonRenderer;
import biomorph.prototype.View.Renderers.Renderer;

/**
 * Created by Antoine on 27/11/2014.
 */
public class Hexagon extends Gene implements Renderable<Hexagon> {
    public short sideLength;
    public short angle;

    public Hexagon() {
        super('H');
    }

    @Override
    protected int maxValues() { return 2; }

    @Override
    protected void parseValues() {
        short[] values = getValues();
        sideLength = values[0];
        angle      = values[1];
    }

    private HexagonRenderer r = null;

    @Override
    public Renderer<Hexagon> getRenderer() {
        if(r == null) r = new HexagonRenderer(this);
        return r;
    }
}
