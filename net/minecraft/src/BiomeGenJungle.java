package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BiomeGenJungle extends BiomeGenBase
{
    public BiomeGenJungle(int par1)
    {
        super(par1);
        biomeDecorator.treesPerChunk = 50;
        biomeDecorator.grassPerChunk = 25;
        biomeDecorator.flowersPerChunk = 4;
        spawnableMonsterList.add(new SpawnListEntry(net.minecraft.src.EntityOcelot.class, 2, 1, 1));
        spawnableCreatureList.add(new SpawnListEntry(net.minecraft.src.EntityChicken.class, 10, 4, 4));
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
    {
        if (par1Random.nextInt(10) == 0)
        {
            return worldGenBigTree;
        }

        if (par1Random.nextInt(2) == 0)
        {
            return new WorldGenShrub(3, 0);
        }

        if (par1Random.nextInt(3) == 0)
        {
            return new WorldGenHugeTrees(false, 10 + par1Random.nextInt(20), 3, 3);
        }
        else
        {
            return new WorldGenTrees(false, 4 + par1Random.nextInt(7), 3, 3, true);
        }
    }

    public WorldGenerator func_48410_b(Random par1Random)
    {
        if (par1Random.nextInt(4) == 0)
        {
            return new WorldGenTallGrass(Block.tallGrass.blockID, 2);
        }
        else
        {
            return new WorldGenTallGrass(Block.tallGrass.blockID, 1);
        }
    }

    public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
        super.decorate(par1World, par2Random, par3, par4);
        WorldGenVines worldgenvines = new WorldGenVines();

        for (int i = 0; i < 50; i++)
        {
            int j = par3 + par2Random.nextInt(16) + 8;
            byte byte0 = 64;
            int k = par4 + par2Random.nextInt(16) + 8;
            worldgenvines.generate(par1World, par2Random, j, byte0, k);
        }
    }
}
