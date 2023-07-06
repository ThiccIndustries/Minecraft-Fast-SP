package net.minecraft.src;

import java.util.Random;

public class BlockDoor extends Block
{
    protected BlockDoor(int par1, Material par2Material)
    {
        super(par1, par2Material);
        blockIndexInTexture = 97;

        if (par2Material == Material.iron)
        {
            blockIndexInTexture++;
        }

        float f = 0.5F;
        float f1 = 1.0F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (par5 == 0 || par5 == 1)
        {
            return blockIndexInTexture;
        }

        int i = getFullMetadata(par1IBlockAccess, par2, par3, par4);
        int j = blockIndexInTexture;

        if ((i & 8) != 0)
        {
            j -= 16;
        }

        int k = i & 3;
        boolean flag = (i & 4) != 0;

        if (!flag)
        {
            if (k == 0 && par5 == 5)
            {
                j = -j;
            }
            else if (k == 1 && par5 == 3)
            {
                j = -j;
            }
            else if (k == 2 && par5 == 4)
            {
                j = -j;
            }
            else if (k == 3 && par5 == 2)
            {
                j = -j;
            }

            if ((i & 0x10) != 0)
            {
                j = -j;
            }
        }
        else if (k == 0 && par5 == 2)
        {
            j = -j;
        }
        else if (k == 1 && par5 == 5)
        {
            j = -j;
        }
        else if (k == 2 && par5 == 3)
        {
            j = -j;
        }
        else if (k == 3 && par5 == 4)
        {
            j = -j;
        }

        return j;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int i = getFullMetadata(par1IBlockAccess, par2, par3, par4);
        return (i & 4) != 0;
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
        return 7;
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
        setDoorRotation(getFullMetadata(par1IBlockAccess, par2, par3, par4));
    }

    /**
     * Returns 0, 1, 2 or 3 depending on where the hinge is.
     */
    public int getDoorOrientation(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return getFullMetadata(par1IBlockAccess, par2, par3, par4) & 3;
    }

    public boolean func_48213_h(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return (getFullMetadata(par1IBlockAccess, par2, par3, par4) & 4) != 0;
    }

    private void setDoorRotation(int par1)
    {
        float f = 0.1875F;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        int i = par1 & 3;
        boolean flag = (par1 & 4) != 0;
        boolean flag1 = (par1 & 0x10) != 0;

        if (i == 0)
        {
            if (!flag)
            {
                setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            }
            else if (!flag1)
            {
                setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            }
            else
            {
                setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            }
        }
        else if (i == 1)
        {
            if (!flag)
            {
                setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            }
            else if (!flag1)
            {
                setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
            else
            {
                setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            }
        }
        else if (i == 2)
        {
            if (!flag)
            {
                setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
            else if (!flag1)
            {
                setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            }
            else
            {
                setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            }
        }
        else if (i == 3)
        {
            if (!flag)
            {
                setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            }
            else if (!flag1)
            {
                setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            }
            else
            {
                setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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

        int i = getFullMetadata(par1World, par2, par3, par4);
        int j = i & 7;
        j ^= 4;

        if ((i & 8) != 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3 - 1, par4, j);
            par1World.markBlocksDirty(par2, par3 - 1, par4, par2, par3, par4);
        }
        else
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, j);
            par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
        }

        par1World.playAuxSFXAtEntity(par5EntityPlayer, 1003, par2, par3, par4, 0);
        return true;
    }

    /**
     * A function to open a door.
     */
    public void onPoweredBlockChange(World par1World, int par2, int par3, int par4, boolean par5)
    {
        int i = getFullMetadata(par1World, par2, par3, par4);
        boolean flag = (i & 4) != 0;

        if (flag == par5)
        {
            return;
        }

        int j = i & 7;
        j ^= 4;

        if ((i & 8) != 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3 - 1, par4, j);
            par1World.markBlocksDirty(par2, par3 - 1, par4, par2, par3, par4);
        }
        else
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, j);
            par1World.markBlocksDirty(par2, par3, par4, par2, par3, par4);
        }

        par1World.playAuxSFXAtEntity(null, 1003, par2, par3, par4, 0);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);

        if ((i & 8) != 0)
        {
            if (par1World.getBlockId(par2, par3 - 1, par4) != blockID)
            {
                par1World.setBlockWithNotify(par2, par3, par4, 0);
            }

            if (par5 > 0 && par5 != blockID)
            {
                onNeighborBlockChange(par1World, par2, par3 - 1, par4, par5);
            }
        }
        else
        {
            boolean flag = false;

            if (par1World.getBlockId(par2, par3 + 1, par4) != blockID)
            {
                par1World.setBlockWithNotify(par2, par3, par4, 0);
                flag = true;
            }

            if (!par1World.isBlockNormalCube(par2, par3 - 1, par4))
            {
                par1World.setBlockWithNotify(par2, par3, par4, 0);
                flag = true;

                if (par1World.getBlockId(par2, par3 + 1, par4) == blockID)
                {
                    par1World.setBlockWithNotify(par2, par3 + 1, par4, 0);
                }
            }

            if (flag)
            {
                if (!par1World.isRemote)
                {
                    dropBlockAsItem(par1World, par2, par3, par4, i, 0);
                }
            }
            else
            {
                boolean flag1 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);

                if ((flag1 || par5 > 0 && Block.blocksList[par5].canProvidePower() || par5 == 0) && par5 != blockID)
                {
                    onPoweredBlockChange(par1World, par2, par3, par4, flag1);
                }
            }
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        if ((par1 & 8) != 0)
        {
            return 0;
        }

        if (blockMaterial == Material.iron)
        {
            return Item.doorSteel.shiftedIndex;
        }
        else
        {
            return Item.doorWood.shiftedIndex;
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
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        if (par3 >= 255)
        {
            return false;
        }
        else
        {
            return par1World.isBlockNormalCube(par2, par3 - 1, par4) && super.canPlaceBlockAt(par1World, par2, par3, par4) && super.canPlaceBlockAt(par1World, par2, par3 + 1, par4);
        }
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int getMobilityFlag()
    {
        return 1;
    }

    /**
     * Returns the full metadata value created by combining the metadata of both blocks the door takes up.
     */
    public int getFullMetadata(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        boolean flag = (i & 8) != 0;
        int j;
        int k;

        if (flag)
        {
            j = par1IBlockAccess.getBlockMetadata(par2, par3 - 1, par4);
            k = i;
        }
        else
        {
            j = i;
            k = par1IBlockAccess.getBlockMetadata(par2, par3 + 1, par4);
        }

        boolean flag1 = (k & 1) != 0;
        int l = j & 7 | (flag ? 8 : 0) | (flag1 ? 0x10 : 0);
        return l;
    }
}
