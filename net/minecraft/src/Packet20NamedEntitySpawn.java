package net.minecraft.src;

import java.io.*;

public class Packet20NamedEntitySpawn extends Packet
{
    /** The entity ID, in this case it's the player ID. */
    public int entityId;

    /** The player's name. */
    public String name;

    /** The player's X position. */
    public int xPosition;

    /** The player's Y position. */
    public int yPosition;

    /** The player's Z position. */
    public int zPosition;

    /** The player's rotation. */
    public byte rotation;

    /** The player's pitch. */
    public byte pitch;

    /** The current item the player is holding. */
    public int currentItem;

    public Packet20NamedEntitySpawn()
    {
    }

    public Packet20NamedEntitySpawn(EntityPlayer par1EntityPlayer)
    {
        entityId = par1EntityPlayer.entityId;
        name = par1EntityPlayer.username;
        xPosition = MathHelper.floor_double(par1EntityPlayer.posX * 32D);
        yPosition = MathHelper.floor_double(par1EntityPlayer.posY * 32D);
        zPosition = MathHelper.floor_double(par1EntityPlayer.posZ * 32D);
        rotation = (byte)(int)((par1EntityPlayer.rotationYaw * 256F) / 360F);
        pitch = (byte)(int)((par1EntityPlayer.rotationPitch * 256F) / 360F);
        ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();
        currentItem = itemstack != null ? itemstack.itemID : 0;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        entityId = par1DataInputStream.readInt();
        name = readString(par1DataInputStream, 16);
        xPosition = par1DataInputStream.readInt();
        yPosition = par1DataInputStream.readInt();
        zPosition = par1DataInputStream.readInt();
        rotation = par1DataInputStream.readByte();
        pitch = par1DataInputStream.readByte();
        currentItem = par1DataInputStream.readShort();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(entityId);
        writeString(name, par1DataOutputStream);
        par1DataOutputStream.writeInt(xPosition);
        par1DataOutputStream.writeInt(yPosition);
        par1DataOutputStream.writeInt(zPosition);
        par1DataOutputStream.writeByte(rotation);
        par1DataOutputStream.writeByte(pitch);
        par1DataOutputStream.writeShort(currentItem);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleNamedEntitySpawn(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 28;
    }
}
