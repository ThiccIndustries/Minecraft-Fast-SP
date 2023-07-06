package net.minecraft.src;

import java.util.*;

public final class ItemStack
{
    /** Size of the stack. */
    public int stackSize;

    /**
     * Number of animation frames to go when receiving an item (by walking into it, for example).
     */
    public int animationsToGo;

    /** ID of the item. */
    public int itemID;

    /**
     * A NBTTagMap containing data about an ItemStack. Can only be used for non stackable items
     */
    public NBTTagCompound stackTagCompound;

    /** Damage dealt to the item or number of use. Raise when using items. */
    private int itemDamage;

    public ItemStack(Block par1Block)
    {
        this(par1Block, 1);
    }

    public ItemStack(Block par1Block, int par2)
    {
        this(par1Block.blockID, par2, 0);
    }

    public ItemStack(Block par1Block, int par2, int par3)
    {
        this(par1Block.blockID, par2, par3);
    }

    public ItemStack(Item par1Item)
    {
        this(par1Item.shiftedIndex, 1, 0);
    }

    public ItemStack(Item par1Item, int par2)
    {
        this(par1Item.shiftedIndex, par2, 0);
    }

    public ItemStack(Item par1Item, int par2, int par3)
    {
        this(par1Item.shiftedIndex, par2, par3);
    }

    public ItemStack(int par1, int par2, int par3)
    {
        stackSize = 0;
        itemID = par1;
        stackSize = par2;
        itemDamage = par3;
    }

    public static ItemStack loadItemStackFromNBT(NBTTagCompound par0NBTTagCompound)
    {
        ItemStack itemstack = new ItemStack();
        itemstack.readFromNBT(par0NBTTagCompound);
        return itemstack.getItem() == null ? null : itemstack;
    }

    private ItemStack()
    {
        stackSize = 0;
    }

    /**
     * Remove the argument from the stack size. Return a new stack object with argument size.
     */
    public ItemStack splitStack(int par1)
    {
        ItemStack itemstack = new ItemStack(itemID, par1, itemDamage);

        if (stackTagCompound != null)
        {
            itemstack.stackTagCompound = (NBTTagCompound)stackTagCompound.copy();
        }

        stackSize -= par1;
        return itemstack;
    }

    /**
     * Returns the object corresponding to the stack.
     */
    public Item getItem()
    {
        return Item.itemsList[itemID];
    }

    /**
     * Returns the icon index of the current stack.
     */
    public int getIconIndex()
    {
        return getItem().getIconIndex(this);
    }

    /**
     * Uses the item stack by the player. Gives the coordinates of the block its being used against and the side. Args:
     * player, world, x, y, z, side
     */
    public boolean useItem(EntityPlayer par1EntityPlayer, World par2World, int par3, int par4, int par5, int par6)
    {
        boolean flag = getItem().onItemUse(this, par1EntityPlayer, par2World, par3, par4, par5, par6);

        if (flag)
        {
            par1EntityPlayer.addStat(StatList.objectUseStats[itemID], 1);
        }

        return flag;
    }

    /**
     * Returns the strength of the stack against a given block.
     */
    public float getStrVsBlock(Block par1Block)
    {
        return getItem().getStrVsBlock(this, par1Block);
    }

    /**
     * Called whenever this item stack is equipped and right clicked. Returns the new item stack to put in the position
     * where this item is. Args: world, player
     */
    public ItemStack useItemRightClick(World par1World, EntityPlayer par2EntityPlayer)
    {
        return getItem().onItemRightClick(this, par1World, par2EntityPlayer);
    }

    public ItemStack onFoodEaten(World par1World, EntityPlayer par2EntityPlayer)
    {
        return getItem().onFoodEaten(this, par1World, par2EntityPlayer);
    }

    /**
     * Write the stack fields to a NBT object. Return the new NBT object.
     */
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("id", (short)itemID);
        par1NBTTagCompound.setByte("Count", (byte)stackSize);
        par1NBTTagCompound.setShort("Damage", (short)itemDamage);

