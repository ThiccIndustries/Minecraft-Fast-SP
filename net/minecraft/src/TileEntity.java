package net.minecraft.src;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class TileEntity
{
    /**
     * A HashMap storing string names of classes mapping to the actual java.lang.Class type.
     */
    private static Map nameToClassMap = new HashMap();

    /**
     * A HashMap storing the classes and mapping to the string names (reverse of nameToClassMap).
     */
    private static Map classToNameMap = new HashMap();

    /** The reference to the world. */
    public World worldObj;

    /** The x coordinate of the tile entity. */
    public int xCoord;

    /** The y coordinate of the tile entity. */
    public int yCoord;

    /** The z coordinate of the tile entity. */
    public int zCoord;
    protected boolean tileEntityInvalid;
    public int blockMetadata;

    /** the Block type that this TileEntity is contained within */
    public Block blockType;

    public TileEntity()
    {
        blockMetadata = -1;
    }

    /**
     * Adds a new two-way mapping between the class and its string name in both hashmaps.
     */
    private static void addMapping(Class par0Class, String par1Str)
    {
        if (classToNameMap.containsKey(par1Str))
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Duplicate id: ").append(par1Str).toString());
        }
        else
        {
            nameToClassMap.put(par1Str, par0Class);
            classToNameMap.put(par0Class, par1Str);
            return;
        }
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        xCoord = par1NBTTagCompound.getInteger("x");
        yCoord = par1NBTTagCompound.getInteger("y");
        zCoord = par1NBTTagCompound.getInteger("z");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        String s = (String)classToNameMap.get(getClass());

        if (s == null)
        {
            throw new RuntimeException((new StringBuilder()).append(getClass()).append(" is missing a mapping! This is a bug!").toString());
        }
        else
        {
            par1NBTTagCompound.setString("id", s);
            par1NBTTagCompound.setInteger("x", xCoord);
            par1NBTTagCompound.setInteger("y", yCoord);
            par1NBTTagCompound.setInteger("z", zCoord);
            return;
        }
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
    }

    /**
     * Creates a new entity and loads its data from the specified NBT.
     */
    public static TileEntity createAndLoadEntity(NBTTagCompound par0NBTTagCompound)
    {
        TileEntity tileentity = null;

        try
        {
            Class class1 = (Class)nameToClassMap.get(par0NBTTagCompound.getString("id"));

            if (class1 != null)
            {
                tileentity = (TileEntity)class1.newInstance();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        if (tileentity != null)
        {
            tileentity.readFromNBT(par0NBTTagCompound);
        }
        else
        {
            System.out.println((new StringBuilder()).append("Skipping TileEntity with id ").append(par0NBTTagCompound.getString("id")).toString());
        }

        return tileentity;
    }

    /**
     * Returns block data at the location of this entity (client-only).
     */
    public int getBlockMetadata()
    {
        if (blockMetadata == -1)
        {
            blockMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        }

        return blockMetadata;
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void onInventoryChanged()
    {
        if (worldObj != null)
        {
            blockMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
            worldObj.updateTileEntityChunkAndDoNothing(xCoord, yCoord, zCoord, this);
        }
    }

    /**
     * Returns the square of the distance between this entity and the passed in coordinates.
     */
    public double getDistanceFrom(double par1, double par3, double par5)
    {
        double d = ((double)xCoord + 0.5D) - par1;
        double d1 = ((double)yCoord + 0.5D) - par3;
        double d2 = ((double)zCoord + 0.5D) - par5;
        return d * d + d1 * d1 + d2 * d2;
    }

    /**
     * Gets the block type at the location of this entity (client-only).
     */
    public Block getBlockType()
    {
        if (blockType == null)
        {
            blockType = Block.blocksList[worldObj.getBlockId(xCoord, yCoord, zCoord)];
        }

        return blockType;
    }

    /**
     * returns true if tile entity is invalid, false otherwise
     */
    public boolean isInvalid()
    {
        return tileEntityInvalid;
    }

    /**
     * invalidates a tile entity
     */
    public void invalidate()
    {
        tileEntityInvalid = true;
    }

    /**
     * validates a tile entity
     */
    public void validate()
    {
        tileEntityInvalid = false;
    }

    public void onTileEntityPowered(int i, int j)
    {
    }

    /**
     * causes the TileEntity to reset all it's cached values for it's container block, blockID, metaData and in the case
     * of chests, the adjcacent chest check
     */
    public void updateContainingBlockInfo()
    {
        blockType = null;
        blockMetadata = -1;
    }

    static
    {
        addMapping(net.minecraft.src.TileEntityFurnace.class, "Furnace");
        addMapping(net.minecraft.src.TileEntityChest.class, "Chest");
        addMapping(net.minecraft.src.TileEntityRecordPlayer.class, "RecordPlayer");
        addMapping(net.minecraft.src.TileEntityDispenser.class, "Trap");
        addMapping(net.minecraft.src.TileEntitySign.class, "Sign");
        addMapping(net.minecraft.src.TileEntityMobSpawner.class, "MobSpawner");
        addMapping(net.minecraft.src.TileEntityNote.class, "Music");
        addMapping(net.minecraft.src.TileEntityPiston.class, "Piston");
        addMapping(net.minecraft.src.TileEntityBrewingStand.class, "Cauldron");
        addMapping(net.minecraft.src.TileEntityEnchantmentTable.class, "EnchantTable");
        addMapping(net.minecraft.src.TileEntityEndPortal.class, "Airportal");
    }
}
