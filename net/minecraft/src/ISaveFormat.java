package net.minecraft.src;

import java.util.List;

public interface ISaveFormat
{
    public abstract String getFormatName();

    /**
     * Returns back a loader for the specified save directory
     */
    public abstract ISaveHandler getSaveLoader(String s, boolean flag);

    public abstract List getSaveList();

    public abstract void flushCache();

    /**
     * gets the world info
     */
    public abstract WorldInfo getWorldInfo(String s);

    /**
     * @args: Takes one argument - the name of the directory of the world to delete. @desc: Delete the world by deleting
     * the associated directory recursively.
     */
    public abstract void deleteWorldDirectory(String s);

    /**
     * @args: Takes two arguments - first the name of the directory containing the world and second the new name for
     * that world. @desc: Renames the world by storing the new name in level.dat. It does *not* rename the directory
     * containing the world data.
     */
    public abstract void renameWorld(String s, String s1);

    /**
     * Checks if the save directory uses the old map format
     */
    public abstract boolean isOldMapFormat(String s);

    /**
     * Converts the specified map to the new map format. Args: worldName, loadingScreen
     */
    public abstract boolean convertMapFormat(String s, IProgressUpdate iprogressupdate);

	public abstract void resizeWorld(String worldName, Integer size);

}
