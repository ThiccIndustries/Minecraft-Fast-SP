package net.minecraft.src;

interface IEnchantmentModifier
{
    /**
     * Generic method use to calculate modifiers of offensive or defensive enchantment values.
     */
    public abstract void calculateModifier(Enchantment enchantment, int i);
}
