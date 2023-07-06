package net.minecraft.src;

public class EntityAIRestrictOpenDoor extends EntityAIBase
{
    private EntityCreature entityObj;
    private VillageDoorInfo frontDoor;

    public EntityAIRestrictOpenDoor(EntityCreature par1EntityCreature)
    {
        entityObj = par1EntityCreature;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (entityObj.worldObj.isDaytime())
        {
            return false;
        }

        Village village = entityObj.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(entityObj.posX), MathHelper.floor_double(entityObj.posY), MathHelper.floor_double(entityObj.posZ), 16);

        if (village == null)
        {
            return false;
        }

        frontDoor = village.findNearestDoor(MathHelper.floor_double(entityObj.posX), MathHelper.floor_double(entityObj.posY), MathHelper.floor_double(entityObj.posZ));

        if (frontDoor == null)
        {
            return false;
        }
        else
        {
            return (double)frontDoor.getInsideDistanceSquare(MathHelper.floor_double(entityObj.posX), MathHelper.floor_double(entityObj.posY), MathHelper.floor_double(entityObj.posZ)) < 2.25D;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        if (entityObj.worldObj.isDaytime())
        {
            return false;
        }
        else
        {
            return !frontDoor.isDetachedFromVillageFlag && frontDoor.isInside(MathHelper.floor_double(entityObj.posX), MathHelper.floor_double(entityObj.posZ));
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        entityObj.getNavigator().setBreakDoors(false);
        entityObj.getNavigator().func_48663_c(false);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        entityObj.getNavigator().setBreakDoors(true);
        entityObj.getNavigator().func_48663_c(true);
        frontDoor = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        frontDoor.incrementDoorOpeningRestrictionCounter();
    }
}
