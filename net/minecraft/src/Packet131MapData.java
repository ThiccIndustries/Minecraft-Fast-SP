package net.minecraft.src;

import java.io.*;

public class Packet131MapData extends Packet
{
    public short itemID;

    /**
     * Contains a unique ID for the item that this packet will be populating.
     */
    public short uniqueID;
    public byte itemData[];

    public Packet131MapData()
    {
        isChunkDataPacket = true;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        itemID = par1DataInputStream.readShort();
        uniqueID = par1DataInputStream.readShort();
        itemData = new byte[par1DataInputStream.readByte() & 0xff];
        par1DataInputStream.readFully(itemData);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeShort(itemID);
        par1DataOutputStream.writeShort(uniqueID);
        par1DataOutputStream.writeByte(itemData.length);
        par1DataOutputStream.write(itemData);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleMapData(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 4 + itemData.length;
    }
}
