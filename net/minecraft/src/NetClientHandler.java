package net.minecraft.src;

import java.io.*;
import java.net.*;
import java.util.*;
import net.minecraft.client.Minecraft;

public class NetClientHandler extends NetHandler
{
    /** True if kicked or disconnected from the server. */
    private boolean disconnected;

    /** Reference to the NetworkManager object. */
    private NetworkManager netManager;
    public String field_1209_a;

    /** Reference to the Minecraft object. */
    private Minecraft mc;
    private WorldClient worldClient;
    private boolean field_1210_g;
    public MapStorage mapStorage;

    /** A HashMap of all player names and their player information objects */
    private Map playerInfoMap;

    /** An ArrayList of all the player names on the current server */
    public List playerNames;
    public int currentServerMaxPlayers;

    /** RNG. */
    Random rand;

    public NetClientHandler(Minecraft par1Minecraft, String par2Str, int par3) throws UnknownHostException, IOException
    {
        disconnected = false;
        field_1210_g = false;
        mapStorage = new MapStorage(null);
        playerInfoMap = new HashMap();
        playerNames = new ArrayList();
        currentServerMaxPlayers = 20;
        rand = new Random();
        mc = par1Minecraft;
        Socket socket = new Socket(InetAddress.getByName(par2Str), par3);
        netManager = new NetworkManager(socket, "Client", this);
    }

    /**
     * Processes the packets that have been read since the last call to this function.
     */
    public void processReadPackets()
    {
        if (!disconnected)
        {
            netManager.processReadPackets();
        }

        netManager.wakeThreads();
    }

    public void handleLogin(Packet1Login par1Packet1Login)
    {
        mc.playerController = new PlayerControllerMP(mc, this);
        mc.statFileWriter.readStat(StatList.joinMultiplayerStat, 1);
        worldClient = new WorldClient(this, new WorldSettings(0L, par1Packet1Login.serverMode, false, false, par1Packet1Login.terrainType, 0), par1Packet1Login.field_48170_e, par1Packet1Login.difficultySetting);
        worldClient.isRemote = true;
        mc.changeWorld1(worldClient, false);
        mc.thePlayer.dimension = par1Packet1Login.field_48170_e;
        mc.displayGuiScreen(new GuiDownloadTerrain(this));
        mc.thePlayer.entityId = par1Packet1Login.protocolVersion;
        currentServerMaxPlayers = par1Packet1Login.maxPlayers;
        ((PlayerControllerMP)mc.playerController).setCreative(par1Packet1Login.serverMode == 1);
    }

    public void handlePickupSpawn(Packet21PickupSpawn par1Packet21PickupSpawn)
    {
        double d = (double)par1Packet21PickupSpawn.xPosition / 32D;
        double d1 = (double)par1Packet21PickupSpawn.yPosition / 32D;
        double d2 = (double)par1Packet21PickupSpawn.zPosition / 32D;
        EntityItem entityitem = new EntityItem(worldClient, d, d1, d2, new ItemStack(par1Packet21PickupSpawn.itemID, par1Packet21PickupSpawn.count, par1Packet21PickupSpawn.itemDamage));
        entityitem.motionX = (double)par1Packet21PickupSpawn.rotation / 128D;
        entityitem.motionY = (double)par1Packet21PickupSpawn.pitch / 128D;
        entityitem.motionZ = (double)par1Packet21PickupSpawn.roll / 128D;
        entityitem.serverPosX = par1Packet21PickupSpawn.xPosition;
        entityitem.serverPosY = par1Packet21PickupSpawn.yPosition;
        entityitem.serverPosZ = par1Packet21PickupSpawn.zPosition;
        worldClient.addEntityToWorld(par1Packet21PickupSpawn.entityId, entityitem);
    }

