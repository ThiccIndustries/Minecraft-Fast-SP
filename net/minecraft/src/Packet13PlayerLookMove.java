package net.minecraft.src;

import java.io.*;

public class Packet13PlayerLookMove extends Packet10Flying
{
    public Packet13PlayerLookMove()
    {
        rotating = true;
        moving = true;
    }

    public Packet13PlayerLookMove(double par1, double par3, double par5, double par7, float par9, float par10, boolean par11)
    {
        xPosition = par1;
        yPosition = par3;
        stance = par5;
        zPosition = par7;
        yaw = par9;
        pitch = par10;
        onGround = par11;
        rotating = true;
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
        yaw = par1DataInputStream.readFloat();
        pitch = par1DataInputStream.readFloat();
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
        par1DataOutputStream.writeFloat(yaw);
        par1DataOutputStream.writeFloat(pitch);
        super.writePacketData(par1DataOutputStream);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 41;
    }
}
