package net.minecraft.src;

import java.io.*;

public class Packet108EnchantItem extends Packet
{
    public int windowId;

    /**
     * The position of the enchantment on the enchantment table window, starting with 0 as the topmost one.
     */
    public int enchantment;

    public Packet108EnchantItem()
    {
    }

    public Packet108EnchantItem(int par1, int par2)
    {
        windowId = par1;
        enchantment = par2;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleEnchantItem(this);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        windowId = par1DataInputStream.readByte();
        enchantment = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(windowId);
        par1DataOutputStream.writeByte(enchantment);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 2;
    }
}
