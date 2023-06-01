package net.minecraft.src;

public class EntityAIMoveTwardsRestriction extends EntityAIBase
{
    private EntityCreature theEntity;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private float field_48352_e;

    public EntityAIMoveTwardsRestriction(EntityCreature par1EntityCreature, float par2)
    {
        theEntity = par1EntityCreature;
        field_48352_e = par2;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (theEntity.isWithinHomeDistanceCurrentPosition())
        {
            return false;
        }

        ChunkCoordinates chunkcoordinates = theEntity.getHomePosition();
        Vec3D vec3d = RandomPositionGenerator.func_48620_a(theEntity, 16, 7, Vec3D.createVector(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ));

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
        return !theEntity.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        theEntity.getNavigator().func_48666_a(movePosX, movePosY, movePosZ, field_48352_e);
    }
}
