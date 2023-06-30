package net.minecraft.src;

import java.io.*;

public class Packet106Transaction extends Packet
{
    /** The id of the window that the action occurred in. */
    public int windowId;
    public short shortWindowId;
    public boolean accepted;

    public Packet106Transaction()
    {
    }

    public Packet106Transaction(int par1, short par2, boolean par3)
    {
        windowId = par1;
        shortWindowId = par2;
        accepted = par3;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleTransaction(this);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        windowId = par1DataInputStream.readByte();
        shortWindowId = par1DataInputStream.readShort();
        accepted = par1DataInputStream.readByte() != 0;
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(windowId);
        par1DataOutputStream.writeShort(shortWindowId);
        par1DataOutputStream.writeByte(accepted ? 1 : 0);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 4;
    }
}
