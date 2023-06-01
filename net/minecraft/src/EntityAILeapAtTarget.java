package net.minecraft.src;

import java.util.Random;

public class EntityAILeapAtTarget extends EntityAIBase
{
    /** The entity that is leaping. */
    EntityLiving leaper;

    /** The entity that the leaper is leaping towards. */
    EntityLiving leapTarget;

    /** The entity's motionY after leaping. */
    float leapMotionY;

    public EntityAILeapAtTarget(EntityLiving par1EntityLiving, float par2)
    {
        leaper = par1EntityLiving;
        leapMotionY = par2;
        setMutexBits(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        leapTarget = leaper.getAttackTarget();

        if (leapTarget == null)
        {
            return false;
        }

        double d = leaper.getDistanceSqToEntity(leapTarget);

        if (d < 4D || d > 16D)
        {
            return false;
        }

        if (!leaper.onGround)
        {
            return false;
        }

        return leaper.getRNG().nextInt(5) == 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !leaper.onGround;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        double d = leapTarget.posX - leaper.posX;
        double d1 = leapTarget.posZ - leaper.posZ;
        float f = MathHelper.sqrt_double(d * d + d1 * d1);
        leaper.motionX += (d / (double)f) * 0.5D * 0.80000001192092896D + leaper.motionX * 0.20000000298023224D;
        leaper.motionZ += (d1 / (double)f) * 0.5D * 0.80000001192092896D + leaper.motionZ * 0.20000000298023224D;
        leaper.motionY = leapMotionY;
    }
}
