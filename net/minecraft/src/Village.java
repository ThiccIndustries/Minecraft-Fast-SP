package net.minecraft.src;

import java.util.*;

public class Village
{
    private final World worldObj;

    /** list of VillageDoorInfo objects */
    private final List villageDoorInfoList = new ArrayList();

    /**
     * This is the sum of all door coordinates and used to calculate the actual village center by dividing by the number
     * of doors.
     */
    private final ChunkCoordinates centerHelper = new ChunkCoordinates(0, 0, 0);

    /** This is the actual village center. */
    private final ChunkCoordinates center = new ChunkCoordinates(0, 0, 0);
    private int villageRadius;
    private int lastAddDoorTimestamp;
    private int tickCounter;
    private int numVillagers;
    private List villageAgressors;
    private int numIronGolems;

    public Village(World par1World)
    {
        villageRadius = 0;
        lastAddDoorTimestamp = 0;
        tickCounter = 0;
        numVillagers = 0;
        villageAgressors = new ArrayList();
        numIronGolems = 0;
        worldObj = par1World;
    }

    /**
     * Called periodically by VillageCollection
     */
    public void tick(int par1)
    {
        tickCounter = par1;
        removeDeadAndOutOfRangeDoors();
        removeDeadAndOldAgressors();

        if (par1 % 20 == 0)
        {
            updateNumVillagers();
        }

        if (par1 % 30 == 0)
        {
            updateNumIronGolems();
        }

        int i = numVillagers / 16;

        if (numIronGolems < i && villageDoorInfoList.size() > 20 && worldObj.rand.nextInt(7000) == 0)
        {
            Vec3D vec3d = tryGetIronGolemSpawningLocation(MathHelper.floor_float(center.posX), MathHelper.floor_float(center.posY), MathHelper.floor_float(center.posZ), 2, 4, 2);

            if (vec3d != null)
            {
                EntityIronGolem entityirongolem = new EntityIronGolem(worldObj);
                entityirongolem.setPosition(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
                worldObj.spawnEntityInWorld(entityirongolem);
                numIronGolems++;
            }
        }
    }

    /**
     * Tries up to 10 times to get a valid spawning location before eventually failing and returning null.
     */
    private Vec3D tryGetIronGolemSpawningLocation(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        for (int i = 0; i < 10; i++)
        {
            int j = (par1 + worldObj.rand.nextInt(16)) - 8;
            int k = (par2 + worldObj.rand.nextInt(6)) - 3;
            int l = (par3 + worldObj.rand.nextInt(16)) - 8;

            if (isInRange(j, k, l) && isValidIronGolemSpawningLocation(j, k, l, par4, par5, par6))
            {
                return Vec3D.createVector(j, k, l);
            }
        }

        return null;
    }

    private boolean isValidIronGolemSpawningLocation(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        if (!worldObj.isBlockNormalCube(par1, par2 - 1, par3))
        {
            return false;
        }

        int i = par1 - par4 / 2;
        int j = par3 - par6 / 2;

        for (int k = i; k < i + par4; k++)
        {
            for (int l = par2; l < par2 + par5; l++)
            {
                for (int i1 = j; i1 < j + par6; i1++)
                {
                    if (worldObj.isBlockNormalCube(k, l, i1))
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void updateNumIronGolems()
    {
        List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityIronGolem.class, AxisAlignedBB.getBoundingBoxFromPool(center.posX - villageRadius, center.posY - 4, center.posZ - villageRadius, center.posX + villageRadius, center.posY + 4, center.posZ + villageRadius));
        numIronGolems = list.size();
    }

    private void updateNumVillagers()
    {
        List list = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityVillager.class, AxisAlignedBB.getBoundingBoxFromPool(center.posX - villageRadius, center.posY - 4, center.posZ - villageRadius, center.posX + villageRadius, center.posY + 4, center.posZ + villageRadius));
        numVillagers = list.size();
    }

    public ChunkCoordinates getCenter()
    {
        return center;
    }

    public int getVillageRadius()
    {
        return villageRadius;
    }

    /**
     * Actually get num village door info entries, but that boils down to number of doors. Called by
     * EntityAIVillagerMate and VillageSiege
     */
    public int getNumVillageDoors()
    {
        return villageDoorInfoList.size();
    }

    public int getTicksSinceLastDoorAdding()
    {
        return tickCounter - lastAddDoorTimestamp;
    }

    public int getNumVillagers()
    {
        return numVillagers;
    }

    /**
     * Returns true, if the given coordinates are within the bounding box of the village.
     */
    public boolean isInRange(int par1, int par2, int par3)
    {
        return center.getDistanceSquared(par1, par2, par3) < (float)(villageRadius * villageRadius);
    }

    /**
     * called only by class EntityAIMoveThroughVillage
     */
    public List getVillageDoorInfoList()
    {
        return villageDoorInfoList;
    }

    public VillageDoorInfo findNearestDoor(int par1, int par2, int par3)
    {
        VillageDoorInfo villagedoorinfo = null;
        int i = 0x7fffffff;
        Iterator iterator = villageDoorInfoList.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            VillageDoorInfo villagedoorinfo1 = (VillageDoorInfo)iterator.next();
            int j = villagedoorinfo1.getDistanceSquared(par1, par2, par3);

            if (j < i)
            {
                villagedoorinfo = villagedoorinfo1;
                i = j;
            }
        }
        while (true);

        return villagedoorinfo;
    }

    /**
     * Find a door suitable for shelter. If there are more doors in a distance of 16 blocks, then the least restricted
     * one (i.e. the one protecting the lowest number of villagers) of them is chosen, else the nearest one regardless
     * of restriction.
     */
    public VillageDoorInfo findNearestDoorUnrestricted(int par1, int par2, int par3)
    {
        VillageDoorInfo villagedoorinfo = null;
        int i = 0x7fffffff;
        Iterator iterator = villageDoorInfoList.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            VillageDoorInfo villagedoorinfo1 = (VillageDoorInfo)iterator.next();
            int j = villagedoorinfo1.getDistanceSquared(par1, par2, par3);

            if (j > 256)
            {
                j *= 1000;
            }
            else
            {
                j = villagedoorinfo1.getDoorOpeningRestrictionCounter();
            }

            if (j < i)
            {
                villagedoorinfo = villagedoorinfo1;
                i = j;
            }
        }
        while (true);

        return villagedoorinfo;
    }

    public VillageDoorInfo getVillageDoorAt(int par1, int par2, int par3)
    {
        if (center.getDistanceSquared(par1, par2, par3) > (float)(villageRadius * villageRadius))
        {
            return null;
        }

        for (Iterator iterator = villageDoorInfoList.iterator(); iterator.hasNext();)
        {
            VillageDoorInfo villagedoorinfo = (VillageDoorInfo)iterator.next();

            if (villagedoorinfo.posX == par1 && villagedoorinfo.posZ == par3 && Math.abs(villagedoorinfo.posY - par2) <= 1)
            {
                return villagedoorinfo;
            }
        }

        return null;
    }

    public void addVillageDoorInfo(VillageDoorInfo par1VillageDoorInfo)
    {
        villageDoorInfoList.add(par1VillageDoorInfo);
        centerHelper.posX += par1VillageDoorInfo.posX;
        centerHelper.posY += par1VillageDoorInfo.posY;
        centerHelper.posZ += par1VillageDoorInfo.posZ;
        updateVillageRadiusAndCenter();
        lastAddDoorTimestamp = par1VillageDoorInfo.lastActivityTimestamp;
    }

    /**
     * Returns true, if there is not a single village door left. Called by VillageCollection
     */
    public boolean isAnnihilated()
    {
        return villageDoorInfoList.isEmpty();
    }

    public void addOrRenewAgressor(EntityLiving par1EntityLiving)
    {
        for (Iterator iterator = villageAgressors.iterator(); iterator.hasNext();)
        {
            VillageAgressor villageagressor = (VillageAgressor)iterator.next();

            if (villageagressor.agressor == par1EntityLiving)
            {
                villageagressor.agressionTime = tickCounter;
                return;
            }
        }

        villageAgressors.add(new VillageAgressor(this, par1EntityLiving, tickCounter));
    }

    public EntityLiving findNearestVillageAggressor(EntityLiving par1EntityLiving)
    {
        double d = Double.MAX_VALUE;
        VillageAgressor villageagressor = null;

        for (int i = 0; i < villageAgressors.size(); i++)
        {
            VillageAgressor villageagressor1 = (VillageAgressor)villageAgressors.get(i);
            double d1 = villageagressor1.agressor.getDistanceSqToEntity(par1EntityLiving);

            if (d1 <= d)
            {
                villageagressor = villageagressor1;
                d = d1;
            }
        }

        return villageagressor == null ? null : villageagressor.agressor;
    }

    private void removeDeadAndOldAgressors()
    {
        Iterator iterator = villageAgressors.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            VillageAgressor villageagressor = (VillageAgressor)iterator.next();

            if (!villageagressor.agressor.isEntityAlive() || Math.abs(tickCounter - villageagressor.agressionTime) > 300)
            {
                iterator.remove();
            }
        }
        while (true);
    }

