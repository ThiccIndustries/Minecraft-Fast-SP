package net.minecraft.src;

import java.util.List;

public interface IChunkProvider
{
    /**
     * Checks to see if a chunk exists at x, y
     */
    public abstract boolean chunkExists(int i, int j);
    
    public abstract boolean chunkInRange(int i, int j);
    
    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    public abstract Chunk provideChunk(int i, int j);

    /**
     * loads or generates the chunk at the chunk location specified
     */
    public abstract Chunk loadChunk(int i, int j);
    public abstract void preloadChunk(int par1, int par2);
    /**
     * Populates chunk with ores etc etc
     */
    public abstract void populate(IChunkProvider ichunkprovider, int i, int j);

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    public abstract boolean saveChunks(boolean flag, boolean drop, IProgressUpdate iprogressupdate);

    /**
     * Unloads the 100 oldest chunks from memory, due to a bug with chunkSet.add() never being called it thinks the list
     * is always empty and will not remove any chunks.
     */
    public abstract boolean unload100OldestChunks();

    /**
     * Returns if the IChunkProvider supports saving.
     */
    public abstract boolean canSave();

    /**
     * Converts the instance data to a readable string.
     */
    public abstract String makeString();

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    public abstract List getPossibleCreatures(EnumCreatureType enumcreaturetype, int i, int j, int k);

    /**
     * Returns the location of the closest structure of the specified type. If not found returns null.
     */
    public abstract ChunkPosition findClosestStructure(World world, String s, int i, int j, int k);
}
