package net.minecraft.src;

import java.util.Random;

public class BlockRail extends Block
{
    /** Power related rails have this field at true. */
    private final boolean isPowered;

    /**
     * Returns true if the block at the coordinates of world passed is a valid rail block (current is rail, powered or
     * detector).
     */
    public static final boolean isRailBlockAt(World par0World, int par1, int par2, int par3)
    {
        int i = par0World.getBlockId(par1, par2, par3);
        return i == Block.rail.blockID || i == Block.railPowered.blockID || i == Block.railDetector.blockID;
    }

    /**
     * Return true if the parameter is a blockID for a valid rail block (current is rail, powered or detector).
     */
    public static final boolean isRailBlock(int par0)
    {
        return par0 == Block.rail.blockID || par0 == Block.railPowered.blockID || par0 == Block.railDetector.blockID;
    }

    protected BlockRail(int par1, int par2, boolean par3)
    {
        super(par1, par2, Material.circuits);
        isPowered = par3;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    /**
     * Returns true if the block is power related rail.
     */
    public boolean isPowered()
    {
        return isPowered;
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
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
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
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

        if (i >= 2 && i <= 5)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
        }
        else
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        if (isPowered)
        {
            if (blockID == Block.railPowered.blockID && (par2 & 8) == 0)
            {
                return blockIndexInTexture - 16;
            }
        }
        else if (par2 >= 6)
        {
            return blockIndexInTexture - 16;
        }

        return blockIndexInTexture;
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
        return 9;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return par1World.isBlockNormalCube(par2, par3 - 1, par4);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isRemote)
        {
            refreshTrackShape(par1World, par2, par3, par4, true);

            if (blockID == Block.railPowered.blockID)
            {
                onNeighborBlockChange(par1World, par2, par3, par4, blockID);
            }
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
        int j = i;

        if (isPowered)
        {
            j &= 7;
        }

        boolean flag = false;

        if (!par1World.isBlockNormalCube(par2, par3 - 1, par4))
        {
            flag = true;
        }

        if (j == 2 && !par1World.isBlockNormalCube(par2 + 1, par3, par4))
        {
            flag = true;
        }

        if (j == 3 && !par1World.isBlockNormalCube(par2 - 1, par3, par4))
        {
            flag = true;
        }

        if (j == 4 && !par1World.isBlockNormalCube(par2, par3, par4 - 1))
        {
            flag = true;
        }

        if (j == 5 && !par1World.isBlockNormalCube(par2, par3, par4 + 1))
        {
            flag = true;
        }

        if (flag)
        {
            dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockWithNotify(par2, par3, par4, 0);
        }
        else if (blockID == Block.railPowered.blockID)
        {
            boolean flag1 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);
            flag1 = flag1 || isNeighborRailPowered(par1World, par2, par3, par4, i, true, 0) || isNeighborRailPowered(par1World, par2, par3, par4, i, false, 0);
            boolean flag2 = false;

            if (flag1 && (i & 8) == 0)
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, j | 8);
                flag2 = true;
            }
            else if (!flag1 && (i & 8) != 0)
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, j);
                flag2 = true;
            }

            if (flag2)
            {
                par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);

                if (j == 2 || j == 3 || j == 4 || j == 5)
                {
                    par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, blockID);
                }
            }
        }
        else if (par5 > 0 && Block.blocksList[par5].canProvidePower() && !isPowered && RailLogic.getNAdjacentTracks(new RailLogic(this, par1World, par2, par3, par4)) == 3)
        {
            refreshTrackShape(par1World, par2, par3, par4, false);
        }
    }

    /**
     * Completely recalculates the track shape based on neighboring tracks
     */
    private void refreshTrackShape(World par1World, int par2, int par3, int par4, boolean par5)
    {
        if (par1World.isRemote)
        {
            return;
        }
        else
        {
            (new RailLogic(this, par1World, par2, par3, par4)).refreshTrackShape(par1World.isBlockIndirectlyGettingPowered(par2, par3, par4), par5);
            return;
        }
    }

    /**
     * Powered minecart rail is conductive like wire, so check for powered neighbors
     */
    private boolean isNeighborRailPowered(World par1World, int par2, int par3, int par4, int par5, boolean par6, int par7)
    {
        if (par7 >= 8)
        {
            return false;
        }

        int i = par5 & 7;
        boolean flag = true;

        switch (i)
        {
            case 0:
                if (par6)
                {
                    par4++;
                }
                else
                {
                    par4--;
                }

                break;

            case 1:
                if (par6)
                {
                    par2--;
                }
                else
                {
                    par2++;
                }

                break;

            case 2:
                if (par6)
                {
                    par2--;
                }
                else
                {
                    par2++;
                    par3++;
                    flag = false;
                }

                i = 1;
                break;

            case 3:
                if (par6)
                {
                    par2--;
                    par3++;
                    flag = false;
                }
                else
                {
                    par2++;
                }

                i = 1;
                break;

            case 4:
                if (par6)
                {
                    par4++;
                }
                else
                {
                    par4--;
                    par3++;
                    flag = false;
                }

                i = 0;
                break;

            case 5:
                if (par6)
                {
                    par4++;
                    par3++;
                    flag = false;
                }
                else
                {
                    par4--;
                }

                i = 0;
                break;
        }

        if (isRailPassingPower(par1World, par2, par3, par4, par6, par7, i))
        {
            return true;
        }

        return flag && isRailPassingPower(par1World, par2, par3 - 1, par4, par6, par7, i);
    }

    /**
     * Returns true if the specified rail is passing power to its neighbor
     */
    private boolean isRailPassingPower(World par1World, int par2, int par3, int par4, boolean par5, int par6, int par7)
    {
        int i = par1World.getBlockId(par2, par3, par4);

        if (i == Block.railPowered.blockID)
        {
            int j = par1World.getBlockMetadata(par2, par3, par4);
            int k = j & 7;

            if (par7 == 1 && (k == 0 || k == 4 || k == 5))
            {
                return false;
            }

            if (par7 == 0 && (k == 1 || k == 2 || k == 3))
            {
                return false;
            }

            if ((j & 8) != 0)
            {
                if (par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
                {
                    return true;
                }
                else
                {
                    return isNeighborRailPowered(par1World, par2, par3, par4, j, par5, par6 + 1);
                }
            }
        }

        return false;
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int getMobilityFlag()
    {
        return 0;
    }

    /**
     * Return true if the blocks passed is a power related rail.
     */
    static boolean isPoweredBlockRail(BlockRail par0BlockRail)
    {
        return par0BlockRail.isPowered;
    }
}
