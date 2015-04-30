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

    private static HashMap<Character, Class<? extends Gene>> geneMap = null;

    private static void buildGeneMap()
    {
        if(geneMap != null) return;

        geneMap = new HashMap<Character, Class<? extends Gene>>();

        String geneclass = Gene.class.getCanonicalName();

        geneclass = geneclass.substring(0, geneclass.lastIndexOf('.'));

        Reflections reflections = new Reflections(geneclass);

        Set<Class<? extends Gene>> geneTypes = reflections.getSubTypesOf(Gene.class);

        for (Class<? extends Gene> gene : geneTypes)
        {
            try
            {
                char code;
                code = gene.newInstance().getGeneCode();

                geneMap.put(code, gene);
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
            return geneMap.get(code).newInstance();
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
}
