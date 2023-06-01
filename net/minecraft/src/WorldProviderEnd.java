package net.minecraft.src;

public class WorldProviderEnd extends WorldProvider
{
    public WorldProviderEnd()
    {
    }

    /**
     * creates a new world chunk manager for WorldProvider
     */
    public void registerWorldChunkManager()
    {
        worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.5F, 0.0F);
        worldType = 1;
        hasNoSky = true;
    }

    /**
     * Returns the chunk provider back for the world provider
     */
    public IChunkProvider getChunkProvider()
    {
        return new ChunkProviderEnd(worldObj, worldObj.getSeed());
    }

    /**
     * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
     */
    public float calculateCelestialAngle(long par1, float par3)
    {
        return 0.0F;
    }

    /**
     * Returns array with sunrise/sunset colors
     */
    public float[] calcSunriseSunsetColors(float par1, float par2)
    {
        return null;
    }

    /**
     * Return Vec3D with biome specific fog color
     */
    public Vec3D getFogColor(float par1, float par2)
    {
        int i = 0x8080a0;
        float f = MathHelper.cos(par1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

        if (f < 0.0F)
        {
            f = 0.0F;
        }

        if (f > 1.0F)
        {
            f = 1.0F;
        }

        float f1 = (float)(i >> 16 & 0xff) / 255F;
        float f2 = (float)(i >> 8 & 0xff) / 255F;
        float f3 = (float)(i & 0xff) / 255F;
        f1 *= f * 0.0F + 0.15F;
        f2 *= f * 0.0F + 0.15F;
        f3 *= f * 0.0F + 0.15F;
        return Vec3D.createVector(f1, f2, f3);
    }

    public boolean isSkyColored()
    {
        return false;
    }

    /**
     * True if the player can respawn in this dimension (true = overworld, false = nether).
     */
    public boolean canRespawnHere()
    {
        return false;
    }

    public boolean func_48217_e()
    {
        return false;
    }

    /**
     * the y level at which clouds are rendered.
     */
    public float getCloudHeight()
    {
        return 8F;
    }

    /**
     * Will check if the x, z position specified is alright to be set as the map spawn point
     */
    public boolean canCoordinateBeSpawn(int par1, int par2)
    {
        int i = worldObj.getFirstUncoveredBlock(par1, par2);

        if (i == 0)
        {
            return false;
        }
        else
        {
            return Block.blocksList[i].blockMaterial.blocksMovement();
        }
    }

    /**
     * Gets the hard-coded portal location to use when entering this dimension
     */
    public ChunkCoordinates getEntrancePortalLocation()
    {
        return new ChunkCoordinates(100, 50, 0);
    }

    public int getAverageGroundLevel()
    {
        return 50;
    }

    public boolean func_48218_b(int par1, int par2)
    {
        return true;
    }
}
