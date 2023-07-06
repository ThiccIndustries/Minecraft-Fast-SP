package net.minecraft.src;

import java.util.*;

public class EnchantmentHelper
{
    /** Is the random seed of enchantment effects. */
    private static final Random enchantmentRand = new Random();

    /**
     * Used to calculate the extra armor of enchantments on armors equipped on player.
     */
    private static final EnchantmentModifierDamage enchantmentModifierDamage = new EnchantmentModifierDamage(null);

    /**
     * Used to calculate the (magic) extra damage done by enchantments on current equipped item of player.
     */
    private static final EnchantmentModifierLiving enchantmentModifierLiving = new EnchantmentModifierLiving(null);

    public EnchantmentHelper()
    {
    }

    /**
     * Returns the level of enchantment on the ItemStack passed.
     */
    public static int getEnchantmentLevel(int par0, ItemStack par1ItemStack)
    {
        if (par1ItemStack == null)
        {
            return 0;
        }

        NBTTagList nbttaglist = par1ItemStack.getEnchantmentTagList();

        if (nbttaglist == null)
        {
            return 0;
        }

        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            short word0 = ((NBTTagCompound)nbttaglist.tagAt(i)).getShort("id");
            short word1 = ((NBTTagCompound)nbttaglist.tagAt(i)).getShort("lvl");

            if (word0 == par0)
            {
                return word1;
            }
        }

