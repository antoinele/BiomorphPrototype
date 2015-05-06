package aston.group2.biomorph.Model;

import aston.group2.biomorph.Storage.Generation;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Antoine
 */
public class Species implements Serializable {
    public Generation firstGeneration;
    private Generation latestGeneration;

    public Calendar creationDate;

    public Species(Generation firstGeneration, Calendar creationDate)
    {
        this.firstGeneration = firstGeneration;
        this.firstGeneration.species = this;
        this.creationDate = creationDate;
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
