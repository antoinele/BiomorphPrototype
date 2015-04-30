package aston.group2.biomorph.Model;

import aston.group2.biomorph.GUI.Renderable;
import aston.group2.biomorph.Model.Genes.Gene;
import aston.group2.biomorph.Model.Genes.RootGene;
import aston.group2.biomorph.Storage.Generation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by antoine on 16/03/15.
 */
public class Mutator implements Serializable {
    public int childrenRequired;
    public Random random;

    public final Map<String, Object> settings;

    public static enum MergeType {
        MEAN, WEIGHTED
    }

    public Mutator(long seed)
    {
        random = new Random(); // TODO: this probably needs changing

        random.setSeed(seed); // TODO: Really, this needs to be set properly

        settings = new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);

        settings.put("gene_mutate_probability", 0.8f);
        settings.put("gene_mutate_value_probability", 0.5f);
        settings.put("gene_mutate_value_max_change", 3);
        settings.put("gene_add_probability", 0.2f);
        settings.put("gene_recurse_add_probability", 0.2f);
        settings.put("gene_remove_probability", 0.1f);
        settings.put("merge_type", MergeType.WEIGHTED);
    }

    public Mutator()
    {
        this(12345678);
    }

    private float probability(String probability)
    {
        return (Float)settings.get(probability + "_probability");
    }
    private Object setting(String key) { return settings.get(key); }

    private Gene mergeGene(Gene g1, Gene g2, Random rng)
    {
        short[] v1 = g1.getValues(),
                v2 = g2.getValues();

        //Merge values together

        short[] newValues = new short[Math.max(v1.length,v2.length)];
        int i=0;
        for(; i < Math.min(v1.length,v2.length); i++)
        {
            switch((MergeType)setting("merge_type"))
            {
                case MEAN:
                    newValues[i] = (short)((v1[i] + v2[i]) / 2); //averaged
                    break;
                case WEIGHTED:
                    float weight = rng.nextFloat(); //weighted
                    newValues[i] = (short)( (v1[i] * weight) + (v2[i] * (1-weight)) );
                    break;
            }
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
            //TODO: this needs a better algorithm
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

    private Gene mergeGenes(Gene rootGene1, Gene rootGene2, Random rng)
    {
        Gene[] sg1 = rootGene1.getSubGenes(),
               sg2 = rootGene2.getSubGenes();

        Gene[] newGene = new Gene[Math.max(sg1.length,sg2.length)];

        // Merge subgenes
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

        // Merge additional genes
        for (; i<Math.max(sg1.length,sg2.length); i++)
        {
            if(sg1.length > sg2.length)
                newGene[i] = sg1[i];
            else
                newGene[i] = sg2[i];
        }

        // Determine gene type
        Gene ng;

        if(rootGene1.getGeneCode() == rootGene2.getGeneCode())
        {
            if(rootGene1.getGeneCode() == 'X')
                ng = new RootGene();
            else
                ng = GeneFactory.getGeneFromCode(rootGene1.getGeneCode());
        }
        else
        {
            //Decide which gene to become
            if(rng.nextBoolean())
            {
                if(rootGene1.getGeneCode() == 'X')
                    ng = new RootGene();
                else
                    ng = GeneFactory.getGeneFromCode(rootGene1.getGeneCode());
            }
            else
            {
                if(rootGene2.getGeneCode() == 'X')
                    ng = new RootGene();
                else
                    ng = GeneFactory.getGeneFromCode(rootGene2.getGeneCode());
            }
        }

        ng.subGenes.addAll(Arrays.asList(newGene));

        return ng;
    }

    private Gene mergeGenes(Gene[] genes, Random rng)
    {
        Gene mergedGenes = genes[0];

        for(int i=1; i<genes.length; i++)
        {
            mergedGenes = mergeGenes(mergedGenes, genes[i], rng);
        }

        return mergedGenes;
    }

    private Gene mergeGenes(Biomorph[] biomorphs, Random rng)
    {
        Gene mergedGenes = biomorphs[0].getRootGene();

        for (int i=1; i<biomorphs.length; i++)
        {
            mergedGenes = mergeGenes(mergedGenes, biomorphs[i].getRootGene(), rng);
        }

        return mergedGenes;
    }

    private void generateSubGenes(Gene gene, Random rng)
    {
        if(! (gene instanceof Renderable) )
            return;

        char[] genecodes = GeneFactory.geneCodes();

        do {
            // pick a gene
            char genecode = genecodes[rng.nextInt(genecodes.length)];

            Gene newGene = GeneFactory.getGeneFromCode(genecode);

            // randomise values
            short[] newGenes = new short[newGene.maxValues()];
            for(int i=0; i<newGenes.length; i++)
            {
                newGenes[i] = (short)rng.nextInt(256);
            }

            newGene.setValues(newGenes);

            if(rng.nextFloat() <= probability("gene_recurse_add"))
            {
                generateSubGenes(newGene, rng);
            }

            gene.addSubGene(newGene);
        }
        while(rng.nextFloat() <= probability("gene_recurse_add"));
    }

    private Gene mutateGenes(Gene rootGene, Random rng)
    {
        Gene newRootGene = GeneFactory.getGeneFromCode(rootGene.getGeneCode());

        int maxChange = (Integer)settings.get("gene_mutate_value_max_change");

        for (int i = 0; i < rootGene.subGenes.size(); i++)
        {
            Gene gene = rootGene.subGenes.get(i);

            if(rng.nextFloat() <= probability("gene_remove"))
            {
                continue;
            }

            short[] values = gene.getValues();

            if(rng.nextFloat() <= probability("gene_mutate")) {
                // Mutate gene values?
                for (int j = 0; j < values.length; j++) {
                    if (rng.nextFloat() <= probability("gene_mutate_value")) {
                        values[j] += rng.nextInt(maxChange*2) - maxChange;
                    }
                }
            }

            Gene newGene = mutateGenes(gene, rng);
            newGene.setValues(values);

            newRootGene.addSubGene(newGene);

            if(rng.nextFloat() <= probability("gene_add"))
            {
                generateSubGenes(gene, rng);
            }
        }

        return newRootGene;
    }
    private Biomorph mutateBiomorph_internal(Generation newGeneration, Biomorph[] biomorphs)
    {
        Biomorph newBiomorph = new Biomorph(newGeneration);

        Gene newGenes = mergeGenes(biomorphs, random);
        newGenes = mutateGenes(newGenes, random);

        //set genes
        Gene rg = newBiomorph.getRootGene();
        for (Gene gene : newGenes.getSubGenes())
        {
            rg.addSubGene(gene);
        }
        
        return newBiomorph;
    }
    
    public Generation mutateBiomorph(Biomorph[] biomorphs)
    {
        return mutateBiomorph(biomorphs, new Gene[0]);
    }
    public Generation mutateBiomorph(Biomorph[] biomorphs, Gene[] protectedParts) {
        if (biomorphs.length == 0) {
            throw new IllegalArgumentException("Not enough arguments");
        }

        Generation newGeneration;

        if(biomorphs[0].generation != null)
            newGeneration = new Generation(biomorphs[0].generation, this);
        else
            newGeneration = new Generation(this);

        newGeneration.children = new Biomorph[childrenRequired];
        newGeneration.parents = biomorphs;

 //       for (int i=0; i<childrenRequired; i++)
//          {
//          	newGeneration.children[i] = mutateBiomorph_internal(newGeneration, biomorphs);
//          }
          
      	for(int i=0; i<childrenRequired; i++)
      	{
      		Biomorph bm = newGeneration.children[i];
      		
      		while(bm == null || bm.getRootGene().getSubGenes().length == 0)
      		{
      			bm = newGeneration.children[i] = mutateBiomorph_internal(newGeneration, biomorphs);

      		}
      		
      	}
      

        return newGeneration;
    }
}
