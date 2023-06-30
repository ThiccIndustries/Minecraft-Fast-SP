package net.minecraft.src;

public class EntityAIOpenDoor extends EntityAIDoorInteract
{
    boolean field_48328_i;
    int field_48327_j;

    public EntityAIOpenDoor(EntityLiving par1EntityLiving, boolean par2)
    {
        super(par1EntityLiving);
        theEntity = par1EntityLiving;
        field_48328_i = par2;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return field_48328_i && field_48327_j > 0 && super.continueExecuting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        field_48327_j = 20;
        targetDoor.onPoweredBlockChange(theEntity.worldObj, entityPosX, entityPosY, entityPosZ, true);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        if (field_48328_i)
        {
            targetDoor.onPoweredBlockChange(theEntity.worldObj, entityPosX, entityPosY, entityPosZ, false);
        }
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        field_48327_j--;
        super.updateTask();
    }
}
