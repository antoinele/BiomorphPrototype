package aston.group2.biomorph.GUI;

import aston.group2.biomorph.Model.Biomorph;
import aston.group2.biomorph.Model.Genes.Gene;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Stack;

/**
 * Created by antoine on 22/04/15.
 */
public class BiomorphRenderer {

    private Biomorph biomorph;
    private Gene rootGene;

    public boolean CLIPMIRROR = false;

    private final int fixedWidth = 800;
    private final int fixedHeight = 600;
    private int newWidth = 800;
    private int newHeight = 600;

    public BiomorphRenderer() {}
    public BiomorphRenderer(Biomorph biomorph)
    {
        this();
        setBiomorph(biomorph);
    }
    public BiomorphRenderer(Biomorph biomorph, int width, int height)
    {
        this(biomorph);
        setSize(width, height);
    }

    public static class RenderState {

        @Retention(RetentionPolicy.RUNTIME)
        private @interface StackVariable {
            String value();
        }

        private final Stack<Color> lineColourStack;
        private final Stack<Color> fillColourStack;

        private final Stack<Integer> lineWidthStack;

        @StackVariable("lineColourStack") public Color lineColour;
        @StackVariable("fillColourStack") public Color fillColour;

        @StackVariable("lineWidthStack") public int lineWidth;

        public RenderState()
        {
            // Initialise stacks
            lineColourStack = new Stack<Color>();
            fillColourStack = new Stack<Color>();
            lineWidthStack = new Stack<Integer>();

            lineColour = Color.black;
            fillColour = Color.black;
            lineWidth = 1;

            pushState();
        }

        /**
         * Sets the state to the topmost stack items
         */
        public void loadState()
        {
            Class<? extends RenderState> self = getClass();
            Field[] fields = self.getFields();

            for(Field field : fields)
            {
                String stackName = field.getAnnotation(StackVariable.class).value();

                if(stackName != null)
                {
                    try {
                        Stack<Object> stack = (Stack<Object>) self.getField(stackName).get(this);

                        field.set(this, stack.peek());
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void pushState()
        {
            Class<? extends RenderState> self = getClass();
            Field[] fields = self.getFields();

            for(Field field : fields)
            {
                String stackName = field.getAnnotation(StackVariable.class).value();

                if(stackName != null)
                {
                    try {
                        Stack<Object> stack = (Stack<Object>) self.getField(stackName).get(this);

                        stack.push(field.get(this));
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void popState()
        {
            Class<? extends RenderState> self = getClass();
            Field[] fields = self.getFields();

            for(Field field : fields)
            {
                String stackName = field.getAnnotation(StackVariable.class).value();

                if(stackName != null)
                {
                    try {
                        Stack<Object> stack = (Stack<Object>) self.getField(stackName).get(this);

                        field.set(this, stack.pop());
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setSize(int width, int height)
    {
        newWidth = width;
        newHeight = height;
    }

    public void doDrawing(Graphics2D g) {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setRenderingHints(rh);
        g.translate(newWidth / 2, newHeight / 2);

        g.scale((double)newWidth/fixedWidth, (double)newHeight/fixedHeight);

        AffineTransform t = g.getTransform(); // Store transform before drawing
        // messes with it

        RenderState renderState = new RenderState();

        if (CLIPMIRROR) {
            g.setClip(-newWidth / 2, -newHeight / 2, newWidth / 2,
                    newHeight);
        }

        // Iterate subgenes directly to avoid processing/drawing the root gene,
        // which is impossible
        for (Gene gene : rootGene.getSubGenes()) {
            drawGene(renderState, g, gene);
        }

        g.setTransform(t); // Restore transform

        AffineTransform mirrorTransform = AffineTransform.getScaleInstance(-1.0, 1.0);

        g.transform(mirrorTransform);

        if (CLIPMIRROR) {
            g.setClip(-newWidth / 2, -newHeight / 2, newWidth / 2, newHeight);
        }

        // Draw genes again, but mirrored
        for (Gene gene : rootGene.getSubGenes()) {
            drawGene(renderState, g, gene);
        }
    }

    private void drawGene(RenderState renderState, Graphics2D g, Gene gene) {
        Gene[] subGenes = gene.getSubGenes();

        for (Gene sg : subGenes) {
            if (sg instanceof Processed) {
                ((Processed) sg).process(renderState, g);
            }
        }

        if (gene instanceof Processed) {
            ((Processed) gene).process(renderState, g);
        }

        if (gene instanceof Renderable) {
            aston.group2.biomorph.GUI.Renderers.Renderer r = ((Renderable) gene).getRenderer();
            r.draw(renderState, g);
        }

        for (Gene sg : subGenes) {
            drawGene(renderState, g, sg);
        }
    }

    public void setBiomorph(Biomorph biomorph) {
        this.biomorph = biomorph;

        rootGene = biomorph.getRootGene();

    }

    public Biomorph getBiomorph() {
        return biomorph;
    }
}