    public void handleVehicleSpawn(Packet23VehicleSpawn par1Packet23VehicleSpawn)
    {
        double d = (double)par1Packet23VehicleSpawn.xPosition / 32D;
        double d1 = (double)par1Packet23VehicleSpawn.yPosition / 32D;
        double d2 = (double)par1Packet23VehicleSpawn.zPosition / 32D;
        Entity obj = null;

        if (par1Packet23VehicleSpawn.type == 10)
        {
            obj = new EntityMinecart(worldClient, d, d1, d2, 0);
        }
        else if (par1Packet23VehicleSpawn.type == 11)
        {
            obj = new EntityMinecart(worldClient, d, d1, d2, 1);
        }
        else if (par1Packet23VehicleSpawn.type == 12)
        {
            obj = new EntityMinecart(worldClient, d, d1, d2, 2);
        }
        else if (par1Packet23VehicleSpawn.type == 90)
        {
            obj = new EntityFishHook(worldClient, d, d1, d2);
        }
        else if (par1Packet23VehicleSpawn.type == 60)
        {
            obj = new EntityArrow(worldClient, d, d1, d2);
        }
        else if (par1Packet23VehicleSpawn.type == 61)
        {
            obj = new EntitySnowball(worldClient, d, d1, d2);
        }
        else if (par1Packet23VehicleSpawn.type == 65)
        {
            obj = new EntityEnderPearl(worldClient, d, d1, d2);
        }
        else if (par1Packet23VehicleSpawn.type == 72)
        {
            obj = new EntityEnderEye(worldClient, d, d1, d2);
        }
        else if (par1Packet23VehicleSpawn.type == 63)
        {
            obj = new EntityFireball(worldClient, d, d1, d2, (double)par1Packet23VehicleSpawn.speedX / 8000D, (double)par1Packet23VehicleSpawn.speedY / 8000D, (double)par1Packet23VehicleSpawn.speedZ / 8000D);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 64)
        {
            obj = new EntitySmallFireball(worldClient, d, d1, d2, (double)par1Packet23VehicleSpawn.speedX / 8000D, (double)par1Packet23VehicleSpawn.speedY / 8000D, (double)par1Packet23VehicleSpawn.speedZ / 8000D);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 62)
        {
            obj = new EntityEgg(worldClient, d, d1, d2);
        }
        else if (par1Packet23VehicleSpawn.type == 73)
        {
            obj = new EntityPotion(worldClient, d, d1, d2, par1Packet23VehicleSpawn.throwerEntityId);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 75)
        {
            obj = new EntityExpBottle(worldClient, d, d1, d2);
            par1Packet23VehicleSpawn.throwerEntityId = 0;
        }
        else if (par1Packet23VehicleSpawn.type == 1)
        {
            obj = new EntityBoat(worldClient, d, d1, d2);
        }
        else if (par1Packet23VehicleSpawn.type == 50)
        {
            obj = new EntityTNTPrimed(worldClient, d, d1, d2);
        }
        else if (par1Packet23VehicleSpawn.type == 51)
        {
            obj = new EntityEnderCrystal(worldClient, d, d1, d2);
        }
        else if (par1Packet23VehicleSpawn.type == 70)
        {
            obj = new EntityFallingSand(worldClient, d, d1, d2, Block.sand.blockID);
        }
        else if (par1Packet23VehicleSpawn.type == 71)
        {
            obj = new EntityFallingSand(worldClient, d, d1, d2, Block.gravel.blockID);
        }
        else if (par1Packet23VehicleSpawn.type == 74)
        {
            obj = new EntityFallingSand(worldClient, d, d1, d2, Block.dragonEgg.blockID);
        }

        if (obj != null)
        {
            obj.serverPosX = par1Packet23VehicleSpawn.xPosition;
            obj.serverPosY = par1Packet23VehicleSpawn.yPosition;
            obj.serverPosZ = par1Packet23VehicleSpawn.zPosition;
            obj.rotationYaw = 0.0F;
            obj.rotationPitch = 0.0F;
            Entity aentity[] = ((Entity)(obj)).getParts();

            if (aentity != null)
            {
                int i = par1Packet23VehicleSpawn.entityId - ((Entity)(obj)).entityId;

                for (int j = 0; j < aentity.length; j++)
                {
                    aentity[j].entityId += i;
                }
            }

            obj.entityId = par1Packet23VehicleSpawn.entityId;
            worldClient.addEntityToWorld(par1Packet23VehicleSpawn.entityId, ((Entity)(obj)));

            if (par1Packet23VehicleSpawn.throwerEntityId > 0)
            {
                if (par1Packet23VehicleSpawn.type == 60)
                {
                    Entity entity = getEntityByID(par1Packet23VehicleSpawn.throwerEntityId);

                    if (entity instanceof EntityLiving)
                    {
                        ((EntityArrow)obj).shootingEntity = (EntityLiving)entity;
                    }
                }

                ((Entity)(obj)).setVelocity((double)par1Packet23VehicleSpawn.speedX / 8000D, (double)par1Packet23VehicleSpawn.speedY / 8000D, (double)par1Packet23VehicleSpawn.speedZ / 8000D);
            }
        }
    }

    /**
     * Handle a entity experience orb packet.
     */
    public void handleEntityExpOrb(Packet26EntityExpOrb par1Packet26EntityExpOrb)
    {
        EntityXPOrb entityxporb = new EntityXPOrb(worldClient, par1Packet26EntityExpOrb.posX, par1Packet26EntityExpOrb.posY, par1Packet26EntityExpOrb.posZ, par1Packet26EntityExpOrb.xpValue);
        entityxporb.serverPosX = par1Packet26EntityExpOrb.posX;
        entityxporb.serverPosY = par1Packet26EntityExpOrb.posY;
        entityxporb.serverPosZ = par1Packet26EntityExpOrb.posZ;
        entityxporb.rotationYaw = 0.0F;
        entityxporb.rotationPitch = 0.0F;
        entityxporb.entityId = par1Packet26EntityExpOrb.entityId;
        worldClient.addEntityToWorld(par1Packet26EntityExpOrb.entityId, entityxporb);
    }

    /**
     * Handles weather packet
     */
    public void handleWeather(Packet71Weather par1Packet71Weather)
    {
        double d = (double)par1Packet71Weather.posX / 32D;
        double d1 = (double)par1Packet71Weather.posY / 32D;
        double d2 = (double)par1Packet71Weather.posZ / 32D;
        EntityLightningBolt entitylightningbolt = null;

        if (par1Packet71Weather.isLightningBolt == 1)
        {
            entitylightningbolt = new EntityLightningBolt(worldClient, d, d1, d2);
        }

        if (entitylightningbolt != null)
        {
            entitylightningbolt.serverPosX = par1Packet71Weather.posX;
            entitylightningbolt.serverPosY = par1Packet71Weather.posY;
            entitylightningbolt.serverPosZ = par1Packet71Weather.posZ;
            entitylightningbolt.rotationYaw = 0.0F;
            entitylightningbolt.rotationPitch = 0.0F;
            entitylightningbolt.entityId = par1Packet71Weather.entityID;
            worldClient.addWeatherEffect(entitylightningbolt);
        }
    }

    /**
     * Packet handler
     */
    public void handleEntityPainting(Packet25EntityPainting par1Packet25EntityPainting)
    {
        EntityPainting entitypainting = new EntityPainting(worldClient, par1Packet25EntityPainting.xPosition, par1Packet25EntityPainting.yPosition, par1Packet25EntityPainting.zPosition, par1Packet25EntityPainting.direction, par1Packet25EntityPainting.title);
        worldClient.addEntityToWorld(par1Packet25EntityPainting.entityId, entitypainting);
    }

    /**
     * Packet handler
     */
    public void handleEntityVelocity(Packet28EntityVelocity par1Packet28EntityVelocity)
    {
        Entity entity = getEntityByID(par1Packet28EntityVelocity.entityId);

        if (entity == null)
        {
            return;
        }
        else
        {
            entity.setVelocity((double)par1Packet28EntityVelocity.motionX / 8000D, (double)par1Packet28EntityVelocity.motionY / 8000D, (double)par1Packet28EntityVelocity.motionZ / 8000D);
            return;
        }
    }

