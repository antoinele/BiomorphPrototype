package aston.group2.biomorph.Model.Genes;

import aston.group2.biomorph.GUI.BiomorphRenderer;
import aston.group2.biomorph.GUI.Processed;
import aston.group2.biomorph.Utilities.ColourHelper;

import java.awt.*;

public class FillColour extends Gene implements Processed {

    private Color colour;

	public FillColour() {
		super('F', 2.5f);
	}

	@Override
	protected void parseValues() {
        short[] v = getValues();

        colour = ColourHelper.indexedHSBColour( (v[0] << 8) & v[1] );
    }

	@Override
	public int maxValues() {
		return 2;
	}

    @Override
    public void process(BiomorphRenderer.RenderState renderState, Graphics g) {
        renderState.fillColour = colour;
    }
}
