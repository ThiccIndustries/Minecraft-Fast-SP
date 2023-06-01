package net.minecraft.src;

import java.util.Random;

public class BlockPortal extends BlockBreakable
{
    public BlockPortal(int par1, int par2)
    {
        super(par1, par2, Material.portal, false);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int i)
    {
        return null;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        if (par1IBlockAccess.getBlockId(par2 - 1, par3, par4) == blockID || par1IBlockAccess.getBlockId(par2 + 1, par3, par4) == blockID)
        {
            float f = 0.5F;
            float f2 = 0.125F;
            setBlockBounds(0.5F - f, 0.0F, 0.5F - f2, 0.5F + f, 1.0F, 0.5F + f2);
        }
        else
        {
            float f1 = 0.125F;
            float f3 = 0.5F;
            setBlockBounds(0.5F - f1, 0.0F, 0.5F - f3, 0.5F + f1, 1.0F, 0.5F + f3);
        }
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
     * Checks to see if this location is valid to create a portal and will return True if it does. Args: world, x, y, z
     */
    public boolean tryToCreatePortal(World par1World, int par2, int par3, int par4)
    {
        int i = 0;
        int j = 0;

        if (par1World.getBlockId(par2 - 1, par3, par4) == Block.obsidian.blockID || par1World.getBlockId(par2 + 1, par3, par4) == Block.obsidian.blockID)
        {
            i = 1;
        }

        if (par1World.getBlockId(par2, par3, par4 - 1) == Block.obsidian.blockID || par1World.getBlockId(par2, par3, par4 + 1) == Block.obsidian.blockID)
        {
            j = 1;
        }

        if (i == j)
        {
            return false;
        }

        if (par1World.getBlockId(par2 - i, par3, par4 - j) == 0)
        {
            par2 -= i;
            par4 -= j;
        }

        for (int k = -1; k <= 2; k++)
        {
            for (int i1 = -1; i1 <= 3; i1++)
            {
                boolean flag = k == -1 || k == 2 || i1 == -1 || i1 == 3;

                if ((k == -1 || k == 2) && (i1 == -1 || i1 == 3))
                {
                    continue;
                }

                int k1 = par1World.getBlockId(par2 + i * k, par3 + i1, par4 + j * k);

                if (flag)
                {
                    if (k1 != Block.obsidian.blockID)
                    {
                        return false;
                    }

                    continue;
                }

                if (k1 != 0 && k1 != Block.fire.blockID)
                {
                    return false;
                }
            }
        }

        par1World.editingBlocks = true;

        for (int l = 0; l < 2; l++)
        {
            for (int j1 = 0; j1 < 3; j1++)
            {
                par1World.setBlockWithNotify(par2 + i * l, par3 + j1, par4 + j * l, Block.portal.blockID);
            }
        }

        par1World.editingBlocks = false;
        return true;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        int i = 0;
        int j = 1;

        if (par1World.getBlockId(par2 - 1, par3, par4) == blockID || par1World.getBlockId(par2 + 1, par3, par4) == blockID)
        {
            i = 1;
            j = 0;
        }

        int k;

        for (k = par3; par1World.getBlockId(par2, k - 1, par4) == blockID; k--) { }

        if (par1World.getBlockId(par2, k - 1, par4) != Block.obsidian.blockID)
        {
            par1World.setBlockWithNotify(par2, par3, par4, 0);
            return;
        }

        int l;

        for (l = 1; l < 4 && par1World.getBlockId(par2, k + l, par4) == blockID; l++) { }

        if (l != 3 || par1World.getBlockId(par2, k + l, par4) != Block.obsidian.blockID)
        {
            par1World.setBlockWithNotify(par2, par3, par4, 0);
            return;
        }

        boolean flag = par1World.getBlockId(par2 - 1, par3, par4) == blockID || par1World.getBlockId(par2 + 1, par3, par4) == blockID;
        boolean flag1 = par1World.getBlockId(par2, par3, par4 - 1) == blockID || par1World.getBlockId(par2, par3, par4 + 1) == blockID;

        if (flag && flag1)
        {
            par1World.setBlockWithNotify(par2, par3, par4, 0);
            return;
        }

        if ((par1World.getBlockId(par2 + i, par3, par4 + j) != Block.obsidian.blockID || par1World.getBlockId(par2 - i, par3, par4 - j) != blockID) && (par1World.getBlockId(par2 - i, par3, par4 - j) != Block.obsidian.blockID || par1World.getBlockId(par2 + i, par3, par4 + j) != blockID))
        {
            par1World.setBlockWithNotify(par2, par3, par4, 0);
            return;
        }
        else
        {
            return;
        }
    }

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (par1IBlockAccess.getBlockId(par2, par3, par4) == blockID)
        {
            return false;
        }

        boolean flag = par1IBlockAccess.getBlockId(par2 - 1, par3, par4) == blockID && par1IBlockAccess.getBlockId(par2 - 2, par3, par4) != blockID;
        boolean flag1 = par1IBlockAccess.getBlockId(par2 + 1, par3, par4) == blockID && par1IBlockAccess.getBlockId(par2 + 2, par3, par4) != blockID;
        boolean flag2 = par1IBlockAccess.getBlockId(par2, par3, par4 - 1) == blockID && par1IBlockAccess.getBlockId(par2, par3, par4 - 2) != blockID;
        boolean flag3 = par1IBlockAccess.getBlockId(par2, par3, par4 + 1) == blockID && par1IBlockAccess.getBlockId(par2, par3, par4 + 2) != blockID;
        boolean flag4 = flag || flag1;
        boolean flag5 = flag2 || flag3;

        if (flag4 && par5 == 4)
        {
            return true;
        }

        if (flag4 && par5 == 5)
        {
            return true;
        }

        if (flag5 && par5 == 2)
        {
            return true;
        }

        return flag5 && par5 == 3;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    public int getRenderBlockPass()
    {
        return 1;
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (par5Entity.ridingEntity == null && par5Entity.riddenByEntity == null)
        {
            par5Entity.setInPortal();
        }
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par5Random.nextInt(100) == 0)
        {
            par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, "portal.portal", 0.5F, par5Random.nextFloat() * 0.4F + 0.8F);
        }

        for (int i = 0; i < 4; i++)
        {
            double d = (float)par2 + par5Random.nextFloat();
            double d1 = (float)par3 + par5Random.nextFloat();
            double d2 = (float)par4 + par5Random.nextFloat();
            double d3 = 0.0D;
            double d4 = 0.0D;
            double d5 = 0.0D;
            int j = par5Random.nextInt(2) * 2 - 1;
            d3 = ((double)par5Random.nextFloat() - 0.5D) * 0.5D;
            d4 = ((double)par5Random.nextFloat() - 0.5D) * 0.5D;
            d5 = ((double)par5Random.nextFloat() - 0.5D) * 0.5D;

            if (par1World.getBlockId(par2 - 1, par3, par4) == blockID || par1World.getBlockId(par2 + 1, par3, par4) == blockID)
            {
                d2 = (double)par4 + 0.5D + 0.25D * (double)j;
                d5 = par5Random.nextFloat() * 2.0F * (float)j;
            }
            else
            {
                d = (double)par2 + 0.5D + 0.25D * (double)j;
                d3 = par5Random.nextFloat() * 2.0F * (float)j;
            }

            par1World.spawnParticle("portal", d, d1, d2, d3, d4, d5);
        }
    }
}
