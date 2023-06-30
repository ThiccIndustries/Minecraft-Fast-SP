package net.minecraft.src;

import java.util.Random;

public class EntityHugeExplodeFX extends EntityFX
{
    private int timeSinceStart;

    /** the maximum time for the explosion */
    private int maximumTime;

    public EntityHugeExplodeFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
        timeSinceStart = 0;
        maximumTime = 0;
        maximumTime = 8;
    }

    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5)
    {
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        for (int i = 0; i < 6; i++)
        {
            double d = posX + (rand.nextDouble() - rand.nextDouble()) * 4D;
            double d1 = posY + (rand.nextDouble() - rand.nextDouble()) * 4D;
            double d2 = posZ + (rand.nextDouble() - rand.nextDouble()) * 4D;
            worldObj.spawnParticle("largeexplode", d, d1, d2, (float)timeSinceStart / (float)maximumTime, 0.0D, 0.0D);
        }

        timeSinceStart++;

        if (timeSinceStart == maximumTime)
        {
            setDead();
        }
    }

    public int getFXLayer()
    {
        return 1;
    }
}