        return 0;
    }

    /**
     * Returns the biggest level of the enchantment on the array of ItemStack passed.
     */
    private static int getMaxEnchantmentLevel(int par0, ItemStack par1ArrayOfItemStack[])
    {
        int i = 0;
        ItemStack aitemstack[] = par1ArrayOfItemStack;
        int j = aitemstack.length;

        for (int k = 0; k < j; k++)
        {
            ItemStack itemstack = aitemstack[k];
            int l = getEnchantmentLevel(par0, itemstack);

            if (l > i)
            {
                i = l;
            }
        }

        return i;
    }

    /**
     * Executes the enchantment modifier on the ItemStack passed.
     */
    private static void applyEnchantmentModifier(IEnchantmentModifier par0IEnchantmentModifier, ItemStack par1ItemStack)
    {
        if (par1ItemStack == null)
        {
            return;
        }

        NBTTagList nbttaglist = par1ItemStack.getEnchantmentTagList();

        if (nbttaglist == null)
        {
            return;
        }

        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            short word0 = ((NBTTagCompound)nbttaglist.tagAt(i)).getShort("id");
            short word1 = ((NBTTagCompound)nbttaglist.tagAt(i)).getShort("lvl");

            if (Enchantment.enchantmentsList[word0] != null)
            {
                par0IEnchantmentModifier.calculateModifier(Enchantment.enchantmentsList[word0], word1);
            }
        }
    }

    /**
     * Executes the enchantment modifier on the array of ItemStack passed.
     */
    private static void applyEnchantmentModifierArray(IEnchantmentModifier par0IEnchantmentModifier, ItemStack par1ArrayOfItemStack[])
    {
        ItemStack aitemstack[] = par1ArrayOfItemStack;
        int i = aitemstack.length;

        for (int j = 0; j < i; j++)
        {
            ItemStack itemstack = aitemstack[j];
            applyEnchantmentModifier(par0IEnchantmentModifier, itemstack);
        }
    }

    /**
     * Returns the modifier of protection enchantments on armors equipped on player.
     */
    public static int getEnchantmentModifierDamage(InventoryPlayer par0InventoryPlayer, DamageSource par1DamageSource)
    {
        enchantmentModifierDamage.damageModifier = 0;
        enchantmentModifierDamage.damageSource = par1DamageSource;
        applyEnchantmentModifierArray(enchantmentModifierDamage, par0InventoryPlayer.armorInventory);

        if (enchantmentModifierDamage.damageModifier > 25)
        {
            enchantmentModifierDamage.damageModifier = 25;
        }

        return (enchantmentModifierDamage.damageModifier + 1 >> 1) + enchantmentRand.nextInt((enchantmentModifierDamage.damageModifier >> 1) + 1);
    }

    /**
     * Return the (magic) extra damage of the enchantments on player equipped item.
     */
    public static int getEnchantmentModifierLiving(InventoryPlayer par0InventoryPlayer, EntityLiving par1EntityLiving)
    {
        enchantmentModifierLiving.livingModifier = 0;
        enchantmentModifierLiving.entityLiving = par1EntityLiving;
        applyEnchantmentModifier(enchantmentModifierLiving, par0InventoryPlayer.getCurrentItem());

        if (enchantmentModifierLiving.livingModifier > 0)
        {
            return 1 + enchantmentRand.nextInt(enchantmentModifierLiving.livingModifier);
        }
        else
        {
            return 0;
        }
    }

    /**
     * Returns the knockback value of enchantments on equipped player item.
     */
    public static int getKnockbackModifier(InventoryPlayer par0InventoryPlayer, EntityLiving par1EntityLiving)
    {
        return getEnchantmentLevel(Enchantment.knockback.effectId, par0InventoryPlayer.getCurrentItem());
    }

    /**
     * Return the fire aspect value of enchantments on equipped player item.
     */
    public static int getFireAspectModifier(InventoryPlayer par0InventoryPlayer, EntityLiving par1EntityLiving)
    {
        return getEnchantmentLevel(Enchantment.fireAspect.effectId, par0InventoryPlayer.getCurrentItem());
    }

    /**
     * Returns the 'Water Breathing' modifier of enchantments on player equipped armors.
     */
    public static int getRespiration(InventoryPlayer par0InventoryPlayer)
    {
        return getMaxEnchantmentLevel(Enchantment.respiration.effectId, par0InventoryPlayer.armorInventory);
    }

    /**
     * Return the extra efficiency of tools based on enchantments on equipped player item.
     */
    public static int getEfficiencyModifier(InventoryPlayer par0InventoryPlayer)
    {
        return getEnchantmentLevel(Enchantment.efficiency.effectId, par0InventoryPlayer.getCurrentItem());
    }

    /**
     * Returns the unbreaking enchantment modifier on current equipped item of player.
     */
    public static int getUnbreakingModifier(InventoryPlayer par0InventoryPlayer)
    {
        return getEnchantmentLevel(Enchantment.unbreaking.effectId, par0InventoryPlayer.getCurrentItem());
    }

    /**
     * Returns the silk touch status of enchantments on current equipped item of player.
     */
    public static boolean getSilkTouchModifier(InventoryPlayer par0InventoryPlayer)
    {
        return getEnchantmentLevel(Enchantment.silkTouch.effectId, par0InventoryPlayer.getCurrentItem()) > 0;
    }

    /**
     * Returns the fortune enchantment modifier of the current equipped item of player.
     */
    public static int getFortuneModifier(InventoryPlayer par0InventoryPlayer)
    {
        return getEnchantmentLevel(Enchantment.fortune.effectId, par0InventoryPlayer.getCurrentItem());
    }

    /**
     * Returns the looting enchantment modifier of the current equipped item of player.
     */
    public static int getLootingModifier(InventoryPlayer par0InventoryPlayer)
    {
        return getEnchantmentLevel(Enchantment.looting.effectId, par0InventoryPlayer.getCurrentItem());
    }

    /**
     * Returns the aqua affinity status of enchantments on current equipped item of player.
     */
    public static boolean getAquaAffinityModifier(InventoryPlayer par0InventoryPlayer)
    {
        return getMaxEnchantmentLevel(Enchantment.aquaAffinity.effectId, par0InventoryPlayer.armorInventory) > 0;
    }

    /**
     * Returns the enchantability of itemstack, it's uses a singular formula for each index (2nd parameter: 0, 1 and 2),
     * cutting to the max enchantability power of the table (3rd parameter)
     */
    public static int calcItemStackEnchantability(Random par0Random, int par1, int par2, ItemStack par3ItemStack)
    {
        Item item = par3ItemStack.getItem();
        int i = item.getItemEnchantability();

        if (i <= 0)
        {
            return 0;
        }

        if (par2 > 30)
        {
            par2 = 30;
        }

        par2 = 1 + (par2 >> 1) + par0Random.nextInt(par2 + 1);
        int j = par0Random.nextInt(5) + par2;

        if (par1 == 0)
        {
            return (j >> 1) + 1;
        }

        if (par1 == 1)
        {
            return (j * 2) / 3 + 1;
        }
        else
        {
            return j;
        }
    }

    public static void func_48441_a(Random par0Random, ItemStack par1ItemStack, int par2)
    {
        List list = buildEnchantmentList(par0Random, par1ItemStack, par2);

        if (list != null)
        {
            EnchantmentData enchantmentdata;

            for (Iterator iterator = list.iterator(); iterator.hasNext(); par1ItemStack.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel))
            {
                enchantmentdata = (EnchantmentData)iterator.next();
            }
        }
    }

    /**
     * Create a list of random EnchantmentData (enchantments) that can be added together to the ItemStack, the 3rd
     * parameter is the total enchantability level.
     */
    public static List buildEnchantmentList(Random par0Random, ItemStack par1ItemStack, int par2)
    {
        Item item = par1ItemStack.getItem();
        int i = item.getItemEnchantability();

        if (i <= 0)
        {
            return null;
        }

        i = 1 + par0Random.nextInt((i >> 1) + 1) + par0Random.nextInt((i >> 1) + 1);
        int j = i + par2;
        float f = ((par0Random.nextFloat() + par0Random.nextFloat()) - 1.0F) * 0.25F;
        int k = (int)((float)j * (1.0F + f) + 0.5F);
        ArrayList arraylist = null;
        Map map = mapEnchantmentData(k, par1ItemStack);

        if (map != null && !map.isEmpty())
        {
            EnchantmentData enchantmentdata = (EnchantmentData)WeightedRandom.getRandomItem(par0Random, map.values());

            if (enchantmentdata != null)
            {
                arraylist = new ArrayList();
                arraylist.add(enchantmentdata);

                for (int l = k >> 1; par0Random.nextInt(50) <= l; l >>= 1)
                {
                    Iterator iterator = map.keySet().iterator();

                    do
                    {
                        if (!iterator.hasNext())
                        {
                            break;
                        }

                        Integer integer = (Integer)iterator.next();
                        boolean flag = true;
                        Iterator iterator1 = arraylist.iterator();

                        do
                        {
                            if (!iterator1.hasNext())
                            {
                                break;
                            }

                            EnchantmentData enchantmentdata2 = (EnchantmentData)iterator1.next();

                            if (enchantmentdata2.enchantmentobj.canApplyTogether(Enchantment.enchantmentsList[integer.intValue()]))
                            {
                                continue;
                            }

                            flag = false;
                            break;
                        }
                        while (true);

                        if (!flag)
                        {
                            iterator.remove();
                        }
                    }
                    while (true);

                    if (!map.isEmpty())
                    {
                        EnchantmentData enchantmentdata1 = (EnchantmentData)WeightedRandom.getRandomItem(par0Random, map.values());
                        arraylist.add(enchantmentdata1);
                    }
                }
            }
        }

        return arraylist;
    }

    /**
     * Creates a 'Map' of EnchantmentData (enchantments) possible to add on the ItemStack and the enchantability level
     * passed.
     */
    public static Map mapEnchantmentData(int par0, ItemStack par1ItemStack)
    {
        Item item = par1ItemStack.getItem();
        HashMap hashmap = null;
        Enchantment aenchantment[] = Enchantment.enchantmentsList;
        int i = aenchantment.length;

        for (int j = 0; j < i; j++)
        {
            Enchantment enchantment = aenchantment[j];

            if (enchantment == null || !enchantment.type.canEnchantItem(item))
            {
                continue;
            }

            for (int k = enchantment.getMinLevel(); k <= enchantment.getMaxLevel(); k++)
            {
                if (par0 < enchantment.getMinEnchantability(k) || par0 > enchantment.getMaxEnchantability(k))
                {
                    continue;
                }

                if (hashmap == null)
                {
                    hashmap = new HashMap();
                }

                hashmap.put(Integer.valueOf(enchantment.effectId), new EnchantmentData(enchantment, k));
            }
        }

        return hashmap;
    }
}
