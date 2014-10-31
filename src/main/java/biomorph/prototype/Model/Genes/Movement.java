package biomorph.prototype.Model.Genes;

import biomorph.prototype.View.Processed;

/**
 * Created by antoine on 30/10/14.
 */
public class Movement extends Gene implements Processed {
    private byte speed;
    private byte distance;

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
    public void process() {
        
    }
}