    /**
     * Packet handler
     */
    public void handleEntityMetadata(Packet40EntityMetadata par1Packet40EntityMetadata)
    {
        Entity entity = getEntityByID(par1Packet40EntityMetadata.entityId);

        if (entity != null && par1Packet40EntityMetadata.getMetadata() != null)
        {
            entity.getDataWatcher().updateWatchedObjectsFromList(par1Packet40EntityMetadata.getMetadata());
        }
    }

    public void handleNamedEntitySpawn(Packet20NamedEntitySpawn par1Packet20NamedEntitySpawn)
    {
        double d = (double)par1Packet20NamedEntitySpawn.xPosition / 32D;
        double d1 = (double)par1Packet20NamedEntitySpawn.yPosition / 32D;
        double d2 = (double)par1Packet20NamedEntitySpawn.zPosition / 32D;
        float f = (float)(par1Packet20NamedEntitySpawn.rotation * 360) / 256F;
        float f1 = (float)(par1Packet20NamedEntitySpawn.pitch * 360) / 256F;
        EntityOtherPlayerMP entityotherplayermp = new EntityOtherPlayerMP(mc.theWorld, par1Packet20NamedEntitySpawn.name);
        entityotherplayermp.prevPosX = entityotherplayermp.lastTickPosX = entityotherplayermp.serverPosX = par1Packet20NamedEntitySpawn.xPosition;
        entityotherplayermp.prevPosY = entityotherplayermp.lastTickPosY = entityotherplayermp.serverPosY = par1Packet20NamedEntitySpawn.yPosition;
        entityotherplayermp.prevPosZ = entityotherplayermp.lastTickPosZ = entityotherplayermp.serverPosZ = par1Packet20NamedEntitySpawn.zPosition;
        int i = par1Packet20NamedEntitySpawn.currentItem;

        if (i == 0)
        {
            entityotherplayermp.inventory.mainInventory[entityotherplayermp.inventory.currentItem] = null;
        }
        else
        {
            entityotherplayermp.inventory.mainInventory[entityotherplayermp.inventory.currentItem] = new ItemStack(i, 1, 0);
        }

        entityotherplayermp.setPositionAndRotation(d, d1, d2, f, f1);
        worldClient.addEntityToWorld(par1Packet20NamedEntitySpawn.entityId, entityotherplayermp);
    }

    public void handleEntityTeleport(Packet34EntityTeleport par1Packet34EntityTeleport)
    {
        Entity entity = getEntityByID(par1Packet34EntityTeleport.entityId);

        if (entity == null)
        {
            return;
        }
        else
        {
            entity.serverPosX = par1Packet34EntityTeleport.xPosition;
            entity.serverPosY = par1Packet34EntityTeleport.yPosition;
            entity.serverPosZ = par1Packet34EntityTeleport.zPosition;
            double d = (double)entity.serverPosX / 32D;
            double d1 = (double)entity.serverPosY / 32D + 0.015625D;
            double d2 = (double)entity.serverPosZ / 32D;
            float f = (float)(par1Packet34EntityTeleport.yaw * 360) / 256F;
            float f1 = (float)(par1Packet34EntityTeleport.pitch * 360) / 256F;
            entity.setPositionAndRotation2(d, d1, d2, f, f1, 3);
            return;
        }
    }

    public void handleEntity(Packet30Entity par1Packet30Entity)
    {
        Entity entity = getEntityByID(par1Packet30Entity.entityId);

        if (entity == null)
        {
            return;
        }
        else
        {
            entity.serverPosX += par1Packet30Entity.xPosition;
            entity.serverPosY += par1Packet30Entity.yPosition;
            entity.serverPosZ += par1Packet30Entity.zPosition;
            double d = (double)entity.serverPosX / 32D;
            double d1 = (double)entity.serverPosY / 32D;
            double d2 = (double)entity.serverPosZ / 32D;
            float f = par1Packet30Entity.rotating ? (float)(par1Packet30Entity.yaw * 360) / 256F : entity.rotationYaw;
            float f1 = par1Packet30Entity.rotating ? (float)(par1Packet30Entity.pitch * 360) / 256F : entity.rotationPitch;
            entity.setPositionAndRotation2(d, d1, d2, f, f1, 3);
            return;
        }
    }

    public void handleEntityHeadRotation(Packet35EntityHeadRotation par1Packet35EntityHeadRotation)
    {
        Entity entity = getEntityByID(par1Packet35EntityHeadRotation.entityId);

        if (entity == null)
        {
            return;
        }
        else
        {
            float f = (float)(par1Packet35EntityHeadRotation.headRotationYaw * 360) / 256F;
            entity.func_48079_f(f);
            return;
        }
    }

    public void handleDestroyEntity(Packet29DestroyEntity par1Packet29DestroyEntity)
    {
        worldClient.removeEntityFromWorld(par1Packet29DestroyEntity.entityId);
    }

