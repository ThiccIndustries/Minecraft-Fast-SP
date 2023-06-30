package net.minecraft.src;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.*;

public class SoundPool
{
    /** The RNG used by SoundPool. */
    private Random rand;

    /**
     * Maps a name (can be sound/newsound/streaming/music/newmusic) to a list of SoundPoolEntry's.
     */
    private Map nameToSoundPoolEntriesMapping;

    /** A list of all SoundPoolEntries that have been loaded. */
    private List allSoundPoolEntries;

    /**
     * The number of soundPoolEntry's. This value is computed but never used (should be equal to
     * allSoundPoolEntries.size()).
     */
    public int numberOfSoundPoolEntries;
    public boolean isGetRandomSound;

    public SoundPool()
    {
        rand = new Random();
        nameToSoundPoolEntriesMapping = new HashMap();
        allSoundPoolEntries = new ArrayList();
        numberOfSoundPoolEntries = 0;
        isGetRandomSound = true;
    }

    /**
     * Adds a sound to this sound pool.
     */
    public SoundPoolEntry addSound(String par1Str, File par2File)
    {
        try
        {
            String s = par1Str;
            par1Str = par1Str.substring(0, par1Str.indexOf("."));

            if (isGetRandomSound)
            {
                for (; Character.isDigit(par1Str.charAt(par1Str.length() - 1)); par1Str = par1Str.substring(0, par1Str.length() - 1)) { }
            }

            par1Str = par1Str.replaceAll("/", ".");

            if (!nameToSoundPoolEntriesMapping.containsKey(par1Str))
            {
                nameToSoundPoolEntriesMapping.put(par1Str, new ArrayList());
            }

            SoundPoolEntry soundpoolentry = new SoundPoolEntry(s, par2File.toURI().toURL());
            ((List)nameToSoundPoolEntriesMapping.get(par1Str)).add(soundpoolentry);
            allSoundPoolEntries.add(soundpoolentry);
            numberOfSoundPoolEntries++;
            return soundpoolentry;
        }
        catch (MalformedURLException malformedurlexception)
        {
            malformedurlexception.printStackTrace();
            throw new RuntimeException(malformedurlexception);
        }
    }

    /**
     * gets a random sound from the specified (by name, can be sound/newsound/streaming/music/newmusic) sound pool.
     */
    public SoundPoolEntry getRandomSoundFromSoundPool(String par1Str)
    {
        List list = (List)nameToSoundPoolEntriesMapping.get(par1Str);

        if (list == null)
        {
            return null;
        }
        else
        {
            return (SoundPoolEntry)list.get(rand.nextInt(list.size()));
        }
    }

    /**
     * Gets a random SoundPoolEntry.
     */
    public SoundPoolEntry getRandomSound()
    {
        if (allSoundPoolEntries.size() == 0)
        {
            return null;
        }
        else
        {
            return (SoundPoolEntry)allSoundPoolEntries.get(rand.nextInt(allSoundPoolEntries.size()));
        }
    }
}
