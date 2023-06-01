package net.minecraft.src;

public abstract class EntityAIDoorInteract extends EntityAIBase
{
    protected EntityLiving theEntity;
    protected int entityPosX;
    protected int entityPosY;
    protected int entityPosZ;
    protected BlockDoor targetDoor;
    boolean field_48319_f;
    float field_48320_g;
    float field_48326_h;

    public EntityAIDoorInteract(EntityLiving par1EntityLiving)
    {
        theEntity = par1EntityLiving;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!theEntity.isCollidedHorizontally)
        {
            return false;
        }

        PathNavigate pathnavigate = theEntity.getNavigator();
        PathEntity pathentity = pathnavigate.getPath();

        if (pathentity == null || pathentity.isFinished() || !pathnavigate.func_48665_b())
        {
            return false;
        }

        for (int i = 0; i < Math.min(pathentity.getCurrentPathIndex() + 2, pathentity.getCurrentPathLength()); i++)
        {
            PathPoint pathpoint = pathentity.getPathPointFromIndex(i);
            entityPosX = pathpoint.xCoord;
            entityPosY = pathpoint.yCoord + 1;
            entityPosZ = pathpoint.zCoord;

            if (theEntity.getDistanceSq(entityPosX, theEntity.posY, entityPosZ) > 2.25D)
            {
                continue;
            }

            targetDoor = func_48318_a(entityPosX, entityPosY, entityPosZ);

            if (targetDoor != null)
            {
                return true;
            }
        }

        entityPosX = MathHelper.floor_double(theEntity.posX);
        entityPosY = MathHelper.floor_double(theEntity.posY + 1.0D);
        entityPosZ = MathHelper.floor_double(theEntity.posZ);
        targetDoor = func_48318_a(entityPosX, entityPosY, entityPosZ);
        return targetDoor != null;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !field_48319_f;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        field_48319_f = false;
        field_48320_g = (float)((double)((float)entityPosX + 0.5F) - theEntity.posX);
        field_48326_h = (float)((double)((float)entityPosZ + 0.5F) - theEntity.posZ);
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        float f = (float)((double)((float)entityPosX + 0.5F) - theEntity.posX);
        float f1 = (float)((double)((float)entityPosZ + 0.5F) - theEntity.posZ);
        float f2 = field_48320_g * f + field_48326_h * f1;

        if (f2 < 0.0F)
        {
            field_48319_f = true;
        }
    }

    private BlockDoor func_48318_a(int par1, int par2, int par3)
    {
        int i = theEntity.worldObj.getBlockId(par1, par2, par3);

        if (i != Block.doorWood.blockID)
        {
            return null;
        }
        else
        {
            BlockDoor blockdoor = (BlockDoor)Block.blocksList[i];
            return blockdoor;
        }
    }
}
