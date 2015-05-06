package aston.group2.biomorph.Storage;

import aston.group2.biomorph.Model.Biomorph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Antoine
 */
public class HallOfFame implements Serializable {
    private final Biomorph[] hallOfFame = new Biomorph[10];

    private transient List<ActionListener> actionListeners = null;

    public Biomorph[] getAll()
    {
        return Arrays.copyOf(hallOfFame, hallOfFame.length);
    }

    public void set(Biomorph biomorph, int index)
    {
        hallOfFame[index] = biomorph;
        notifyUpdateListeners();
    }

    /**
     * This creates the hall of boxes, where each biomorph is displayed.
     * @param biomorph biomorph being added.
     * @return
     */
    public boolean add(Biomorph biomorph)
    {
        for (int i = 0; i < hallOfFame.length; i++) {
            if(hallOfFame[i] == null)
            {
                hallOfFame[i] = biomorph;
                notifyUpdateListeners();
                return true;
            }
        }

        return false;
    }

    public Biomorph get(int index)
    {
        return hallOfFame[index];
    }

    public void remove(int index)
    {
        hallOfFame[index] = null;
    }

    public int maxSize()
    {
        return hallOfFame.length;
    }

    private void notifyUpdateListeners()
    {
        for (ActionListener e : actionListeners)
        {
            e.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Inserted Item"));
        }
    }

    public void addUpdateListener(ActionListener e)
    {
        if(actionListeners == null)
        {
            actionListeners = new ArrayList<>();
        }

        actionListeners.add(e);
    }
}
