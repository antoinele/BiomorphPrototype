package aston.group2.biomorph.Model;

import aston.group2.biomorph.GUI.Renderers.Renderer;
import aston.group2.biomorph.Model.Genes.Gene;
import aston.group2.biomorph.GUI.Coordinate;
import aston.group2.biomorph.GUI.Renderable;
import aston.group2.biomorph.Storage.Generation;

import java.awt.*;
import java.util.Arrays;
import java.util.Stack;

/**
 * Created by antoine on 29/10/14.
 */
public class Biomorph {
    public final Generation generation;

    private Coordinate origin;

    private class RootGene extends Gene implements Renderable {

        private class RootGeneRenderer extends Renderer<RootGene> {

            public RootGeneRenderer()
            {
                super(RootGene.this);
            }

            @Override
            public void draw(Graphics2D g) {
                throw new RuntimeException("This should never be called");
            }

            @Override
            public Coordinate getAttachPoint() {
                return origin;
            }
        }

        public RootGene()
        {
            super('X');
        }

        @Override
        protected int maxValues()
        {
            return 0;
        }

        @Override
        protected void parseValues() {

        }

        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder();

            for (Gene gene : getSubGenes()) {
                sb.append(gene.toString());
            }

            return sb.toString();
        }

        RootGeneRenderer r = null;

        @Override
        public Renderer getRenderer() {
            if(r == null) r = new RootGeneRenderer();
            return r;
        }
    }

    private RootGene rootGene;

    public Biomorph(Generation generation)
    {
        rootGene = new RootGene();

        this.origin = new Coordinate(0,0);

        this.generation = generation;
    }

    public Biomorph(String genome)
    {
        this((Generation)null);

        deserialiseString(genome);
    }

    public void setOrigin(Coordinate origin)
    {
        this.origin = origin;
    }

    public Gene getRootGene()
    {
        return rootGene;
    }

    private void deserialiseString(String genomeString)
    {
        char[] genomeChars = genomeString.toCharArray();

        Stack<Gene> geneStack = new Stack<Gene>();

        rootGene = new RootGene();

        geneStack.push(rootGene);

        boolean skipLine = false;

        for (int i = 0; i < genomeChars.length; i++ ) {

            // Skip comments
            if( genomeChars[i] == '#' )
            {
                skipLine = true;
                continue;
            }
            if( skipLine )
            {
                if(genomeChars[i] == '\n' || genomeChars[i] == '\r')
                {
                    skipLine = false;
                }

                continue;
            }

            //Ensure gene code is valid (a-zA-Z)
            if( ! ( (genomeChars[i] >= 'a' && genomeChars[i] <= 'z') || (genomeChars[i] >= 'A' && genomeChars[i] <= 'Z') ) )
            {
                continue;
            }

            if(genomeChars[i] == 'S') //Subgene
            {
                Gene[] parentGenes = geneStack.peek().getSubGenes();
                geneStack.push(parentGenes[parentGenes.length-1]);
                continue;
            }

            if(genomeChars[i] == 's') //Parent Sibling gene
            {
                if(geneStack.size() > 1)
                    geneStack.pop();
                continue;
            }

            Gene newgene = GeneFactory.getGeneFromCode(genomeChars[i]);

            if(newgene == null)
            {
                throw new InvalidGeneSequenceException(genomeChars[i]);
            }

            char[] remainingGenome = Arrays.copyOfRange(genomeChars, i, genomeChars.length);

            i += newgene.deserialise(remainingGenome) - 1;

            geneStack.peek().addSubGene(newgene);
        }
    }

    public static Biomorph deserialise(String genome)
    {
        Biomorph bm = new Biomorph(genome);
        return bm;
    }

    public String toString()
    {
        return rootGene.toString();
    }
}
