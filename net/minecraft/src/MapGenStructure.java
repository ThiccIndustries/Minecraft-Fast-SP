package net.minecraft.src;

import java.util.*;

public abstract class MapGenStructure extends MapGenBase
{
    protected HashMap coordMap;

    public MapGenStructure()
    {
        coordMap = new HashMap();
    }

    public void generate(IChunkProvider par1IChunkProvider, World par2World, int par3, int par4, byte par5ArrayOfByte[])
    {
        super.generate(par1IChunkProvider, par2World, par3, par4, par5ArrayOfByte);
    }

    /**
     * Recursively called by generate() (generate) and optionally by itself.
     */
    protected void recursiveGenerate(World par1World, int par2, int par3, int par4, int par5, byte par6ArrayOfByte[])
    {
        if (coordMap.containsKey(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(par2, par3))))
        {
            return;
        }

        rand.nextInt();

        if (canSpawnStructureAtCoords(par2, par3))
        {
            StructureStart structurestart = getStructureStart(par2, par3);
            coordMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(par2, par3)), structurestart);
        }
    }

    /**
     * Generates structures in specified chunk next to existing structures. Does *not* generate StructureStarts.
     */
    public boolean generateStructuresInChunk(World par1World, Random par2Random, int par3, int par4)
    {
        int i = (par3 << 4) + 8;
        int j = (par4 << 4) + 8;
        boolean flag = false;
        Iterator iterator = coordMap.values().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            StructureStart structurestart = (StructureStart)iterator.next();

            if (structurestart.isSizeableStructure() && structurestart.getBoundingBox().intersectsWith(i, j, i + 15, j + 15))
            {
                structurestart.generateStructure(par1World, par2Random, new StructureBoundingBox(i, j, i + 15, j + 15));
                flag = true;
            }
        }
        while (true);

        return flag;
    }

    public boolean func_40483_a(int par1, int par2, int par3)
    {
        Iterator iterator = coordMap.values().iterator();
        label0:

        do
        {
            if (iterator.hasNext())
            {
                StructureStart structurestart = (StructureStart)iterator.next();

                if (!structurestart.isSizeableStructure() || !structurestart.getBoundingBox().intersectsWith(par1, par3, par1, par3))
                {
                    continue;
                }

                Iterator iterator1 = structurestart.getComponents().iterator();
                StructureComponent structurecomponent;

                do
                {
                    if (!iterator1.hasNext())
                    {
                        continue label0;
                    }

                    structurecomponent = (StructureComponent)iterator1.next();
                }
                while (!structurecomponent.getBoundingBox().isVecInside(par1, par2, par3));

                break;
            }
            else
            {
                return false;
            }
        }
        while (true);

        return true;
    }

    public ChunkPosition getNearestInstance(World par1World, int par2, int par3, int par4)
    {
        worldObj = par1World;
        rand.setSeed(par1World.getSeed());
        long l = rand.nextLong();
        long l1 = rand.nextLong();
        long l2 = (long)(par2 >> 4) * l;
        long l3 = (long)(par4 >> 4) * l1;
        rand.setSeed(l2 ^ l3 ^ par1World.getSeed());
        recursiveGenerate(par1World, par2 >> 4, par4 >> 4, 0, 0, null);
        double d = Double.MAX_VALUE;
        ChunkPosition chunkposition = null;
        Object obj = coordMap.values().iterator();

        do
        {
            if (!((Iterator)(obj)).hasNext())
            {
                break;
            }

            StructureStart structurestart = (StructureStart)((Iterator)(obj)).next();

            if (structurestart.isSizeableStructure())
            {
                StructureComponent structurecomponent = (StructureComponent)structurestart.getComponents().get(0);
                ChunkPosition chunkposition2 = structurecomponent.getCenter();
                int i = chunkposition2.x - par2;
                int k = chunkposition2.y - par3;
                int j1 = chunkposition2.z - par4;
                double d1 = i + i * k * k + j1 * j1;

                if (d1 < d)
                {
                    d = d1;
                    chunkposition = chunkposition2;
                }
            }
        }
        while (true);

        if (chunkposition != null)
        {
            return chunkposition;
        }

        obj = func_40482_a();

        if (obj != null)
        {
            ChunkPosition chunkposition1 = null;
            Iterator iterator = ((List)(obj)).iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                ChunkPosition chunkposition3 = (ChunkPosition)iterator.next();
                int j = chunkposition3.x - par2;
                int i1 = chunkposition3.y - par3;
                int k1 = chunkposition3.z - par4;
                double d2 = j + j * i1 * i1 + k1 * k1;

                if (d2 < d)
                {
                    d = d2;
                    chunkposition1 = chunkposition3;
                }
            }
            while (true);

            return chunkposition1;
        }
        else
        {
            return null;
        }
    }

    protected List func_40482_a()
    {
        return null;
    }

    protected abstract boolean canSpawnStructureAtCoords(int i, int j);

    protected abstract StructureStart getStructureStart(int i, int j);
}
