package net.minecraft.src;

import java.io.*;
import java.util.*;

public class AnvilSaveConverter extends SaveFormatOld
{
    public AnvilSaveConverter(File par1File)
    {
        super(par1File);
    }

    public String getFormatName()
    {
        return "Anvil";
    }

    public List getSaveList()
    {
        ArrayList arraylist = new ArrayList();
        File afile[] = savesDirectory.listFiles();
        File afile1[] = afile;
        int i = afile1.length;

        for (int j = 0; j < i; j++)
        {
            File file = afile1[j];

            if (!file.isDirectory())
            {
                continue;
            }

            String s = file.getName();
            WorldInfo worldinfo = getWorldInfo(s);

            if (worldinfo == null || worldinfo.getSaveVersion() != 19132 && worldinfo.getSaveVersion() != 19133)
            {
                continue;
            }

            boolean flag = worldinfo.getSaveVersion() != func_48431_c();
            String s1 = worldinfo.getWorldName();

            if (s1 == null || MathHelper.stringNullOrLengthZero(s1))
            {
                s1 = s;
            }

            long l = 0L;
            arraylist.add(new SaveFormatComparator(s, s1, worldinfo.getLastTimePlayed(), l, worldinfo.getGameType(), flag, worldinfo.isHardcoreModeEnabled(), worldinfo.getWorldSize()));
        }

        return arraylist;
    }

    protected int func_48431_c()
    {
        return 19133;
    }

    public void flushCache()
    {
        RegionFileCache.clearRegionFileReferences();
    }

    /**
     * Returns back a loader for the specified save directory
     */
    public ISaveHandler getSaveLoader(String par1Str, boolean par2)
    {
        return new AnvilSaveHandler(savesDirectory, par1Str, par2);
    }

    /**
     * Checks if the save directory uses the old map format
     */
    public boolean isOldMapFormat(String par1Str)
    {
        WorldInfo worldinfo = getWorldInfo(par1Str);
        return worldinfo != null && worldinfo.getSaveVersion() != func_48431_c();
    }

    /**
     * Converts the specified map to the new map format. Args: worldName, loadingScreen
     */
    public boolean convertMapFormat(String par1Str, IProgressUpdate par2IProgressUpdate)
    {
        par2IProgressUpdate.setLoadingProgress(0);
        ArrayList arraylist = new ArrayList();
        ArrayList arraylist1 = new ArrayList();
        ArrayList arraylist2 = new ArrayList();
        File file = new File(savesDirectory, par1Str);
        File file1 = new File(file, "DIM-1");
        File file2 = new File(file, "DIM1");
        System.out.println("Scanning folders...");
        func_48432_a(file, arraylist);

        if (file1.exists())
        {
            func_48432_a(file1, arraylist1);
        }

        if (file2.exists())
        {
            func_48432_a(file2, arraylist2);
        }

        int i = arraylist.size() + arraylist1.size() + arraylist2.size();
        System.out.println((new StringBuilder()).append("Total conversion count is ").append(i).toString());
        WorldInfo worldinfo = getWorldInfo(par1Str);
        Object obj = null;

        if (worldinfo.getTerrainType() == WorldType.FLAT)
        {
            obj = new WorldChunkManagerHell(BiomeGenBase.plains, 0.5F, 0.5F);
        }
        else
        {
            obj = new WorldChunkManager(worldinfo.getSeed(), worldinfo.getTerrainType());
        }

        func_48428_a(new File(file, "region"), arraylist, ((WorldChunkManager)(obj)), 0, i, par2IProgressUpdate);
        func_48428_a(new File(file1, "region"), arraylist1, new WorldChunkManagerHell(BiomeGenBase.hell, 1.0F, 0.0F), arraylist.size(), i, par2IProgressUpdate);
        func_48428_a(new File(file2, "region"), arraylist2, new WorldChunkManagerHell(BiomeGenBase.sky, 0.5F, 0.0F), arraylist.size() + arraylist1.size(), i, par2IProgressUpdate);
        worldinfo.setSaveVersion(19133);

        if (worldinfo.getTerrainType() == WorldType.DEFAULT_1_1)
        {
            worldinfo.setTerrainType(WorldType.DEFAULT);
        }

        func_48429_d(par1Str);
        ISaveHandler isavehandler = getSaveLoader(par1Str, false);
        isavehandler.saveWorldInfo(worldinfo);
        return true;
    }

