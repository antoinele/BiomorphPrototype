package biomorph.prototype.Model;

import biomorph.prototype.Model.Genes.Gene;
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

        Reflections reflections = new Reflections("biomorph.prototype.Model.Genes");

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
}
