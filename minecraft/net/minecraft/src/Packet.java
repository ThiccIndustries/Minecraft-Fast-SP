package net.minecraft.src;

import java.io.*;
import java.util.*;

public abstract class Packet
{
    /** Maps packet id to packet class */
    public static IntHashMap packetIdToClassMap = new IntHashMap();

    /** Maps packet class to packet id */
    private static Map packetClassToIdMap = new HashMap();

    /** list of the client's packets id */
    private static Set clientPacketIdList = new HashSet();

    /** list of the server's packets id */
    private static Set serverPacketIdList = new HashSet();

    /** the system time in milliseconds when this packet was created. */
    public final long creationTimeMillis = System.currentTimeMillis();
    public static long field_48158_m;
    public static long field_48156_n;
    public static long field_48157_o;
    public static long field_48155_p;

    /**
     * Only true for Packet51MapChunk, Packet52MultiBlockChange, Packet53BlockChange and Packet59ComplexEntity. Used to
     * separate them into a different send queue.
     */
    public boolean isChunkDataPacket;

    public Packet()
    {
        isChunkDataPacket = false;
    }

    /**
     * Adds a two way mapping between the packet ID and packet class.
     */
    static void addIdClassMapping(int par0, boolean par1, boolean par2, Class par3Class)
    {
        if (packetIdToClassMap.containsItem(par0))
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Duplicate packet id:").append(par0).toString());
        }

