package net.minecraft.src;

public class EnchantmentProtection extends Enchantment
{
    private static final String protectionName[] =
    {
        "all", "fire", "fall", "explosion", "projectile"
    };
    private static final int baseEnchantability[] =
    {
        1, 10, 5, 5, 3
    };
    private static final int levelEnchantability[] =
    {
        16, 8, 6, 8, 6
    };
    private static final int thresholdEnchantability[] =
    {
        20, 12, 10, 12, 15
    };

    /**
     * Defines the type of protection of the enchantment, 0 = all, 1 = fire, 2 = fall (feather fall), 3 = explosion and
     * 4 = projectile.
     */
    public final int protectionType;

    public EnchantmentProtection(int par1, int par2, int par3)
    {
        super(par1, par2, EnumEnchantmentType.armor);
        protectionType = par3;

        if (par3 == 2)
        {
            type = EnumEnchantmentType.armor_feet;
        }
    }

    /**
     * Returns the minimal value of enchantability nedded on the enchantment level passed.
     */
    public int getMinEnchantability(int par1)
    {
        return baseEnchantability[protectionType] + (par1 - 1) * levelEnchantability[protectionType];
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int par1)
    {
        return getMinEnchantability(par1) + thresholdEnchantability[protectionType];
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 4;
    }

    /**
     * Calculates de damage protection of the enchantment based on level and damage source passed.
     */
    public int calcModifierDamage(int par1, DamageSource par2DamageSource)
    {
        if (par2DamageSource.canHarmInCreative())
        {
            return 0;
        }

        int i = (6 + par1 * par1) / 2;

        if (protectionType == 0)
        {
            return i;
        }

        if (protectionType == 1 && par2DamageSource.fireDamage())
        {
            return i;
        }

        if (protectionType == 2 && par2DamageSource == DamageSource.fall)
        {
            return i * 2;
        }

        if (protectionType == 3 && par2DamageSource == DamageSource.explosion)
        {
            return i;
        }

        if (protectionType == 4 && par2DamageSource.isProjectile())
        {
            return i;
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
        return (new StringBuilder()).append("enchantment.protect.").append(protectionName[protectionType]).toString();
    }

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean canApplyTogether(Enchantment par1Enchantment)
    {
        if (par1Enchantment instanceof EnchantmentProtection)
        {
            EnchantmentProtection enchantmentprotection = (EnchantmentProtection)par1Enchantment;

            if (enchantmentprotection.protectionType == protectionType)
            {
                return false;
            }

            return protectionType == 2 || enchantmentprotection.protectionType == 2;
        }
        else
        {
            return super.canApplyTogether(par1Enchantment);
        }
    }
}
