package net.minecraft.src;

import java.util.Random;

public class WorldGenSpikes extends WorldGenerator
{
    /**
     * The Block ID that the generator is allowed to replace while generating the terrain.
     */
    private int replaceID;

    public WorldGenSpikes(int par1)
    {
        replaceID = par1;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        if (!par1World.isAirBlock(par3, par4, par5) || par1World.getBlockId(par3, par4 - 1, par5) != replaceID)
        {
            return false;
        }

        int i = par2Random.nextInt(32) + 6;
        int j = par2Random.nextInt(4) + 1;

        for (int k = par3 - j; k <= par3 + j; k++)
        {
            for (int i1 = par5 - j; i1 <= par5 + j; i1++)
            {
                int k1 = k - par3;
                int i2 = i1 - par5;

                if (k1 * k1 + i2 * i2 <= j * j + 1 && par1World.getBlockId(k, par4 - 1, i1) != replaceID)
                {
                    return false;
                }
            }
        }

        for (int l = par4; l < par4 + i && l < 128; l++)
        {
            for (int j1 = par3 - j; j1 <= par3 + j; j1++)
            {
                for (int l1 = par5 - j; l1 <= par5 + j; l1++)
                {
                    int j2 = j1 - par3;
                    int k2 = l1 - par5;

                    if (j2 * j2 + k2 * k2 <= j * j + 1)
                    {
                        par1World.setBlockWithNotify(j1, l, l1, Block.obsidian.blockID);
                    }
                }
            }
        }

        EntityEnderCrystal entityendercrystal = new EntityEnderCrystal(par1World);
        entityendercrystal.setLocationAndAngles((float)par3 + 0.5F, par4 + i, (float)par5 + 0.5F, par2Random.nextFloat() * 360F, 0.0F);
        par1World.spawnEntityInWorld(entityendercrystal);
        par1World.setBlockWithNotify(par3, par4 + i, par5, Block.bedrock.blockID);
        return true;
    }
}
