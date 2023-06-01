package net.minecraft.src;

import java.io.*;

public class Packet18Animation extends Packet
{
    /** The entity ID, in this case it's the player ID. */
    public int entityId;
    public int animate;

    public Packet18Animation()
    {
    }

    public Packet18Animation(Entity par1Entity, int par2)
    {
        entityId = par1Entity.entityId;
        animate = par2;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        entityId = par1DataInputStream.readInt();
        animate = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(entityId);
        par1DataOutputStream.writeByte(animate);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleAnimation(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 5;
    }
}
