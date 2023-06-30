package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BiomeGenTaiga extends BiomeGenBase
{
    public BiomeGenTaiga(int par1)
    {
        super(par1);
        spawnableCreatureList.add(new SpawnListEntry(net.minecraft.src.EntityWolf.class, 8, 4, 4));
        biomeDecorator.treesPerChunk = 10;
        biomeDecorator.grassPerChunk = 1;
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
    {
        if (par1Random.nextInt(3) == 0)
        {
            return new WorldGenTaiga1();
        }
        else
        {
            return new WorldGenTaiga2(false);
        }
    }
}
