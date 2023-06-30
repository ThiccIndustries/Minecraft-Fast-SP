package net.minecraft.src;

import java.io.*;

public class Packet42RemoveEntityEffect extends Packet
{
    /** The ID of the entity which an effect is being removed from. */
    public int entityId;

    /** The ID of the effect which is being removed from an entity. */
    public byte effectId;

    public Packet42RemoveEntityEffect()
    {
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        entityId = par1DataInputStream.readInt();
        effectId = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(entityId);
        par1DataOutputStream.writeByte(effectId);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleRemoveEntityEffect(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 5;
    }
}
