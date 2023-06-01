package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class BiomeCache
{
    /** Reference to the WorldChunkManager */
    private final WorldChunkManager chunkManager;

    /** The last time this BiomeCache was cleaned, in milliseconds. */
    private long lastCleanupTime;

    /**
     * The map of keys to BiomeCacheBlocks. Keys are based on the chunk x, z coordinates as (x | z << 32).
     */
    private LongHashMap cacheMap;

    /** The list of cached BiomeCacheBlocks */
    private List cache;

    public BiomeCache(WorldChunkManager par1WorldChunkManager)
    {
        lastCleanupTime = 0L;
        cacheMap = new LongHashMap();
        cache = new ArrayList();
        chunkManager = par1WorldChunkManager;
    }

    /**
     * Returns a biome cache block at location specified.
     */
    public BiomeCacheBlock getBiomeCacheBlock(int par1, int par2)
    {
        par1 >>= 4;
        par2 >>= 4;
        long l = (long)par1 & 0xffffffffL | ((long)par2 & 0xffffffffL) << 32;
        BiomeCacheBlock biomecacheblock = (BiomeCacheBlock)cacheMap.getValueByKey(l);

        if (biomecacheblock == null)
        {
            biomecacheblock = new BiomeCacheBlock(this, par1, par2);
            cacheMap.add(l, biomecacheblock);
            cache.add(biomecacheblock);
        }

        biomecacheblock.lastAccessTime = System.currentTimeMillis();
        return biomecacheblock;
    }

    /**
     * Returns the BiomeGenBase related to the x, z position from the cache.
     */
    public BiomeGenBase getBiomeGenAt(int par1, int par2)
    {
        return getBiomeCacheBlock(par1, par2).getBiomeGenAt(par1, par2);
    }

    /**
     * Removes BiomeCacheBlocks from this cache that haven't been accessed in at least 30 seconds.
     */
    public void cleanupCache()
    {
        long l = System.currentTimeMillis();
        long l1 = l - lastCleanupTime;

        if (l1 > 7500L || l1 < 0L)
        {
            lastCleanupTime = l;

            for (int i = 0; i < cache.size(); i++)
            {
                BiomeCacheBlock biomecacheblock = (BiomeCacheBlock)cache.get(i);
                long l2 = l - biomecacheblock.lastAccessTime;

                if (l2 > 30000L || l2 < 0L)
                {
                    cache.remove(i--);
                    long l3 = (long)biomecacheblock.xPosition & 0xffffffffL | ((long)biomecacheblock.zPosition & 0xffffffffL) << 32;
                    cacheMap.remove(l3);
                }
            }
        }
    }

    /**
     * Returns the array of cached biome types in the BiomeCacheBlock at the given location.
     */
    public BiomeGenBase[] getCachedBiomes(int par1, int par2)
    {
        return getBiomeCacheBlock(par1, par2).biomes;
    }

    /**
     * Get the world chunk manager object for a biome list.
     */
    static WorldChunkManager getChunkManager(BiomeCache par0BiomeCache)
    {
        return par0BiomeCache.chunkManager;
    }
}
