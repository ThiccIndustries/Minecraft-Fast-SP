package net.minecraft.src;

import java.io.*;
import java.util.List;

public class Packet24MobSpawn extends Packet
{
    /** The entity ID. */
    public int entityId;

    /** The type of mob. */
    public int type;

    /** The X position of the entity. */
    public int xPosition;

    /** The Y position of the entity. */
    public int yPosition;

    /** The Z position of the entity. */
    public int zPosition;

    /** The yaw of the entity. */
    public byte yaw;

    /** The pitch of the entity. */
    public byte pitch;
    public byte field_48169_h;

    /** Indexed metadata for Mob, terminated by 0x7F */
    private DataWatcher metaData;
    private List receivedMetadata;

    public Packet24MobSpawn()
    {
    }

    public Packet24MobSpawn(EntityLiving par1EntityLiving)
    {
        entityId = par1EntityLiving.entityId;
        type = (byte)EntityList.getEntityID(par1EntityLiving);
        xPosition = MathHelper.floor_double(par1EntityLiving.posX * 32D);
        yPosition = MathHelper.floor_double(par1EntityLiving.posY * 32D);
        zPosition = MathHelper.floor_double(par1EntityLiving.posZ * 32D);
        yaw = (byte)(int)((par1EntityLiving.rotationYaw * 256F) / 360F);
        pitch = (byte)(int)((par1EntityLiving.rotationPitch * 256F) / 360F);
        field_48169_h = (byte)(int)((par1EntityLiving.rotationYawHead * 256F) / 360F);
        metaData = par1EntityLiving.getDataWatcher();
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        entityId = par1DataInputStream.readInt();
        type = par1DataInputStream.readByte() & 0xff;
        xPosition = par1DataInputStream.readInt();
        yPosition = par1DataInputStream.readInt();
        zPosition = par1DataInputStream.readInt();
        yaw = par1DataInputStream.readByte();
        pitch = par1DataInputStream.readByte();
        field_48169_h = par1DataInputStream.readByte();
        receivedMetadata = DataWatcher.readWatchableObjects(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(entityId);
        par1DataOutputStream.writeByte(type & 0xff);
        par1DataOutputStream.writeInt(xPosition);
        par1DataOutputStream.writeInt(yPosition);
        par1DataOutputStream.writeInt(zPosition);
        par1DataOutputStream.writeByte(yaw);
        par1DataOutputStream.writeByte(pitch);
        par1DataOutputStream.writeByte(field_48169_h);
        metaData.writeWatchableObjects(par1DataOutputStream);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleMobSpawn(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 20;
    }

    public List getMetadata()
    {
        return receivedMetadata;
    }
}
