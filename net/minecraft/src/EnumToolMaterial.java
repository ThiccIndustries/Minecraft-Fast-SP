package net.minecraft.src;

public enum EnumToolMaterial
{
    WOOD(0, 59, 2.0F, 0, 15),
    STONE(1, 131, 4F, 1, 5),
    IRON(2, 250, 6F, 2, 14),
    EMERALD(3, 1561, 8F, 3, 10),
    GOLD(0, 32, 12F, 0, 22);

    /**
     * The level of material this tool can harvest (3 = DIAMOND, 2 = IRON, 1 = STONE, 0 = IRON/GOLD)
     */
    private final int harvestLevel;

    /**
     * The number of uses this material allows. (wood = 59, stone = 131, iron = 250, diamond = 1561, gold = 32)
     */
    private final int maxUses;

    /**
     * The strength of this tool material against blocks which it is effective against.
     */
    private final float efficiencyOnProperMaterial;

    /** Damage versus entities. */
    private final int damageVsEntity;

    /** Defines the natural enchantability factor of the material. */
    private final int enchantability;

    private EnumToolMaterial(int par3, int par4, float par5, int par6, int par7)
    {
        harvestLevel = par3;
        maxUses = par4;
        efficiencyOnProperMaterial = par5;
        damageVsEntity = par6;
        enchantability = par7;
    }

    /**
     * The number of uses this material allows. (wood = 59, stone = 131, iron = 250, diamond = 1561, gold = 32)
     */
    public int getMaxUses()
    {
        return maxUses;
    }

    /**
     * The strength of this tool material against blocks which it is effective against.
     */
    public float getEfficiencyOnProperMaterial()
    {
        return efficiencyOnProperMaterial;
    }

    /**
     * Damage versus entities.
     */
    public int getDamageVsEntity()
    {
        return damageVsEntity;
    }

    /**
     * The level of material this tool can harvest (3 = DIAMOND, 2 = IRON, 1 = STONE, 0 = IRON/GOLD)
     */
    public int getHarvestLevel()
    {
        return harvestLevel;
    }

    /**
     * Return the natural enchantability factor of the material.
     */
    public int getEnchantability()
    {
        return enchantability;
    }
}
