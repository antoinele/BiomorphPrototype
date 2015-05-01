package aston.group2.biomorph.Model;

import aston.group2.biomorph.Storage.BiomorphHistoryLoader;
import aston.group2.biomorph.Storage.Generation;

import java.util.Calendar;

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
        Generation generation = mutator.mutateBiomorph(biomorphs, null);

        try {
            biomorphs[0].generation.addNextGeneration(generation);
        } catch (IncompatibleSpeciesException e) {
            //TODO: what should happen if it can't add a generation?
            return null;
        }

        BiomorphHistoryLoader.save();

        return generation;
    }

    public static Species generateSpecies(Mutator mutator)
    {
        // TODO: I guess this is where "intelligent first generation" would go?
        Generation generation = new Generation(mutator);

        Calendar creationDate = Calendar.getInstance();

        Species species = new Species(generation, creationDate);

        Biomorph[] bma = {new Biomorph("D21F00CSLBEEF00SMCAFEsL123456LFF12F0SLF24300s")};

        bma[0].generation = generation;

        generation.children = bma;

        Generation newGeneration = mutate(bma, mutator);

        // rewrite the first generation
        newGeneration.prevGeneration = null;
        species.firstGeneration = newGeneration;

        BiomorphHistoryLoader.biomorphHistory.add(species);

        BiomorphHistoryLoader.save();

        return species;
    }

}
