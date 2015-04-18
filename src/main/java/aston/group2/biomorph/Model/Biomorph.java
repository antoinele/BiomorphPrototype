package aston.group2.biomorph.Model;

import aston.group2.biomorph.GUI.Renderers.Renderer;
import aston.group2.biomorph.Model.Genes.Gene;
import aston.group2.biomorph.GUI.Coordinate;
import aston.group2.biomorph.GUI.Renderable;
import aston.group2.biomorph.Model.Genes.RootGene;
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

    private RootGene rootGene;

    public Biomorph(Generation generation)
    {
        this.origin = new Coordinate(0,0);

        rootGene = new RootGene(this.origin);

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

        rootGene = new RootGene(this.origin);

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
