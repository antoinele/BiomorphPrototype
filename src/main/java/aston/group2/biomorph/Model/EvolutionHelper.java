package aston.group2.biomorph.Model;

import aston.group2.biomorph.Storage.BiomorphHistoryLoader;
import aston.group2.biomorph.Storage.Generation;

import java.util.*;

/**
 * Created by antoine on 16/03/15.
 */
public class EvolutionHelper {

    // Intelligent First Generation stuff
    private static final int IFG_MIN_PICK = 6;
    private static final int IFG_MAX_PICK = 20;

    public static Generation mutate(Biomorph[] biomorphs)
    {
        return mutate(biomorphs, biomorphs[0].generation.mutator);
    }

    public static Generation mutate(Biomorph[] biomorphs, Mutator mutator)
    {
        try {
            Generation generation = mutator.mutateBiomorph(biomorphs);

            BiomorphHistoryLoader.save();

            return generation;
        } catch (IncompatibleSpeciesException e) {
            //TODO: what should happen if it can't add a generation?
            throw new RuntimeException("Mutator broke :(");
//            return null;
        }
    }

    private static List<Biomorph> getParentBiomorphs(Generation generation)
    {
        List<Biomorph> result = new LinkedList<>();

        if(generation.parents != null)
            result.addAll(Arrays.asList(generation.parents));

        for (Generation subGeneration : generation.nextGeneration)
        {
            result.addAll(getParentBiomorphs(subGeneration));
        }

        return result;
    }

    private static Biomorph[] intelligentFirstGenerationMagic()
    {
        List<Biomorph> selectedBiomorphs = new LinkedList<>();

        for (Species species : BiomorphHistoryLoader.biomorphHistory.get())
        {
            selectedBiomorphs.addAll(getParentBiomorphs(species.firstGeneration));
        }

        Random rng = new Random();
        int pickn = Math.min(rng.nextInt(IFG_MAX_PICK - IFG_MIN_PICK) + IFG_MIN_PICK, selectedBiomorphs.size());
        int[] indicies = new int[pickn];
        for (int i = 0; i < pickn; i++) indicies[i] = -1;

        //generate indicies
        for (int i = 0; i < pickn; i++) {
            int newi;
            boolean unique;
            do {
                newi = rng.nextInt(selectedBiomorphs.size());

                unique = true;
                for (int j = 0; j < pickn; j++) {
                    if(indicies[i] == newi)
                    {
                        unique = false;
                        break;
                    }
                }
            }
            while (!unique);

            indicies[i] = newi;
        }

        //convert selectedBiomorphs into an array for faster random indexing
        Biomorph[] biomorphs = selectedBiomorphs.toArray(new Biomorph[selectedBiomorphs.size()]);

        Biomorph[] resultBM = new Biomorph[pickn];
        for (int i = 0; i < pickn; i++) {
            resultBM[i] = biomorphs[indicies[i]];
        }

        return resultBM;
    }

    public static Species generateSpecies(Mutator mutator, Biomorph[] seedBiomorphs)
    {
        Generation generation = new Generation(mutator);

        Calendar creationDate = Calendar.getInstance();

        Species species = new Species(generation, creationDate);

        seedBiomorphs[0].generation = generation;

        generation.children = seedBiomorphs;

        Generation newGeneration = mutate(seedBiomorphs, mutator);

        // rewrite the first generation
        newGeneration.prevGeneration = null;
        species.firstGeneration = newGeneration;

        BiomorphHistoryLoader.biomorphHistory.add(species);

        BiomorphHistoryLoader.save();

        return species;
    }

    public static Species generateSpecies(Mutator mutator, boolean IFG)
    {
        Biomorph[] bma = null;

        if(IFG)
        {
            bma = intelligentFirstGenerationMagic();
        }

        if(bma == null || bma.length == 0)
        {
            bma = new Biomorph[] {new Biomorph("D21F00CSLBEEF00L123456LFF12F0SLF24300s")};
        }

        return generateSpecies(mutator, bma);
    }
    

}
