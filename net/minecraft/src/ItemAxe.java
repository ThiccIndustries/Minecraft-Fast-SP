package net.minecraft.src;

public class ItemAxe extends ItemTool
{
    private static Block blocksEffectiveAgainst[];

    protected ItemAxe(int par1, EnumToolMaterial par2EnumToolMaterial)
    {
        super(par1, 3, par2EnumToolMaterial, blocksEffectiveAgainst);
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        if (par2Block != null && par2Block.blockMaterial == Material.wood)
        {
            return efficiencyOnProperMaterial;
        }
        else
        {
            return super.getStrVsBlock(par1ItemStack, par2Block);
        }
    }

    static
    {
        blocksEffectiveAgainst = (new Block[]
                {
                    Block.planks, Block.bookShelf, Block.wood, Block.chest, Block.stairDouble, Block.stairSingle, Block.pumpkin, Block.pumpkinLantern
                });
    }
}
