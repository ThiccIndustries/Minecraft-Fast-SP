package net.minecraft.src;

import java.util.*;

public class EntityAIMoveThroughVillage extends EntityAIBase
{
    private EntityCreature theEntity;
    private float field_48290_b;
    private PathEntity field_48291_c;
    private VillageDoorInfo doorInfo;
    private boolean field_48289_e;
    private List doorList;

    public EntityAIMoveThroughVillage(EntityCreature par1EntityCreature, float par2, boolean par3)
    {
        doorList = new ArrayList();
        theEntity = par1EntityCreature;
        field_48290_b = par2;
        field_48289_e = par3;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        func_48286_h();

        if (field_48289_e && theEntity.worldObj.isDaytime())
        {
            return false;
        }

        Village village = theEntity.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(theEntity.posX), MathHelper.floor_double(theEntity.posY), MathHelper.floor_double(theEntity.posZ), 0);

        if (village == null)
        {
            return false;
        }

        doorInfo = func_48284_a(village);

        if (doorInfo == null)
        {
            return false;
        }

        boolean flag = theEntity.getNavigator().func_48665_b();
        theEntity.getNavigator().setBreakDoors(false);
        field_48291_c = theEntity.getNavigator().getPathToXYZ(doorInfo.posX, doorInfo.posY, doorInfo.posZ);
        theEntity.getNavigator().setBreakDoors(flag);

        if (field_48291_c != null)
        {
            return true;
        }

        Vec3D vec3d = RandomPositionGenerator.func_48620_a(theEntity, 10, 7, Vec3D.createVector(doorInfo.posX, doorInfo.posY, doorInfo.posZ));

        if (vec3d == null)
        {
            return false;
        }
        else
        {
            theEntity.getNavigator().setBreakDoors(false);
            field_48291_c = theEntity.getNavigator().getPathToXYZ(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
            theEntity.getNavigator().setBreakDoors(flag);
            return field_48291_c != null;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        if (theEntity.getNavigator().noPath())
        {
            return false;
        }
        else
        {
            float f = theEntity.width + 4F;
            return theEntity.getDistanceSq(doorInfo.posX, doorInfo.posY, doorInfo.posZ) > (double)(f * f);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        theEntity.getNavigator().setPath(field_48291_c, field_48290_b);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        if (theEntity.getNavigator().noPath() || theEntity.getDistanceSq(doorInfo.posX, doorInfo.posY, doorInfo.posZ) < 16D)
        {
            doorList.add(doorInfo);
        }
    }

    private VillageDoorInfo func_48284_a(Village par1Village)
    {
        VillageDoorInfo villagedoorinfo = null;
        int i = 0x7fffffff;
        List list = par1Village.getVillageDoorInfoList();
        Iterator iterator = list.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            VillageDoorInfo villagedoorinfo1 = (VillageDoorInfo)iterator.next();
            int j = villagedoorinfo1.getDistanceSquared(MathHelper.floor_double(theEntity.posX), MathHelper.floor_double(theEntity.posY), MathHelper.floor_double(theEntity.posZ));

            if (j < i && !func_48285_a(villagedoorinfo1))
            {
                villagedoorinfo = villagedoorinfo1;
                i = j;
            }
        }
        while (true);

        return villagedoorinfo;
    }

    private boolean func_48285_a(VillageDoorInfo par1VillageDoorInfo)
    {
        for (Iterator iterator = doorList.iterator(); iterator.hasNext();)
        {
            VillageDoorInfo villagedoorinfo = (VillageDoorInfo)iterator.next();

            if (par1VillageDoorInfo.posX == villagedoorinfo.posX && par1VillageDoorInfo.posY == villagedoorinfo.posY && par1VillageDoorInfo.posZ == villagedoorinfo.posZ)
            {
                return true;
            }
        }

        return false;
    }

    private void func_48286_h()
    {
        if (doorList.size() > 15)
        {
            doorList.remove(0);
        }
    }
}
