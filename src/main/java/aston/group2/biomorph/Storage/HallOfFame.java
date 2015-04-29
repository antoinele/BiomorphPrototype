package aston.group2.biomorph.Storage;

import aston.group2.biomorph.Model.Biomorph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antoine on 12/03/15.
 */
public class HallOfFame implements Serializable {
    public final Biomorph[] hallOfFame = new Biomorph[10];
}
