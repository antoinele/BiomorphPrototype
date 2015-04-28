package aston.group2.biomorph.Storage;

import aston.group2.biomorph.Model.Biomorph;
import aston.group2.biomorph.Model.IncompatibleSpeciesException;
import aston.group2.biomorph.Model.Mutator;
import aston.group2.biomorph.Model.Species;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by antoine on 12/03/15.
 */
public class Generation implements Serializable {
    public Species species;
    public Biomorph[] children;
    public Biomorph[] parents;
    public final Mutator mutator;
    public Generation prevGeneration;
    public Generation[] nextGeneration;

    public Generation(Mutator mutator)
    {
        nextGeneration = new Generation[0];
        this.prevGeneration = null;
        this.species = null;
        this.mutator = mutator;
    }

    public Generation(Generation prevGeneration, Mutator mutator) {
        this(mutator);
        this.prevGeneration = prevGeneration;
        this.species = prevGeneration.species;
    }

    public void addNextGeneration(Generation generation) throws IncompatibleSpeciesException {
        if (generation.species != species) {
            throw new IncompatibleSpeciesException();
        }

//        Generation[] newNextGeneration = Arrays.copyOf(nextGeneration, nextGeneration.length + 1);
        Generation[] newNextGeneration = new Generation[nextGeneration.length+1];
        System.arraycopy(nextGeneration, 0, newNextGeneration, 0, nextGeneration.length);

        newNextGeneration[newNextGeneration.length - 1] = generation;

        nextGeneration = newNextGeneration;
    }

    public void printTree()
    {
        printTree("",true);
    }
    private void printTree(String prefix, boolean isTail)
    {
        for(int i=0; i<children.length; i++) {
            Biomorph b = children[i];
            System.out.println(prefix + (isTail && i == children.length - 1 ? "└── " : "├── ") + b.toString());
        }

        for (int i = 0; i < nextGeneration.length - 1; i++) {
             nextGeneration[i].printTree(prefix + (isTail ? "    " : "│   "), false);
        }
        if (nextGeneration.length > 0) {
            nextGeneration[nextGeneration.length - 1].printTree(prefix + (isTail ?"    " : "│   "), true);
        }
    }
}
