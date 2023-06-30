package net.minecraft.src;

import java.io.*;

public class Packet16BlockItemSwitch extends Packet
{
    /** The block/item id to be equipped. */
    public int id;

    public Packet16BlockItemSwitch()
    {
    }

    public Packet16BlockItemSwitch(int par1)
    {
        id = par1;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        id = par1DataInputStream.readShort();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeShort(id);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleBlockItemSwitch(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 2;
    }
}
