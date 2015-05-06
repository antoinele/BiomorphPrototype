package aston.group2.biomorph.Storage;

import java.io.*;

/**
 * @author Antoine
 */
public class BiomorphHistoryLoader {
    public static BiomorphHistory biomorphHistory;
    public static HallOfFame hallOfFame;

    public static String filename = "biomorphhistory.dat";

    private static class Storage implements Serializable
    {
        public BiomorphHistory biomorphHistory;
        public HallOfFame hallOfFame;
    }

    public static boolean create()
    {
        biomorphHistory = new BiomorphHistory();
        hallOfFame = new HallOfFame();

        return save();
    }

    public static boolean load()
    {
        Storage storage;
        boolean status;
        try {
            FileInputStream fi = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fi);
            storage = (Storage)in.readObject();

            if(storage.biomorphHistory == null || storage.hallOfFame == null) {
                status = false;
            }
            else {
                biomorphHistory = storage.biomorphHistory;
                hallOfFame = storage.hallOfFame;

                status = true;
            }
        }
        catch(IOException|ClassCastException|ClassNotFoundException e)
        {
            status = false;
        }

        return status;
    }

    public static boolean save()
    {
        Storage storage = new Storage();
        storage.biomorphHistory = biomorphHistory;
        storage.hallOfFame = hallOfFame;

        boolean status;

        try {
            FileOutputStream fo = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fo);
            out.writeObject(storage);
            out.close();
            fo.close();

            status = true;
        }
        catch(NotSerializableException e)
        {
            System.err.println(e.toString());
            status = false;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            status = false;
        }

        return status;
    }
}
