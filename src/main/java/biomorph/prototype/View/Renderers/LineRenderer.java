package biomorph.prototype.View.Renderers;

import biomorph.prototype.Model.Genes.Line;
import biomorph.prototype.View.Coordinate;

import java.awt.*;

/**
 * Created by antoine on 31/10/14.
 */
public class LineRenderer extends Renderer<Line> {
    public LineRenderer(Line line) { super(line); }

    private Coordinate calculateSecondPoint()
    {
        Line l = getGene();

        double angleRad = (l.angle / 256d) * Math.PI;

        int xOfs = (int)Math.round(Math.cos(angleRad) * l.length);
        int yOfs = (int)Math.round(Math.sin(angleRad) * l.length);

        Coordinate pc = getParentAttachPoint();

        return new Coordinate(pc.x + xOfs, pc.y + yOfs);
    }

    @Override
    public void draw(Graphics g) {
        Coordinate pc = getParentAttachPoint();
        Coordinate sc = calculateSecondPoint();

        System.err.println(String.format("Drawing line from (%d,%d) to (%d,%d)", pc.x, pc.y, sc.x, sc.y));

        g.drawLine(pc.x, pc.y, sc.x, sc.y);
    }

    @Override
    public Coordinate getAttachPoint() {
        return calculateSecondPoint();
    }
}
