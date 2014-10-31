package biomorph.prototype.Model;

import biomorph.prototype.Model.Genes.Gene;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Stack;

/**
 * Created by antoine on 29/10/14.
 */
public class Biomorph implements Serializable {
//    private ArrayList<Gene> genome;

    private class RootGene extends Gene {

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

            Gene newgene = null;

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

            newgene = GeneFactory.getGeneFromCode(genomeChars[i]);

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