        if (stackTagCompound != null)
        {
            par1NBTTagCompound.setTag("tag", stackTagCompound);
        }

        return par1NBTTagCompound;
    }

    /**
     * Read the stack fields from a NBT object.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        itemID = par1NBTTagCompound.getShort("id");
        stackSize = par1NBTTagCompound.getByte("Count");
        itemDamage = par1NBTTagCompound.getShort("Damage");

        if (par1NBTTagCompound.hasKey("tag"))
        {
            stackTagCompound = par1NBTTagCompound.getCompoundTag("tag");
        }
    }

    /**
     * Returns maximum size of the stack.
     */
    public int getMaxStackSize()
    {
        return getItem().getItemStackLimit();
    }

    /**
     * Returns true if the ItemStack can hold 2 or more units of the item.
     */
    public boolean isStackable()
    {
        return getMaxStackSize() > 1 && (!isItemStackDamageable() || !isItemDamaged());
    }

    /**
     * true if this itemStack is damageable
     */
    public boolean isItemStackDamageable()
    {
        return Item.itemsList[itemID].getMaxDamage() > 0;
    }

    public boolean getHasSubtypes()
    {
        return Item.itemsList[itemID].getHasSubtypes();
    }

    /**
     * returns true when a damageable item is damaged
     */
    public boolean isItemDamaged()
    {
        return isItemStackDamageable() && itemDamage > 0;
    }

    /**
     * gets the damage of an itemstack, for displaying purposes
     */
    public int getItemDamageForDisplay()
    {
        return itemDamage;
    }

    /**
     * gets the damage of an itemstack
     */
    public int getItemDamage()
    {
        return itemDamage;
    }

    /**
     * Sets the item damage of the ItemStack.
     */
    public void setItemDamage(int par1)
    {
        itemDamage = par1;
    }

    /**
     * Returns the max damage an item in the stack can take.
     */
    public int getMaxDamage()
    {
        return Item.itemsList[itemID].getMaxDamage();
    }

    /**
     * Damages the item in the ItemStack
     */
    public void damageItem(int par1, EntityLiving par2EntityLiving)
    {
        if (!isItemStackDamageable())
        {
            return;
        }

        if (par1 > 0 && (par2EntityLiving instanceof EntityPlayer))
        {
            int i = EnchantmentHelper.getUnbreakingModifier(((EntityPlayer)par2EntityLiving).inventory);

            if (i > 0 && par2EntityLiving.worldObj.rand.nextInt(i + 1) > 0)
            {
                return;
            }
        }

        itemDamage += par1;

        if (itemDamage > getMaxDamage())
        {
            par2EntityLiving.renderBrokenItemStack(this);

            if (par2EntityLiving instanceof EntityPlayer)
            {
                ((EntityPlayer)par2EntityLiving).addStat(StatList.objectBreakStats[itemID], 1);
            }

            stackSize--;

            if (stackSize < 0)
            {
                stackSize = 0;
            }

            itemDamage = 0;
        }
    }

    /**
     * Calls the corresponding fct in di
     */
    public void hitEntity(EntityLiving par1EntityLiving, EntityPlayer par2EntityPlayer)
    {
        boolean flag = Item.itemsList[itemID].hitEntity(this, par1EntityLiving, par2EntityPlayer);

        if (flag)
        {
            par2EntityPlayer.addStat(StatList.objectUseStats[itemID], 1);
        }
    }

    public void onDestroyBlock(int par1, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
    {
        boolean flag = Item.itemsList[itemID].onBlockDestroyed(this, par1, par2, par3, par4, par5EntityPlayer);

        if (flag)
        {
            par5EntityPlayer.addStat(StatList.objectUseStats[itemID], 1);
        }
    }

    /**
     * Returns the damage against a given entity.
     */
    public int getDamageVsEntity(Entity par1Entity)
    {
        return Item.itemsList[itemID].getDamageVsEntity(par1Entity);
    }

    /**
     * Checks if the itemStack object can harvest a specified block
     */
    public boolean canHarvestBlock(Block par1Block)
    {
        return Item.itemsList[itemID].canHarvestBlock(par1Block);
    }

    /**
     * Called when a given item stack is about to be destroyed due to its damage level expiring when used on a block or
     * entity. Typically used by tools.
     */
    public void onItemDestroyedByUse(EntityPlayer entityplayer)
    {
    }

    /**
     * Uses the stack on the entity.
     */
    public void useItemOnEntity(EntityLiving par1EntityLiving)
    {
        Item.itemsList[itemID].useItemOnEntity(this, par1EntityLiving);
    }

    /**
     * Returns a new stack with the same properties.
     */
    public ItemStack copy()
    {
        ItemStack itemstack = new ItemStack(itemID, stackSize, itemDamage);

        if (stackTagCompound != null)
        {
            itemstack.stackTagCompound = (NBTTagCompound)stackTagCompound.copy();

            if (!itemstack.stackTagCompound.equals(stackTagCompound))
            {
                return itemstack;
            }
        }

        return itemstack;
    }

    public static boolean func_46154_a(ItemStack par0ItemStack, ItemStack par1ItemStack)
    {
        if (par0ItemStack == null && par1ItemStack == null)
        {
            return true;
        }

        if (par0ItemStack == null || par1ItemStack == null)
        {
            return false;
        }

        if (par0ItemStack.stackTagCompound == null && par1ItemStack.stackTagCompound != null)
        {
            return false;
        }

        return par0ItemStack.stackTagCompound == null || par0ItemStack.stackTagCompound.equals(par1ItemStack.stackTagCompound);
    }

    /**
     * compares ItemStack argument1 with ItemStack argument2; returns true if both ItemStacks are equal
     */
    public static boolean areItemStacksEqual(ItemStack par0ItemStack, ItemStack par1ItemStack)
    {
        if (par0ItemStack == null && par1ItemStack == null)
        {
            return true;
        }

        if (par0ItemStack == null || par1ItemStack == null)
        {
            return false;
        }
        else
        {
            return par0ItemStack.isItemStackEqual(par1ItemStack);
        }
    }

    /**
     * compares ItemStack argument to the instance ItemStack; returns true if both ItemStacks are equal
     */
    private boolean isItemStackEqual(ItemStack par1ItemStack)
    {
        if (stackSize != par1ItemStack.stackSize)
        {
            return false;
        }

        if (itemID != par1ItemStack.itemID)
        {
            return false;
        }

        if (itemDamage != par1ItemStack.itemDamage)
        {
            return false;
        }

        if (stackTagCompound == null && par1ItemStack.stackTagCompound != null)
        {
            return false;
        }

        return stackTagCompound == null || stackTagCompound.equals(par1ItemStack.stackTagCompound);
    }

    /**
     * compares ItemStack argument to the instance ItemStack; returns true if the Items contained in both ItemStacks are
     * equal
     */
    public boolean isItemEqual(ItemStack par1ItemStack)
    {
        return itemID == par1ItemStack.itemID && itemDamage == par1ItemStack.itemDamage;
    }

    /**
     * Creates a copy of a ItemStack, a null parameters will return a null.
     */
    public static ItemStack copyItemStack(ItemStack par0ItemStack)
    {
        return par0ItemStack != null ? par0ItemStack.copy() : null;
    }

    public String toString()
    {
        return (new StringBuilder()).append(stackSize).append("x").append(Item.itemsList[itemID].getItemName()).append("@").append(itemDamage).toString();
    }

    /**
     * Called each tick as long the ItemStack in on player inventory. Used to progress the pickup animation and update
     * maps.
     */
    public void updateAnimation(World par1World, Entity par2Entity, int par3, boolean par4)
    {
        if (animationsToGo > 0)
        {
            animationsToGo--;
        }

        Item.itemsList[itemID].onUpdate(this, par1World, par2Entity, par3, par4);
    }

    public void onCrafting(World par1World, EntityPlayer par2EntityPlayer, int par3)
    {
        par2EntityPlayer.addStat(StatList.objectCraftStats[itemID], par3);
        Item.itemsList[itemID].onCreated(this, par1World, par2EntityPlayer);
    }

    public boolean isStackEqual(ItemStack par1ItemStack)
    {
        return itemID == par1ItemStack.itemID && stackSize == par1ItemStack.stackSize && itemDamage == par1ItemStack.itemDamage;
    }

    public int getMaxItemUseDuration()
    {
        return getItem().getMaxItemUseDuration(this);
    }

    public EnumAction getItemUseAction()
    {
        return getItem().getItemUseAction(this);
    }

    /**
     * called when the player releases the use item button. Args: world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(World par1World, EntityPlayer par2EntityPlayer, int par3)
    {
        getItem().onPlayerStoppedUsing(this, par1World, par2EntityPlayer, par3);
    }

    /**
     * Returns true if the ItemStack have a NBTTagCompound. Used currently to store enchantments.
     */
    public boolean hasTagCompound()
    {
        return stackTagCompound != null;
    }

    /**
     * Returns the NBTTagCompound of the ItemStack.
     */
    public NBTTagCompound getTagCompound()
    {
        return stackTagCompound;
    }

    public NBTTagList getEnchantmentTagList()
    {
        if (stackTagCompound == null)
        {
            return null;
        }
        else
        {
            return (NBTTagList)stackTagCompound.getTag("ench");
        }
    }

    /**
     * Assigns a NBTTagCompound to the ItemStack, minecraft validates that only non-stackable items can have it.
     */
    public void setTagCompound(NBTTagCompound par1NBTTagCompound)
    {
        stackTagCompound = par1NBTTagCompound;
    }

    /**
     * gets a list of strings representing the item name and successive extra data, eg Enchantments and potion effects
     */
    public List getItemNameandInformation()
    {
        ArrayList arraylist = new ArrayList();
        Item item = Item.itemsList[itemID];
        arraylist.add(item.getItemDisplayName(this));
        item.addInformation(this, arraylist);

        if (hasTagCompound())
        {
            NBTTagList nbttaglist = getEnchantmentTagList();

            if (nbttaglist != null)
            {
                for (int i = 0; i < nbttaglist.tagCount(); i++)
                {
                    short word0 = ((NBTTagCompound)nbttaglist.tagAt(i)).getShort("id");
                    short word1 = ((NBTTagCompound)nbttaglist.tagAt(i)).getShort("lvl");

                    if (Enchantment.enchantmentsList[word0] != null)
                    {
                        arraylist.add(Enchantment.enchantmentsList[word0].getTranslatedName(word1));
                    }
                }
            }
        }

        return arraylist;
    }

    public boolean hasEffect()
    {
        return getItem().hasEffect(this);
    }

    public EnumRarity getRarity()
    {
        return getItem().getRarity(this);
    }

    /**
     * True if it is a tool and has no enchantments to begin with
     */
    public boolean isItemEnchantable()
    {
        if (!getItem().isItemTool(this))
        {
            return false;
        }

        return !isItemEnchanted();
    }

    /**
     * Adds a enchantments with a desired level on the ItemStack.
     */
    public void addEnchantment(Enchantment par1Enchantment, int par2)
    {
        if (stackTagCompound == null)
        {
            setTagCompound(new NBTTagCompound());
        }

        if (!stackTagCompound.hasKey("ench"))
        {
            stackTagCompound.setTag("ench", new NBTTagList("ench"));
        }

        NBTTagList nbttaglist = (NBTTagList)stackTagCompound.getTag("ench");
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setShort("id", (short)par1Enchantment.effectId);
        nbttagcompound.setShort("lvl", (byte)par2);
        nbttaglist.appendTag(nbttagcompound);
    }

    /**
     * True if the item has enchantment data
     */
    public boolean isItemEnchanted()
    {
        return stackTagCompound != null && stackTagCompound.hasKey("ench");
    }
}
