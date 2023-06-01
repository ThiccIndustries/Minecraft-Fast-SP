package net.minecraft.src;

import java.io.*;

public class Packet19EntityAction extends Packet
{
    /** Player ID. */
    public int entityId;

    /** 1=sneak, 2=normal */
    public int state;

    public Packet19EntityAction()
    {
    }

    public Packet19EntityAction(Entity par1Entity, int par2)
    {
        entityId = par1Entity.entityId;
        state = par2;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        entityId = par1DataInputStream.readInt();
        state = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(entityId);
        par1DataOutputStream.writeByte(state);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleEntityAction(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 5;
    }
}
