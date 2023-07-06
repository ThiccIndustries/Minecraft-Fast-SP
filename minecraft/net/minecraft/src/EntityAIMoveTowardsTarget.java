package net.minecraft.src;

public class EntityAIMoveTowardsTarget extends EntityAIBase
{
    private EntityCreature theEntity;
    private EntityLiving targetEntity;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private float field_48330_f;
    private float field_48331_g;

    public EntityAIMoveTowardsTarget(EntityCreature par1EntityCreature, float par2, float par3)
    {
        theEntity = par1EntityCreature;
        field_48330_f = par2;
        field_48331_g = par3;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        targetEntity = theEntity.getAttackTarget();

        if (targetEntity == null)
        {
            return false;
        }

        if (targetEntity.getDistanceSqToEntity(theEntity) > (double)(field_48331_g * field_48331_g))
        {
            return false;
        }

        Vec3D vec3d = RandomPositionGenerator.func_48620_a(theEntity, 16, 7, Vec3D.createVector(targetEntity.posX, targetEntity.posY, targetEntity.posZ));

        if (vec3d == null)
        {
            return false;
        }
        else
        {
            movePosX = vec3d.xCoord;
            movePosY = vec3d.yCoord;
            movePosZ = vec3d.zCoord;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !theEntity.getNavigator().noPath() && targetEntity.isEntityAlive() && targetEntity.getDistanceSqToEntity(theEntity) < (double)(field_48331_g * field_48331_g);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        targetEntity = null;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        theEntity.getNavigator().func_48666_a(movePosX, movePosY, movePosZ, field_48330_f);
    }
}
