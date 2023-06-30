package net.minecraft.src;

public class BlockTrapDoor extends Block
{
    protected BlockTrapDoor(int par1, Material par2Material)
    {
        super(par1, par2Material);
        blockIndexInTexture = 84;

        if (par2Material == Material.iron)
        {
            blockIndexInTexture++;
        }

        float f = 0.5F;
        float f1 = 1.0F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
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

    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return !isTrapdoorOpen(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 0;
    }

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        setBlockBoundsForBlockRender(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        float f = 0.1875F;
        setBlockBounds(0.0F, 0.5F - f / 2.0F, 0.0F, 1.0F, 0.5F + f / 2.0F, 1.0F);
    }

    public void setBlockBoundsForBlockRender(int par1)
    {
        float f = 0.1875F;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);

        if (isTrapdoorOpen(par1))
        {
            if ((par1 & 3) == 0)
            {
                setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            }

            if ((par1 & 3) == 1)
            {
                setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            }

            if ((par1 & 3) == 2)
            {
                setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }

            if ((par1 & 3) == 3)
            {
                setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            }
        }
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        blockActivated(par1World, par2, par3, par4, par5EntityPlayer);
    }

    /**
     * Called upon block activation (left or right click on the block.). The three integers represent x,y,z of the
     * block.
     */
    public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        if (blockMaterial == Material.iron)
        {
            return true;
        }
        else
        {
            int i = par1World.getBlockMetadata(par2, par3, par4);
            par1World.setBlockMetadataWithNotify(par2, par3, par4, i ^ 4);
            par1World.playAuxSFXAtEntity(par5EntityPlayer, 1003, par2, par3, par4, 0);
            return true;
        }
    }

    public void onPoweredBlockChange(World par1World, int par2, int par3, int par4, boolean par5)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);
        boolean flag = (i & 4) > 0;

        if (flag == par5)
        {
            return;
        }
        else
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, i ^ 4);
            par1World.playAuxSFXAtEntity(null, 1003, par2, par3, par4, 0);
            return;
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par1World.isRemote)
        {
            return;
        }

        int i = par1World.getBlockMetadata(par2, par3, par4);
        int j = par2;
        int k = par4;

        if ((i & 3) == 0)
        {
            k++;
        }

        if ((i & 3) == 1)
        {
            k--;
        }

        if ((i & 3) == 2)
        {
            j++;
        }

        if ((i & 3) == 3)
        {
            j--;
        }

        if (!isValidSupportBlock(par1World.getBlockId(j, par3, k)))
        {
            par1World.setBlockWithNotify(par2, par3, par4, 0);
            dropBlockAsItem(par1World, par2, par3, par4, i, 0);
        }

        boolean flag = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);

        if (flag || par5 > 0 && Block.blocksList[par5].canProvidePower() || par5 == 0)
        {
            onPoweredBlockChange(par1World, par2, par3, par4, flag);
        }
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3D par5Vec3D, Vec3D par6Vec3D)
    {
        setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3D, par6Vec3D);
    }

    /**
     * Called when a block is placed using an item. Used often for taking the facing and figuring out how to position
     * the item. Args: x, y, z, facing
     */
    public void onBlockPlaced(World par1World, int par2, int par3, int par4, int par5)
    {
        byte byte0 = 0;

        if (par5 == 2)
        {
            byte0 = 0;
        }

        if (par5 == 3)
        {
            byte0 = 1;
        }

        if (par5 == 4)
        {
            byte0 = 2;
        }

        if (par5 == 5)
        {
            byte0 = 3;
        }

        par1World.setBlockMetadataWithNotify(par2, par3, par4, byte0);
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par5 == 0)
        {
            return false;
        }

        if (par5 == 1)
        {
            return false;
        }

        if (par5 == 2)
        {
            par4++;
        }

        if (par5 == 3)
        {
            par4--;
        }

        if (par5 == 4)
        {
            par2++;
        }

        if (par5 == 5)
        {
            par2--;
        }

        return isValidSupportBlock(par1World.getBlockId(par2, par3, par4));
    }

    public static boolean isTrapdoorOpen(int par0)
    {
        return (par0 & 4) != 0;
    }

    /**
     * Checks if the block ID is a valid support block for the trap door to connect with. If it is not the trapdoor is
     * dropped into the world.
     */
    private static boolean isValidSupportBlock(int par0)
    {
        if (par0 <= 0)
        {
            return false;
        }
        else
        {
            Block block = Block.blocksList[par0];
            return block != null && block.blockMaterial.isOpaque() && block.renderAsNormalBlock() || block == Block.glowStone;
        }
    }
}
