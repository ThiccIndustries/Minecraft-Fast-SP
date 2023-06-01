package net.minecraft.src;

import java.io.*;

public class Packet30Entity extends Packet
{
    /** The ID of this entity. */
    public int entityId;

    /** The X axis relative movement. */
    public byte xPosition;

    /** The Y axis relative movement. */
    public byte yPosition;

    /** The Z axis relative movement. */
    public byte zPosition;

    /** The X axis rotation. */
    public byte yaw;

    /** The Y axis rotation. */
    public byte pitch;

    /** Boolean set to true if the entity is rotating. */
    public boolean rotating;

    public Packet30Entity()
    {
        rotating = false;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        entityId = par1DataInputStream.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(entityId);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleEntity(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 4;
    }
}
