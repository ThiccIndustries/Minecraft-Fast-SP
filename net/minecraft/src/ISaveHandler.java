package net.minecraft.src;

import java.io.File;
import java.util.List;

public interface ISaveHandler
{
    /**
     * Loads and returns the world info
     */
    public abstract WorldInfo loadWorldInfo();

    /**
     * Checks the session lock to prevent save collisions
     */
    public abstract void checkSessionLock();

    /**
     * Returns the chunk loader with the provided world provider
     */
    public abstract IChunkLoader getChunkLoader(WorldProvider worldprovider);

    /**
     * saves level.dat and backs up the existing one to level.dat_old
     */
    public abstract void saveWorldInfoAndPlayer(WorldInfo worldinfo, List list);

    /**
     * Saves the passed in world info.
     */
    public abstract void saveWorldInfo(WorldInfo worldinfo);

    /**
     * Gets the file location of the given map
     */
    public abstract File getMapFileFromName(String s);

    /**
     * Returns the name of the directory where world information is saved
     */
    public abstract String getSaveDirectoryName();
}