    public void handleFlying(Packet10Flying par1Packet10Flying)
    {
        EntityPlayerSP entityplayersp = mc.thePlayer;
        double d = ((EntityPlayer)(entityplayersp)).posX;
        double d1 = ((EntityPlayer)(entityplayersp)).posY;
        double d2 = ((EntityPlayer)(entityplayersp)).posZ;
        float f = ((EntityPlayer)(entityplayersp)).rotationYaw;
        float f1 = ((EntityPlayer)(entityplayersp)).rotationPitch;

        if (par1Packet10Flying.moving)
        {
            d = par1Packet10Flying.xPosition;
            d1 = par1Packet10Flying.yPosition;
            d2 = par1Packet10Flying.zPosition;
        }

        if (par1Packet10Flying.rotating)
        {
            f = par1Packet10Flying.yaw;
            f1 = par1Packet10Flying.pitch;
        }

        entityplayersp.ySize = 0.0F;
        entityplayersp.motionX = entityplayersp.motionY = entityplayersp.motionZ = 0.0D;
        entityplayersp.setPositionAndRotation(d, d1, d2, f, f1);
        par1Packet10Flying.xPosition = ((EntityPlayer)(entityplayersp)).posX;
        par1Packet10Flying.yPosition = ((EntityPlayer)(entityplayersp)).boundingBox.minY;
        par1Packet10Flying.zPosition = ((EntityPlayer)(entityplayersp)).posZ;
        par1Packet10Flying.stance = ((EntityPlayer)(entityplayersp)).posY;
        netManager.addToSendQueue(par1Packet10Flying);

        if (!field_1210_g)
        {
            mc.thePlayer.prevPosX = mc.thePlayer.posX;
            mc.thePlayer.prevPosY = mc.thePlayer.posY;
            mc.thePlayer.prevPosZ = mc.thePlayer.posZ;
            field_1210_g = true;
            mc.displayGuiScreen(null);
        }
    }

    public void handlePreChunk(Packet50PreChunk par1Packet50PreChunk)
    {
        worldClient.doPreChunk(par1Packet50PreChunk.xPosition, par1Packet50PreChunk.yPosition, par1Packet50PreChunk.mode);
    }

    public void handleMultiBlockChange(Packet52MultiBlockChange par1Packet52MultiBlockChange)
    {
        int i = par1Packet52MultiBlockChange.xPosition * 16;
        int j = par1Packet52MultiBlockChange.zPosition * 16;

        if (par1Packet52MultiBlockChange.metadataArray == null)
        {
            return;
        }

        DataInputStream datainputstream = new DataInputStream(new ByteArrayInputStream(par1Packet52MultiBlockChange.metadataArray));

        try
        {
            for (int k = 0; k < par1Packet52MultiBlockChange.size; k++)
            {
                short word0 = datainputstream.readShort();
                short word1 = datainputstream.readShort();
                int l = (word1 & 0xfff) >> 4;
                int i1 = word1 & 0xf;
                int j1 = word0 >> 12 & 0xf;
                int k1 = word0 >> 8 & 0xf;
                int l1 = word0 & 0xff;
                worldClient.setBlockAndMetadataAndInvalidate(j1 + i, l1, k1 + j, l, i1);
            }
        }
        catch (IOException ioexception) { }
    }

    public void func_48487_a(Packet51MapChunk par1Packet51MapChunk)
    {
        worldClient.invalidateBlockReceiveRegion(par1Packet51MapChunk.xCh << 4, 0, par1Packet51MapChunk.zCh << 4, (par1Packet51MapChunk.xCh << 4) + 15, 256, (par1Packet51MapChunk.zCh << 4) + 15);
        Chunk chunk = worldClient.getChunkFromChunkCoords(par1Packet51MapChunk.xCh, par1Packet51MapChunk.zCh);

        if (par1Packet51MapChunk.includeInitialize && chunk == null)
        {
            worldClient.doPreChunk(par1Packet51MapChunk.xCh, par1Packet51MapChunk.zCh, true);
            chunk = worldClient.getChunkFromChunkCoords(par1Packet51MapChunk.xCh, par1Packet51MapChunk.zCh);
        }

        if (chunk != null)
        {
            chunk.func_48494_a(par1Packet51MapChunk.chunkData, par1Packet51MapChunk.yChMin, par1Packet51MapChunk.yChMax, par1Packet51MapChunk.includeInitialize);
            worldClient.markBlocksDirty(par1Packet51MapChunk.xCh << 4, 0, par1Packet51MapChunk.zCh << 4, (par1Packet51MapChunk.xCh << 4) + 15, 256, (par1Packet51MapChunk.zCh << 4) + 15);

            if (!par1Packet51MapChunk.includeInitialize || !(worldClient.worldProvider instanceof WorldProviderSurface))
            {
                chunk.resetRelightChecks();
            }
        }
    }

    public void handleBlockChange(Packet53BlockChange par1Packet53BlockChange)
    {
        worldClient.setBlockAndMetadataAndInvalidate(par1Packet53BlockChange.xPosition, par1Packet53BlockChange.yPosition, par1Packet53BlockChange.zPosition, par1Packet53BlockChange.type, par1Packet53BlockChange.metadata);
    }

    public void handleKickDisconnect(Packet255KickDisconnect par1Packet255KickDisconnect)
    {
        netManager.networkShutdown("disconnect.kicked", new Object[0]);
        disconnected = true;
        mc.changeWorld1(null, false);
        mc.displayGuiScreen(new GuiDisconnected("disconnect.disconnected", "disconnect.genericReason", new Object[]
                {
                    par1Packet255KickDisconnect.reason
                }));
    }

    public void handleErrorMessage(String par1Str, Object par2ArrayOfObj[])
    {
        if (disconnected)
        {
            return;
        }
        else
        {
            disconnected = true;
            mc.changeWorld1(null, false);
            mc.displayGuiScreen(new GuiDisconnected("disconnect.lost", par1Str, par2ArrayOfObj));
            return;
        }
    }

    public void quitWithPacket(Packet par1Packet)
    {
        if (disconnected)
        {
            return;
        }
        else
        {
            netManager.addToSendQueue(par1Packet);
            netManager.serverShutdown();
            return;
        }
    }

    /**
     * Adds the packet to the send queue
     */
    public void addToSendQueue(Packet par1Packet)
    {
        if (disconnected)
        {
            return;
        }
        else
        {
            netManager.addToSendQueue(par1Packet);
            return;
        }
    }

