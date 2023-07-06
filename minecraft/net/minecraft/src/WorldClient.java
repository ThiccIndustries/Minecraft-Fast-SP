package net.minecraft.src;

import java.util.*;

public class WorldClient extends World
{
    /**
     * Contains a list of blocks to to receive and process after they've been 'accepted' by the client (i.e., not
     * invalidated).
     */
    private LinkedList blocksToReceive;

    /** The packets that need to be sent to the server. */
    private NetClientHandler sendQueue;
    private ChunkProviderClient field_20915_C;

    /**
     * The hash set of entities handled by this client. Uses the entity's ID as the hash set's key.
     */
    private IntHashMap entityHashSet;

    /** Contains all entities for this client, both spawned and non-spawned. */
    private Set entityList;

    /**
     * Contains all entities for this client that were not spawned due to a non-present chunk. The game will attempt to
     * spawn up to 10 pending entities with each subsequent tick until the spawn queue is empty.
     */
    private Set entitySpawnQueue;

    public WorldClient(NetClientHandler par1NetClientHandler, WorldSettings par2WorldSettings, int par3, int par4)
    {
        super(new SaveHandlerMP(), "MpServer", WorldProvider.getProviderForDimension(par3), par2WorldSettings);
        blocksToReceive = new LinkedList();
        entityHashSet = new IntHashMap();
        entityList = new HashSet();
        entitySpawnQueue = new HashSet();
        sendQueue = par1NetClientHandler;
        difficultySetting = par4;
        setSpawnPoint(new ChunkCoordinates(8, 64, 8));
        mapStorage = par1NetClientHandler.mapStorage;
    }

    /**
     * Runs a single tick for the world
     */
    public void tick()
    {
        setWorldTime(getWorldTime() + 1L);

        for (int i = 0; i < 10 && !entitySpawnQueue.isEmpty(); i++)
        {
            Entity entity = (Entity)entitySpawnQueue.iterator().next();
            entitySpawnQueue.remove(entity);

            if (!loadedEntityList.contains(entity))
            {
                spawnEntityInWorld(entity);
            }
        }

        sendQueue.processReadPackets();

        for (int j = 0; j < blocksToReceive.size(); j++)
        {
            WorldBlockPositionType worldblockpositiontype = (WorldBlockPositionType)blocksToReceive.get(j);

            if (--worldblockpositiontype.acceptCountdown == 0)
            {
                super.setBlockAndMetadata(worldblockpositiontype.posX, worldblockpositiontype.posY, worldblockpositiontype.posZ, worldblockpositiontype.blockID, worldblockpositiontype.metadata);
                super.markBlockNeedsUpdate(worldblockpositiontype.posX, worldblockpositiontype.posY, worldblockpositiontype.posZ);
                blocksToReceive.remove(j--);
            }
        }

        field_20915_C.unload100OldestChunks();
        tickBlocksAndAmbiance();
    }

    /**
     * Invalidates an AABB region of blocks from the receive queue, in the event that the block has been modified
     * client-side in the intervening 80 receive ticks.
     */
    public void invalidateBlockReceiveRegion(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        for (int i = 0; i < blocksToReceive.size(); i++)
        {
            WorldBlockPositionType worldblockpositiontype = (WorldBlockPositionType)blocksToReceive.get(i);

            if (worldblockpositiontype.posX >= par1 && worldblockpositiontype.posY >= par2 && worldblockpositiontype.posZ >= par3 && worldblockpositiontype.posX <= par4 && worldblockpositiontype.posY <= par5 && worldblockpositiontype.posZ <= par6)
            {
                blocksToReceive.remove(i--);
            }
        }
    }

    /**
     * Creates the chunk provider for this world. Called in the constructor. Retrieves provider from worldProvider?
     */
    protected IChunkProvider createChunkProvider()
    {
        field_20915_C = new ChunkProviderClient(this);
        return field_20915_C;
    }

    /**
     * Sets a new spawn location by finding an uncovered block at a random (x,z) location in the chunk.
     */
    public void setSpawnLocation()
    {
        setSpawnPoint(new ChunkCoordinates(8, 64, 8));
    }

