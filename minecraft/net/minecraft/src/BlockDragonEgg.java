package net.minecraft.src;

import java.util.Random;

public class BlockDragonEgg extends Block
{
    public BlockDragonEgg(int par1, int par2)
    {
        super(par1, par2, Material.dragonEgg);
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
        fallIfPossible(par1World, par2, par3, par4);
    }

    /**
     * Checks if the dragon egg can fall down, and if so, makes it fall.
     */
    private void fallIfPossible(World par1World, int par2, int par3, int par4)
    {
        int i = par2;
        int j = par3;
        int k = par4;

        if (BlockSand.canFallBelow(par1World, i, j - 1, k) && j >= 0)
        {
            byte byte0 = 32;

            if (BlockSand.fallInstantly || !par1World.checkChunksExist(par2 - byte0, par3 - byte0, par4 - byte0, par2 + byte0, par3 + byte0, par4 + byte0))
            {
                par1World.setBlockWithNotify(par2, par3, par4, 0);

                for (; BlockSand.canFallBelow(par1World, par2, par3 - 1, par4) && par3 > 0; par3--) { }

                if (par3 > 0)
                {
                    par1World.setBlockWithNotify(par2, par3, par4, blockID);
                }
            }
            else
            {
                EntityFallingSand entityfallingsand = new EntityFallingSand(par1World, (float)par2 + 0.5F, (float)par3 + 0.5F, (float)par4 + 0.5F, blockID);
                par1World.spawnEntityInWorld(entityfallingsand);
            }
        }
    }

    /**
     * Called upon block activation (left or right click on the block.). The three integers represent x,y,z of the
     * block.
     */
    public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        teleportNearby(par1World, par2, par3, par4);
        return true;
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        teleportNearby(par1World, par2, par3, par4);
    }

    /**
     * Teleports the dragon egg somewhere else in a 31x19x31 area centered on the egg.
     */
    private void teleportNearby(World par1World, int par2, int par3, int par4)
    {
        if (par1World.getBlockId(par2, par3, par4) != blockID)
        {
            return;
        }

        if (par1World.isRemote)
        {
            return;
        }

        for (int i = 0; i < 1000; i++)
        {
            int j = (par2 + par1World.rand.nextInt(16)) - par1World.rand.nextInt(16);
            int k = (par3 + par1World.rand.nextInt(8)) - par1World.rand.nextInt(8);
            int l = (par4 + par1World.rand.nextInt(16)) - par1World.rand.nextInt(16);

            if (par1World.getBlockId(j, k, l) == 0)
            {
                par1World.setBlockAndMetadataWithNotify(j, k, l, blockID, par1World.getBlockMetadata(par2, par3, par4));
                par1World.setBlockWithNotify(par2, par3, par4, 0);
                char c = '\200';

                for (int i1 = 0; i1 < c; i1++)
                {
                    double d = par1World.rand.nextDouble();
                    float f = (par1World.rand.nextFloat() - 0.5F) * 0.2F;
                    float f1 = (par1World.rand.nextFloat() - 0.5F) * 0.2F;
                    float f2 = (par1World.rand.nextFloat() - 0.5F) * 0.2F;
                    double d1 = (double)j + (double)(par2 - j) * d + (par1World.rand.nextDouble() - 0.5D) * 1.0D + 0.5D;
                    double d2 = ((double)k + (double)(par3 - k) * d + par1World.rand.nextDouble() * 1.0D) - 0.5D;
                    double d3 = (double)l + (double)(par4 - l) * d + (par1World.rand.nextDouble() - 0.5D) * 1.0D + 0.5D;
                    par1World.spawnParticle("portal", d1, d2, d3, f, f1, f2);
                }

                return;
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
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return super.canPlaceBlockAt(par1World, par2, par3, par4);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 27;
    }
}
