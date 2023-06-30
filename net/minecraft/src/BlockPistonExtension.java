package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class BlockPistonExtension extends Block
{
    /** The texture for the 'head' of the piston. Sticky or normal. */
    private int headTexture;

    public BlockPistonExtension(int par1, int par2)
    {
        super(par1, par2, Material.piston);
        headTexture = -1;
        setStepSound(soundStoneFootstep);
        setHardness(0.5F);
    }

    public void setHeadTexture(int par1)
    {
        headTexture = par1;
    }

    public void clearHeadTexture()
    {
        headTexture = -1;
    }

    /**
     * Called whenever the block is removed.
     */
    public void onBlockRemoval(World par1World, int par2, int par3, int par4)
    {
        super.onBlockRemoval(par1World, par2, par3, par4);
        int i = par1World.getBlockMetadata(par2, par3, par4);
        int k = Facing.faceToSide[getDirectionMeta(i)];
        par2 += Facing.offsetsXForSide[k];
        par3 += Facing.offsetsYForSide[k];
        par4 += Facing.offsetsZForSide[k];
        int l = par1World.getBlockId(par2, par3, par4);

        if (l == Block.pistonBase.blockID || l == Block.pistonStickyBase.blockID)
        {
            int j = par1World.getBlockMetadata(par2, par3, par4);

            if (BlockPistonBase.isExtended(j))
            {
                Block.blocksList[l].dropBlockAsItem(par1World, par2, par3, par4, j, 0);
                par1World.setBlockWithNotify(par2, par3, par4, 0);
            }
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        int i = getDirectionMeta(par2);

        if (par1 == i)
        {
            if (headTexture >= 0)
            {
                return headTexture;
            }

            if ((par2 & 8) != 0)
            {
                return blockIndexInTexture - 1;
            }
            else
            {
                return blockIndexInTexture;
            }
        }

        return par1 != Facing.faceToSide[i] ? 108 : 107;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 17;
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
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int i)
    {
        return false;
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int i, int j)
    {
        return false;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    /**
     * Adds to the supplied array any colliding bounding boxes with the passed in bounding box. Args: world, x, y, z,
     * axisAlignedBB, arrayList
     */
    public void getCollidingBoundingBoxes(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, ArrayList par6ArrayList)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);

        switch (getDirectionMeta(i))
        {
            case 0:
                setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
                super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
                setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
                super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
                break;

            case 1:
                setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
                super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
                setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
                super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
                break;

            case 2:
                setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
                super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
                setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
                super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
                break;

            case 3:
                setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
                super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
                setBlockBounds(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
                super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
                break;

            case 4:
                setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
                super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
                setBlockBounds(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
                super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
                break;

            case 5:
                setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
                setBlockBounds(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
                super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
                break;
        }

        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

        switch (getDirectionMeta(i))
        {
            case 0:
                setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
                break;

            case 1:
                setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;

            case 2:
                setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
                break;

            case 3:
                setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
                break;

            case 4:
                setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
                break;

            case 5:
                setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        int i = getDirectionMeta(par1World.getBlockMetadata(par2, par3, par4));
        int j = par1World.getBlockId(par2 - Facing.offsetsXForSide[i], par3 - Facing.offsetsYForSide[i], par4 - Facing.offsetsZForSide[i]);

        if (j != Block.pistonBase.blockID && j != Block.pistonStickyBase.blockID)
        {
            par1World.setBlockWithNotify(par2, par3, par4, 0);
        }
        else
        {
            Block.blocksList[j].onNeighborBlockChange(par1World, par2 - Facing.offsetsXForSide[i], par3 - Facing.offsetsYForSide[i], par4 - Facing.offsetsZForSide[i], par5);
        }
    }

    public static int getDirectionMeta(int par0)
    {
        return par0 & 7;
    }
}
