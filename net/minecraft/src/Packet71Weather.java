package net.minecraft.src;

import java.io.*;

public class Packet71Weather extends Packet
{
    public int entityID;
    public int posX;
    public int posY;
    public int posZ;
    public int isLightningBolt;

    public Packet71Weather()
    {
    }

    public Packet71Weather(Entity par1Entity)
    {
        entityID = par1Entity.entityId;
        posX = MathHelper.floor_double(par1Entity.posX * 32D);
        posY = MathHelper.floor_double(par1Entity.posY * 32D);
        posZ = MathHelper.floor_double(par1Entity.posZ * 32D);

        if (par1Entity instanceof EntityLightningBolt)
        {
            isLightningBolt = 1;
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        entityID = par1DataInputStream.readInt();
        isLightningBolt = par1DataInputStream.readByte();
        posX = par1DataInputStream.readInt();
        posY = par1DataInputStream.readInt();
        posZ = par1DataInputStream.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(entityID);
        par1DataOutputStream.writeByte(isLightningBolt);
        par1DataOutputStream.writeInt(posX);
        par1DataOutputStream.writeInt(posY);
        par1DataOutputStream.writeInt(posZ);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleWeather(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 17;
    }
}
