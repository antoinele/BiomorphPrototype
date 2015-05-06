package aston.group2.biomorph.GUI.Renderers;

import aston.group2.biomorph.GUI.Coordinate;
import aston.group2.biomorph.Model.Genes.Line;

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
    public void draw(Graphics2D g) {
        Coordinate pc = getParentAttachPoint();
        Coordinate sc = calculateSecondPoint();

        Stroke s = g.getStroke();

        // Limit the stroke size to 10
        float stroke = (10 * getGene().thickness) / 256f;

        g.setStroke(new BasicStroke(stroke));

        g.drawLine(pc.x, pc.y, sc.x, sc.y);

        g.setStroke(s);
    }

    @Override
    public Coordinate getAttachPoint() {
        return calculateSecondPoint();
    }
}