        if (packetClassToIdMap.containsKey(par3Class))
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Duplicate packet class:").append(par3Class).toString());
        }

        packetIdToClassMap.addKey(par0, par3Class);
        packetClassToIdMap.put(par3Class, Integer.valueOf(par0));

        if (par1)
        {
            clientPacketIdList.add(Integer.valueOf(par0));
        }

        if (par2)
        {
            serverPacketIdList.add(Integer.valueOf(par0));
        }
    }

    /**
     * Returns a new instance of the specified Packet class.
     */
    public static Packet getNewPacket(int par0)
    {
        try
        {
            Class class1 = (Class)packetIdToClassMap.lookup(par0);

            if (class1 == null)
            {
                return null;
            }
            else
            {
                return (Packet)class1.newInstance();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        System.out.println((new StringBuilder()).append("Skipping packet with id ").append(par0).toString());
        return null;
    }

    /**
     * Returns the ID of this packet.
     */
    public final int getPacketId()
    {
        return ((Integer)packetClassToIdMap.get(getClass())).intValue();
    }

    /**
     * Read a packet, prefixed by its ID, from the data stream.
     */
    public static Packet readPacket(DataInputStream par0DataInputStream, boolean par1) throws IOException
    {
        int i = 0;
        Packet packet = null;

        try
        {
            i = par0DataInputStream.read();

            if (i == -1)
            {
                return null;
            }

            if (par1 && !serverPacketIdList.contains(Integer.valueOf(i)) || !par1 && !clientPacketIdList.contains(Integer.valueOf(i)))
            {
                throw new IOException((new StringBuilder()).append("Bad packet id ").append(i).toString());
            }

            packet = getNewPacket(i);

            if (packet == null)
            {
                throw new IOException((new StringBuilder()).append("Bad packet id ").append(i).toString());
            }

            packet.readPacketData(par0DataInputStream);
            field_48158_m++;
            field_48156_n += packet.getPacketSize();
        }
        catch (EOFException eofexception)
        {
            System.out.println("Reached end of stream");
            return null;
        }

        PacketCount.countPacket(i, packet.getPacketSize());
        field_48158_m++;
        field_48156_n += packet.getPacketSize();
        return packet;
    }

    /**
     * Writes a packet, prefixed by its ID, to the data stream.
     */
    public static void writePacket(Packet par0Packet, DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.write(par0Packet.getPacketId());
        par0Packet.writePacketData(par1DataOutputStream);
        field_48157_o++;
        field_48155_p += par0Packet.getPacketSize();
    }

    /**
     * Writes a string to a packet
     */
    public static void writeString(String par0Str, DataOutputStream par1DataOutputStream) throws IOException
    {
        if (par0Str.length() > 32767)
        {
            throw new IOException("String too big");
        }
        else
        {
            par1DataOutputStream.writeShort(par0Str.length());
            par1DataOutputStream.writeChars(par0Str);
            return;
        }
    }

    /**
     * Reads a string from a packet
     */
    public static String readString(DataInputStream par0DataInputStream, int par1) throws IOException
    {
        short word0 = par0DataInputStream.readShort();

        if (word0 > par1)
        {
            throw new IOException((new StringBuilder()).append("Received string length longer than maximum allowed (").append(word0).append(" > ").append(par1).append(")").toString());
        }

        if (word0 < 0)
        {
            throw new IOException("Received string length is less than zero! Weird string!");
        }

        StringBuilder stringbuilder = new StringBuilder();

        for (int i = 0; i < word0; i++)
        {
            stringbuilder.append(par0DataInputStream.readChar());
        }

        return stringbuilder.toString();
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public abstract void readPacketData(DataInputStream datainputstream) throws IOException;

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public abstract void writePacketData(DataOutputStream dataoutputstream) throws IOException;

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public abstract void processPacket(NetHandler nethandler);

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public abstract int getPacketSize();

    /**
     * Reads a ItemStack from the InputStream
     */
    protected ItemStack readItemStack(DataInputStream par1DataInputStream) throws IOException
    {
        ItemStack itemstack = null;
        short word0 = par1DataInputStream.readShort();

        if (word0 >= 0)
        {
            byte byte0 = par1DataInputStream.readByte();
            short word1 = par1DataInputStream.readShort();
            itemstack = new ItemStack(word0, byte0, word1);

            if (Item.itemsList[word0].isDamageable() || Item.itemsList[word0].func_46056_k())
            {
                itemstack.stackTagCompound = readNBTTagCompound(par1DataInputStream);
            }
        }

        return itemstack;
    }

    /**
     * Writes the ItemStack's ID (short), then size (byte), then damage. (short)
     */
    protected void writeItemStack(ItemStack par1ItemStack, DataOutputStream par2DataOutputStream) throws IOException
    {
        if (par1ItemStack == null)
        {
            par2DataOutputStream.writeShort(-1);
        }
        else
        {
            par2DataOutputStream.writeShort(par1ItemStack.itemID);
            par2DataOutputStream.writeByte(par1ItemStack.stackSize);
            par2DataOutputStream.writeShort(par1ItemStack.getItemDamage());

            if (par1ItemStack.getItem().isDamageable() || par1ItemStack.getItem().func_46056_k())
            {
                writeNBTTagCompound(par1ItemStack.stackTagCompound, par2DataOutputStream);
            }
        }
    }

    /**
     * Reads a compressed NBTTagCompound from the InputStream
     */
    protected NBTTagCompound readNBTTagCompound(DataInputStream par1DataInputStream) throws IOException
    {
        short word0 = par1DataInputStream.readShort();

        if (word0 < 0)
        {
            return null;
        }
        else
        {
            byte abyte0[] = new byte[word0];
            par1DataInputStream.readFully(abyte0);
            return CompressedStreamTools.decompress(abyte0);
        }
    }

    /**
     * Writes a compressed NBTTagCompound to the OutputStream
     */
    protected void writeNBTTagCompound(NBTTagCompound par1NBTTagCompound, DataOutputStream par2DataOutputStream) throws IOException
    {
        if (par1NBTTagCompound == null)
        {
            par2DataOutputStream.writeShort(-1);
        }
        else
        {
            byte abyte0[] = CompressedStreamTools.compress(par1NBTTagCompound);
            par2DataOutputStream.writeShort((short)abyte0.length);
            par2DataOutputStream.write(abyte0);
        }
    }

    static
    {
        addIdClassMapping(0, true, true, net.minecraft.src.Packet0KeepAlive.class);
        addIdClassMapping(1, true, true, net.minecraft.src.Packet1Login.class);
        addIdClassMapping(2, true, true, net.minecraft.src.Packet2Handshake.class);
        addIdClassMapping(3, true, true, net.minecraft.src.Packet3Chat.class);
        addIdClassMapping(4, true, false, net.minecraft.src.Packet4UpdateTime.class);
        addIdClassMapping(5, true, false, net.minecraft.src.Packet5PlayerInventory.class);
        addIdClassMapping(6, true, false, net.minecraft.src.Packet6SpawnPosition.class);
        addIdClassMapping(7, false, true, net.minecraft.src.Packet7UseEntity.class);
        addIdClassMapping(8, true, false, net.minecraft.src.Packet8UpdateHealth.class);
        addIdClassMapping(9, true, true, net.minecraft.src.Packet9Respawn.class);
        addIdClassMapping(10, true, true, net.minecraft.src.Packet10Flying.class);
        addIdClassMapping(11, true, true, net.minecraft.src.Packet11PlayerPosition.class);
        addIdClassMapping(12, true, true, net.minecraft.src.Packet12PlayerLook.class);
        addIdClassMapping(13, true, true, net.minecraft.src.Packet13PlayerLookMove.class);
        addIdClassMapping(14, false, true, net.minecraft.src.Packet14BlockDig.class);
        addIdClassMapping(15, false, true, net.minecraft.src.Packet15Place.class);
        addIdClassMapping(16, false, true, net.minecraft.src.Packet16BlockItemSwitch.class);
        addIdClassMapping(17, true, false, net.minecraft.src.Packet17Sleep.class);
        addIdClassMapping(18, true, true, net.minecraft.src.Packet18Animation.class);
        addIdClassMapping(19, false, true, net.minecraft.src.Packet19EntityAction.class);
        addIdClassMapping(20, true, false, net.minecraft.src.Packet20NamedEntitySpawn.class);
        addIdClassMapping(21, true, false, net.minecraft.src.Packet21PickupSpawn.class);
        addIdClassMapping(22, true, false, net.minecraft.src.Packet22Collect.class);
        addIdClassMapping(23, true, false, net.minecraft.src.Packet23VehicleSpawn.class);
        addIdClassMapping(24, true, false, net.minecraft.src.Packet24MobSpawn.class);
        addIdClassMapping(25, true, false, net.minecraft.src.Packet25EntityPainting.class);
        addIdClassMapping(26, true, false, net.minecraft.src.Packet26EntityExpOrb.class);
        addIdClassMapping(28, true, false, net.minecraft.src.Packet28EntityVelocity.class);
        addIdClassMapping(29, true, false, net.minecraft.src.Packet29DestroyEntity.class);
        addIdClassMapping(30, true, false, net.minecraft.src.Packet30Entity.class);
        addIdClassMapping(31, true, false, net.minecraft.src.Packet31RelEntityMove.class);
        addIdClassMapping(32, true, false, net.minecraft.src.Packet32EntityLook.class);
        addIdClassMapping(33, true, false, net.minecraft.src.Packet33RelEntityMoveLook.class);
        addIdClassMapping(34, true, false, net.minecraft.src.Packet34EntityTeleport.class);
        addIdClassMapping(35, true, false, net.minecraft.src.Packet35EntityHeadRotation.class);
        addIdClassMapping(38, true, false, net.minecraft.src.Packet38EntityStatus.class);
        addIdClassMapping(39, true, false, net.minecraft.src.Packet39AttachEntity.class);
        addIdClassMapping(40, true, false, net.minecraft.src.Packet40EntityMetadata.class);
        addIdClassMapping(41, true, false, net.minecraft.src.Packet41EntityEffect.class);
        addIdClassMapping(42, true, false, net.minecraft.src.Packet42RemoveEntityEffect.class);
        addIdClassMapping(43, true, false, net.minecraft.src.Packet43Experience.class);
        addIdClassMapping(50, true, false, net.minecraft.src.Packet50PreChunk.class);
        addIdClassMapping(51, true, false, net.minecraft.src.Packet51MapChunk.class);
        addIdClassMapping(52, true, false, net.minecraft.src.Packet52MultiBlockChange.class);
        addIdClassMapping(53, true, false, net.minecraft.src.Packet53BlockChange.class);
        addIdClassMapping(54, true, false, net.minecraft.src.Packet54PlayNoteBlock.class);
        addIdClassMapping(60, true, false, net.minecraft.src.Packet60Explosion.class);
        addIdClassMapping(61, true, false, net.minecraft.src.Packet61DoorChange.class);
        addIdClassMapping(70, true, false, net.minecraft.src.Packet70Bed.class);
        addIdClassMapping(71, true, false, net.minecraft.src.Packet71Weather.class);
        addIdClassMapping(100, true, false, net.minecraft.src.Packet100OpenWindow.class);
        addIdClassMapping(101, true, true, net.minecraft.src.Packet101CloseWindow.class);
        addIdClassMapping(102, false, true, net.minecraft.src.Packet102WindowClick.class);
        addIdClassMapping(103, true, false, net.minecraft.src.Packet103SetSlot.class);
        addIdClassMapping(104, true, false, net.minecraft.src.Packet104WindowItems.class);
        addIdClassMapping(105, true, false, net.minecraft.src.Packet105UpdateProgressbar.class);
        addIdClassMapping(106, true, true, net.minecraft.src.Packet106Transaction.class);
        addIdClassMapping(107, true, true, net.minecraft.src.Packet107CreativeSetSlot.class);
        addIdClassMapping(108, false, true, net.minecraft.src.Packet108EnchantItem.class);
        addIdClassMapping(130, true, true, net.minecraft.src.Packet130UpdateSign.class);
        addIdClassMapping(131, true, false, net.minecraft.src.Packet131MapData.class);
        addIdClassMapping(132, true, false, net.minecraft.src.Packet132TileEntityData.class);
        addIdClassMapping(200, true, false, net.minecraft.src.Packet200Statistic.class);
        addIdClassMapping(201, true, false, net.minecraft.src.Packet201PlayerInfo.class);
        addIdClassMapping(202, true, true, net.minecraft.src.Packet202PlayerAbilities.class);
        addIdClassMapping(250, true, true, net.minecraft.src.Packet250CustomPayload.class);
        addIdClassMapping(254, false, true, net.minecraft.src.Packet254ServerPing.class);
        addIdClassMapping(255, true, true, net.minecraft.src.Packet255KickDisconnect.class);
    }
}
