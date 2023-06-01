package net.minecraft.src;

public class BlockSoulSand extends Block
{
    public BlockSoulSand(int par1, int par2)
    {
        super(par1, par2, Material.sand);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        float f = 0.125F;
        return AxisAlignedBB.getBoundingBoxFromPool(par2, par3, par4, par2 + 1, (float)(par3 + 1) - f, par4 + 1);
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        par5Entity.motionX *= 0.40000000000000002D;
        par5Entity.motionZ *= 0.40000000000000002D;
    }
}
