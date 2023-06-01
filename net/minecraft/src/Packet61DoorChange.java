package net.minecraft.src;

import java.io.*;

public class Packet61DoorChange extends Packet
{
    public int sfxID;
    public int auxData;
    public int posX;
    public int posY;
    public int posZ;

    public Packet61DoorChange()
    {
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        sfxID = par1DataInputStream.readInt();
        posX = par1DataInputStream.readInt();
        posY = par1DataInputStream.readByte() & 0xff;
        posZ = par1DataInputStream.readInt();
        auxData = par1DataInputStream.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(sfxID);
        par1DataOutputStream.writeInt(posX);
        par1DataOutputStream.writeByte(posY & 0xff);
        par1DataOutputStream.writeInt(posZ);
        par1DataOutputStream.writeInt(auxData);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleDoorChange(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 20;
    }
}
