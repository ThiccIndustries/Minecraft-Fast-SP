package net.minecraft.src;

public class BiomeCacheBlock
{
    public float temperatureValues[];
    public float rainfallValues[];
    public BiomeGenBase biomes[];

    /** The x coordinate of the BiomeCacheBlock. */
    public int xPosition;

    /** The z coordinate of the BiomeCacheBlock. */
    public int zPosition;

    /** The last time this BiomeCacheBlock was accessed, in milliseconds. */
    public long lastAccessTime;

    /** The BiomeCache objevt that contains this BiomeCacheBlock */
    final BiomeCache biomeCache;

    public BiomeCacheBlock(BiomeCache par1BiomeCache, int par2, int par3)
    {
        biomeCache = par1BiomeCache;
        temperatureValues = new float[256];
        rainfallValues = new float[256];
        biomes = new BiomeGenBase[256];
        xPosition = par2;
        zPosition = par3;
        BiomeCache.getChunkManager(par1BiomeCache).getTemperatures(temperatureValues, par2 << 4, par3 << 4, 16, 16);
        BiomeCache.getChunkManager(par1BiomeCache).getRainfall(rainfallValues, par2 << 4, par3 << 4, 16, 16);
        BiomeCache.getChunkManager(par1BiomeCache).getBiomeGenAt(biomes, par2 << 4, par3 << 4, 16, 16, false);
    }

    /**
     * Returns the BiomeGenBase related to the x, z position from the cache block.
     */
    public BiomeGenBase getBiomeGenAt(int par1, int par2)
    {
        return biomes[par1 & 0xf | (par2 & 0xf) << 4];
    }
}
