package biomorph.prototype.Model;

import biomorph.prototype.Model.Genes.Gene;
import biomorph.prototype.View.Coordinate;
import biomorph.prototype.View.Renderable;
import biomorph.prototype.View.Renderers.Renderer;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Stack;

/**
 * Created by antoine on 29/10/14.
 */
public class Biomorph implements Serializable {
//    private ArrayList<Gene> genome;

    private class RootGene extends Gene implements Renderable {

        private class RootGeneRenderer extends Renderer<RootGene> {

            public RootGeneRenderer()
            {
                super(RootGene.this);
            }

            @Override
            public void draw(Graphics g) {
                throw new RuntimeException("This should never be called");
            }

            @Override
            public Coordinate getAttachPoint() {
                return new Coordinate(0,0);
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

    public Biomorph()
    {
        rootGene = new RootGene();
    }

    private void writeObject(ObjectOutputStream out)
            throws IOException
    {
        out.write(this.toString().getBytes(Charset.forName("UTF-7")));
    }

    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException
    {
        deserialiseString(in.readUTF());
    }

    private void readObjectNoData()
            throws ObjectStreamException
    {

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

        for (int i = 0; i < genomeChars.length; /*i++*/ ) {

            //Ensure gene code is valid (a-zA-Z)
            assert( (genomeChars[i] >= 'a' && genomeChars[i] <= 'z') || (genomeChars[i] >= 'A' && genomeChars[i] <= 'Z') );

            if(genomeChars[i] == 'S') //Subgene
            {
                i++;

                Gene[] parentGenes = geneStack.peek().getSubGenes();
                geneStack.push(parentGenes[parentGenes.length-1]);
            }

            if(genomeChars[i] == 's') //Parent Sibling gene
            {
                i++;
                geneStack.pop();
            }

            if(i >= genomeChars.length)
            {
                break;
            }

            Gene newgene = GeneFactory.getGeneFromCode(genomeChars[i]);

//            System.err.println("Gene: " + genomeChars[i]);
//            System.err.println("Depth: " + geneStack.size());

            if(newgene == null)
            {
                throw new InvalidGeneSequenceException();
            }

            char[] remainingGenome = Arrays.copyOfRange(genomeChars, i, genomeChars.length);

//            System.err.println(remainingGenome);

            i += newgene.deserialise(remainingGenome);

            geneStack.peek().addSubGene(newgene);
        }
    }

    public static Biomorph deserialise(String genome)
    {
        Biomorph bm = new Biomorph();
        bm.deserialiseString(genome);
        return bm;
    }

    public String toString()
    {
        return rootGene.toString();
    }
}
