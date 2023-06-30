package net.minecraft.src;

public class TileEntityRecordPlayer extends TileEntity
{
    /** ID of record which is in Jukebox */
    public int record;

    public TileEntityRecordPlayer()
    {
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        record = par1NBTTagCompound.getInteger("Record");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);

        if (record > 0)
        {
            par1NBTTagCompound.setInteger("Record", record);
        }
    }
}
