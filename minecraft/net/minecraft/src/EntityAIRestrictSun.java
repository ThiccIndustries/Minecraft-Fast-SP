package net.minecraft.src;

public class EntityAIRestrictSun extends EntityAIBase
{
    private EntityCreature theEntity;

    public EntityAIRestrictSun(EntityCreature par1EntityCreature)
    {
        theEntity = par1EntityCreature;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return theEntity.worldObj.isDaytime();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        theEntity.getNavigator().func_48680_d(true);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        theEntity.getNavigator().func_48680_d(false);
    }
}
