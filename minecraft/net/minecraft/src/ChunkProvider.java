package net.minecraft.src;

import java.io.IOException;
import java.util.*;

public class ChunkProvider implements IChunkProvider
{
    /** A set of dropped chunks. Currently not used in single player. */
    private Set droppedChunksSet;
    private Chunk emptyChunk;

    /** The parent IChunkProvider for this ChunkProvider. */
    private IChunkProvider chunkProvider;

    /** The IChunkLoader used by this ChunkProvider */
    private IChunkLoader chunkLoader;

    /**
     * A map of all the currently loaded chunks, uses the chunk id as the key.
     */
    private LongHashMap chunkMap;

    /** A list of all the currently loaded chunks. */
    private List chunkList;

    /** The World object which this ChunkProvider was constructed with */
    private World worldObj;
    private int field_35392_h;

    public ChunkProvider(World par1World, IChunkLoader par2IChunkLoader, IChunkProvider par3IChunkProvider)
    {
        droppedChunksSet = new HashSet();
        chunkMap = new LongHashMap();
        chunkList = new ArrayList();
        emptyChunk = new EmptyChunk(par1World, 0, 0);
        worldObj = par1World;
        chunkLoader = par2IChunkLoader;
        chunkProvider = par3IChunkProvider;
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
    public boolean chunkExists(int par1, int par2)
    {
        return chunkMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
    }

    /**
     * Drops the specified chunk.
     */
    public void dropChunk(int par1, int par2)
    {
        ChunkCoordinates chunkcoordinates = worldObj.getSpawnPoint();
        int i = (par1 * 16 + 8) - chunkcoordinates.posX;
        int j = (par2 * 16 + 8) - chunkcoordinates.posZ;
        char c = '\200';

        if (i < -c || i > c || j < -c || j > c)
        {
            droppedChunksSet.add(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(par1, par2)));
        }
    }
    
    public void preloadChunk(int par1, int par2){
    	Chunk c = loadChunk(par1, par2);
    	c.onChunkUnload();
        saveChunkData(c);
        saveChunkExtraData(c);
        c.isModified = false;
        long l = ChunkCoordIntPair.chunkXZ2Int(par1, par2);
        chunkMap.remove(l);
        chunkList.remove(c);
    }

    public boolean chunkInRange(int par1, int par2){
    	int i = worldObj.getWorldInfo().getWorldSize();
    	return !(par1 < -i || par2 < -i || par1 > (i - 1) || par2 > (i - 1));
    }
    
    /**
     * loads or generates the chunk at the chunk location specified
     */
    public Chunk loadChunk(int par1, int par2)
    {
    	
    	
        long l = ChunkCoordIntPair.chunkXZ2Int(par1, par2);
        droppedChunksSet.remove(Long.valueOf(l));
        Chunk chunk = (Chunk)chunkMap.getValueByKey(l);

        if (chunk == null)
        {
            int i = worldObj.getWorldInfo().getWorldSize();
            
            boolean chunkOutOfRange = par1 < -i || par2 < -i || par1 > (i - 1) || par2 > (i - 1);
            boolean addToMap = true;
            
            if(chunkOutOfRange){
            	int distance1 = 0, distance2 = 0;
            	if(par1 < -i)
            		distance1 = -(par1 + i);
            	
            	if(par1 > (i - 1))
            		distance1 = par1 - (i-1);
            	
            	if(par2 < -i)
            		distance2 = -(par2 + i);
            	
            	if(par2 > (i - 1))
            		distance2 = par2 - (i-1);
            	
            	int distance = Math.max(distance1, distance2);
            	System.out.println(par1 + "," + par2 + "," + distance);
            	addToMap = distance <= 1;
            }
            
            if(par1 == 0 && par2 == 0)
            	chunkOutOfRange = false;
            
            chunk = loadChunkFromFile(par1, par2);
            

            if(chunkOutOfRange){
            	chunk = emptyChunk;
            }
            
            if (chunk == null)
            {
                if (chunkProvider == null)
                {
                    chunk = emptyChunk;
                }
                else
                {
                    chunk = chunkProvider.provideChunk(par1, par2);
                    
                }
            }
            
            if(addToMap){
            	chunkMap.add(l, chunk);
            	chunkList.add(chunk);
            }

            if (chunk != null)
            {
                chunk.func_4143_d();
                chunk.onChunkLoad();
            }
            
            if(!chunkOutOfRange)
            	chunk.populateChunk(this, this, par1, par2);
        }

        return chunk;
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    public Chunk provideChunk(int par1, int par2)
    {
        Chunk chunk = (Chunk)chunkMap.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(par1, par2));

        if (chunk == null)
        {
            return loadChunk(par1, par2);
        }
        else
        {
            return chunk;
        }
    }

