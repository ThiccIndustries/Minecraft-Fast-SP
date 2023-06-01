package net.minecraft.src;

public class ChunkLoader
{
    public ChunkLoader()
    {
    }

    public static AnvilConverterData load(NBTTagCompound par0NBTTagCompound)
    {
        int i = par0NBTTagCompound.getInteger("xPos");
        int j = par0NBTTagCompound.getInteger("zPos");
        AnvilConverterData anvilconverterdata = new AnvilConverterData(i, j);
        anvilconverterdata.blocks = par0NBTTagCompound.getByteArray("Blocks");
        anvilconverterdata.data = new NibbleArrayReader(par0NBTTagCompound.getByteArray("Data"), 7);
        anvilconverterdata.skyLight = new NibbleArrayReader(par0NBTTagCompound.getByteArray("SkyLight"), 7);
        anvilconverterdata.blockLight = new NibbleArrayReader(par0NBTTagCompound.getByteArray("BlockLight"), 7);
        anvilconverterdata.heightmap = par0NBTTagCompound.getByteArray("HeightMap");
        anvilconverterdata.terrainPopulated = par0NBTTagCompound.getBoolean("TerrainPopulated");
        anvilconverterdata.entities = par0NBTTagCompound.getTagList("Entities");
        anvilconverterdata.tileEntities = par0NBTTagCompound.getTagList("TileEntities");
        anvilconverterdata.tileTicks = par0NBTTagCompound.getTagList("TileTicks");

        try
        {
            anvilconverterdata.lastUpdated = par0NBTTagCompound.getLong("LastUpdate");
        }
        catch (ClassCastException classcastexception)
        {
            anvilconverterdata.lastUpdated = par0NBTTagCompound.getInteger("LastUpdate");
        }

        return anvilconverterdata;
    }

    public static void convertToAnvilFormat(AnvilConverterData par0AnvilConverterData, NBTTagCompound par1NBTTagCompound, WorldChunkManager par2WorldChunkManager)
    {
        par1NBTTagCompound.setInteger("xPos", par0AnvilConverterData.x);
        par1NBTTagCompound.setInteger("zPos", par0AnvilConverterData.z);
        par1NBTTagCompound.setLong("LastUpdate", par0AnvilConverterData.lastUpdated);
        int ai[] = new int[par0AnvilConverterData.heightmap.length];

        for (int i = 0; i < par0AnvilConverterData.heightmap.length; i++)
        {
            ai[i] = par0AnvilConverterData.heightmap[i];
        }

        par1NBTTagCompound.func_48183_a("HeightMap", ai);
        par1NBTTagCompound.setBoolean("TerrainPopulated", par0AnvilConverterData.terrainPopulated);
        NBTTagList nbttaglist = new NBTTagList("Sections");

        for (int j = 0; j < 8; j++)
        {
            boolean flag = true;

            for (int l = 0; l < 16 && flag; l++)
            {
                label0:

                for (int j1 = 0; j1 < 16 && flag; j1++)
                {
                    int k1 = 0;

                    do
                    {
                        if (k1 >= 16)
                        {
                            continue label0;
                        }

                        int l1 = l << 11 | k1 << 7 | j1 + (j << 4);
                        byte byte0 = par0AnvilConverterData.blocks[l1];

                        if (byte0 != 0)
                        {
                            flag = false;
                            continue label0;
                        }

                        k1++;
                    }
                    while (true);
                }
            }

            if (flag)
            {
                continue;
            }

            byte abyte1[] = new byte[4096];
            NibbleArray nibblearray = new NibbleArray(abyte1.length, 4);
            NibbleArray nibblearray1 = new NibbleArray(abyte1.length, 4);
            NibbleArray nibblearray2 = new NibbleArray(abyte1.length, 4);

            for (int i2 = 0; i2 < 16; i2++)
            {
                for (int j2 = 0; j2 < 16; j2++)
                {
                    for (int k2 = 0; k2 < 16; k2++)
                    {
                        int l2 = i2 << 11 | k2 << 7 | j2 + (j << 4);
                        byte byte1 = par0AnvilConverterData.blocks[l2];
                        abyte1[j2 << 8 | k2 << 4 | i2] = (byte)(byte1 & 0xff);
                        nibblearray.set(i2, j2, k2, par0AnvilConverterData.data.get(i2, j2 + (j << 4), k2));
                        nibblearray1.set(i2, j2, k2, par0AnvilConverterData.skyLight.get(i2, j2 + (j << 4), k2));
                        nibblearray2.set(i2, j2, k2, par0AnvilConverterData.blockLight.get(i2, j2 + (j << 4), k2));
                    }
                }
            }

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setByte("Y", (byte)(j & 0xff));
            nbttagcompound.setByteArray("Blocks", abyte1);
            nbttagcompound.setByteArray("Data", nibblearray.data);
            nbttagcompound.setByteArray("SkyLight", nibblearray1.data);
            nbttagcompound.setByteArray("BlockLight", nibblearray2.data);
            nbttaglist.appendTag(nbttagcompound);
        }

        par1NBTTagCompound.setTag("Sections", nbttaglist);
        byte abyte0[] = new byte[256];

        for (int k = 0; k < 16; k++)
        {
            for (int i1 = 0; i1 < 16; i1++)
            {
                abyte0[i1 << 4 | k] = (byte)(par2WorldChunkManager.getBiomeGenAt(par0AnvilConverterData.x << 4 | k, par0AnvilConverterData.z << 4 | i1).biomeID & 0xff);
            }
        }

        par1NBTTagCompound.setByteArray("Biomes", abyte0);
        par1NBTTagCompound.setTag("Entities", par0AnvilConverterData.entities);
        par1NBTTagCompound.setTag("TileEntities", par0AnvilConverterData.tileEntities);

        if (par0AnvilConverterData.tileTicks != null)
        {
            par1NBTTagCompound.setTag("TileTicks", par0AnvilConverterData.tileTicks);
        }
    }
}
