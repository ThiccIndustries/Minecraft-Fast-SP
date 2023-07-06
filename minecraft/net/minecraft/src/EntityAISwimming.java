package net.minecraft.src;

import java.util.Random;

public class EntityAISwimming extends EntityAIBase
{
    private EntityLiving theEntity;

    public EntityAISwimming(EntityLiving par1EntityLiving)
    {
        theEntity = par1EntityLiving;
        setMutexBits(4);
        par1EntityLiving.getNavigator().func_48669_e(true);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return theEntity.isInWater() || theEntity.handleLavaMovement();
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        if (theEntity.getRNG().nextFloat() < 0.8F)
        {
            theEntity.getJumpHelper().setJumping();
        }
    }
}
