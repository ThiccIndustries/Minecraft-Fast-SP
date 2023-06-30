package net.minecraft.src;

import java.io.*;

public class Packet17Sleep extends Packet
{
    public int entityID;
    public int bedX;
    public int bedY;
    public int bedZ;
    public int field_22046_e;

    public Packet17Sleep()
    {
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        entityID = par1DataInputStream.readInt();
        field_22046_e = par1DataInputStream.readByte();
        bedX = par1DataInputStream.readInt();
        bedY = par1DataInputStream.readByte();
        bedZ = par1DataInputStream.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(entityID);
        par1DataOutputStream.writeByte(field_22046_e);
        par1DataOutputStream.writeInt(bedX);
        par1DataOutputStream.writeByte(bedY);
        par1DataOutputStream.writeInt(bedZ);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleSleep(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 14;
    }
}
