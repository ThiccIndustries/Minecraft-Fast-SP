package net.minecraft.src;

import java.util.Random;

public class WorldGenHellLava extends WorldGenerator
{
    /** Stores ID for WorldGenHellLava */
    private int hellLavaID;

    public WorldGenHellLava(int par1)
    {
        hellLavaID = par1;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        if (par1World.getBlockId(par3, par4 + 1, par5) != Block.netherrack.blockID)
        {
            return false;
        }

        if (par1World.getBlockId(par3, par4, par5) != 0 && par1World.getBlockId(par3, par4, par5) != Block.netherrack.blockID)
        {
            return false;
        }

        int i = 0;

        if (par1World.getBlockId(par3 - 1, par4, par5) == Block.netherrack.blockID)
        {
            i++;
        }

        if (par1World.getBlockId(par3 + 1, par4, par5) == Block.netherrack.blockID)
        {
            i++;
        }

        if (par1World.getBlockId(par3, par4, par5 - 1) == Block.netherrack.blockID)
        {
            i++;
        }

        if (par1World.getBlockId(par3, par4, par5 + 1) == Block.netherrack.blockID)
        {
            i++;
        }

        if (par1World.getBlockId(par3, par4 - 1, par5) == Block.netherrack.blockID)
        {
            i++;
        }

        int j = 0;

        if (par1World.isAirBlock(par3 - 1, par4, par5))
        {
            j++;
        }

        if (par1World.isAirBlock(par3 + 1, par4, par5))
        {
            j++;
        }

        if (par1World.isAirBlock(par3, par4, par5 - 1))
        {
            j++;
        }

        if (par1World.isAirBlock(par3, par4, par5 + 1))
        {
            j++;
        }

        if (par1World.isAirBlock(par3, par4 - 1, par5))
        {
            j++;
        }

        if (i == 4 && j == 1)
        {
            par1World.setBlockWithNotify(par3, par4, par5, hellLavaID);
            par1World.scheduledUpdatesAreImmediate = true;
            Block.blocksList[hellLavaID].updateTick(par1World, par3, par4, par5, par2Random);
            par1World.scheduledUpdatesAreImmediate = false;
        }

        return true;
    }
}
