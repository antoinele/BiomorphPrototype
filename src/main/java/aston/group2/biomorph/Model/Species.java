package aston.group2.biomorph.Model;

import aston.group2.biomorph.Storage.Generation;

/**
 * Created by Antoine on 19/03/2015.
 */
public class Species {
    public final Generation firstGeneration;

    public Species(Generation firstGeneration)
    {
        this.firstGeneration = firstGeneration;
        this.firstGeneration.species = this;
    }
}
