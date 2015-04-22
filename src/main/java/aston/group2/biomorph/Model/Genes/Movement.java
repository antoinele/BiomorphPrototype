package aston.group2.biomorph.Model.Genes;

import aston.group2.biomorph.GUI.Processed;

import java.awt.*;

/**
 * Created by antoine on 30/10/14.
 */
public class Movement extends Gene implements Processed {
    private short speed;
    private short distance;

    public Movement()
    {
        super('M');
    }

    @Override
    protected int maxValues() {
        return 2;
    }

    @Override
    protected void parseValues()
    {
        speed = getValues()[0];
        distance = getValues()[1];
    }

    @Override
    public void process(Graphics g) {

    }
}
