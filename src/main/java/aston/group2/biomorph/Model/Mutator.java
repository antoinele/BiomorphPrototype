package aston.group2.biomorph.Model;

import aston.group2.biomorph.Model.Genes.Gene;
import aston.group2.biomorph.Storage.Generation;

import java.util.Random;

/**
 * Created by antoine on 16/03/15.
 */
public class Mutator {
    public int childrenRequired;
    public Random random;

    private static Gene mergeGene(Gene g1, Gene g2, Random rng)
    {
        short[] v1 = g1.getValues(),
                v2 = g2.getValues();

        //Merge values together

        short[] newValues = new short[Math.max(v1.length,v2.length)];
        int i=0;
        for(; i < Math.min(v1.length,v2.length); i++)
        {
            newValues[i] = (short)((v1[i] + v2[i]) / 2);
        }

        for (; i < newValues.length; i++)
        {
            if(v1.length > v2.length) {
                newValues[i] = v1[i];
            }
            else
            {
                newValues[i] = v2[i];
            }
        }

        //Determine gene type
        char geneCode;
        if(g1.getGeneCode() != g2.getGeneCode())
        {
            //Flip a coin?
            if(rng.nextBoolean())
                geneCode = g1.getGeneCode();
            else
                geneCode = g2.getGeneCode();
        }
        else
        {
            geneCode = g1.getGeneCode();
        }

        Gene newGene = GeneFactory.getGeneFromCode(geneCode);
        newGene.setValues(newValues);

        return newGene;
    }

    private static Gene mergeGenes(Gene rootGene1, Gene rootGene2, Random rng)
    {
        Gene[] sg1 = rootGene1.getSubGenes(),
               sg2 = rootGene2.getSubGenes();

        Gene[] newGene = new Gene[Math.max(sg1.length,sg2.length)];

        int i=0;
        for (; i<Math.min(sg1.length,sg2.length); i++)
        {
            newGene[i] = mergeGene(sg1[i], sg2[i], rng);

            if(rootGene1.getSubGenes().length > 0 || rootGene2.getSubGenes().length > 0)
            {
                Gene newSubGene = mergeGenes(sg1[i], sg2[i], rng);
                for(Gene nsg : newSubGene.getSubGenes())
                    newGene[i].addSubGene(nsg);
            }
        }

        for (; i<Math.max(sg1.length,sg2.length); i++)
        {   // TODO: figure out how to merge additional genes
            if(sg1.length > sg2.length)
            {
                newGene[i] = sg1[i];
            }
            else
            {
                newGene[i] = sg2[i];
            }
        }


    }

    private static Gene mergeGenes(Gene[] genes, Random rng)
    {
        Gene mergedGenes = genes[0];

        for(int i=1; i<genes.length; i++)
        {
            mergedGenes = mergeGenes(mergedGenes, genes[i], rng);
        }

        return mergedGenes;
    }

    private static Gene mergeGenes(Biomorph[] biomorphs, Random rng)
    {
        Gene mergedGenes = biomorphs[0].getRootGene();

        for (int i=1; i<biomorphs.length; i++)
        {
            mergedGenes = mergeGenes(mergedGenes, biomorphs[i].getRootGene(), rng);
        }

        return mergedGenes;
    }

    private static Gene mutateGenes(Gene rootGene, Random rng)
    {

    }

    public Generation mutateBiomorph(Biomorph[] biomorph, Gene[] protectedParts) {
        if (biomorph.length == 0) {
            throw new IllegalArgumentException("Not enough arguments");
        }
        if(protectedParts == null)
        {
            protectedParts = new Gene[0];
        }

        Generation newGeneration = new Generation(biomorph[0].generation, this);
        newGeneration.children = new Biomorph[childrenRequired];
        newGeneration.parents = biomorph;

        for (int i=0; i<childrenRequired; i++)
        {
            Biomorph newBiomorph = new Biomorph(newGeneration);

            Gene newGenes = mergeGenes(biomorph, random);
            newGenes = mutateGenes(newGenes, random);

            //set genes
            Gene rg = newBiomorph.getRootGene();
            for (Gene gene : newGenes.getSubGenes())
            {
                rg.addSubGene(gene);
            }

            newGeneration.children[i] = newBiomorph;
        }

        return newGeneration;
    }
}
