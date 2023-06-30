package net.minecraft.src;

public class NextTickListEntry implements Comparable
{
    /** The id number for the next tick entry */
    private static long nextTickEntryID = 0L;

    /** X position this tick is occuring at */
    public int xCoord;

    /** Y position this tick is occuring at */
    public int yCoord;

    /** Z position this tick is occuring at */
    public int zCoord;

    /**
     * blockID of the scheduled tick (ensures when the tick occurs its still for this block)
     */
    public int blockID;

    /** Time this tick is scheduled to occur at */
    public long scheduledTime;

    /** The id of the tick entry */
    private long tickEntryID;

    public NextTickListEntry(int par1, int par2, int par3, int par4)
    {
        tickEntryID = nextTickEntryID++;
        xCoord = par1;
        yCoord = par2;
        zCoord = par3;
        blockID = par4;
    }

    public boolean equals(Object par1Obj)
    {
        if (par1Obj instanceof NextTickListEntry)
        {
            NextTickListEntry nextticklistentry = (NextTickListEntry)par1Obj;
            return xCoord == nextticklistentry.xCoord && yCoord == nextticklistentry.yCoord && zCoord == nextticklistentry.zCoord && blockID == nextticklistentry.blockID;
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return (xCoord * 1024 * 1024 + zCoord * 1024 + yCoord) * 256 + blockID;
    }

    /**
     * Sets the scheduled time for this tick entry
     */
    public NextTickListEntry setScheduledTime(long par1)
    {
        scheduledTime = par1;
        return this;
    }

    /**
     * Compares this tick entry to another tick entry for sorting purposes. Compared first based on the scheduled time
     * and second based on tickEntryID.
     */
    public int comparer(NextTickListEntry par1NextTickListEntry)
    {
        if (scheduledTime < par1NextTickListEntry.scheduledTime)
        {
            return -1;
        }

        if (scheduledTime > par1NextTickListEntry.scheduledTime)
        {
            return 1;
        }

        if (tickEntryID < par1NextTickListEntry.tickEntryID)
        {
            return -1;
        }

        return tickEntryID <= par1NextTickListEntry.tickEntryID ? 0 : 1;
    }

    public int compareTo(Object par1Obj)
    {
        return comparer((NextTickListEntry)par1Obj);
    }
}
