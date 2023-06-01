package net.minecraft.src;

public class ItemTool extends Item
{
    private Block blocksEffectiveAgainst[];
    protected float efficiencyOnProperMaterial;

    /** Damage versus entities. */
    private int damageVsEntity;

    /** The material this tool is made from. */
    protected EnumToolMaterial toolMaterial;

    protected ItemTool(int par1, int par2, EnumToolMaterial par3EnumToolMaterial, Block par4ArrayOfBlock[])
    {
        super(par1);
        efficiencyOnProperMaterial = 4F;
        toolMaterial = par3EnumToolMaterial;
        blocksEffectiveAgainst = par4ArrayOfBlock;
        maxStackSize = 1;
        setMaxDamage(par3EnumToolMaterial.getMaxUses());
        efficiencyOnProperMaterial = par3EnumToolMaterial.getEfficiencyOnProperMaterial();
        damageVsEntity = par2 + par3EnumToolMaterial.getDamageVsEntity();
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        for (int i = 0; i < blocksEffectiveAgainst.length; i++)
        {
            if (blocksEffectiveAgainst[i] == par2Block)
            {
                return efficiencyOnProperMaterial;
            }
        }

        return 1.0F;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
    {
        par1ItemStack.damageItem(2, par3EntityLiving);
        return true;
    }

    public boolean onBlockDestroyed(ItemStack par1ItemStack, int par2, int par3, int par4, int par5, EntityLiving par6EntityLiving)
    {
        par1ItemStack.damageItem(1, par6EntityLiving);
        return true;
    }

    /**
     * Returns the damage against a given entity.
     */
    public int getDamageVsEntity(Entity par1Entity)
    {
        return damageVsEntity;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return true;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return toolMaterial.getEnchantability();
    }
}
