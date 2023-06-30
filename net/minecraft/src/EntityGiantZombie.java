package net.minecraft.src;

public class EntityGiantZombie extends EntityMob
{
    public EntityGiantZombie(World par1World)
    {
        super(par1World);
        texture = "/mob/zombie.png";
        moveSpeed = 0.5F;
        attackStrength = 50;
        yOffset *= 6F;
        setSize(width * 6F, height * 6F);
    }

    public int getMaxHealth()
    {
        return 100;
    }

    /**
     * Takes a coordinate in and returns a weight to determine how likely this creature will try to path to the block.
     * Args: x, y, z
     */
    public float getBlockPathWeight(int par1, int par2, int par3)
    {
        return worldObj.getLightBrightness(par1, par2, par3) - 0.5F;
    }
}
