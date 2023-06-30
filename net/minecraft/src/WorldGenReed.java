package net.minecraft.src;

import java.util.Random;

public class WorldGenReed extends WorldGenerator
{
    public WorldGenReed()
    {
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int i = 0; i < 20; i++)
        {
            int j = (par3 + par2Random.nextInt(4)) - par2Random.nextInt(4);
            int k = par4;
            int l = (par5 + par2Random.nextInt(4)) - par2Random.nextInt(4);

            if (!par1World.isAirBlock(j, k, l) || par1World.getBlockMaterial(j - 1, k - 1, l) != Material.water && par1World.getBlockMaterial(j + 1, k - 1, l) != Material.water && par1World.getBlockMaterial(j, k - 1, l - 1) != Material.water && par1World.getBlockMaterial(j, k - 1, l + 1) != Material.water)
            {
                continue;
            }

            int i1 = 2 + par2Random.nextInt(par2Random.nextInt(3) + 1);

            for (int j1 = 0; j1 < i1; j1++)
            {
                if (Block.reed.canBlockStay(par1World, j, k + j1, l))
                {
                    par1World.setBlock(j, k + j1, l, Block.reed.blockID);
                }
            }
        }

        return true;
    }
}
