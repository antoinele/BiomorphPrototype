package biomorph.prototype.Model.Genes;

/**
 * Created by antoine on 30/10/14.
 */
public class Line extends Gene {
    private short length;
    private short thickness;
    private short angle;

    public Line()
    {
        super('L');
    }

    @Override
    protected int maxValues()
    {
        return 3;
    }

    @Override
    protected void parseValues() {
        byte[] values = this.getValues();
        length    = (short)(values[0] & 0xff);
        thickness = (short)(values[1] & 0xff);
        angle     = (short)(values[2] & 0xff);
    }
}
