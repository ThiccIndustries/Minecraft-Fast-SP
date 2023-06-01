package net.minecraft.src;

import java.util.Random;

public class WorldGenGlowStone1 extends WorldGenerator
{
    public WorldGenGlowStone1()
    {
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        if (!par1World.isAirBlock(par3, par4, par5))
        {
            return false;
        }

        if (par1World.getBlockId(par3, par4 + 1, par5) != Block.netherrack.blockID)
        {
            return false;
        }

        par1World.setBlockWithNotify(par3, par4, par5, Block.glowStone.blockID);

        for (int i = 0; i < 1500; i++)
        {
            int j = (par3 + par2Random.nextInt(8)) - par2Random.nextInt(8);
            int k = par4 - par2Random.nextInt(12);
            int l = (par5 + par2Random.nextInt(8)) - par2Random.nextInt(8);

            if (par1World.getBlockId(j, k, l) != 0)
            {
                continue;
            }

            int i1 = 0;

            for (int j1 = 0; j1 < 6; j1++)
            {
                int k1 = 0;

                if (j1 == 0)
                {
                    k1 = par1World.getBlockId(j - 1, k, l);
                }

                if (j1 == 1)
                {
                    k1 = par1World.getBlockId(j + 1, k, l);
                }

                if (j1 == 2)
                {
                    k1 = par1World.getBlockId(j, k - 1, l);
                }

                if (j1 == 3)
                {
                    k1 = par1World.getBlockId(j, k + 1, l);
                }

                if (j1 == 4)
                {
                    k1 = par1World.getBlockId(j, k, l - 1);
                }

                if (j1 == 5)
                {
                    k1 = par1World.getBlockId(j, k, l + 1);
                }

                if (k1 == Block.glowStone.blockID)
                {
                    i1++;
                }
            }

            if (i1 == 1)
            {
                par1World.setBlockWithNotify(j, k, l, Block.glowStone.blockID);
            }
        }

        return true;
    }
}