    public void handleCollect(Packet22Collect par1Packet22Collect)
    {
        Entity entity = getEntityByID(par1Packet22Collect.collectedEntityId);
        Object obj = (EntityLiving)getEntityByID(par1Packet22Collect.collectorEntityId);

        if (obj == null)
        {
            obj = mc.thePlayer;
        }

        if (entity != null)
        {
            if (entity instanceof EntityXPOrb)
            {
                worldClient.playSoundAtEntity(entity, "random.orb", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            }
            else
            {
                worldClient.playSoundAtEntity(entity, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            }

            mc.effectRenderer.addEffect(new EntityPickupFX(mc.theWorld, entity, ((Entity)(obj)), -0.5F));
            worldClient.removeEntityFromWorld(par1Packet22Collect.collectedEntityId);
        }
    }

    public void handleChat(Packet3Chat par1Packet3Chat)
    {
        mc.ingameGUI.addChatMessage(par1Packet3Chat.message);
    }

    public void handleAnimation(Packet18Animation par1Packet18Animation)
    {
        Entity entity = getEntityByID(par1Packet18Animation.entityId);

        if (entity == null)
        {
            return;
        }

        if (par1Packet18Animation.animate == 1)
        {
            EntityPlayer entityplayer = (EntityPlayer)entity;
            entityplayer.swingItem();
        }
        else if (par1Packet18Animation.animate == 2)
        {
            entity.performHurtAnimation();
        }
        else if (par1Packet18Animation.animate == 3)
        {
            EntityPlayer entityplayer1 = (EntityPlayer)entity;
            entityplayer1.wakeUpPlayer(false, false, false);
        }
        else if (par1Packet18Animation.animate == 4)
        {
            EntityPlayer entityplayer2 = (EntityPlayer)entity;
            entityplayer2.func_6420_o();
        }
        else if (par1Packet18Animation.animate == 6)
        {
            mc.effectRenderer.addEffect(new EntityCrit2FX(mc.theWorld, entity));
        }
        else if (par1Packet18Animation.animate == 7)
        {
            EntityCrit2FX entitycrit2fx = new EntityCrit2FX(mc.theWorld, entity, "magicCrit");
            mc.effectRenderer.addEffect(entitycrit2fx);
        }
        else if (par1Packet18Animation.animate == 5)
        {
            if (!(entity instanceof EntityOtherPlayerMP));
        }
    }

    public void handleSleep(Packet17Sleep par1Packet17Sleep)
    {
        Entity entity = getEntityByID(par1Packet17Sleep.entityID);

        if (entity == null)
        {
            return;
        }

        if (par1Packet17Sleep.field_22046_e == 0)
        {
            EntityPlayer entityplayer = (EntityPlayer)entity;
            entityplayer.sleepInBedAt(par1Packet17Sleep.bedX, par1Packet17Sleep.bedY, par1Packet17Sleep.bedZ);
        }
    }

    public void handleHandshake(Packet2Handshake par1Packet2Handshake)
    {
        boolean flag = true;
        String s = par1Packet2Handshake.username;

        if (s == null || s.trim().length() == 0)
        {
            flag = false;
        }
        else if (!s.equals("-"))
        {
            try
            {
                Long.parseLong(s, 16);
            }
            catch (NumberFormatException numberformatexception)
            {
                flag = false;
            }
        }

        if (!flag)
        {
            netManager.networkShutdown("disconnect.genericReason", new Object[]
                    {
                        "The server responded with an invalid server key"
                    });
        }
        else if (par1Packet2Handshake.username.equals("-"))
        {
            addToSendQueue(new Packet1Login(mc.session.username, 29));
        }
        else
        {
            try
            {
                URL url = new URL((new StringBuilder()).append("http://session.minecraft.net/game/joinserver.jsp?user=").append(mc.session.username).append("&sessionId=").append(mc.session.sessionId).append("&serverId=").append(par1Packet2Handshake.username).toString());
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(url.openStream()));
                String s1 = bufferedreader.readLine();
                bufferedreader.close();

                if (s1.equalsIgnoreCase("ok"))
                {
                    addToSendQueue(new Packet1Login(mc.session.username, 29));
                }
                else
                {
                    netManager.networkShutdown("disconnect.loginFailedInfo", new Object[]
                            {
                                s1
                            });
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                netManager.networkShutdown("disconnect.genericReason", new Object[]
                        {
                            (new StringBuilder()).append("Internal client error: ").append(exception.toString()).toString()
                        });
            }
        }
    }

    /**
     * Disconnects the network connection.
     */
    public void disconnect()
    {
        disconnected = true;
        netManager.wakeThreads();
        netManager.networkShutdown("disconnect.closed", new Object[0]);
    }

    public void handleMobSpawn(Packet24MobSpawn par1Packet24MobSpawn)
    {
        double d = (double)par1Packet24MobSpawn.xPosition / 32D;
        double d1 = (double)par1Packet24MobSpawn.yPosition / 32D;
        double d2 = (double)par1Packet24MobSpawn.zPosition / 32D;
        float f = (float)(par1Packet24MobSpawn.yaw * 360) / 256F;
        float f1 = (float)(par1Packet24MobSpawn.pitch * 360) / 256F;
        EntityLiving entityliving = (EntityLiving)EntityList.createEntityByID(par1Packet24MobSpawn.type, mc.theWorld);
        entityliving.serverPosX = par1Packet24MobSpawn.xPosition;
        entityliving.serverPosY = par1Packet24MobSpawn.yPosition;
        entityliving.serverPosZ = par1Packet24MobSpawn.zPosition;
        entityliving.rotationYawHead = (float)(par1Packet24MobSpawn.field_48169_h * 360) / 256F;
        Entity aentity[] = entityliving.getParts();

        if (aentity != null)
        {
            int i = par1Packet24MobSpawn.entityId - entityliving.entityId;

            for (int j = 0; j < aentity.length; j++)
            {
                aentity[j].entityId += i;
            }
        }

        entityliving.entityId = par1Packet24MobSpawn.entityId;
        entityliving.setPositionAndRotation(d, d1, d2, f, f1);
        worldClient.addEntityToWorld(par1Packet24MobSpawn.entityId, entityliving);
        List list = par1Packet24MobSpawn.getMetadata();

        if (list != null)
        {
            entityliving.getDataWatcher().updateWatchedObjectsFromList(list);
        }
    }

    public void handleUpdateTime(Packet4UpdateTime par1Packet4UpdateTime)
    {
        mc.theWorld.setWorldTime(par1Packet4UpdateTime.time);
    }

    public void handleSpawnPosition(Packet6SpawnPosition par1Packet6SpawnPosition)
    {
        mc.thePlayer.setSpawnChunk(new ChunkCoordinates(par1Packet6SpawnPosition.xPosition, par1Packet6SpawnPosition.yPosition, par1Packet6SpawnPosition.zPosition));
        mc.theWorld.getWorldInfo().setSpawnPosition(par1Packet6SpawnPosition.xPosition, par1Packet6SpawnPosition.yPosition, par1Packet6SpawnPosition.zPosition);
    }

    /**
     * Packet handler
     */
    public void handleAttachEntity(Packet39AttachEntity par1Packet39AttachEntity)
    {
        Object obj = getEntityByID(par1Packet39AttachEntity.entityId);
        Entity entity = getEntityByID(par1Packet39AttachEntity.vehicleEntityId);

        if (par1Packet39AttachEntity.entityId == mc.thePlayer.entityId)
        {
            obj = mc.thePlayer;
        }

        if (obj == null)
        {
            return;
        }
        else
        {
            ((Entity)(obj)).mountEntity(entity);
            return;
        }
    }

    /**
     * Packet handler
     */
    public void handleEntityStatus(Packet38EntityStatus par1Packet38EntityStatus)
    {
        Entity entity = getEntityByID(par1Packet38EntityStatus.entityId);

        if (entity != null)
        {
            entity.handleHealthUpdate(par1Packet38EntityStatus.entityStatus);
        }
    }

    private Entity getEntityByID(int par1)
    {
        if (par1 == mc.thePlayer.entityId)
        {
            return mc.thePlayer;
        }
        else
        {
            return worldClient.getEntityByID(par1);
        }
    }

    /**
     * Recieves player health from the server and then proceeds to set it locally on the client.
     */
    public void handleUpdateHealth(Packet8UpdateHealth par1Packet8UpdateHealth)
    {
        mc.thePlayer.setHealth(par1Packet8UpdateHealth.healthMP);
        mc.thePlayer.getFoodStats().setFoodLevel(par1Packet8UpdateHealth.food);
        mc.thePlayer.getFoodStats().setFoodSaturationLevel(par1Packet8UpdateHealth.foodSaturation);
    }

    /**
     * Handle an experience packet.
     */
    public void handleExperience(Packet43Experience par1Packet43Experience)
    {
        mc.thePlayer.setXPStats(par1Packet43Experience.experience, par1Packet43Experience.experienceTotal, par1Packet43Experience.experienceLevel);
    }

    /**
     * respawns the player
     */
    public void handleRespawn(Packet9Respawn par1Packet9Respawn)
    {
        if (par1Packet9Respawn.respawnDimension != mc.thePlayer.dimension)
        {
            field_1210_g = false;
            worldClient = new WorldClient(this, new WorldSettings(0L, par1Packet9Respawn.creativeMode, false, false, par1Packet9Respawn.terrainType, 0), par1Packet9Respawn.respawnDimension, par1Packet9Respawn.difficulty);
            worldClient.isRemote = true;
            mc.changeWorld1(worldClient, false);
            mc.thePlayer.dimension = par1Packet9Respawn.respawnDimension;
            mc.displayGuiScreen(new GuiDownloadTerrain(this));
        }

        mc.respawn(true, par1Packet9Respawn.respawnDimension, false);
        ((PlayerControllerMP)mc.playerController).setCreative(par1Packet9Respawn.creativeMode == 1);
    }

    public void handleExplosion(Packet60Explosion par1Packet60Explosion)
    {
        Explosion explosion = new Explosion(mc.theWorld, null, par1Packet60Explosion.explosionX, par1Packet60Explosion.explosionY, par1Packet60Explosion.explosionZ, par1Packet60Explosion.explosionSize);
        explosion.destroyedBlockPositions = par1Packet60Explosion.destroyedBlockPositions;
        explosion.doExplosionB(true);
    }

    public void handleOpenWindow(Packet100OpenWindow par1Packet100OpenWindow)
    {
        EntityPlayerSP entityplayersp = mc.thePlayer;

        switch (par1Packet100OpenWindow.inventoryType)
        {
            case 0:
                entityplayersp.displayGUIChest(new InventoryBasic(par1Packet100OpenWindow.windowTitle, par1Packet100OpenWindow.slotsCount));
                entityplayersp.craftingInventory.windowId = par1Packet100OpenWindow.windowId;
                break;

            case 2:
                entityplayersp.displayGUIFurnace(new TileEntityFurnace());
                entityplayersp.craftingInventory.windowId = par1Packet100OpenWindow.windowId;
                break;

            case 5:
                entityplayersp.displayGUIBrewingStand(new TileEntityBrewingStand());
                entityplayersp.craftingInventory.windowId = par1Packet100OpenWindow.windowId;
                break;

            case 3:
                entityplayersp.displayGUIDispenser(new TileEntityDispenser());
                entityplayersp.craftingInventory.windowId = par1Packet100OpenWindow.windowId;
                break;

            case 1:
                entityplayersp.displayWorkbenchGUI(MathHelper.floor_double(entityplayersp.posX), MathHelper.floor_double(entityplayersp.posY), MathHelper.floor_double(entityplayersp.posZ));
                entityplayersp.craftingInventory.windowId = par1Packet100OpenWindow.windowId;
                break;

            case 4:
                entityplayersp.displayGUIEnchantment(MathHelper.floor_double(entityplayersp.posX), MathHelper.floor_double(entityplayersp.posY), MathHelper.floor_double(entityplayersp.posZ));
                entityplayersp.craftingInventory.windowId = par1Packet100OpenWindow.windowId;
                break;
        }
    }

    public void handleSetSlot(Packet103SetSlot par1Packet103SetSlot)
    {
        EntityPlayerSP entityplayersp = mc.thePlayer;

        if (par1Packet103SetSlot.windowId == -1)
        {
            ((EntityPlayer)(entityplayersp)).inventory.setItemStack(par1Packet103SetSlot.myItemStack);
        }
        else if (par1Packet103SetSlot.windowId == 0 && par1Packet103SetSlot.itemSlot >= 36 && par1Packet103SetSlot.itemSlot < 45)
        {
            ItemStack itemstack = ((EntityPlayer)(entityplayersp)).inventorySlots.getSlot(par1Packet103SetSlot.itemSlot).getStack();

            if (par1Packet103SetSlot.myItemStack != null && (itemstack == null || itemstack.stackSize < par1Packet103SetSlot.myItemStack.stackSize))
            {
                par1Packet103SetSlot.myItemStack.animationsToGo = 5;
            }

            ((EntityPlayer)(entityplayersp)).inventorySlots.putStackInSlot(par1Packet103SetSlot.itemSlot, par1Packet103SetSlot.myItemStack);
        }
        else if (par1Packet103SetSlot.windowId == ((EntityPlayer)(entityplayersp)).craftingInventory.windowId)
        {
            ((EntityPlayer)(entityplayersp)).craftingInventory.putStackInSlot(par1Packet103SetSlot.itemSlot, par1Packet103SetSlot.myItemStack);
        }
    }

    public void handleTransaction(Packet106Transaction par1Packet106Transaction)
    {
        Container container = null;
        EntityPlayerSP entityplayersp = mc.thePlayer;

        if (par1Packet106Transaction.windowId == 0)
        {
            container = ((EntityPlayer)(entityplayersp)).inventorySlots;
        }
        else if (par1Packet106Transaction.windowId == ((EntityPlayer)(entityplayersp)).craftingInventory.windowId)
        {
            container = ((EntityPlayer)(entityplayersp)).craftingInventory;
        }

        if (container != null)
        {
            if (par1Packet106Transaction.accepted)
            {
                container.func_20113_a(par1Packet106Transaction.shortWindowId);
            }
            else
            {
                container.func_20110_b(par1Packet106Transaction.shortWindowId);
                addToSendQueue(new Packet106Transaction(par1Packet106Transaction.windowId, par1Packet106Transaction.shortWindowId, true));
            }
        }
    }

    public void handleWindowItems(Packet104WindowItems par1Packet104WindowItems)
    {
        EntityPlayerSP entityplayersp = mc.thePlayer;

        if (par1Packet104WindowItems.windowId == 0)
        {
            ((EntityPlayer)(entityplayersp)).inventorySlots.putStacksInSlots(par1Packet104WindowItems.itemStack);
        }
        else if (par1Packet104WindowItems.windowId == ((EntityPlayer)(entityplayersp)).craftingInventory.windowId)
        {
            ((EntityPlayer)(entityplayersp)).craftingInventory.putStacksInSlots(par1Packet104WindowItems.itemStack);
        }
    }

    /**
     * Updates Client side signs
     */
    public void handleUpdateSign(Packet130UpdateSign par1Packet130UpdateSign)
    {
        if (mc.theWorld.blockExists(par1Packet130UpdateSign.xPosition, par1Packet130UpdateSign.yPosition, par1Packet130UpdateSign.zPosition))
        {
            TileEntity tileentity = mc.theWorld.getBlockTileEntity(par1Packet130UpdateSign.xPosition, par1Packet130UpdateSign.yPosition, par1Packet130UpdateSign.zPosition);

            if (tileentity instanceof TileEntitySign)
            {
                TileEntitySign tileentitysign = (TileEntitySign)tileentity;

                if (tileentitysign.func_50007_a())
                {
                    for (int i = 0; i < 4; i++)
                    {
                        tileentitysign.signText[i] = par1Packet130UpdateSign.signLines[i];
                    }

                    tileentitysign.onInventoryChanged();
                }
            }
        }
    }

    public void handleTileEntityData(Packet132TileEntityData par1Packet132TileEntityData)
    {
        if (mc.theWorld.blockExists(par1Packet132TileEntityData.xPosition, par1Packet132TileEntityData.yPosition, par1Packet132TileEntityData.zPosition))
        {
            TileEntity tileentity = mc.theWorld.getBlockTileEntity(par1Packet132TileEntityData.xPosition, par1Packet132TileEntityData.yPosition, par1Packet132TileEntityData.zPosition);

            if (tileentity != null && par1Packet132TileEntityData.actionType == 1 && (tileentity instanceof TileEntityMobSpawner))
            {
                ((TileEntityMobSpawner)tileentity).setMobID(EntityList.getStringFromID(par1Packet132TileEntityData.customParam1));
            }
        }
    }

    public void handleUpdateProgressbar(Packet105UpdateProgressbar par1Packet105UpdateProgressbar)
    {
        EntityPlayerSP entityplayersp = mc.thePlayer;
        registerPacket(par1Packet105UpdateProgressbar);

        if (((EntityPlayer)(entityplayersp)).craftingInventory != null && ((EntityPlayer)(entityplayersp)).craftingInventory.windowId == par1Packet105UpdateProgressbar.windowId)
        {
            ((EntityPlayer)(entityplayersp)).craftingInventory.updateProgressBar(par1Packet105UpdateProgressbar.progressBar, par1Packet105UpdateProgressbar.progressBarValue);
        }
    }

    public void handlePlayerInventory(Packet5PlayerInventory par1Packet5PlayerInventory)
    {
        Entity entity = getEntityByID(par1Packet5PlayerInventory.entityID);

        if (entity != null)
        {
            entity.outfitWithItem(par1Packet5PlayerInventory.slot, par1Packet5PlayerInventory.itemID, par1Packet5PlayerInventory.itemDamage);
        }
    }

    public void handleCloseWindow(Packet101CloseWindow par1Packet101CloseWindow)
    {
        mc.thePlayer.closeScreen();
    }

    public void handlePlayNoteBlock(Packet54PlayNoteBlock par1Packet54PlayNoteBlock)
    {
        mc.theWorld.playNoteAt(par1Packet54PlayNoteBlock.xLocation, par1Packet54PlayNoteBlock.yLocation, par1Packet54PlayNoteBlock.zLocation, par1Packet54PlayNoteBlock.instrumentType, par1Packet54PlayNoteBlock.pitch);
    }

    public void handleBed(Packet70Bed par1Packet70Bed)
    {
        EntityPlayerSP entityplayersp = mc.thePlayer;
        int i = par1Packet70Bed.bedState;

        if (i >= 0 && i < Packet70Bed.bedChat.length && Packet70Bed.bedChat[i] != null)
        {
            entityplayersp.addChatMessage(Packet70Bed.bedChat[i]);
        }

        if (i == 1)
        {
            worldClient.getWorldInfo().setRaining(true);
            worldClient.setRainStrength(1.0F);
        }
        else if (i == 2)
        {
            worldClient.getWorldInfo().setRaining(false);
            worldClient.setRainStrength(0.0F);
        }
        else if (i == 3)
        {
            ((PlayerControllerMP)mc.playerController).setCreative(par1Packet70Bed.gameMode == 1);
        }
        else if (i == 4)
        {
            mc.displayGuiScreen(new GuiWinGame());
        }
    }

    /**
     * Contains logic for handling packets containing arbitrary unique item data. Currently this is only for maps.
     */
    public void handleMapData(Packet131MapData par1Packet131MapData)
    {
        if (par1Packet131MapData.itemID == Item.map.shiftedIndex)
        {
            ItemMap.getMPMapData(par1Packet131MapData.uniqueID, mc.theWorld).func_28171_a(par1Packet131MapData.itemData);
        }
        else
        {
            System.out.println((new StringBuilder()).append("Unknown itemid: ").append(par1Packet131MapData.uniqueID).toString());
        }
    }

    public void handleDoorChange(Packet61DoorChange par1Packet61DoorChange)
    {
        mc.theWorld.playAuxSFX(par1Packet61DoorChange.sfxID, par1Packet61DoorChange.posX, par1Packet61DoorChange.posY, par1Packet61DoorChange.posZ, par1Packet61DoorChange.auxData);
    }

    /**
     * runs registerPacket on the given Packet200Statistic
     */
    public void handleStatistic(Packet200Statistic par1Packet200Statistic)
    {
        ((EntityClientPlayerMP)mc.thePlayer).incrementStat(StatList.getOneShotStat(par1Packet200Statistic.statisticId), par1Packet200Statistic.amount);
    }

    /**
     * Handle an entity effect packet.
     */
    public void handleEntityEffect(Packet41EntityEffect par1Packet41EntityEffect)
    {
        Entity entity = getEntityByID(par1Packet41EntityEffect.entityId);

        if (entity == null || !(entity instanceof EntityLiving))
        {
            return;
        }
        else
        {
            ((EntityLiving)entity).addPotionEffect(new PotionEffect(par1Packet41EntityEffect.effectId, par1Packet41EntityEffect.duration, par1Packet41EntityEffect.effectAmp));
            return;
        }
    }

    /**
     * Handle a remove entity effect packet.
     */
    public void handleRemoveEntityEffect(Packet42RemoveEntityEffect par1Packet42RemoveEntityEffect)
    {
        Entity entity = getEntityByID(par1Packet42RemoveEntityEffect.entityId);

        if (entity == null || !(entity instanceof EntityLiving))
        {
            return;
        }
        else
        {
            ((EntityLiving)entity).removePotionEffect(par1Packet42RemoveEntityEffect.effectId);
            return;
        }
    }

    /**
     * determine if it is a server handler
     */
    public boolean isServerHandler()
    {
        return false;
    }

    /**
     * Handle a player information packet.
     */
    public void handlePlayerInfo(Packet201PlayerInfo par1Packet201PlayerInfo)
    {
        GuiPlayerInfo guiplayerinfo = (GuiPlayerInfo)playerInfoMap.get(par1Packet201PlayerInfo.playerName);

        if (guiplayerinfo == null && par1Packet201PlayerInfo.isConnected)
        {
            guiplayerinfo = new GuiPlayerInfo(par1Packet201PlayerInfo.playerName);
            playerInfoMap.put(par1Packet201PlayerInfo.playerName, guiplayerinfo);
            playerNames.add(guiplayerinfo);
        }

        if (guiplayerinfo != null && !par1Packet201PlayerInfo.isConnected)
        {
            playerInfoMap.remove(par1Packet201PlayerInfo.playerName);
            playerNames.remove(guiplayerinfo);
        }

        if (par1Packet201PlayerInfo.isConnected && guiplayerinfo != null)
        {
            guiplayerinfo.responseTime = par1Packet201PlayerInfo.ping;
        }
    }

    /**
     * Handle a keep alive packet.
     */
    public void handleKeepAlive(Packet0KeepAlive par1Packet0KeepAlive)
    {
        addToSendQueue(new Packet0KeepAlive(par1Packet0KeepAlive.randomId));
    }

    public void func_50100_a(Packet202PlayerAbilities par1Packet202PlayerAbilities)
    {
        EntityPlayerSP entityplayersp = mc.thePlayer;
        ((EntityPlayer)(entityplayersp)).capabilities.isFlying = par1Packet202PlayerAbilities.field_50070_b;
        ((EntityPlayer)(entityplayersp)).capabilities.isCreativeMode = par1Packet202PlayerAbilities.field_50069_d;
        ((EntityPlayer)(entityplayersp)).capabilities.disableDamage = par1Packet202PlayerAbilities.field_50072_a;
        ((EntityPlayer)(entityplayersp)).capabilities.allowFlying = par1Packet202PlayerAbilities.field_50071_c;
    }
}
