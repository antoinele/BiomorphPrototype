package aston.group2.biomorph.Model;

/**
 * Created by antoine on 30/10/14.
 */
public class InvalidGeneSequenceException extends RuntimeException {
    public InvalidGeneSequenceException(char geneCode)
    {
        super(String.format("Invalid gene \"%c\"", geneCode));
    }
}