    /**
     * Attemps to load the chunk from the save file, returns null if the chunk is not available.
     */
    private Chunk loadChunkFromFile(int par1, int par2)
    {
        if (chunkLoader == null)
        {
            return null;
        }

        try
        {
            Chunk chunk = chunkLoader.loadChunk(worldObj, par1, par2);

            if (chunk != null)
            {
                chunk.lastSaveTime = worldObj.getWorldTime();
            }

            return chunk;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return null;
    }

    private void saveChunkExtraData(Chunk par1Chunk)
    {
        if (chunkLoader == null)
        {
            return;
        }

        try
        {
            chunkLoader.saveExtraChunkData(worldObj, par1Chunk);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void saveChunkData(Chunk par1Chunk)
    {
        if (chunkLoader == null)
        {
            return;
        }

        try
        {
            par1Chunk.lastSaveTime = worldObj.getWorldTime();
            chunkLoader.saveChunk(worldObj, par1Chunk);
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    /**
     * Populates chunk with ores etc etc
     */
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
    {
        Chunk chunk = provideChunk(par2, par3);

        if (!chunk.isTerrainPopulated)
        {
            chunk.isTerrainPopulated = true;

            if (chunkProvider != null)
            {
                chunkProvider.populate(par1IChunkProvider, par2, par3);
                chunk.setChunkModified();
            }
        }
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    public boolean saveChunks(boolean par1, boolean drop, IProgressUpdate par2IProgressUpdate)
    {
        int i = 0;
        float p = 0;
        int s = chunkList.size();
        for (int j = 0; j < chunkList.size(); j++)
        {
        	if(par2IProgressUpdate != null){
        		p = (100.0f / s) * j;
        		par2IProgressUpdate.setLoadingProgress((int)p);
        	}
        	
            Chunk chunk = (Chunk)chunkList.get(j);

            if (par1)
            {
                saveChunkExtraData(chunk);
            }

            if (!chunk.needsSaving(par1))
            {
                continue;
            }

            saveChunkData(chunk);
            chunk.isModified = false;

            if (++i == 24 && !par1)
            {
                return false;
            }
            
            if(drop){
            	chunk.onChunkUnload();
            	chunkList.remove(chunk);
            }
        }

        if (par1)
        {
            if (chunkLoader == null)
            {
                return true;
            }

            chunkLoader.saveExtraData();
        }

        return true;
    }

    /**
     * Unloads the 100 oldest chunks from memory, due to a bug with chunkSet.add() never being called it thinks the list
     * is always empty and will not remove any chunks.
     */
    public boolean unload100OldestChunks()
    {
        for (int i = 0; i < 100; i++)
        {
            if (!droppedChunksSet.isEmpty())
            {
                Long long1 = (Long)droppedChunksSet.iterator().next();
                Chunk chunk1 = (Chunk)chunkMap.getValueByKey(long1.longValue());
                chunk1.onChunkUnload();
                saveChunkData(chunk1);
                saveChunkExtraData(chunk1);
                droppedChunksSet.remove(long1);
                chunkMap.remove(long1.longValue());
                chunkList.remove(chunk1);
            }
        }

        for (int j = 0; j < 10; j++)
        {
            if (field_35392_h >= chunkList.size())
            {
                field_35392_h = 0;
                break;
            }

            Chunk chunk = (Chunk)chunkList.get(field_35392_h++);
            EntityPlayer entityplayer = worldObj.func_48456_a((double)(chunk.xPosition << 4) + 8D, (double)(chunk.zPosition << 4) + 8D, 288D);

            if (entityplayer == null)
            {
                dropChunk(chunk.xPosition, chunk.zPosition);
            }
        }

        if (chunkLoader != null)
        {
            chunkLoader.chunkTick();
        }

        return chunkProvider.unload100OldestChunks();
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
        return (new StringBuilder()).append("ServerChunkCache: ").append(chunkMap.getNumHashElements()).append(" Drop: ").append(droppedChunksSet.size()).toString();
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
        return chunkProvider.getPossibleCreatures(par1EnumCreatureType, par2, par3, par4);
    }

    /**
     * Returns the location of the closest structure of the specified type. If not found returns null.
     */
    public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int par4, int par5)
    {
        return chunkProvider.findClosestStructure(par1World, par2Str, par3, par4, par5);
    }
}
