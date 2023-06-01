package net.minecraft.src;

import java.io.*;

public class Packet34EntityTeleport extends Packet
{
    /** ID of the entity. */
    public int entityId;

    /** X position of the entity. */
    public int xPosition;

    /** Y position of the entity. */
    public int yPosition;

    /** Z position of the entity. */
    public int zPosition;

    /** Yaw of the entity. */
    public byte yaw;

    /** Pitch of the entity. */
    public byte pitch;

    public Packet34EntityTeleport()
    {
    }

    public Packet34EntityTeleport(Entity par1Entity)
    {
        entityId = par1Entity.entityId;
        xPosition = MathHelper.floor_double(par1Entity.posX * 32D);
        yPosition = MathHelper.floor_double(par1Entity.posY * 32D);
        zPosition = MathHelper.floor_double(par1Entity.posZ * 32D);
        yaw = (byte)(int)((par1Entity.rotationYaw * 256F) / 360F);
        pitch = (byte)(int)((par1Entity.rotationPitch * 256F) / 360F);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        entityId = par1DataInputStream.readInt();
        xPosition = par1DataInputStream.readInt();
        yPosition = par1DataInputStream.readInt();
        zPosition = par1DataInputStream.readInt();
        yaw = (byte)par1DataInputStream.read();
        pitch = (byte)par1DataInputStream.read();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(entityId);
        par1DataOutputStream.writeInt(xPosition);
        par1DataOutputStream.writeInt(yPosition);
        par1DataOutputStream.writeInt(zPosition);
        par1DataOutputStream.write(yaw);
        par1DataOutputStream.write(pitch);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleEntityTeleport(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 34;
    }
}
