package net.minecraft.src;

public class TileEntityNote extends TileEntity
{
    /** Note to play */
    public byte note;

    /** stores the latest redstone state */
    public boolean previousRedstoneState;

    public TileEntityNote()
    {
        note = 0;
        previousRedstoneState = false;
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setByte("note", note);
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        note = par1NBTTagCompound.getByte("note");

        if (note < 0)
        {
            note = 0;
        }

        if (note > 24)
        {
            note = 24;
        }
    }

    /**
     * change pitch by -> (currentPitch + 1) % 25
     */
    public void changePitch()
    {
        note = (byte)((note + 1) % 25);
        onInventoryChanged();
    }

    /**
     * plays the stored note
     */
    public void triggerNote(World par1World, int par2, int par3, int par4)
    {
        if (par1World.getBlockMaterial(par2, par3 + 1, par4) != Material.air)
        {
            return;
        }

        Material material = par1World.getBlockMaterial(par2, par3 - 1, par4);
        byte byte0 = 0;

        if (material == Material.rock)
        {
            byte0 = 1;
        }

        if (material == Material.sand)
        {
            byte0 = 2;
        }

        if (material == Material.glass)
        {
            byte0 = 3;
        }

        if (material == Material.wood)
        {
            byte0 = 4;
        }

        par1World.playNoteAt(par2, par3, par4, byte0, note);
    }
}
