package net.minecraft.src;

import java.util.*;

public class ItemPotion extends Item
{
    /** maps potion damage values to lists of effect names */
    private HashMap effectCache;

    public ItemPotion(int par1)
    {
        super(par1);
        effectCache = new HashMap();
        setMaxStackSize(1);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    /**
     * Returns a list of potion effects for the specified itemstack.
     */
    public List getEffects(ItemStack par1ItemStack)
    {
        return getEffects(par1ItemStack.getItemDamage());
    }

    /**
     * Returns a list of effects for the specified potion damage value.
     */
    public List getEffects(int par1)
    {
        List list = (List)effectCache.get(Integer.valueOf(par1));

        if (list == null)
        {
            list = PotionHelper.getPotionEffects(par1, false);
            effectCache.put(Integer.valueOf(par1), list);
        }

        return list;
    }

    public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par1ItemStack.stackSize--;

        if (!par2World.isRemote)
        {
            List list = getEffects(par1ItemStack);

            if (list != null)
            {
                PotionEffect potioneffect;

                for (Iterator iterator = list.iterator(); iterator.hasNext(); par3EntityPlayer.addPotionEffect(new PotionEffect(potioneffect)))
                {
                    potioneffect = (PotionEffect)iterator.next();
                }
            }
        }

        if (par1ItemStack.stackSize <= 0)
        {
            return new ItemStack(Item.glassBottle);
        }
        else
        {
            par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
            return par1ItemStack;
        }
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.drink;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (isSplash(par1ItemStack.getItemDamage()))
        {
            par1ItemStack.stackSize--;
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

            if (!par2World.isRemote)
            {
                par2World.spawnEntityInWorld(new EntityPotion(par2World, par3EntityPlayer, par1ItemStack.getItemDamage()));
            }

            return par1ItemStack;
        }
        else
        {
            par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
            return par1ItemStack;
        }
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int i, int j, int k, int l)
    {
        return false;
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int par1)
    {
        return !isSplash(par1) ? 140 : 154;
    }

    public int func_46057_a(int par1, int par2)
    {
        if (par2 == 0)
        {
            return 141;
        }
        else
        {
            return super.func_46057_a(par1, par2);
        }
    }

    /**
     * returns wether or not a potion is a throwable splash potion based on damage value
     */
    public static boolean isSplash(int par0)
    {
        return (par0 & 0x4000) != 0;
    }

    public int getColorFromDamage(int par1, int par2)
    {
        if (par2 > 0)
        {
            return 0xffffff;
        }
        else
        {
            return PotionHelper.func_40358_a(par1, false);
        }
    }

    public boolean func_46058_c()
    {
        return true;
    }

    public boolean isEffectInstant(int par1)
    {
        List list = getEffects(par1);

        if (list == null || list.isEmpty())
        {
            return false;
        }

        for (Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            PotionEffect potioneffect = (PotionEffect)iterator.next();

            if (Potion.potionTypes[potioneffect.getPotionID()].isInstant())
            {
                return true;
            }
        }

        return false;
    }

    public String getItemDisplayName(ItemStack par1ItemStack)
    {
        if (par1ItemStack.getItemDamage() == 0)
        {
            return StatCollector.translateToLocal("item.emptyPotion.name").trim();
        }

        String s = "";

        if (isSplash(par1ItemStack.getItemDamage()))
        {
            s = (new StringBuilder()).append(StatCollector.translateToLocal("potion.prefix.grenade").trim()).append(" ").toString();
        }

        List list = Item.potion.getEffects(par1ItemStack);

        if (list != null && !list.isEmpty())
        {
            String s1 = ((PotionEffect)list.get(0)).getEffectName();
            s1 = (new StringBuilder()).append(s1).append(".postfix").toString();
            return (new StringBuilder()).append(s).append(StatCollector.translateToLocal(s1).trim()).toString();
        }
        else
        {
            String s2 = PotionHelper.func_40359_b(par1ItemStack.getItemDamage());
            return (new StringBuilder()).append(StatCollector.translateToLocal(s2).trim()).append(" ").append(super.getItemDisplayName(par1ItemStack)).toString();
        }
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, List par2List)
    {
        if (par1ItemStack.getItemDamage() == 0)
        {
            return;
        }

        List list = Item.potion.getEffects(par1ItemStack);

        if (list != null && !list.isEmpty())
        {
            for (Iterator iterator = list.iterator(); iterator.hasNext();)
            {
                PotionEffect potioneffect = (PotionEffect)iterator.next();
                String s1 = StatCollector.translateToLocal(potioneffect.getEffectName()).trim();

                if (potioneffect.getAmplifier() > 0)
                {
                    s1 = (new StringBuilder()).append(s1).append(" ").append(StatCollector.translateToLocal((new StringBuilder()).append("potion.potency.").append(potioneffect.getAmplifier()).toString()).trim()).toString();
                }

                if (potioneffect.getDuration() > 20)
                {
                    s1 = (new StringBuilder()).append(s1).append(" (").append(Potion.getDurationString(potioneffect)).append(")").toString();
                }

                if (Potion.potionTypes[potioneffect.getPotionID()].isBadEffect())
                {
                    par2List.add((new StringBuilder()).append("\247c").append(s1).toString());
                }
                else
                {
                    par2List.add((new StringBuilder()).append("\2477").append(s1).toString());
                }
            }
        }
        else
        {
            String s = StatCollector.translateToLocal("potion.empty").trim();
            par2List.add((new StringBuilder()).append("\2477").append(s).toString());
        }
    }

    public boolean hasEffect(ItemStack par1ItemStack)
    {
        List list = getEffects(par1ItemStack);
        return list != null && !list.isEmpty();
    }
}
