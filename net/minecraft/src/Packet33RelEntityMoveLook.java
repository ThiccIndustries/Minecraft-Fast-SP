package net.minecraft.src;

import java.io.*;

public class Packet33RelEntityMoveLook extends Packet30Entity
{
    public Packet33RelEntityMoveLook()
    {
        rotating = true;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        super.readPacketData(par1DataInputStream);
        xPosition = par1DataInputStream.readByte();
        yPosition = par1DataInputStream.readByte();
        zPosition = par1DataInputStream.readByte();
        yaw = par1DataInputStream.readByte();
        pitch = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        super.writePacketData(par1DataOutputStream);
        par1DataOutputStream.writeByte(xPosition);
        par1DataOutputStream.writeByte(yPosition);
        par1DataOutputStream.writeByte(zPosition);
        par1DataOutputStream.writeByte(yaw);
        par1DataOutputStream.writeByte(pitch);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 9;
    }
}
