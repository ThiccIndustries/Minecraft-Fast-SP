package net.minecraft.src;

public class WorldProviderHell extends WorldProvider
{
    public WorldProviderHell()
    {
    }

    /**
     * creates a new world chunk manager for WorldProvider
     */
    public void registerWorldChunkManager()
    {
        worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.hell, 1.0F, 0.0F);
        isHellWorld = true;
        hasNoSky = true;
        worldType = -1;
    }

    /**
     * Return Vec3D with biome specific fog color
     */
    public Vec3D getFogColor(float par1, float par2)
    {
        return Vec3D.createVector(0.20000000298023224D, 0.029999999329447746D, 0.029999999329447746D);
    }

    /**
     * Creates the light to brightness table
     */
    protected void generateLightBrightnessTable()
    {
        float f = 0.1F;

        for (int i = 0; i <= 15; i++)
        {
            float f1 = 1.0F - (float)i / 15F;
            lightBrightnessTable[i] = ((1.0F - f1) / (f1 * 3F + 1.0F)) * (1.0F - f) + f;
        }
    }

    /**
     * Returns the chunk provider back for the world provider
     */
    public IChunkProvider getChunkProvider()
    {
        return new ChunkProviderHell(worldObj, worldObj.getSeed());
    }

    public boolean func_48217_e()
    {
        return false;
    }

    /**
     * Will check if the x, z position specified is alright to be set as the map spawn point
     */
    public boolean canCoordinateBeSpawn(int par1, int par2)
    {
        return false;
    }

    /**
     * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
     */
    public float calculateCelestialAngle(long par1, float par3)
    {
        return 0.5F;
    }

    /**
     * True if the player can respawn in this dimension (true = overworld, false = nether).
     */
    public boolean canRespawnHere()
    {
        return false;
    }

    public boolean func_48218_b(int par1, int par2)
    {
        return true;
    }
}
