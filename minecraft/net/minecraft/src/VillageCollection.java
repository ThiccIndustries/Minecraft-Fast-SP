package net.minecraft.src;

import java.util.*;

public class VillageCollection
{
    private World worldObj;

    /**
     * This is a black hole. You can add data to this list through a public interface, but you can't query that
     * information in any way and it's not used internally either.
     */
    private final List villagerPositionsList = new ArrayList();
    private final List newDoors = new ArrayList();
    private final List villageList = new ArrayList();
    private int tickCounter;

    public VillageCollection(World par1World)
    {
        tickCounter = 0;
        worldObj = par1World;
    }

    /**
     * This is a black hole. You can add data to this list through a public interface, but you can't query that
     * information in any way and it's not used internally either.
     */
    public void addVillagerPosition(int par1, int par2, int par3)
    {
        if (villagerPositionsList.size() > 64)
        {
            return;
        }

        if (!isVillagerPositionPresent(par1, par2, par3))
        {
            villagerPositionsList.add(new ChunkCoordinates(par1, par2, par3));
        }
    }

    /**
     * Runs a single tick for the village collection
     */
    public void tick()
    {
        tickCounter++;
        Village village;

        for (Iterator iterator = villageList.iterator(); iterator.hasNext(); village.tick(tickCounter))
        {
            village = (Village)iterator.next();
        }

        removeAnnihilatedVillages();
        dropOldestVillagerPosition();
        addNewDoorsToVillageOrCreateVillage();
    }

    private void removeAnnihilatedVillages()
    {
        Iterator iterator = villageList.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            Village village = (Village)iterator.next();

            if (village.isAnnihilated())
            {
                iterator.remove();
            }
        }
        while (true);
    }

    public List func_48554_b()
    {
        return villageList;
    }

    /**
     * Finds the nearest village, but only the given coordinates are withing it's bounding box plus the given the
     * distance.
     */
    public Village findNearestVillage(int par1, int par2, int par3, int par4)
    {
        Village village = null;
        float f = 3.402823E+038F;
        Iterator iterator = villageList.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            Village village1 = (Village)iterator.next();
            float f1 = village1.getCenter().getDistanceSquared(par1, par2, par3);

            if (f1 < f)
            {
                int i = par4 + village1.getVillageRadius();

                if (f1 <= (float)(i * i))
                {
                    village = village1;
                    f = f1;
                }
            }
        }
        while (true);

        return village;
    }

    private void dropOldestVillagerPosition()
    {
        if (villagerPositionsList.isEmpty())
        {
            return;
        }
        else
        {
            addUnassignedWoodenDoorsAroundToNewDoorsList((ChunkCoordinates)villagerPositionsList.remove(0));
            return;
        }
    }

    private void addNewDoorsToVillageOrCreateVillage()
    {
        for (int i = 0; i < newDoors.size(); i++)
        {
            VillageDoorInfo villagedoorinfo = (VillageDoorInfo)newDoors.get(i);
            boolean flag = false;
            Iterator iterator = villageList.iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                Village village1 = (Village)iterator.next();
                int j = (int)village1.getCenter().getEuclideanDistanceTo(villagedoorinfo.posX, villagedoorinfo.posY, villagedoorinfo.posZ);

                if (j > 32 + village1.getVillageRadius())
                {
                    continue;
                }

                village1.addVillageDoorInfo(villagedoorinfo);
                flag = true;
                break;
            }
            while (true);

            if (!flag)
            {
                Village village = new Village(worldObj);
                village.addVillageDoorInfo(villagedoorinfo);
                villageList.add(village);
            }
        }

        newDoors.clear();
    }

    private void addUnassignedWoodenDoorsAroundToNewDoorsList(ChunkCoordinates par1ChunkCoordinates)
    {
        byte byte0 = 16;
        byte byte1 = 4;
        byte byte2 = 16;

        for (int i = par1ChunkCoordinates.posX - byte0; i < par1ChunkCoordinates.posX + byte0; i++)
        {
            for (int j = par1ChunkCoordinates.posY - byte1; j < par1ChunkCoordinates.posY + byte1; j++)
            {
                for (int k = par1ChunkCoordinates.posZ - byte2; k < par1ChunkCoordinates.posZ + byte2; k++)
                {
                    if (!isWoodenDoorAt(i, j, k))
                    {
                        continue;
                    }

                    VillageDoorInfo villagedoorinfo = getVillageDoorAt(i, j, k);

                    if (villagedoorinfo == null)
                    {
                        addDoorToNewListIfAppropriate(i, j, k);
                    }
                    else
                    {
                        villagedoorinfo.lastActivityTimestamp = tickCounter;
                    }
                }
            }
        }
    }

    private VillageDoorInfo getVillageDoorAt(int par1, int par2, int par3)
    {
        for (Iterator iterator = newDoors.iterator(); iterator.hasNext();)
        {
            VillageDoorInfo villagedoorinfo = (VillageDoorInfo)iterator.next();

            if (villagedoorinfo.posX == par1 && villagedoorinfo.posZ == par3 && Math.abs(villagedoorinfo.posY - par2) <= 1)
            {
                return villagedoorinfo;
            }
        }

        for (Iterator iterator1 = villageList.iterator(); iterator1.hasNext();)
        {
            Village village = (Village)iterator1.next();
            VillageDoorInfo villagedoorinfo1 = village.getVillageDoorAt(par1, par2, par3);

            if (villagedoorinfo1 != null)
            {
                return villagedoorinfo1;
            }
        }

        return null;
    }

    private void addDoorToNewListIfAppropriate(int par1, int par2, int par3)
    {
        int i = ((BlockDoor)Block.doorWood).getDoorOrientation(worldObj, par1, par2, par3);

        if (i == 0 || i == 2)
        {
            int j = 0;

            for (int l = -5; l < 0; l++)
            {
                if (worldObj.canBlockSeeTheSky(par1 + l, par2, par3))
                {
                    j--;
                }
            }

            for (int i1 = 1; i1 <= 5; i1++)
            {
                if (worldObj.canBlockSeeTheSky(par1 + i1, par2, par3))
                {
                    j++;
                }
            }

            if (j != 0)
            {
                newDoors.add(new VillageDoorInfo(par1, par2, par3, j <= 0 ? 2 : -2, 0, tickCounter));
            }
        }
        else
        {
            int k = 0;

            for (int j1 = -5; j1 < 0; j1++)
            {
                if (worldObj.canBlockSeeTheSky(par1, par2, par3 + j1))
                {
                    k--;
                }
            }

            for (int k1 = 1; k1 <= 5; k1++)
            {
                if (worldObj.canBlockSeeTheSky(par1, par2, par3 + k1))
                {
                    k++;
                }
            }

            if (k != 0)
            {
                newDoors.add(new VillageDoorInfo(par1, par2, par3, 0, k <= 0 ? 2 : -2, tickCounter));
            }
        }
    }

    private boolean isVillagerPositionPresent(int par1, int par2, int par3)
    {
        for (Iterator iterator = villagerPositionsList.iterator(); iterator.hasNext();)
        {
            ChunkCoordinates chunkcoordinates = (ChunkCoordinates)iterator.next();

            if (chunkcoordinates.posX == par1 && chunkcoordinates.posY == par2 && chunkcoordinates.posZ == par3)
            {
                return true;
            }
        }

        return false;
    }

    private boolean isWoodenDoorAt(int par1, int par2, int par3)
    {
        int i = worldObj.getBlockId(par1, par2, par3);
        return i == Block.doorWood.blockID;
    }
}
