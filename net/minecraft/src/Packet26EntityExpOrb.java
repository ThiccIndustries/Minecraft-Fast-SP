package net.minecraft.src;

import java.io.*;

public class Packet26EntityExpOrb extends Packet
{
    /** Entity ID for the XP Orb */
    public int entityId;
    public int posX;
    public int posY;
    public int posZ;

    /** The Orbs Experience points value. */
    public int xpValue;

    public Packet26EntityExpOrb()
    {
    }

    public Packet26EntityExpOrb(EntityXPOrb par1EntityXPOrb)
    {
        entityId = par1EntityXPOrb.entityId;
        posX = MathHelper.floor_double(par1EntityXPOrb.posX * 32D);
        posY = MathHelper.floor_double(par1EntityXPOrb.posY * 32D);
        posZ = MathHelper.floor_double(par1EntityXPOrb.posZ * 32D);
        xpValue = par1EntityXPOrb.getXpValue();
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        entityId = par1DataInputStream.readInt();
        posX = par1DataInputStream.readInt();
        posY = par1DataInputStream.readInt();
        posZ = par1DataInputStream.readInt();
        xpValue = par1DataInputStream.readShort();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(entityId);
        par1DataOutputStream.writeInt(posX);
        par1DataOutputStream.writeInt(posY);
        par1DataOutputStream.writeInt(posZ);
        par1DataOutputStream.writeShort(xpValue);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleEntityExpOrb(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 18;
    }
}
