package aston.group2.biomorph.Model;

import aston.group2.biomorph.Model.Genes.Gene;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by antoine on 29/10/14.
 */
public class GeneFactory {

    static {
        buildGeneMap();
    }

    public static class GeneWeight {
        public final char geneCode;
        public final Class<? extends Gene> gene;
        public final float weight;

        public GeneWeight(char geneCode, Class<? extends Gene> gene, float weight) {
            this.geneCode = geneCode;
            this.gene = gene;
            this.weight = weight;
        }
    }

    private static HashMap<Character, GeneWeight> geneMap = null;

    private static void buildGeneMap()
    {
        if(geneMap != null) return;

        geneMap = new HashMap<>();

        String geneclass = Gene.class.getCanonicalName();

        geneclass = geneclass.substring(0, geneclass.lastIndexOf('.'));

        Reflections reflections = new Reflections(geneclass);

        Set<Class<? extends Gene>> geneTypes = reflections.getSubTypesOf(Gene.class);

        for (Class<? extends Gene> gene : geneTypes)
        {
            try
            {
                Gene ng = gene.newInstance();

                geneMap.put(ng.getGeneCode(), new GeneWeight(ng.getGeneCode(), gene, ng.getWeight()));
            }
            catch(InstantiationException ie)
            {
            }
            catch(IllegalAccessException e)
            {
            }

        }
    }

    public static Gene getGeneFromCode(char code)
    {
        buildGeneMap();

        if(!geneMap.containsKey(code))
        {
            return null;
        }

        try
        {
            return geneMap.get(code).gene.newInstance();
        }
        catch(InstantiationException ie)
        {
            return null;
        }
        catch(IllegalAccessException e)
        {
            return null;
        }
    }

    public static char[] geneCodes()
    {
        buildGeneMap();

        Set<Character> cm = geneMap.keySet();

        char[] result = new char[cm.size() - 1];

        int i=0;
        for(char c : cm)
        {
            if(c != 'X')
                result[i++] = c;
        }

        return result;
    }

    public static GeneWeight[] geneWeights()
    {
        buildGeneMap();

        GeneWeight[] result = new GeneWeight[geneMap.size() - 1];

        int i=0;
        for(GeneWeight gw : geneMap.values())
        {
            if(gw.geneCode != 'X')
            {
                System.out.println("Gene: " + gw.geneCode);
                result[i++] = gw;
            }
        }

        return result;
    }
}
