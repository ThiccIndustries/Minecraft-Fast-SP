package net.minecraft.src;

import java.util.Random;

public class BlockPistonMoving extends BlockContainer
{
    public BlockPistonMoving(int par1)
    {
        super(par1, Material.piston);
        setHardness(-1F);
    }

    /**
     * Returns the TileEntity used by this block.
     */
    public TileEntity getBlockEntity()
    {
        return null;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int i, int j, int k)
    {
    }

    /**
     * Called whenever the block is removed.
     */
    public void onBlockRemoval(World par1World, int par2, int par3, int par4)
    {
        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentity != null && (tileentity instanceof TileEntityPiston))
        {
            ((TileEntityPiston)tileentity).clearPistonTileEntity();
        }
        else
        {
            super.onBlockRemoval(par1World, par2, par3, par4);
        }
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
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return -1;
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
     * Called upon block activation (left or right click on the block.). The three integers represent x,y,z of the
     * block.
     */
    public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        if (!par1World.isRemote && par1World.getBlockTileEntity(par2, par3, par4) == null)
        {
            par1World.setBlockWithNotify(par2, par3, par4, 0);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return 0;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        if (par1World.isRemote)
        {
            return;
        }

        TileEntityPiston tileentitypiston = getTileEntityAtLocation(par1World, par2, par3, par4);

        if (tileentitypiston == null)
        {
            return;
        }
        else
        {
            Block.blocksList[tileentitypiston.getStoredBlockID()].dropBlockAsItem(par1World, par2, par3, par4, tileentitypiston.getBlockMetadata(), 0);
            return;
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isRemote)
        {
            if (par1World.getBlockTileEntity(par2, par3, par4) != null);
        }
    }

    /**
     * gets a new TileEntityPiston created with the arguments provided.
     */
    public static TileEntity getTileEntity(int par0, int par1, int par2, boolean par3, boolean par4)
    {
        return new TileEntityPiston(par0, par1, par2, par3, par4);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        TileEntityPiston tileentitypiston = getTileEntityAtLocation(par1World, par2, par3, par4);

        if (tileentitypiston == null)
        {
            return null;
        }

        float f = tileentitypiston.getProgress(0.0F);

        if (tileentitypiston.isExtending())
        {
            f = 1.0F - f;
        }

        return getAxisAlignedBB(par1World, par2, par3, par4, tileentitypiston.getStoredBlockID(), f, tileentitypiston.getPistonOrientation());
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        TileEntityPiston tileentitypiston = getTileEntityAtLocation(par1IBlockAccess, par2, par3, par4);

        if (tileentitypiston != null)
        {
            Block block = Block.blocksList[tileentitypiston.getStoredBlockID()];

            if (block == null || block == this)
            {
                return;
            }

            block.setBlockBoundsBasedOnState(par1IBlockAccess, par2, par3, par4);
            float f = tileentitypiston.getProgress(0.0F);

            if (tileentitypiston.isExtending())
            {
                f = 1.0F - f;
            }

            int i = tileentitypiston.getPistonOrientation();
            minX = block.minX - (double)((float)Facing.offsetsXForSide[i] * f);
            minY = block.minY - (double)((float)Facing.offsetsYForSide[i] * f);
            minZ = block.minZ - (double)((float)Facing.offsetsZForSide[i] * f);
            maxX = block.maxX - (double)((float)Facing.offsetsXForSide[i] * f);
            maxY = block.maxY - (double)((float)Facing.offsetsYForSide[i] * f);
            maxZ = block.maxZ - (double)((float)Facing.offsetsZForSide[i] * f);
        }
    }

    public AxisAlignedBB getAxisAlignedBB(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        if (par5 == 0 || par5 == blockID)
        {
            return null;
        }

        AxisAlignedBB axisalignedbb = Block.blocksList[par5].getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);

        if (axisalignedbb == null)
        {
            return null;
        }

        if (Facing.offsetsXForSide[par7] < 0)
        {
            axisalignedbb.minX -= (float)Facing.offsetsXForSide[par7] * par6;
        }
        else
        {
            axisalignedbb.maxX -= (float)Facing.offsetsXForSide[par7] * par6;
        }

        if (Facing.offsetsYForSide[par7] < 0)
        {
            axisalignedbb.minY -= (float)Facing.offsetsYForSide[par7] * par6;
        }
        else
        {
            axisalignedbb.maxY -= (float)Facing.offsetsYForSide[par7] * par6;
        }

        if (Facing.offsetsZForSide[par7] < 0)
        {
            axisalignedbb.minZ -= (float)Facing.offsetsZForSide[par7] * par6;
        }
        else
        {
            axisalignedbb.maxZ -= (float)Facing.offsetsZForSide[par7] * par6;
        }

        return axisalignedbb;
    }

    /**
     * gets the piston tile entity at the specified location
     */
    private TileEntityPiston getTileEntityAtLocation(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        TileEntity tileentity = par1IBlockAccess.getBlockTileEntity(par2, par3, par4);

        if (tileentity != null && (tileentity instanceof TileEntityPiston))
        {
            return (TileEntityPiston)tileentity;
        }
        else
        {
            return null;
        }
    }
}
