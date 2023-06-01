package net.minecraft.src;

public final class WorldSettings
{
    /** The seed for the map. */
    private final long seed;

    /** The game mode, 1 for creative, 0 for survival. */
    private final int gameType;

    /**
     * Switch for the map features. 'true' for enabled, 'false' for disabled.
     */
    private final boolean mapFeaturesEnabled;

    /** True if hardcore mode is enabled */
    private final boolean hardcoreEnabled;
    private final WorldType terrainType;
    
    /** Size of the finite world to generate */
    private final int worldSize;
    
    public WorldSettings(long par1, int par3, boolean par4, boolean par5, WorldType par6WorldType, int size)
    {
        seed = par1;
        gameType = par3;
        mapFeaturesEnabled = par4;
        hardcoreEnabled = par5;
        terrainType = par6WorldType;
        worldSize = size;
    }

    /**
     * Returns the seed for the world.
     */
    public long getSeed()
    {
        return seed;
    }

    /**
     * Returns the world game type.
     */
    public int getGameType()
    {
        return gameType;
    }

    /**
     * Returns true if hardcore mode is enabled, otherwise false
     */
    public boolean getHardcoreEnabled()
    {
        return hardcoreEnabled;
    }

    /**
     * Returns if map features are enabled, caves, mines, etc..
     */
    public boolean isMapFeaturesEnabled()
    {
        return mapFeaturesEnabled;
    }

    public WorldType getTerrainType()
    {
        return terrainType;
    }
    
    public int getWorldSize()
    {
    	return worldSize;
    }
}
