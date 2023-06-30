package net.minecraft.src;

public abstract class NetHandler
{
    public NetHandler()
    {
    }

    /**
     * determine if it is a server handler
     */
    public abstract boolean isServerHandler();

    public void func_48487_a(Packet51MapChunk packet51mapchunk)
    {
    }

    public void registerPacket(Packet packet)
    {
    }

    public void handleErrorMessage(String s, Object aobj[])
    {
    }

    public void handleKickDisconnect(Packet255KickDisconnect par1Packet255KickDisconnect)
    {
        registerPacket(par1Packet255KickDisconnect);
    }

    public void handleLogin(Packet1Login par1Packet1Login)
    {
        registerPacket(par1Packet1Login);
    }

    public void handleFlying(Packet10Flying par1Packet10Flying)
    {
        registerPacket(par1Packet10Flying);
    }

    public void handleMultiBlockChange(Packet52MultiBlockChange par1Packet52MultiBlockChange)
    {
        registerPacket(par1Packet52MultiBlockChange);
    }

    public void handleBlockDig(Packet14BlockDig par1Packet14BlockDig)
    {
        registerPacket(par1Packet14BlockDig);
    }

    public void handleBlockChange(Packet53BlockChange par1Packet53BlockChange)
    {
        registerPacket(par1Packet53BlockChange);
    }

    public void handlePreChunk(Packet50PreChunk par1Packet50PreChunk)
    {
        registerPacket(par1Packet50PreChunk);
    }

    public void handleNamedEntitySpawn(Packet20NamedEntitySpawn par1Packet20NamedEntitySpawn)
    {
        registerPacket(par1Packet20NamedEntitySpawn);
    }

    public void handleEntity(Packet30Entity par1Packet30Entity)
    {
        registerPacket(par1Packet30Entity);
    }

    public void handleEntityTeleport(Packet34EntityTeleport par1Packet34EntityTeleport)
    {
        registerPacket(par1Packet34EntityTeleport);
    }

    public void handlePlace(Packet15Place par1Packet15Place)
    {
        registerPacket(par1Packet15Place);
    }

    public void handleBlockItemSwitch(Packet16BlockItemSwitch par1Packet16BlockItemSwitch)
    {
        registerPacket(par1Packet16BlockItemSwitch);
    }

    public void handleDestroyEntity(Packet29DestroyEntity par1Packet29DestroyEntity)
    {
        registerPacket(par1Packet29DestroyEntity);
    }

    public void handlePickupSpawn(Packet21PickupSpawn par1Packet21PickupSpawn)
    {
        registerPacket(par1Packet21PickupSpawn);
    }

    public void handleCollect(Packet22Collect par1Packet22Collect)
    {
        registerPacket(par1Packet22Collect);
    }

    public void handleChat(Packet3Chat par1Packet3Chat)
    {
        registerPacket(par1Packet3Chat);
    }

    public void handleVehicleSpawn(Packet23VehicleSpawn par1Packet23VehicleSpawn)
    {
        registerPacket(par1Packet23VehicleSpawn);
    }

    public void handleAnimation(Packet18Animation par1Packet18Animation)
    {
        registerPacket(par1Packet18Animation);
    }

    /**
     * runs registerPacket on the given Packet19EntityAction
     */
    public void handleEntityAction(Packet19EntityAction par1Packet19EntityAction)
    {
        registerPacket(par1Packet19EntityAction);
    }

    public void handleHandshake(Packet2Handshake par1Packet2Handshake)
    {
        registerPacket(par1Packet2Handshake);
    }

    public void handleMobSpawn(Packet24MobSpawn par1Packet24MobSpawn)
    {
        registerPacket(par1Packet24MobSpawn);
    }

    public void handleUpdateTime(Packet4UpdateTime par1Packet4UpdateTime)
    {
        registerPacket(par1Packet4UpdateTime);
    }

    public void handleSpawnPosition(Packet6SpawnPosition par1Packet6SpawnPosition)
    {
        registerPacket(par1Packet6SpawnPosition);
    }

    /**
     * Packet handler
     */
    public void handleEntityVelocity(Packet28EntityVelocity par1Packet28EntityVelocity)
    {
        registerPacket(par1Packet28EntityVelocity);
    }

    /**
     * Packet handler
     */
    public void handleEntityMetadata(Packet40EntityMetadata par1Packet40EntityMetadata)
    {
        registerPacket(par1Packet40EntityMetadata);
    }

    /**
     * Packet handler
     */
    public void handleAttachEntity(Packet39AttachEntity par1Packet39AttachEntity)
    {
        registerPacket(par1Packet39AttachEntity);
    }

    public void handleUseEntity(Packet7UseEntity par1Packet7UseEntity)
    {
        registerPacket(par1Packet7UseEntity);
    }

    /**
     * Packet handler
     */
    public void handleEntityStatus(Packet38EntityStatus par1Packet38EntityStatus)
    {
        registerPacket(par1Packet38EntityStatus);
    }

    /**
     * Recieves player health from the server and then proceeds to set it locally on the client.
     */
    public void handleUpdateHealth(Packet8UpdateHealth par1Packet8UpdateHealth)
    {
        registerPacket(par1Packet8UpdateHealth);
    }

    /**
     * respawns the player
     */
    public void handleRespawn(Packet9Respawn par1Packet9Respawn)
    {
        registerPacket(par1Packet9Respawn);
    }

