package net.minecraft.src;

import java.io.*;

public class Packet103SetSlot extends Packet
{
    /** The window which is being updated. 0 for player inventory */
    public int windowId;

    /** Slot that should be updated */
    public int itemSlot;

    /** Item stack */
    public ItemStack myItemStack;

    public Packet103SetSlot()
    {
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleSetSlot(this);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        windowId = par1DataInputStream.readByte();
        itemSlot = par1DataInputStream.readShort();
        myItemStack = readItemStack(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(windowId);
        par1DataOutputStream.writeShort(itemSlot);
        writeItemStack(myItemStack, par1DataOutputStream);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 8;
    }
}
