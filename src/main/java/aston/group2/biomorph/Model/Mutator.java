package aston.group2.biomorph.Model;

import aston.group2.biomorph.Storage.Generation;

/**
 * Created by antoine on 16/03/15.
 */
public class Mutator {
    public int childrenRequired;

    public Generation mutateBiomorph(Biomorph[] biomorph) {
        if (biomorph.length == 0) {
            throw new IllegalArgumentException("Not enough arguments");
        }
        Generation newGeneration = new Generation(biomorph[0].generation, this);
        newGeneration.children = new Biomorph[childrenRequired];

        //TODO: actually put something here

        try {
            biomorph[0].generation.addNextGeneration(newGeneration);
        } catch (IncompatibleSpeciesException e) {
            return null;
        }

        return newGeneration;
    }
}
