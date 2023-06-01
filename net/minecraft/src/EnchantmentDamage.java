package net.minecraft.src;

public class EnchantmentDamage extends Enchantment
{
    private static final String protectionName[] =
    {
        "all", "undead", "arthropods"
    };
    private static final int baseEnchantability[] =
    {
        1, 5, 5
    };
    private static final int levelEnchantability[] =
    {
        16, 8, 8
    };
    private static final int thresholdEnchantability[] =
    {
        20, 20, 20
    };

    /**
     * Defines the type of damage of the enchantment, 0 = all, 1 = undead, 3 = arthropods
     */
    public final int damageType;

    public EnchantmentDamage(int par1, int par2, int par3)
    {
        super(par1, par2, EnumEnchantmentType.weapon);
        damageType = par3;
    }

    /**
     * Returns the minimal value of enchantability nedded on the enchantment level passed.
     */
    public int getMinEnchantability(int par1)
    {
        return baseEnchantability[damageType] + (par1 - 1) * levelEnchantability[damageType];
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int par1)
    {
        return getMinEnchantability(par1) + thresholdEnchantability[damageType];
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 5;
    }

    /**
     * Calculates de (magic) damage done by the enchantment on a living entity based on level and entity passed.
     */
    public int calcModifierLiving(int par1, EntityLiving par2EntityLiving)
    {
        if (damageType == 0)
        {
            return par1 * 3;
        }

        if (damageType == 1 && par2EntityLiving.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
        {
            return par1 * 4;
        }

        if (damageType == 2 && par2EntityLiving.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD)
        {
            return par1 * 4;
        }
        else
        {
            return 0;
        }
    }

    /**
     * Return the name of key in translation table of this enchantment.
     */
    public String getName()
    {
        return (new StringBuilder()).append("enchantment.damage.").append(protectionName[damageType]).toString();
    }

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean canApplyTogether(Enchantment par1Enchantment)
    {
        return !(par1Enchantment instanceof EnchantmentDamage);
    }
}
