package net.minecraft.src;

public class BlockNote extends BlockContainer
{
    public BlockNote(int par1)
    {
        super(par1, 74, Material.wood);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
        return blockIndexInTexture;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par5 > 0)
        {
            boolean flag = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);
            TileEntityNote tileentitynote = (TileEntityNote)par1World.getBlockTileEntity(par2, par3, par4);

            if (tileentitynote != null && tileentitynote.previousRedstoneState != flag)
            {
                if (flag)
                {
                    tileentitynote.triggerNote(par1World, par2, par3, par4);
                }

                tileentitynote.previousRedstoneState = flag;
            }
        }
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

        TileEntityNote tileentitynote = (TileEntityNote)par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentitynote != null)
        {
            tileentitynote.changePitch();
            tileentitynote.triggerNote(par1World, par2, par3, par4);
        }

        return true;
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        if (par1World.isRemote)
        {
            return;
        }

        TileEntityNote tileentitynote = (TileEntityNote)par1World.getBlockTileEntity(par2, par3, par4);

        if (tileentitynote != null)
        {
            tileentitynote.triggerNote(par1World, par2, par3, par4);
        }
    }

    /**
     * Returns the TileEntity used by this block.
     */
    public TileEntity getBlockEntity()
    {
        return new TileEntityNote();
    }

    public void powerBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        float f = (float)Math.pow(2D, (double)(par6 - 12) / 12D);
        String s = "harp";

        if (par5 == 1)
        {
            s = "bd";
        }

        if (par5 == 2)
        {
            s = "snare";
        }

        if (par5 == 3)
        {
            s = "hat";
        }

        if (par5 == 4)
        {
            s = "bassattack";
        }

        par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, (new StringBuilder()).append("note.").append(s).toString(), 3F, f);
        par1World.spawnParticle("note", (double)par2 + 0.5D, (double)par3 + 1.2D, (double)par4 + 0.5D, (double)par6 / 24D, 0.0D, 0.0D);
    }
}
