package net.minecraft.src;

public class BlockSponge extends Block
{
    protected BlockSponge(int par1)
    {
        super(par1, Material.sponge);
        blockIndexInTexture = 48;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int i, int j, int k)
    {
    }

    /**
     * Called whenever the block is removed.
     */
    public void onBlockRemoval(World world, int i, int j, int k)
    {
    }
}
