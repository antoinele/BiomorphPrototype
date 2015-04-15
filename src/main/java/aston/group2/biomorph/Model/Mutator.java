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

    private static Gene mergeGenes(Gene rootGene1, Gene rootGene2, Random rng)
    {
        short[] rgv1 = rootGene1.getValues(),
                rgv2 = rootGene2.getValues();

        //Merge values together

        short[] newValues = new short[Math.max(rgv1.length,rgv2.length)];
        int i=0;
        for(; i < Math.min(rgv1.length,rgv2.length); i++)
        {
            newValues[i] = (short)((rgv1[i] + rgv2[i]) / 2);
        }

        for (; i < newValues.length; i++)
        {
            if(rgv1.length > rgv2.length) {
                newValues[i] = rgv1[i];
            }
            else
            {
                newValues[i] = rgv2[i];
            }
        }

        //Determine gene type
        char geneCode;
        if(rootGene1.getGeneCode() != rootGene2.getGeneCode())
        {
            //Flip a coin?
            if(rng.nextBoolean())
                geneCode = rootGene1.getGeneCode();
            else
                geneCode = rootGene2.getGeneCode();
        }
        else
        {
            geneCode = rootGene1.getGeneCode();
        }

        Gene newGene = GeneFactory.getGeneFromCode(geneCode);
        newGene.setValues(newValues);

        return newGene;
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

    private Gene mutateGenes(Gene rootGene1, Gene rootGene2, Random rng)
    {
        return null;
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

        Gene mergedGenes = mergeGenes(biomorph, random);

        for (int i=0; i<childrenRequired; i++)
        {
            Biomorph newBiomorph = new Biomorph(newGeneration);

        }

        return newGeneration;
    }
}
