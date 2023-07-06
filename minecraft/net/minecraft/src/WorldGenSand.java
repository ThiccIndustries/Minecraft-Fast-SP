package net.minecraft.src;

import java.util.Random;

public class WorldGenSand extends WorldGenerator
{
    /** Stores ID for WorldGenSand */
    private int sandID;

    /** The maximum radius used when generating a patch of blocks. */
    private int radius;

    public WorldGenSand(int par1, int par2)
    {
        sandID = par2;
        radius = par1;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        if (par1World.getBlockMaterial(par3, par4, par5) != Material.water)
        {
            return false;
        }

        int i = par2Random.nextInt(radius - 2) + 2;
        byte byte0 = 2;

        for (int j = par3 - i; j <= par3 + i; j++)
        {
            for (int k = par5 - i; k <= par5 + i; k++)
            {
                int l = j - par3;
                int i1 = k - par5;

                if (l * l + i1 * i1 > i * i)
                {
                    continue;
                }

                for (int j1 = par4 - byte0; j1 <= par4 + byte0; j1++)
                {
                    int k1 = par1World.getBlockId(j, j1, k);

                    if (k1 == Block.dirt.blockID || k1 == Block.grass.blockID)
                    {
                        par1World.setBlock(j, j1, k, sandID);
                    }
                }
            }
        }

        return true;
    }
}
