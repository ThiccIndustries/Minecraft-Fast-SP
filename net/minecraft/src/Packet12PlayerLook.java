package net.minecraft.src;

import java.io.*;

public class Packet12PlayerLook extends Packet10Flying
{
    public Packet12PlayerLook()
    {
        rotating = true;
    }

    public Packet12PlayerLook(float par1, float par2, boolean par3)
    {
        yaw = par1;
        pitch = par2;
        onGround = par3;
        rotating = true;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        yaw = par1DataInputStream.readFloat();
        pitch = par1DataInputStream.readFloat();
        super.readPacketData(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeFloat(yaw);
        par1DataOutputStream.writeFloat(pitch);
        super.writePacketData(par1DataOutputStream);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 9;
    }
}
