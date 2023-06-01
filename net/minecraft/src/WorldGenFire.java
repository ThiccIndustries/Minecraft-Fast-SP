package net.minecraft.src;

import java.util.Random;

public class WorldGenFire extends WorldGenerator
{
    public WorldGenFire()
    {
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int i = 0; i < 64; i++)
        {
            int j = (par3 + par2Random.nextInt(8)) - par2Random.nextInt(8);
            int k = (par4 + par2Random.nextInt(4)) - par2Random.nextInt(4);
            int l = (par5 + par2Random.nextInt(8)) - par2Random.nextInt(8);

            if (par1World.isAirBlock(j, k, l) && par1World.getBlockId(j, k - 1, l) == Block.netherrack.blockID)
            {
                par1World.setBlockWithNotify(j, k, l, Block.fire.blockID);
            }
        }

        return true;
    }
}
