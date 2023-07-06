package net.minecraft.src;

import java.util.Random;

public class BlockEnchantmentTable extends BlockContainer
{
    protected BlockEnchantmentTable(int par1)
    {
        super(par1, 166, Material.rock);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
        setLightOpacity(0);
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.randomDisplayTick(par1World, par2, par3, par4, par5Random);

        for (int i = par2 - 2; i <= par2 + 2; i++)
        {
            for (int j = par4 - 2; j <= par4 + 2; j++)
            {
                if (i > par2 - 2 && i < par2 + 2 && j == par4 - 1)
                {
                    j = par4 + 2;
                }

                if (par5Random.nextInt(16) != 0)
                {
                    continue;
                }

                for (int k = par3; k <= par3 + 1; k++)
                {
                    if (par1World.getBlockId(i, k, j) != Block.bookShelf.blockID)
                    {
                        continue;
                    }

                    if (!par1World.isAirBlock((i - par2) / 2 + par2, k, (j - par4) / 2 + par4))
                    {
                        break;
                    }

                    par1World.spawnParticle("enchantmenttable", (double)par2 + 0.5D, (double)par3 + 2D, (double)par4 + 0.5D, (double)((float)(i - par2) + par5Random.nextFloat()) - 0.5D, (float)(k - par3) - par5Random.nextFloat() - 1.0F, (double)((float)(j - par4) + par5Random.nextFloat()) - 0.5D);
                }
            }
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
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return getBlockTextureFromSide(par1);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        if (par1 == 0)
        {
            return blockIndexInTexture + 17;
        }

        if (par1 == 1)
        {
            return blockIndexInTexture;
        }
        else
        {
            return blockIndexInTexture + 16;
        }
    }

    /**
     * Returns the TileEntity used by this block.
     */
    public TileEntity getBlockEntity()
    {
        return new TileEntityEnchantmentTable();
    }

    /**
     * Called upon block activation (left or right click on the block.). The three integers represent x,y,z of the
     * block.
     */
    public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        if (par1World.isRemote)
        {
            return true;
        }
        else
        {
            par5EntityPlayer.displayGUIEnchantment(par2, par3, par4);
            return true;
        }
    }
}