    private void func_48429_d(String par1Str)
    {
        File file = new File(savesDirectory, par1Str);

        if (!file.exists())
        {
            System.out.println("Warning: Unable to create level.dat_mcr backup");
            return;
        }

        File file1 = new File(file, "level.dat");

        if (!file1.exists())
        {
            System.out.println("Warning: Unable to create level.dat_mcr backup");
            return;
        }

        File file2 = new File(file, "level.dat_mcr");

        if (!file1.renameTo(file2))
        {
            System.out.println("Warning: Unable to create level.dat_mcr backup");
        }
    }

    private void func_48428_a(File par1File, ArrayList par2ArrayList, WorldChunkManager par3WorldChunkManager, int par4, int par5, IProgressUpdate par6IProgressUpdate)
    {
        int i;

        for (Iterator iterator = par2ArrayList.iterator(); iterator.hasNext(); par6IProgressUpdate.setLoadingProgress(i))
        {
            File file = (File)iterator.next();
            func_48430_a(par1File, file, par3WorldChunkManager, par4, par5, par6IProgressUpdate);
            par4++;
            i = (int)Math.round((100D * (double)par4) / (double)par5);
        }
    }

    private void func_48430_a(File par1File, File par2File, WorldChunkManager par3WorldChunkManager, int par4, int par5, IProgressUpdate par6IProgressUpdate)
    {
        try
        {
            String s = par2File.getName();
            RegionFile regionfile = new RegionFile(par2File);
            RegionFile regionfile1 = new RegionFile(new File(par1File, (new StringBuilder()).append(s.substring(0, s.length() - ".mcr".length())).append(".mca").toString()));

            for (int i = 0; i < 32; i++)
            {
                for (int j = 0; j < 32; j++)
                {
                    if (!regionfile.isChunkSaved(i, j) || regionfile1.isChunkSaved(i, j))
                    {
                        continue;
                    }

                    DataInputStream datainputstream = regionfile.getChunkDataInputStream(i, j);

                    if (datainputstream == null)
                    {
                        System.out.println("Failed to fetch input stream");
                    }
                    else
                    {
                        NBTTagCompound nbttagcompound = CompressedStreamTools.read(datainputstream);
                        datainputstream.close();
                        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Level");
                        AnvilConverterData anvilconverterdata = ChunkLoader.load(nbttagcompound1);
                        NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                        NBTTagCompound nbttagcompound3 = new NBTTagCompound();
                        nbttagcompound2.setTag("Level", nbttagcompound3);
                        ChunkLoader.convertToAnvilFormat(anvilconverterdata, nbttagcompound3, par3WorldChunkManager);
                        DataOutputStream dataoutputstream = regionfile1.getChunkDataOutputStream(i, j);
                        CompressedStreamTools.write(nbttagcompound2, dataoutputstream);
                        dataoutputstream.close();
                    }
                }

                int k = (int)Math.round((100D * (double)(par4 * 1024)) / (double)(par5 * 1024));
                int l = (int)Math.round((100D * (double)((i + 1) * 32 + par4 * 1024)) / (double)(par5 * 1024));

                if (l > k)
                {
                    par6IProgressUpdate.setLoadingProgress(l);
                }
            }

            regionfile.close();
            regionfile1.close();
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    private void func_48432_a(File par1File, ArrayList par2ArrayList)
    {
        File file = new File(par1File, "region");
        File afile[] = file.listFiles(new AnvilSaveConverterFileFilter(this));

        if (afile != null)
        {
            File afile1[] = afile;
            int i = afile1.length;

            for (int j = 0; j < i; j++)
            {
                File file1 = afile1[j];
                par2ArrayList.add(file1);
            }
        }
    }
}