    public void handleExplosion(Packet60Explosion par1Packet60Explosion)
    {
        registerPacket(par1Packet60Explosion);
    }

    public void handleOpenWindow(Packet100OpenWindow par1Packet100OpenWindow)
    {
        registerPacket(par1Packet100OpenWindow);
    }

    public void handleCloseWindow(Packet101CloseWindow par1Packet101CloseWindow)
    {
        registerPacket(par1Packet101CloseWindow);
    }

    public void handleWindowClick(Packet102WindowClick par1Packet102WindowClick)
    {
        registerPacket(par1Packet102WindowClick);
    }

    public void handleSetSlot(Packet103SetSlot par1Packet103SetSlot)
    {
        registerPacket(par1Packet103SetSlot);
    }

    public void handleWindowItems(Packet104WindowItems par1Packet104WindowItems)
    {
        registerPacket(par1Packet104WindowItems);
    }

    /**
     * Updates Client side signs
     */
    public void handleUpdateSign(Packet130UpdateSign par1Packet130UpdateSign)
    {
        registerPacket(par1Packet130UpdateSign);
    }

    public void handleUpdateProgressbar(Packet105UpdateProgressbar par1Packet105UpdateProgressbar)
    {
        registerPacket(par1Packet105UpdateProgressbar);
    }

    public void handlePlayerInventory(Packet5PlayerInventory par1Packet5PlayerInventory)
    {
        registerPacket(par1Packet5PlayerInventory);
    }

    public void handleTransaction(Packet106Transaction par1Packet106Transaction)
    {
        registerPacket(par1Packet106Transaction);
    }

    /**
     * Packet handler
     */
    public void handleEntityPainting(Packet25EntityPainting par1Packet25EntityPainting)
    {
        registerPacket(par1Packet25EntityPainting);
    }

    public void handlePlayNoteBlock(Packet54PlayNoteBlock par1Packet54PlayNoteBlock)
    {
        registerPacket(par1Packet54PlayNoteBlock);
    }

    /**
     * runs registerPacket on the given Packet200Statistic
     */
    public void handleStatistic(Packet200Statistic par1Packet200Statistic)
    {
        registerPacket(par1Packet200Statistic);
    }

    public void handleSleep(Packet17Sleep par1Packet17Sleep)
    {
        registerPacket(par1Packet17Sleep);
    }

    public void handleBed(Packet70Bed par1Packet70Bed)
    {
        registerPacket(par1Packet70Bed);
    }

    /**
     * Handles weather packet
     */
    public void handleWeather(Packet71Weather par1Packet71Weather)
    {
        registerPacket(par1Packet71Weather);
    }

    /**
     * Contains logic for handling packets containing arbitrary unique item data. Currently this is only for maps.
     */
    public void handleMapData(Packet131MapData par1Packet131MapData)
    {
        registerPacket(par1Packet131MapData);
    }

    public void handleDoorChange(Packet61DoorChange par1Packet61DoorChange)
    {
        registerPacket(par1Packet61DoorChange);
    }

    /**
     * Handle a server ping packet.
     */
    public void handleServerPing(Packet254ServerPing par1Packet254ServerPing)
    {
        registerPacket(par1Packet254ServerPing);
    }

    /**
     * Handle an entity effect packet.
     */
    public void handleEntityEffect(Packet41EntityEffect par1Packet41EntityEffect)
    {
        registerPacket(par1Packet41EntityEffect);
    }

    /**
     * Handle a remove entity effect packet.
     */
    public void handleRemoveEntityEffect(Packet42RemoveEntityEffect par1Packet42RemoveEntityEffect)
    {
        registerPacket(par1Packet42RemoveEntityEffect);
    }

    /**
     * Handle a player information packet.
     */
    public void handlePlayerInfo(Packet201PlayerInfo par1Packet201PlayerInfo)
    {
        registerPacket(par1Packet201PlayerInfo);
    }

    /**
     * Handle a keep alive packet.
     */
    public void handleKeepAlive(Packet0KeepAlive par1Packet0KeepAlive)
    {
        registerPacket(par1Packet0KeepAlive);
    }

    /**
     * Handle an experience packet.
     */
    public void handleExperience(Packet43Experience par1Packet43Experience)
    {
        registerPacket(par1Packet43Experience);
    }

    /**
     * Handle a creative slot packet.
     */
    public void handleCreativeSetSlot(Packet107CreativeSetSlot par1Packet107CreativeSetSlot)
    {
        registerPacket(par1Packet107CreativeSetSlot);
    }

    /**
     * Handle a entity experience orb packet.
     */
    public void handleEntityExpOrb(Packet26EntityExpOrb par1Packet26EntityExpOrb)
    {
        registerPacket(par1Packet26EntityExpOrb);
    }

    public void handleEnchantItem(Packet108EnchantItem packet108enchantitem)
    {
    }

    public void handleCustomPayload(Packet250CustomPayload packet250custompayload)
    {
    }

    public void handleEntityHeadRotation(Packet35EntityHeadRotation par1Packet35EntityHeadRotation)
    {
        registerPacket(par1Packet35EntityHeadRotation);
    }

    public void handleTileEntityData(Packet132TileEntityData par1Packet132TileEntityData)
    {
        registerPacket(par1Packet132TileEntityData);
    }

    public void func_50100_a(Packet202PlayerAbilities par1Packet202PlayerAbilities)
    {
        registerPacket(par1Packet202PlayerAbilities);
    }
}
