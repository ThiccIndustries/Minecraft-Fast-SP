package net.minecraft.src;

import java.io.*;

public class Packet11PlayerPosition extends Packet10Flying
{
    public Packet11PlayerPosition()
    {
        moving = true;
    }

    public Packet11PlayerPosition(double par1, double par3, double par5, double par7, boolean par9)
    {
        xPosition = par1;
        yPosition = par3;
        stance = par5;
        zPosition = par7;
        onGround = par9;
        moving = true;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        xPosition = par1DataInputStream.readDouble();
        yPosition = par1DataInputStream.readDouble();
        stance = par1DataInputStream.readDouble();
        zPosition = par1DataInputStream.readDouble();
        super.readPacketData(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeDouble(xPosition);
        par1DataOutputStream.writeDouble(yPosition);
        par1DataOutputStream.writeDouble(stance);
        par1DataOutputStream.writeDouble(zPosition);
        super.writePacketData(par1DataOutputStream);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 33;
    }
}
