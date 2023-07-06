package net.minecraft.src;

import java.util.*;

public class MapGenNetherBridge extends MapGenStructure
{
    private List spawnList;

    public MapGenNetherBridge()
    {
        spawnList = new ArrayList();
        spawnList.add(new SpawnListEntry(net.minecraft.src.EntityBlaze.class, 10, 2, 3));
        spawnList.add(new SpawnListEntry(net.minecraft.src.EntityPigZombie.class, 10, 4, 4));
        spawnList.add(new SpawnListEntry(net.minecraft.src.EntityMagmaCube.class, 3, 4, 4));
    }

    public List getSpawnList()
    {
        return spawnList;
    }

    protected boolean canSpawnStructureAtCoords(int par1, int par2)
    {
        int i = par1 >> 4;
        int j = par2 >> 4;
        rand.setSeed((long)(i ^ j << 4) ^ worldObj.getSeed());
        rand.nextInt();

        if (rand.nextInt(3) != 0)
        {
            return false;
        }

        if (par1 != (i << 4) + 4 + rand.nextInt(8))
        {
            return false;
        }

        return par2 == (j << 4) + 4 + rand.nextInt(8);
    }

    protected StructureStart getStructureStart(int par1, int par2)
    {
        return new StructureNetherBridgeStart(worldObj, rand, par1, par2);
    }
}
