package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageHall extends ComponentVillage
{
    private int averageGroundLevel;

    public ComponentVillageHall(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        averageGroundLevel = -1;
        coordBaseMode = par4;
        boundingBox = par3StructureBoundingBox;
    }

    /**
     * 'Initiates construction of the Structure Component picked, at the current Location of StructGen'
     */
    public void buildComponent(StructureComponent structurecomponent, List list, Random random)
    {
    }

    /**
     * Trys to find a valid place to put this component.
     */
    public static ComponentVillageHall findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, 0, 0, 0, 9, 7, 11, par5);

        if (!canVillageGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(par0List, structureboundingbox) != null)
        {
            return null;
        }
        else
        {
            return new ComponentVillageHall(par6, par1Random, structureboundingbox, par5);
        }
    }

    /**
     * 'second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...'
     */
    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        if (averageGroundLevel < 0)
        {
            averageGroundLevel = getAverageGroundLevel(par1World, par3StructureBoundingBox);

            if (averageGroundLevel < 0)
            {
                return true;
            }

            boundingBox.offset(0, ((averageGroundLevel - boundingBox.maxY) + 7) - 1, 0);
        }

        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 1, 7, 4, 4, 0, 0, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 2, 1, 6, 8, 4, 10, 0, 0, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 2, 0, 6, 8, 0, 10, Block.dirt.blockID, Block.dirt.blockID, false);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 6, 0, 6, par3StructureBoundingBox);
        fillWithBlocks(par1World, par3StructureBoundingBox, 2, 1, 6, 2, 1, 10, Block.fence.blockID, Block.fence.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 8, 1, 6, 8, 1, 10, Block.fence.blockID, Block.fence.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 10, 7, 1, 10, Block.fence.blockID, Block.fence.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 0, 1, 7, 0, 4, Block.planks.blockID, Block.planks.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 0, 3, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 8, 0, 0, 8, 3, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 0, 0, 7, 1, 0, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 0, 5, 7, 1, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 2, 0, 7, 3, 0, Block.planks.blockID, Block.planks.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 2, 5, 7, 3, 5, Block.planks.blockID, Block.planks.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 4, 1, 8, 4, 1, Block.planks.blockID, Block.planks.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 4, 4, 8, 4, 4, Block.planks.blockID, Block.planks.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 5, 2, 8, 5, 3, Block.planks.blockID, Block.planks.blockID, false);
        placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 0, 4, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 0, 4, 3, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 8, 4, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 8, 4, 3, par3StructureBoundingBox);
        int i = getMetadataWithOffset(Block.stairCompactPlanks.blockID, 3);
        int j = getMetadataWithOffset(Block.stairCompactPlanks.blockID, 2);

        for (int k = -1; k <= 2; k++)
        {
            for (int i1 = 0; i1 <= 8; i1++)
            {
                placeBlockAtCurrentPosition(par1World, Block.stairCompactPlanks.blockID, i, i1, 4 + k, k, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stairCompactPlanks.blockID, j, i1, 4 + k, 5 - k, par3StructureBoundingBox);
            }
        }

        placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 0, 2, 1, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 0, 2, 4, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 8, 2, 1, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 8, 2, 4, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 2, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 2, 3, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 8, 2, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 8, 2, 3, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 2, 2, 5, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 3, 2, 5, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 5, 2, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 6, 2, 5, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 2, 1, 3, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.pressurePlatePlanks.blockID, 0, 2, 2, 3, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 1, 1, 4, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stairCompactPlanks.blockID, getMetadataWithOffset(Block.stairCompactPlanks.blockID, 3), 2, 1, 4, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stairCompactPlanks.blockID, getMetadataWithOffset(Block.stairCompactPlanks.blockID, 1), 1, 1, 3, par3StructureBoundingBox);
        fillWithBlocks(par1World, par3StructureBoundingBox, 5, 0, 1, 7, 0, 3, Block.stairDouble.blockID, Block.stairDouble.blockID, false);
        placeBlockAtCurrentPosition(par1World, Block.stairDouble.blockID, 0, 6, 1, 1, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stairDouble.blockID, 0, 6, 1, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, 0, 0, 2, 1, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, 0, 0, 2, 2, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 2, 3, 1, par3StructureBoundingBox);
        placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 2, 1, 0, getMetadataWithOffset(Block.doorWood.blockID, 1));

        if (getBlockIdAtCurrentPosition(par1World, 2, 0, -1, par3StructureBoundingBox) == 0 && getBlockIdAtCurrentPosition(par1World, 2, -1, -1, par3StructureBoundingBox) != 0)
        {
            placeBlockAtCurrentPosition(par1World, Block.stairCompactCobblestone.blockID, getMetadataWithOffset(Block.stairCompactCobblestone.blockID, 3), 2, 0, -1, par3StructureBoundingBox);
        }

        placeBlockAtCurrentPosition(par1World, 0, 0, 6, 1, 5, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, 0, 0, 6, 2, 5, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 6, 3, 4, par3StructureBoundingBox);
        placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 6, 1, 5, getMetadataWithOffset(Block.doorWood.blockID, 1));

        for (int l = 0; l < 5; l++)
        {
            for (int j1 = 0; j1 < 9; j1++)
            {
                clearCurrentPositionBlocksUpwards(par1World, j1, 7, l, par3StructureBoundingBox);
                fillCurrentPositionBlocksDownwards(par1World, Block.cobblestone.blockID, 0, j1, -1, l, par3StructureBoundingBox);
            }
        }

        spawnVillagers(par1World, par3StructureBoundingBox, 4, 1, 2, 2);
        return true;
    }

    /**
     * Returns the villager type to spawn in this component, based on the number of villagers already spawned.
     */
    protected int getVillagerType(int par1)
    {
        return par1 != 0 ? 0 : 4;
    }
}
