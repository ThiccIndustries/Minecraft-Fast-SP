package net.minecraft.src;

import java.io.IOException;

public interface IChunkLoader
{
    /**
     * Loads the specified(XZ) chunk into the specified world.
     */
    public abstract Chunk loadChunk(World world, int i, int j) throws IOException;

    public abstract void saveChunk(World world, Chunk chunk) throws IOException;

    /**
     * Save extra data associated with this Chunk not normally saved during autosave, only during chunk unload.
     * Currently unused.
     */
    public abstract void saveExtraChunkData(World world, Chunk chunk) throws IOException;

    /**
     * Called every World.tick()
     */
    public abstract void chunkTick();

    /**
     * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.  Currently
     * unused.
     */
    public abstract void saveExtraData();
}
