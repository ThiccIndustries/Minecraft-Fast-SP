package net.minecraft.src;

public class BlockFence extends Block
{
    public BlockFence(int par1, int par2)
    {
        super(par1, par2, Material.wood);
    }

    public BlockFence(int par1, int par2, Material par3Material)
    {
        super(par1, par2, par3Material);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return super.canPlaceBlockAt(par1World, par2, par3, par4);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        boolean flag = canConnectFenceTo(par1World, par2, par3, par4 - 1);
        boolean flag1 = canConnectFenceTo(par1World, par2, par3, par4 + 1);
        boolean flag2 = canConnectFenceTo(par1World, par2 - 1, par3, par4);
        boolean flag3 = canConnectFenceTo(par1World, par2 + 1, par3, par4);
        float f = 0.375F;
        float f1 = 0.625F;
        float f2 = 0.375F;
        float f3 = 0.625F;

        if (flag)
        {
            f2 = 0.0F;
        }

        if (flag1)
        {
            f3 = 1.0F;
        }

        if (flag2)
        {
            f = 0.0F;
        }

        if (flag3)
        {
            f1 = 1.0F;
        }

        return AxisAlignedBB.getBoundingBoxFromPool((float)par2 + f, par3, (float)par4 + f2, (float)par2 + f1, (float)par3 + 1.5F, (float)par4 + f3);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        boolean flag = canConnectFenceTo(par1IBlockAccess, par2, par3, par4 - 1);
        boolean flag1 = canConnectFenceTo(par1IBlockAccess, par2, par3, par4 + 1);
        boolean flag2 = canConnectFenceTo(par1IBlockAccess, par2 - 1, par3, par4);
        boolean flag3 = canConnectFenceTo(par1IBlockAccess, par2 + 1, par3, par4);
        float f = 0.375F;
        float f1 = 0.625F;
        float f2 = 0.375F;
        float f3 = 0.625F;

        if (flag)
        {
            f2 = 0.0F;
        }

        if (flag1)
        {
            f3 = 1.0F;
        }

        if (flag2)
        {
            f = 0.0F;
        }

        if (flag3)
        {
            f1 = 1.0F;
        }

        setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
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

    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int i)
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 11;
    }

    /**
     * Returns true if the specified block can be connected by a fence
     */
    public boolean canConnectFenceTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int i = par1IBlockAccess.getBlockId(par2, par3, par4);

        if (i == blockID || i == Block.fenceGate.blockID)
        {
            return true;
        }

        Block block = Block.blocksList[i];

        if (block != null && block.blockMaterial.isOpaque() && block.renderAsNormalBlock())
        {
            return block.blockMaterial != Material.pumpkin;
        }
        else
        {
            return false;
        }
    }
}
