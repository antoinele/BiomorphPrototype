package aston.group2.biomorph.Storage;

import aston.group2.biomorph.Model.Biomorph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antoine on 12/03/15.
 */
public class HallOfFame implements Serializable {
    private List<Biomorph> hallOfFame;

    public HallOfFame()
    {
        hallOfFame = new ArrayList<Biomorph>();
    }
}
