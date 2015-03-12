package aston.group2.biomorph.Storage;

import aston.group2.biomorph.Model.Biomorph;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by antoine on 12/03/15.
 */
public class Generation {
    public Generation           prevGeneration;
    public Generation[]         nextGeneration;
    private int                 seed; //TODO: we need more mutation info (settings, etc)

    private Map<String, Object> settings;

    public Biomorph[]           children;

    public Generation()
    {
        settings = new HashMap<String, Object>();
    }

    public void setProperty(String key, Object value)
    {
        settings.put(key, value);
    }
}
