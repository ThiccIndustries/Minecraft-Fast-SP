package net.minecraft.src;

import java.io.*;

public class Packet25EntityPainting extends Packet
{
    public int entityId;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public int direction;
    public String title;

    public Packet25EntityPainting()
    {
    }

    public Packet25EntityPainting(EntityPainting par1EntityPainting)
    {
        entityId = par1EntityPainting.entityId;
        xPosition = par1EntityPainting.xPosition;
        yPosition = par1EntityPainting.yPosition;
        zPosition = par1EntityPainting.zPosition;
        direction = par1EntityPainting.direction;
        title = par1EntityPainting.art.title;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        entityId = par1DataInputStream.readInt();
        title = readString(par1DataInputStream, EnumArt.maxArtTitleLength);
        xPosition = par1DataInputStream.readInt();
        yPosition = par1DataInputStream.readInt();
        zPosition = par1DataInputStream.readInt();
        direction = par1DataInputStream.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(entityId);
        writeString(title, par1DataOutputStream);
        par1DataOutputStream.writeInt(xPosition);
        par1DataOutputStream.writeInt(yPosition);
        par1DataOutputStream.writeInt(zPosition);
        par1DataOutputStream.writeInt(direction);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleEntityPainting(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 24;
    }
}
