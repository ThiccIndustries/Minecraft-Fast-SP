package net.minecraft.src;

import java.util.Random;

public class BlockPumpkin extends BlockDirectional
{
    /** Boolean used to seperate different states of blocks */
    private boolean blockType;

    protected BlockPumpkin(int par1, int par2, boolean par3)
    {
        super(par1, Material.pumpkin);
        blockIndexInTexture = par2;
        setTickRandomly(true);
        blockType = par3;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        if (par1 == 1)
        {
            return blockIndexInTexture;
        }

        if (par1 == 0)
        {
            return blockIndexInTexture;
        }

        int i = blockIndexInTexture + 1 + 16;

        if (blockType)
        {
            i++;
        }

        if (par2 == 2 && par1 == 2)
        {
            return i;
        }

        if (par2 == 3 && par1 == 5)
        {
            return i;
        }

        if (par2 == 0 && par1 == 3)
        {
            return i;
        }

        if (par2 == 1 && par1 == 4)
        {
            return i;
        }
        else
        {
            return blockIndexInTexture + 16;
        }
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        if (par1 == 1)
        {
            return blockIndexInTexture;
        }

        if (par1 == 0)
        {
            return blockIndexInTexture;
        }

        if (par1 == 3)
        {
            return blockIndexInTexture + 1 + 16;
        }
        else
        {
            return blockIndexInTexture + 16;
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);

        if (par1World.getBlockId(par2, par3 - 1, par4) == Block.blockSnow.blockID && par1World.getBlockId(par2, par3 - 2, par4) == Block.blockSnow.blockID)
        {
            if (!par1World.isRemote)
            {
                par1World.setBlock(par2, par3, par4, 0);
                par1World.setBlock(par2, par3 - 1, par4, 0);
                par1World.setBlock(par2, par3 - 2, par4, 0);
                EntitySnowman entitysnowman = new EntitySnowman(par1World);
                entitysnowman.setLocationAndAngles((double)par2 + 0.5D, (double)par3 - 1.95D, (double)par4 + 0.5D, 0.0F, 0.0F);
                par1World.spawnEntityInWorld(entitysnowman);
                par1World.notifyBlockChange(par2, par3, par4, 0);
                par1World.notifyBlockChange(par2, par3 - 1, par4, 0);
                par1World.notifyBlockChange(par2, par3 - 2, par4, 0);
            }

            for (int i = 0; i < 120; i++)
            {
                par1World.spawnParticle("snowshovel", (double)par2 + par1World.rand.nextDouble(), (double)(par3 - 2) + par1World.rand.nextDouble() * 2.5D, (double)par4 + par1World.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
            }
        }
        else if (par1World.getBlockId(par2, par3 - 1, par4) == Block.blockSteel.blockID && par1World.getBlockId(par2, par3 - 2, par4) == Block.blockSteel.blockID)
        {
            boolean flag = par1World.getBlockId(par2 - 1, par3 - 1, par4) == Block.blockSteel.blockID && par1World.getBlockId(par2 + 1, par3 - 1, par4) == Block.blockSteel.blockID;
            boolean flag1 = par1World.getBlockId(par2, par3 - 1, par4 - 1) == Block.blockSteel.blockID && par1World.getBlockId(par2, par3 - 1, par4 + 1) == Block.blockSteel.blockID;

            if (flag || flag1)
            {
                par1World.setBlock(par2, par3, par4, 0);
                par1World.setBlock(par2, par3 - 1, par4, 0);
                par1World.setBlock(par2, par3 - 2, par4, 0);

                if (flag)
                {
                    par1World.setBlock(par2 - 1, par3 - 1, par4, 0);
                    par1World.setBlock(par2 + 1, par3 - 1, par4, 0);
                }
                else
                {
                    par1World.setBlock(par2, par3 - 1, par4 - 1, 0);
                    par1World.setBlock(par2, par3 - 1, par4 + 1, 0);
                }

                EntityIronGolem entityirongolem = new EntityIronGolem(par1World);
                entityirongolem.func_48115_b(true);
                entityirongolem.setLocationAndAngles((double)par2 + 0.5D, (double)par3 - 1.95D, (double)par4 + 0.5D, 0.0F, 0.0F);
                par1World.spawnEntityInWorld(entityirongolem);

                for (int j = 0; j < 120; j++)
                {
                    par1World.spawnParticle("snowballpoof", (double)par2 + par1World.rand.nextDouble(), (double)(par3 - 2) + par1World.rand.nextDouble() * 3.8999999999999999D, (double)par4 + par1World.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
                }

                par1World.notifyBlockChange(par2, par3, par4, 0);
                par1World.notifyBlockChange(par2, par3 - 1, par4, 0);
                par1World.notifyBlockChange(par2, par3 - 2, par4, 0);

                if (flag)
                {
                    par1World.notifyBlockChange(par2 - 1, par3 - 1, par4, 0);
                    par1World.notifyBlockChange(par2 + 1, par3 - 1, par4, 0);
                }
                else
                {
                    par1World.notifyBlockChange(par2, par3 - 1, par4 - 1, 0);
                    par1World.notifyBlockChange(par2, par3 - 1, par4 + 1, 0);
                }
            }
        }
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        int i = par1World.getBlockId(par2, par3, par4);
        return (i == 0 || Block.blocksList[i].blockMaterial.isGroundCover()) && par1World.isBlockNormalCube(par2, par3 - 1, par4);
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int i = MathHelper.floor_double((double)((par5EntityLiving.rotationYaw * 4F) / 360F) + 2.5D) & 3;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, i);
    }
}
