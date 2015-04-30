package aston.group2.biomorph.Model;

import aston.group2.biomorph.Storage.Generation;

import java.io.Serializable;

/**
 * Created by Antoine on 19/03/2015.
 */
public class Species implements Serializable {
    public final Generation firstGeneration;
    private Generation latestGeneration;

    public Species(Generation firstGeneration)
    {
        this.firstGeneration = firstGeneration;
        this.firstGeneration.species = this;
    }

    private Generation findLatestGeneration(Generation generation)
    {
        if(generation.nextGeneration.length > 0)
        {
            return findLatestGeneration(generation.nextGeneration[generation.nextGeneration.length - 1]);
        }
        else
        {
            return generation;
        }
    }

    public Generation getLastestGeneration()
    {
        return findLatestGeneration(firstGeneration);
    }

    public void printTree()
    {
        firstGeneration.printTree();
    }
}
