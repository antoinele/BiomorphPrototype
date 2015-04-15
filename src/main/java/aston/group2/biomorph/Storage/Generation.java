package aston.group2.biomorph.Storage;

import aston.group2.biomorph.Model.Biomorph;
import aston.group2.biomorph.Model.IncompatibleSpeciesException;
import aston.group2.biomorph.Model.Mutator;
import aston.group2.biomorph.Model.Species;

import java.util.Arrays;

/**
 * Created by antoine on 12/03/15.
 */
public class Generation {
    public final Species species;
    public Biomorph[] children;
    public Biomorph[] parents;
    public final Mutator mutator;
    public Generation prevGeneration;
    public Generation[] nextGeneration;

    public Generation(Generation prevGeneration, Mutator mutator) {
        this.prevGeneration = prevGeneration;
        this.species = prevGeneration.species;
        this.mutator = mutator;
    }

    public void addNextGeneration(Generation generation) throws IncompatibleSpeciesException {
        if (generation.species != species) {
            throw new IncompatibleSpeciesException();
        }

        Generation[] newNextGeneration = Arrays.copyOf(nextGeneration, nextGeneration.length + 1);
        newNextGeneration[newNextGeneration.length] = generation;

        nextGeneration = newNextGeneration;
    }
}
