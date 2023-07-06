package net.minecraft.src;

import java.util.Random;

public class WorldGenClay extends WorldGenerator
{
    /** The block ID for clay. */
    private int clayBlockId;

    /** The number of blocks to generate. */
    private int numberOfBlocks;

    public WorldGenClay(int par1)
    {
        clayBlockId = Block.blockClay.blockID;
        numberOfBlocks = par1;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        if (par1World.getBlockMaterial(par3, par4, par5) != Material.water)
        {
            return false;
        }

        int i = par2Random.nextInt(numberOfBlocks - 2) + 2;
        int j = 1;

        for (int k = par3 - i; k <= par3 + i; k++)
        {
            for (int l = par5 - i; l <= par5 + i; l++)
            {
                int i1 = k - par3;
                int j1 = l - par5;

                if (i1 * i1 + j1 * j1 > i * i)
                {
                    continue;
                }

                for (int k1 = par4 - j; k1 <= par4 + j; k1++)
                {
                    int l1 = par1World.getBlockId(k, k1, l);

                    if (l1 == Block.dirt.blockID || l1 == Block.blockClay.blockID)
                    {
                        par1World.setBlock(k, k1, l, clayBlockId);
                    }
                }
            }
        }

        return true;
    }
}
