package net.minecraft.src;

import java.util.Random;

public class WorldGenPumpkin extends WorldGenerator
{
    public WorldGenPumpkin()
    {
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int i = 0; i < 64; i++)
        {
            int j = (par3 + par2Random.nextInt(8)) - par2Random.nextInt(8);
            int k = (par4 + par2Random.nextInt(4)) - par2Random.nextInt(4);
            int l = (par5 + par2Random.nextInt(8)) - par2Random.nextInt(8);

            if (par1World.isAirBlock(j, k, l) && par1World.getBlockId(j, k - 1, l) == Block.grass.blockID && Block.pumpkin.canPlaceBlockAt(par1World, j, k, l))
            {
                par1World.setBlockAndMetadata(j, k, l, Block.pumpkin.blockID, par2Random.nextInt(4));
            }
        }

        return true;
    }
}
