package net.minecraft.src;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;

public class MapStorage
{
    private ISaveHandler saveHandler;

    /** Map of item data String id to loaded MapDataBases */
    private Map loadedDataMap;

    /** List of loaded MapDataBases. */
    private List loadedDataList;

    /**
     * Map of MapDataBase id String prefixes ('map' etc) to max known unique Short id (the 0 part etc) for that prefix
     */
    private Map idCounts;

    public MapStorage(ISaveHandler par1ISaveHandler)
    {
        loadedDataMap = new HashMap();
        loadedDataList = new ArrayList();
        idCounts = new HashMap();
        saveHandler = par1ISaveHandler;
        loadIdCounts();
    }

    /**
     * Loads an existing MapDataBase corresponding to the given String id from disk, instantiating the given Class, or
     * returns null if none such file exists. args: Class to instantiate, String dataid
     */
    public WorldSavedData loadData(Class par1Class, String par2Str)
    {
        WorldSavedData worldsaveddata = (WorldSavedData)loadedDataMap.get(par2Str);

        if (worldsaveddata != null)
        {
            return worldsaveddata;
        }

        if (saveHandler != null)
        {
            try
            {
                File file = saveHandler.getMapFileFromName(par2Str);

                if (file != null && file.exists())
                {
                    try
                    {
                        worldsaveddata = (WorldSavedData)par1Class.getConstructor(new Class[]
                                {
                                    java.lang.String.class
                                }).newInstance(new Object[]
                                        {
                                            par2Str
                                        });
                    }
                    catch (Exception exception1)
                    {
                        throw new RuntimeException((new StringBuilder()).append("Failed to instantiate ").append(par1Class.toString()).toString(), exception1);
                    }

                    FileInputStream fileinputstream = new FileInputStream(file);
                    NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(fileinputstream);
                    fileinputstream.close();
                    worldsaveddata.readFromNBT(nbttagcompound.getCompoundTag("data"));
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

        if (worldsaveddata != null)
        {
            loadedDataMap.put(par2Str, worldsaveddata);
            loadedDataList.add(worldsaveddata);
        }

        return worldsaveddata;
    }

    /**
     * Assigns the given String id to the given MapDataBase, removing any existing ones of the same id.
     */
    public void setData(String par1Str, WorldSavedData par2WorldSavedData)
    {
        if (par2WorldSavedData == null)
        {
            throw new RuntimeException("Can't set null data");
        }

        if (loadedDataMap.containsKey(par1Str))
        {
            loadedDataList.remove(loadedDataMap.remove(par1Str));
        }

        loadedDataMap.put(par1Str, par2WorldSavedData);
        loadedDataList.add(par2WorldSavedData);
    }

    /**
     * Saves all dirty loaded MapDataBases to disk.
     */
    public void saveAllData()
    {
        for (int i = 0; i < loadedDataList.size(); i++)
        {
            WorldSavedData worldsaveddata = (WorldSavedData)loadedDataList.get(i);

            if (worldsaveddata.isDirty())
            {
                saveData(worldsaveddata);
                worldsaveddata.setDirty(false);
            }
        }
    }

    /**
     * Saves the given MapDataBase to disk.
     */
    private void saveData(WorldSavedData par1WorldSavedData)
    {
        if (saveHandler == null)
        {
            return;
        }

        try
        {
            File file = saveHandler.getMapFileFromName(par1WorldSavedData.mapName);

            if (file != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                par1WorldSavedData.writeToNBT(nbttagcompound);
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setCompoundTag("data", nbttagcompound);
                FileOutputStream fileoutputstream = new FileOutputStream(file);
                CompressedStreamTools.writeCompressed(nbttagcompound1, fileoutputstream);
                fileoutputstream.close();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    /**
     * Loads the idCounts Map from the 'idcounts' file.
     */
    private void loadIdCounts()
    {
        try
        {
            idCounts.clear();

            if (saveHandler == null)
            {
                return;
            }

            File file = saveHandler.getMapFileFromName("idcounts");

            if (file != null && file.exists())
            {
                DataInputStream datainputstream = new DataInputStream(new FileInputStream(file));
                NBTTagCompound nbttagcompound = CompressedStreamTools.read(datainputstream);
                datainputstream.close();
                Iterator iterator = nbttagcompound.getTags().iterator();

                do
                {
                    if (!iterator.hasNext())
                    {
                        break;
                    }

                    NBTBase nbtbase = (NBTBase)iterator.next();

                    if (nbtbase instanceof NBTTagShort)
                    {
                        NBTTagShort nbttagshort = (NBTTagShort)nbtbase;
                        String s = nbttagshort.getName();
                        short word0 = nbttagshort.data;
                        idCounts.put(s, Short.valueOf(word0));
                    }
                }
                while (true);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    /**
     * Returns an unique new data id for the given prefix and saves the idCounts map to the 'idcounts' file.
     */
    public int getUniqueDataId(String par1Str)
    {
        Short short1 = (Short)idCounts.get(par1Str);

        if (short1 == null)
        {
            short1 = Short.valueOf((short)0);
        }
        else
        {
            Short short2 = short1;
            Short short3 = short1 = Short.valueOf((short)(short1.shortValue() + 1));
            Short _tmp = short2;
        }

        idCounts.put(par1Str, short1);

        if (saveHandler == null)
        {
            return short1.shortValue();
        }

        try
        {
            File file = saveHandler.getMapFileFromName("idcounts");

            if (file != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                String s;
                short word0;

                for (Iterator iterator = idCounts.keySet().iterator(); iterator.hasNext(); nbttagcompound.setShort(s, word0))
                {
                    s = (String)iterator.next();
                    word0 = ((Short)idCounts.get(s)).shortValue();
                }

                DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file));
                CompressedStreamTools.write(nbttagcompound, dataoutputstream);
                dataoutputstream.close();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return short1.shortValue();
    }
}