    /**
     * plays random cave ambient sounds and runs updateTick on random blocks within each chunk in the vacinity of a
     * player
     */
    protected void tickBlocksAndAmbiance()
    {
        func_48461_r();

        for (Iterator iterator = activeChunkSet.iterator(); iterator.hasNext(); Profiler.endSection())
        {
            ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)iterator.next();
            int i = chunkcoordintpair.chunkXPos * 16;
            int j = chunkcoordintpair.chunkZPos * 16;
            Profiler.startSection("getChunk");
            Chunk chunk = getChunkFromChunkCoords(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos);
            func_48458_a(i, j, chunk);
        }
    }

    /**
     * Schedules a tick to a block with a delay (Most commonly the tick rate)
     */
    public void scheduleBlockUpdate(int i, int j, int k, int l, int i1)
    {
    }

    /**
     * Runs through the list of updates to run and ticks them
     */
    public boolean tickUpdates(boolean par1)
    {
        return false;
    }

    public void doPreChunk(int par1, int par2, boolean par3)
    {
        if (par3)
        {
            field_20915_C.loadChunk(par1, par2);
        }
        else
        {
            field_20915_C.func_539_c(par1, par2);
        }

        if (!par3)
        {
            markBlocksDirty(par1 * 16, 0, par2 * 16, par1 * 16 + 15, 256, par2 * 16 + 15);
        }
    }

    /**
     * Called to place all entities as part of a world
     */
    public boolean spawnEntityInWorld(Entity par1Entity)
    {
        boolean flag = super.spawnEntityInWorld(par1Entity);
        entityList.add(par1Entity);

        if (!flag)
        {
            entitySpawnQueue.add(par1Entity);
        }

        return flag;
    }

    /**
     * Not sure what this does 100%, but from the calling methods this method should be called like this.
     */
    public void setEntityDead(Entity par1Entity)
    {
        super.setEntityDead(par1Entity);
        entityList.remove(par1Entity);
    }

    /**
     * Start the skin for this entity downloading, if necessary, and increment its reference counter
     */
    protected void obtainEntitySkin(Entity par1Entity)
    {
        super.obtainEntitySkin(par1Entity);

        if (entitySpawnQueue.contains(par1Entity))
        {
            entitySpawnQueue.remove(par1Entity);
        }
    }

    /**
     * Decrement the reference counter for this entity's skin image data
     */
    protected void releaseEntitySkin(Entity par1Entity)
    {
        super.releaseEntitySkin(par1Entity);

        if (entityList.contains(par1Entity))
        {
            if (par1Entity.isEntityAlive())
            {
                entitySpawnQueue.add(par1Entity);
            }
            else
            {
                entityList.remove(par1Entity);
            }
        }
    }

    /**
     * Add an ID to Entity mapping to entityHashSet
     */
    public void addEntityToWorld(int par1, Entity par2Entity)
    {
        Entity entity = getEntityByID(par1);

        if (entity != null)
        {
            setEntityDead(entity);
        }

        entityList.add(par2Entity);
        par2Entity.entityId = par1;

        if (!spawnEntityInWorld(par2Entity))
        {
            entitySpawnQueue.add(par2Entity);
        }

        entityHashSet.addKey(par1, par2Entity);
    }

    /**
     * Lookup and return an Entity based on its ID
     */
    public Entity getEntityByID(int par1)
    {
        return (Entity)entityHashSet.lookup(par1);
    }

    public Entity removeEntityFromWorld(int par1)
    {
        Entity entity = (Entity)entityHashSet.removeObject(par1);

        if (entity != null)
        {
            entityList.remove(entity);
            setEntityDead(entity);
        }

        return entity;
    }

    public boolean setBlockAndMetadataAndInvalidate(int par1, int par2, int par3, int par4, int par5)
    {
        invalidateBlockReceiveRegion(par1, par2, par3, par1, par2, par3);
        return super.setBlockAndMetadataWithNotify(par1, par2, par3, par4, par5);
    }

    /**
     * If on MP, sends a quitting packet.
     */
    public void sendQuittingDisconnectingPacket()
    {
        sendQueue.quitWithPacket(new Packet255KickDisconnect("Quitting"));
    }

    /**
     * Updates all weather states.
     */
    protected void updateWeather()
    {
        if (worldProvider.hasNoSky)
        {
            return;
        }

        if (lastLightningBolt > 0)
        {
            lastLightningBolt--;
        }

        prevRainingStrength = rainingStrength;

        if (worldInfo.isRaining())
        {
            rainingStrength += 0.01D;
        }
        else
        {
            rainingStrength -= 0.01D;
        }

        if (rainingStrength < 0.0F)
        {
            rainingStrength = 0.0F;
        }

        if (rainingStrength > 1.0F)
        {
            rainingStrength = 1.0F;
        }

        prevThunderingStrength = thunderingStrength;

        if (worldInfo.isThundering())
        {
            thunderingStrength += 0.01D;
        }
        else
        {
            thunderingStrength -= 0.01D;
        }

        if (thunderingStrength < 0.0F)
        {
            thunderingStrength = 0.0F;
        }

        if (thunderingStrength > 1.0F)
        {
            thunderingStrength = 1.0F;
        }
    }
}
