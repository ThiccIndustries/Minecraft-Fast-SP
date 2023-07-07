package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ChunkProviderFlat implements IChunkProvider
{
    private World worldObj;
    private Random random;
    private final boolean useStructures;
    private MapGenVillage villageGen;

    public ChunkProviderFlat(World par1World, long par2, boolean par4)
    {
        villageGen = new MapGenVillage(1);
        worldObj = par1World;
        useStructures = par4;
        random = new Random(par2);
    }
    public boolean isHell(){
    	return false;
    }
    private void generate(byte par1ArrayOfByte[])
    {
        int i = par1ArrayOfByte.length / 256;

        for (int j = 0; j < 16; j++)
        {
            for (int k = 0; k < 16; k++)
            {
                for (int l = 0; l < i; l++)
                {
                    int i1 = 0;

                    if (l == 0)
                    {
                        i1 = Block.bedrock.blockID;
                    }
                    else if (l <= 2)
                    {
                        i1 = Block.dirt.blockID;
                    }
                    else if (l == 3)
                    {
                        i1 = Block.grass.blockID;
                    }

                    par1ArrayOfByte[j << 11 | k << 7 | l] = (byte)i1;
                }
            }
        }
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    public Chunk loadChunk(int par1, int par2)
    {
        return provideChunk(par1, par2);
    }
    
    //TODO: todo
    public void preloadChunk(int par1, int par2){
    	
    }
    
    //TODO: user size
    public boolean chunkInRange(int par1, int par2){
    	int worldSize = worldObj.getWorldInfo().getWorldSize();
    	return par1 >= -worldSize && par1 <= worldSize && par2 >= -worldSize && par2 <= worldSize;
    }
    
    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    public Chunk provideChunk(int par1, int par2)
    {
        byte abyte0[] = new byte[32768];
        generate(abyte0);
        Chunk chunk = new Chunk(worldObj, abyte0, par1, par2);

        if (useStructures)
        {
            villageGen.generate(this, worldObj, par1, par2, abyte0);
        }

        BiomeGenBase abiomegenbase[] = worldObj.getWorldChunkManager().loadBlockGeneratorData(null, par1 * 16, par2 * 16, 16, 16);
        byte abyte1[] = chunk.getBiomeArray();

        for (int i = 0; i < abyte1.length; i++)
        {
            abyte1[i] = (byte)abiomegenbase[i].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
    public boolean chunkExists(int par1, int par2)
    {
        return true;
    }

    /**
     * Populates chunk with ores etc etc
     */
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
    {
        random.setSeed(worldObj.getSeed());
        long l = (random.nextLong() / 2L) * 2L + 1L;
        long l1 = (random.nextLong() / 2L) * 2L + 1L;
        random.setSeed((long)par2 * l + (long)par3 * l1 ^ worldObj.getSeed());

        if (useStructures)
        {
            villageGen.generateStructuresInChunk(worldObj, random, par2, par3);
        }
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    public boolean saveChunks(boolean par1, boolean drop, IProgressUpdate par2IProgressUpdate)
    {
        return true;
    }

    /**
     * Unloads the 100 oldest chunks from memory, due to a bug with chunkSet.add() never being called it thinks the list
     * is always empty and will not remove any chunks.
     */
    public boolean unload100OldestChunks()
    {
        return false;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    public boolean canSave()
    {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    public String makeString()
    {
        return "FlatLevelSource";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
        BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(par2, par4);

        if (biomegenbase == null)
        {
            return null;
        }
        else
        {
            return biomegenbase.getSpawnableList(par1EnumCreatureType);
        }
    }

    /**
     * Returns the location of the closest structure of the specified type. If not found returns null.
     */
    public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int i, int j)
    {
        return null;
    }
}
