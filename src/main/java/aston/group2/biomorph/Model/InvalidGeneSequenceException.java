package aston.group2.biomorph.Model;

/**
 * @author Antoine
 */
public class InvalidGeneSequenceException extends RuntimeException {
    public InvalidGeneSequenceException(char geneCode)
    {
        super(String.format("Invalid gene \"%c\"", geneCode));
    }
}
