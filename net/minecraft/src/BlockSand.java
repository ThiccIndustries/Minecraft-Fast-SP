package net.minecraft.src;

import java.util.Random;

public class BlockSand extends Block
{
    /** Do blocks fall instantly to where they stop or do they fall over time */
    public static boolean fallInstantly = false;

    public BlockSand(int par1, int par2)
    {
        super(par1, par2, Material.sand);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate());
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate());
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        tryToFall(par1World, par2, par3, par4);
    }

    /**
     * If there is space to fall below will start this block falling
     */
    private void tryToFall(World par1World, int par2, int par3, int par4)
    {
        int i = par2;
        int j = par3;
        int k = par4;

        if (canFallBelow(par1World, i, j - 1, k) && j >= 0)
        {
            byte byte0 = 32;

            if (fallInstantly || !par1World.checkChunksExist(par2 - byte0, par3 - byte0, par4 - byte0, par2 + byte0, par3 + byte0, par4 + byte0))
            {
                par1World.setBlockWithNotify(par2, par3, par4, 0);

                for (; canFallBelow(par1World, par2, par3 - 1, par4) && par3 > 0; par3--) { }

                if (par3 > 0)
                {
                    par1World.setBlockWithNotify(par2, par3, par4, blockID);
                }
            }
            else if (!par1World.isRemote)
            {
                EntityFallingSand entityfallingsand = new EntityFallingSand(par1World, (float)par2 + 0.5F, (float)par3 + 0.5F, (float)par4 + 0.5F, blockID);
                par1World.spawnEntityInWorld(entityfallingsand);
            }
        }
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 3;
    }

    /**
     * Checks to see if the sand can fall into the block below it
     */
    public static boolean canFallBelow(World par0World, int par1, int par2, int par3)
    {
        int i = par0World.getBlockId(par1, par2, par3);

        if (i == 0)
        {
            return true;
        }

        if (i == Block.fire.blockID)
        {
            return true;
        }

        Material material = Block.blocksList[i].blockMaterial;

        if (material == Material.water)
        {
            return true;
        }

        return material == Material.lava;
    }
}
