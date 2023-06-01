package net.minecraft.src;

public class SaveFormatComparator implements Comparable
{
    /** the file name of this save */
    private final String fileName;

    /** the displayed name of this save file */
    private final String displayName;
    private final long lastTimePlayed;
    private final long sizeOnDisk;
    private final boolean requiresConversion;
    private final int gameType;
    private final int worldSize;
    private final boolean hardcore;

    public SaveFormatComparator(String par1Str, String par2Str, long par3, long par5, int par7, boolean par8, boolean par9, int par10)
    {
        fileName = par1Str;
        displayName = par2Str;
        lastTimePlayed = par3;
        sizeOnDisk = par5;
        gameType = par7;
        requiresConversion = par8;
        hardcore = par9;
        worldSize = par10;
    }

    /**
     * return the file name
     */
    public String getFileName()
    {
        return fileName;
    }

    /**
     * return the display name of the save
     */
    public String getDisplayName()
    {
        return displayName;
    }

    public boolean requiresConversion()
    {
        return requiresConversion;
    }

    public long getLastTimePlayed()
    {
        return lastTimePlayed;
    }

    public int compareTo(SaveFormatComparator par1SaveFormatComparator)
    {
        if (lastTimePlayed < par1SaveFormatComparator.lastTimePlayed)
        {
            return 1;
        }

        if (lastTimePlayed > par1SaveFormatComparator.lastTimePlayed)
        {
            return -1;
        }
        else
        {
            return fileName.compareTo(par1SaveFormatComparator.fileName);
        }
    }

    public int getGameType()
    {
        return gameType;
    }

    public boolean isHardcoreModeEnabled()
    {
        return hardcore;
    }

    public int compareTo(Object par1Obj)
    {
        return compareTo((SaveFormatComparator)par1Obj);
    }

	public String getWorldSize() {
		return new Integer(worldSize).toString();
	}
}
