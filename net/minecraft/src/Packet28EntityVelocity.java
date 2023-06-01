package net.minecraft.src;

import java.io.*;

public class Packet28EntityVelocity extends Packet
{
    public int entityId;
    public int motionX;
    public int motionY;
    public int motionZ;

    public Packet28EntityVelocity()
    {
    }

    public Packet28EntityVelocity(Entity par1Entity)
    {
        this(par1Entity.entityId, par1Entity.motionX, par1Entity.motionY, par1Entity.motionZ);
    }

    public Packet28EntityVelocity(int par1, double par2, double par4, double par6)
    {
        entityId = par1;
        double d = 3.8999999999999999D;

        if (par2 < -d)
        {
            par2 = -d;
        }

        if (par4 < -d)
        {
            par4 = -d;
        }

        if (par6 < -d)
        {
            par6 = -d;
        }

        if (par2 > d)
        {
            par2 = d;
        }

        if (par4 > d)
        {
            par4 = d;
        }

        if (par6 > d)
        {
            par6 = d;
        }

        motionX = (int)(par2 * 8000D);
        motionY = (int)(par4 * 8000D);
        motionZ = (int)(par6 * 8000D);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        entityId = par1DataInputStream.readInt();
        motionX = par1DataInputStream.readShort();
        motionY = par1DataInputStream.readShort();
        motionZ = par1DataInputStream.readShort();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(entityId);
        par1DataOutputStream.writeShort(motionX);
        par1DataOutputStream.writeShort(motionY);
        par1DataOutputStream.writeShort(motionZ);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleEntityVelocity(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 10;
    }
}
