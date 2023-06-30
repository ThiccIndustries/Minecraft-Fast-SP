package net.minecraft.src;

public class ItemPainting extends Item
{
    public ItemPainting(int par1)
    {
        super(par1);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7)
    {
        if (par7 == 0)
        {
            return false;
        }

        if (par7 == 1)
        {
            return false;
        }

        byte byte0 = 0;

        if (par7 == 4)
        {
            byte0 = 1;
        }

        if (par7 == 3)
        {
            byte0 = 2;
        }

        if (par7 == 5)
        {
            byte0 = 3;
        }

        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6))
        {
            return false;
        }

        EntityPainting entitypainting = new EntityPainting(par3World, par4, par5, par6, byte0);

        if (entitypainting.onValidSurface())
        {
            if (!par3World.isRemote)
            {
                par3World.spawnEntityInWorld(entitypainting);
            }

            par1ItemStack.stackSize--;
        }

        return true;
    }
}
