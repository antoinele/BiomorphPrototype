package aston.group2.biomorph.Model;

import aston.group2.biomorph.GUI.Renderable;
import aston.group2.biomorph.Model.Genes.Gene;
import aston.group2.biomorph.Model.Genes.RootGene;
import aston.group2.biomorph.Storage.Generation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Antoine and Theo
 */
public class Mutator implements Serializable {
    public Random random;

    public final Map<String, Setting> settings;

    public enum MergeType {
        MEAN, WEIGHTED
    }

    public static class Setting implements Serializable {
        public enum Type {
            UNKNOWN, INT, DOUBLE, STRING, MERGETYPE
        }
        public String name;
        public Object value;
        public double min;
        public double max;
        public int precision;
        public Type type;

        public Setting(String name, Object value)
        {
            this.name = name;
            this.value = value;
            this.type = Type.UNKNOWN;
        }

        public Setting(String name, double value)
        {
            this(name, value, 0d, 1d, 3);
        }
        public Setting(String name, double value, double min, double max, int precision)
        {
            this.name = name;
            this.value = value;
            this.min = min;
            this.max = max;
            this.precision = precision;

            this.type = Type.DOUBLE;
        }

        public Setting(String name, int value)
        {
            this(name, value, -1, -1);
        }

        public Setting(String name, int value, int min, int max)
        {
            this.name = name;
            this.value = value;
            this.min = min;
            this.max = max;

            this.type = Type.INT;
        }

        public Setting(String name, String value)
        {
            this.name = name;
            this.value = value;

            this.type = Type.STRING;
        }
    }

    public Mutator(long seed)
    {
        random = new Random(); // TODO: this probably needs changing

        settings = new HashMap<>();

        settings.put("required_children", new Setting("Required Children", 0, 2, 10));
        settings.put("gene_mutate_probability", new Setting("Gene Mutation probability", 0.8));
        settings.put("gene_mutate_value_probability", new Setting("Gene value mutation probability", 0.5));
        settings.put("gene_mutate_value_max_change", new Setting("Maximum mutation value change", 3, 1, 255));
        settings.put("gene_add_probability", new Setting("Gene addition probability", 0.2));
        settings.put("gene_recurse_add_probability", new Setting("Recursive gene addition probability", 0.2));
        settings.put("gene_remove_probability", new Setting("Gene removal probability", 0.1));
        settings.put("merge_type", new Setting("Merge type", MergeType.WEIGHTED));
        settings.put("seed", new Setting("Seed", String.valueOf(seed)));
    }

    public int childrenRequired()
    {
        return (Integer)setting("required_children").value;
    }

    private double probability(String probability)
    {
        return (Double)settings.get(probability + "_probability").value;
    }
    public Setting setting(String key) { return settings.get(key); }

    private Gene mergeGene(final Gene g1, final Gene g2, final Random rng)
    {
        short[] v1 = g1.getValues(),
                v2 = g2.getValues();

        //Merge values together

        short[] newValues = new short[Math.max(v1.length,v2.length)];
        int i=0;
        for(; i < Math.min(v1.length,v2.length); i++)
        {
            switch((MergeType)setting("merge_type").value)
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

    private Gene mergeGenes(final Gene rootGene1, final Gene rootGene2, final Random rng)
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

    private Gene mergeGenes(final Gene[] genes, final Random rng)
    {
        Gene mergedGenes = genes[0];

        for(int i=1; i<genes.length; i++)
        {
            mergedGenes = mergeGenes(mergedGenes, genes[i], rng);
        }

        return mergedGenes;
    }

    private Gene mergeGenes(final Biomorph[] biomorphs, final Random rng)
    {
        Gene mergedGenes = biomorphs[0].getRootGene();

        for (int i=1; i<biomorphs.length; i++)
        {
            mergedGenes = mergeGenes(mergedGenes, biomorphs[i].getRootGene(), rng);
        }

        return mergedGenes;
    }

    private static class GenePicker {
        public static GeneFactory.GeneWeight[] geneWeights;
        public static float totalWeight = 0f;

        static  {
            geneWeights = GeneFactory.geneWeights();
            totalWeight = 0f;


            for (GeneFactory.GeneWeight gw : geneWeights)
            {
                totalWeight += gw.weight;
            }
        }

        public static Gene pickGene(Random rng) {
            double weightCount = rng.nextDouble() * totalWeight;

            Class<? extends Gene> gene = null;

            for (GeneFactory.GeneWeight gw : geneWeights)
            {
                assert(gw.geneCode != 'X');

                weightCount -= gw.weight;

                if(weightCount <= 0)
                {
                    gene = gw.gene;
                    break;
                }
            }

            assert(gene != null);

            try {
                return gene.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                System.exit(1);
            }

            return null;
        }
    }

    private void generateSubGenes(final Gene gene, final Random rng)
    {
        if(! (gene instanceof Renderable) )
            return;

        do {
            // pick a gene
            Gene newGene = GenePicker.pickGene(rng);

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

    private Gene mutateGenes(final Gene rootGene, final Random rng)
    {
        Gene newRootGene = GeneFactory.getGeneFromCode(rootGene.getGeneCode());

        int maxChange = (Integer)settings.get("gene_mutate_value_max_change").value;

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
    private Biomorph mutateBiomorph_internal(Generation newGeneration, final Biomorph[] biomorphs)
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
    
    public Generation mutateBiomorph(final Biomorph[] biomorphs) throws IncompatibleSpeciesException {
        return mutateBiomorph(biomorphs, new Gene[0]);
    }
    public Generation mutateBiomorph(final Biomorph[] biomorphs, Gene[] protectedParts) throws IncompatibleSpeciesException {
        if (biomorphs.length == 0) {
            throw new IllegalArgumentException("Not enough arguments");
        }

        Biomorph[] mutatingBiomorphs = new Biomorph[biomorphs.length];

        for (int i = 0; i < biomorphs.length; i++) {
            try {
                mutatingBiomorphs[i] = biomorphs[i].clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        random = new Random();
        random.setSeed(settings.get("seed").value.hashCode()); // Reset seed to ensure consistent reuse

        Generation newGeneration;

        if(mutatingBiomorphs[0].generation != null)
            newGeneration = new Generation(mutatingBiomorphs[0].generation, this);
        else
            newGeneration = new Generation(this);

        newGeneration.children = new Biomorph[childrenRequired()];
        newGeneration.parents = mutatingBiomorphs;
          
      	for(int i=0; i<childrenRequired(); i++)
      	{
      		Biomorph bm = newGeneration.children[i];
      		
      		while(bm == null || bm.getRootGene().getSubGenes().length == 0)
      		{
      			bm = newGeneration.children[i] = mutateBiomorph_internal(newGeneration, mutatingBiomorphs);
      		}
      	}

        if(mutatingBiomorphs[0].generation != null)
            mutatingBiomorphs[0].generation.addNextGeneration(newGeneration);

        return newGeneration;
    }
}
