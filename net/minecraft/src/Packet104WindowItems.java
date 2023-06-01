package net.minecraft.src;

import java.io.*;

public class Packet104WindowItems extends Packet
{
    /**
     * The id of window which items are being sent for. 0 for player inventory.
     */
    public int windowId;
    public ItemStack itemStack[];

    public Packet104WindowItems()
    {
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        windowId = par1DataInputStream.readByte();
        short word0 = par1DataInputStream.readShort();
        itemStack = new ItemStack[word0];

        for (int i = 0; i < word0; i++)
        {
            itemStack[i] = readItemStack(par1DataInputStream);
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(windowId);
        par1DataOutputStream.writeShort(itemStack.length);

        for (int i = 0; i < itemStack.length; i++)
        {
            writeItemStack(itemStack[i], par1DataOutputStream);
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleWindowItems(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 3 + itemStack.length * 5;
    }
}
