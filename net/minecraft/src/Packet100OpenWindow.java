package net.minecraft.src;

import java.io.*;

public class Packet100OpenWindow extends Packet
{
    public int windowId;
    public int inventoryType;
    public String windowTitle;
    public int slotsCount;

    public Packet100OpenWindow()
    {
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleOpenWindow(this);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        windowId = par1DataInputStream.readByte() & 0xff;
        inventoryType = par1DataInputStream.readByte() & 0xff;
        windowTitle = readString(par1DataInputStream, 32);
        slotsCount = par1DataInputStream.readByte() & 0xff;
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(windowId & 0xff);
        par1DataOutputStream.writeByte(inventoryType & 0xff);
        writeString(windowTitle, par1DataOutputStream);
        par1DataOutputStream.writeByte(slotsCount & 0xff);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 3 + windowTitle.length();
    }
}
