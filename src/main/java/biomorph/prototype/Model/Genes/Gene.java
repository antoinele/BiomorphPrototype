package biomorph.prototype.Model.Genes;

import biomorph.prototype.Model.InvalidGeneSequenceException;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by antoine on 29/10/14.
 */
public abstract class Gene {
//    public static final byte MAX_VALUES = 3;
    private char geneCode;
    private Gene parent = null;
    private byte[] values;

    private ArrayList<Gene> subGenes;

    public Gene(char geneCode)
    {
        this.geneCode = geneCode;

        this.values = new byte[0];

        subGenes = new ArrayList<Gene>();
    }

    /**
     * Create a gene
     * @param geneCode a unique char identifying the class of the gene
     * @param values
     */
    public Gene(char geneCode, byte[] values) throws Exception
    {
        this(geneCode);

        setValues(values);
    }

    protected int maxValues()
    {
        return 3;
    }
    protected abstract void parseValues();

    public char getGeneCode()
    {
        return geneCode;
    }

    public Gene getParent()
    {
        return parent;
    }

    public void setValues(byte[] values)
    {
        assert(values.length == maxValues());

        this.values = Arrays.copyOf(values, maxValues());

        parseValues();
    }

    public byte[] getValues()
    {
        return this.values;
    }

    public Gene[] getSubGenes()
    {
        return subGenes.toArray(new Gene[subGenes.size()]);
    }

    public void addSubGene(Gene subGene)
    {
        subGene.parent = this;
        subGenes.add(subGene);
    }

    public int deserialise(char[] data) throws InvalidGeneSequenceException
    {
        assert(data[0] == getGeneCode());

        String hex = String.valueOf(data, 1, maxValues()*2);

//        System.err.println(hex);

        byte[] newvalues = DatatypeConverter.parseHexBinary(hex);
/*
        byte[] newvalues = new byte[maxValues()];

        for (int i = 1; i < (maxValues()*2)+1; i+=2) {
//            if(!hex.matcher(String.valueOf(data[i+1])).matches())
            char c = data[i];
            if( ! ( (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') ) )
            {
                throw new InvalidGeneSequenceException();
            }

            newvalues[(i-1) / 2] = (byte)((Character.digit(data[i], 16) << 4) + (Character.digit(data[i+1], 16)));
        }
*/

        setValues(newvalues);

        return (maxValues()*2) + 1;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(getGeneCode());

        for(int i=0; i < maxValues(); i++)
        {
            sb.append(String.format("%02X", getValues()[i]));
        }

        if(getSubGenes().length > 0)
        {
            sb.append('S');

            for (Gene gene : getSubGenes()) {
                sb.append(gene.toString());
            }

            sb.append('s');
        }

        return sb.toString();
    }
}
