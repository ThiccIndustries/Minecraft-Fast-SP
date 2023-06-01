package net.minecraft.src;

import java.util.Random;

public class EntityAILookIdle extends EntityAIBase
{
    /** The entity that is looking idle. */
    private EntityLiving idleEntity;

    /** X offset to look at */
    private double lookX;

    /** Z offset to look at */
    private double lookZ;

    /**
     * A decrementing tick that stops the entity from being idle once it reaches 0.
     */
    private int idleTime;

    public EntityAILookIdle(EntityLiving par1EntityLiving)
    {
        idleTime = 0;
        idleEntity = par1EntityLiving;
        setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return idleEntity.getRNG().nextFloat() < 0.02F;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return idleTime >= 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        double d = (Math.PI * 2D) * idleEntity.getRNG().nextDouble();
        lookX = Math.cos(d);
        lookZ = Math.sin(d);
        idleTime = 20 + idleEntity.getRNG().nextInt(20);
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        idleTime--;
        idleEntity.getLookHelper().setLookPosition(idleEntity.posX + lookX, idleEntity.posY + (double)idleEntity.getEyeHeight(), idleEntity.posZ + lookZ, 10F, idleEntity.getVerticalFaceSpeed());
    }
}
