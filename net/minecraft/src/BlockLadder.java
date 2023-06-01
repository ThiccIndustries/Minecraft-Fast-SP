package net.minecraft.src;

import java.util.Random;

public class BlockLadder extends Block
{
    protected BlockLadder(int par1, int par2)
    {
        super(par1, par2, Material.circuits);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        float f = 0.125F;

        if (i == 2)
        {
            setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        }

        if (i == 3)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        }

        if (i == 4)
        {
            setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }

        if (i == 5)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        }

        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        float f = 0.125F;

        if (i == 2)
        {
            setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        }

        if (i == 3)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        }

        if (i == 4)
        {
            setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }

        if (i == 5)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        }

        return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
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
        return 8;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        if (par1World.isBlockNormalCube(par2 - 1, par3, par4))
        {
            return true;
        }

        if (par1World.isBlockNormalCube(par2 + 1, par3, par4))
        {
            return true;
        }

        if (par1World.isBlockNormalCube(par2, par3, par4 - 1))
        {
            return true;
        }

        return par1World.isBlockNormalCube(par2, par3, par4 + 1);
    }

    /**
     * Called when a block is placed using an item. Used often for taking the facing and figuring out how to position
     * the item. Args: x, y, z, facing
     */
    public void onBlockPlaced(World par1World, int par2, int par3, int par4, int par5)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);

        if ((i == 0 || par5 == 2) && par1World.isBlockNormalCube(par2, par3, par4 + 1))
        {
            i = 2;
        }

        if ((i == 0 || par5 == 3) && par1World.isBlockNormalCube(par2, par3, par4 - 1))
        {
            i = 3;
        }

        if ((i == 0 || par5 == 4) && par1World.isBlockNormalCube(par2 + 1, par3, par4))
        {
            i = 4;
        }

        if ((i == 0 || par5 == 5) && par1World.isBlockNormalCube(par2 - 1, par3, par4))
        {
            i = 5;
        }

        par1World.setBlockMetadataWithNotify(par2, par3, par4, i);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        boolean flag = false;

        if (i == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1))
        {
            flag = true;
        }

        if (i == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1))
        {
            flag = true;
        }

        if (i == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4))
        {
            flag = true;
        }

        if (i == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4))
        {
            flag = true;
        }

        if (!flag)
        {
            dropBlockAsItem(par1World, par2, par3, par4, i, 0);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
        }

        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }
}