    private void removeDeadAndOutOfRangeDoors()
    {
        boolean flag = false;
        boolean flag1 = worldObj.rand.nextInt(50) == 0;
        Iterator iterator = villageDoorInfoList.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            VillageDoorInfo villagedoorinfo = (VillageDoorInfo)iterator.next();

            if (flag1)
            {
                villagedoorinfo.resetDoorOpeningRestrictionCounter();
            }

            if (!isBlockDoor(villagedoorinfo.posX, villagedoorinfo.posY, villagedoorinfo.posZ) || Math.abs(tickCounter - villagedoorinfo.lastActivityTimestamp) > 1200)
            {
                centerHelper.posX -= villagedoorinfo.posX;
                centerHelper.posY -= villagedoorinfo.posY;
                centerHelper.posZ -= villagedoorinfo.posZ;
                flag = true;
                villagedoorinfo.isDetachedFromVillageFlag = true;
                iterator.remove();
            }
        }
        while (true);

        if (flag)
        {
            updateVillageRadiusAndCenter();
        }
    }

    private boolean isBlockDoor(int par1, int par2, int par3)
    {
        int i = worldObj.getBlockId(par1, par2, par3);

        if (i <= 0)
        {
            return false;
        }
        else
        {
            return i == Block.doorWood.blockID;
        }
    }

    private void updateVillageRadiusAndCenter()
    {
        int i = villageDoorInfoList.size();

        if (i == 0)
        {
            center.set(0, 0, 0);
            villageRadius = 0;
            return;
        }

        center.set(centerHelper.posX / i, centerHelper.posY / i, centerHelper.posZ / i);
        int j = 0;

        for (Iterator iterator = villageDoorInfoList.iterator(); iterator.hasNext();)
        {
            VillageDoorInfo villagedoorinfo = (VillageDoorInfo)iterator.next();
            j = Math.max(villagedoorinfo.getDistanceSquared(center.posX, center.posY, center.posZ), j);
        }

        villageRadius = Math.max(32, (int)Math.sqrt(j) + 1);
    }
}
