package aston.group2.biomorph.Model.Genes;

import aston.group2.biomorph.GUI.BiomorphRenderer;
import aston.group2.biomorph.GUI.Processed;
import aston.group2.biomorph.Utilities.ColourHelper;

import java.awt.*;

public class FillColour extends Gene implements Processed {

    private Color colour;

	public FillColour(byte[] values) throws Exception {
		super('F', values);
	}

	@Override
	protected void parseValues() {
        colour = ColourHelper.indexedHSBColour(getValues()[0]);
    }

	@Override
	protected int maxValues() {
		return 1;
	}

    public Color getColour()
    {
        return colour;
    }

    @Override
    public void process(BiomorphRenderer.RenderState renderState, Graphics g) {
        renderState.fillColour = colour;
    }
}
