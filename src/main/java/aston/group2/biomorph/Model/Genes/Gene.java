package aston.group2.biomorph.Model.Genes;

import aston.group2.biomorph.Model.InvalidGeneSequenceException;

import javax.xml.bind.DatatypeConverter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Antoine
 */
public abstract class Gene implements Serializable, Cloneable {
//    public static final byte MAX_VALUES = 3;
    public final char geneCode;
    private Gene parent = null;
    private byte[] values;
    private final float generateWeight;

    public List<Gene> subGenes;

    public Gene(char geneCode)
    {
        this.geneCode = geneCode;

        this.values = new byte[0];

        subGenes = new ArrayList<>();

        generateWeight = 1f;
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

    /**
     * Create a gene with a generation weight
     * @param geneCode a unique char identifying the class of the gene
     * @param weight 1 is normal probability
     */
    public Gene(char geneCode, float weight)
    {
        generateWeight = weight;

        this.geneCode = geneCode;

        this.values = new byte[0];

        subGenes = new ArrayList<>();
    }

    public Gene(char geneCode, float weight, byte[] values)
    {
        this(geneCode, weight);

        setValues(values);
    }

    public abstract int maxValues();
    protected abstract void parseValues();

    public char getGeneCode()
    {
        return geneCode;
    }
    public float getWeight() { return generateWeight; }

    public Gene getParent()
    {
        return parent;
    }

    public void setValues(byte[] values)
    {
        assert(values.length >= maxValues());

        this.values = Arrays.copyOf(values, maxValues());

        parseValues();
    }

    public void setValues(short[] values)
    {
        assert(values.length >= maxValues());

        byte[] newValues = new byte[maxValues()];

        for (int i=0; i<newValues.length; i++)
        {
            newValues[i] = (byte)(values[i] & 0xff); // Convert a short, representing an unsigned byte into a byte
        }

        setValues(newValues);
    }

    public short[] getValues()
    {
        short[] output = new short[values.length];

        for (int i = 0; i < values.length; i++) {
            output[i] = (short)(values[i] & 0xff); // Convert a signed byte into its unsigned value by upgrading its
                                                   // type


            assert(output[i] >= 0 && output[i] < 256);
        }

        return output;
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

        byte[] newvalues = DatatypeConverter.parseHexBinary(hex);

        setValues(newvalues);

        return (maxValues()*2) + 1;
    }

    private static String fillSpaces(int n)
    {
        char[] spaces = new char[n*4]; // 4 space indent
        Arrays.fill(spaces, ' ');
        return new String(spaces);
    }

    @Override
    public String toString()
    {
        return toString(false, 0);
    }
    public String toString(boolean newlines, int indent)
    {
        StringBuilder sb = new StringBuilder();

        if(newlines) sb.append(fillSpaces(indent));
        sb.append(getGeneCode());

        short[] values = getValues();
        for(int i=0; i < Math.min(maxValues(), values.length); i++)
        {
            sb.append(String.format("%02X", values[i]));
        }

        if(newlines) sb.append('\n');

        if(getSubGenes().length > 0)
        {
            if(newlines) {
                sb.append(fillSpaces(indent));
                sb.append("S\n");
            }
            else
                sb.append('S');

            indent++;

            for (Gene gene : getSubGenes()) {
                sb.append(gene.toString(newlines, indent));
                if(newlines) sb.append('\n');
            }

            indent--;

            if(newlines) {
                sb.append(fillSpaces(indent));
                sb.append("s\n");
            }
            else
                sb.append('s');
        }

        return sb.toString();
    }

    @Override
    public Gene clone() throws CloneNotSupportedException {
        Gene g = (Gene)super.clone();
        g.values = values.clone();

        List<Gene> cSubGenes = new ArrayList<>(subGenes.size());
        for (Gene sg : subGenes)
        {
            cSubGenes.add(sg.clone(g));
        }

        g.subGenes = cSubGenes;

        return g;
    }

    private Gene clone(Gene parent) throws CloneNotSupportedException {
        Gene g = (Gene)super.clone();
        g.parent = parent;
        g.values = values.clone();

        List<Gene> cSubGenes = new ArrayList<>(subGenes.size());
        for (Gene sg : subGenes)
        {
            cSubGenes.add(sg.clone(g));
        }

        g.subGenes = cSubGenes;

        return g;
    }
}
