package aston.group2.biomorph.Model;

import aston.group2.biomorph.Storage.Generation;

/**
 * Created by antoine on 16/03/15.
 */
public class EvolutionHelper {

    public static Generation mutate()
    {
        return null; //TODO: first generation
    }

    public static Generation mutate(Biomorph[] biomorphs)
    {
        return mutate(biomorphs, biomorphs[0].generation.mutator);
    }

    public static Generation mutate(Biomorph[] biomorphs, Mutator mutator)
    {
        Generation generation = mutator.mutateBiomorph(biomorphs);

        try {
            biomorphs[0].generation.addNextGeneration(generation);
        } catch (IncompatibleSpeciesException e) {
            //TODO: what should happen if it can't add a generation?
            return null;
        }

        return generation;
    }

}
