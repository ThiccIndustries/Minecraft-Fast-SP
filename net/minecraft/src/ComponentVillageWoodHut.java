package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageWoodHut extends ComponentVillage
{
    private int averageGroundLevel;
    private final boolean isTallHouse;
    private final int tablePosition;

    public ComponentVillageWoodHut(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        averageGroundLevel = -1;
        coordBaseMode = par4;
        boundingBox = par3StructureBoundingBox;
        isTallHouse = par2Random.nextBoolean();
        tablePosition = par2Random.nextInt(3);
    }

    /**
     * 'Initiates construction of the Structure Component picked, at the current Location of StructGen'
     */
    public void buildComponent(StructureComponent structurecomponent, List list, Random random)
    {
    }

    public static ComponentVillageWoodHut findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, 0, 0, 0, 4, 6, 5, par5);

        if (!canVillageGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(par0List, structureboundingbox) != null)
        {
            return null;
        }
        else
        {
            return new ComponentVillageWoodHut(par6, par1Random, structureboundingbox, par5);
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

            boundingBox.offset(0, ((averageGroundLevel - boundingBox.maxY) + 6) - 1, 0);
        }

        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 1, 3, 5, 4, 0, 0, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 3, 0, 4, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 0, 1, 2, 0, 3, Block.dirt.blockID, Block.dirt.blockID, false);

        if (isTallHouse)
        {
            fillWithBlocks(par1World, par3StructureBoundingBox, 1, 4, 1, 2, 4, 3, Block.wood.blockID, Block.wood.blockID, false);
        }
        else
        {
            fillWithBlocks(par1World, par3StructureBoundingBox, 1, 5, 1, 2, 5, 3, Block.wood.blockID, Block.wood.blockID, false);
        }

        placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 1, 4, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 2, 4, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 1, 4, 4, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 2, 4, 4, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 0, 4, 1, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 0, 4, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 0, 4, 3, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 3, 4, 1, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 3, 4, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 3, 4, 3, par3StructureBoundingBox);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 0, 0, 3, 0, Block.wood.blockID, Block.wood.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 0, 3, 3, 0, Block.wood.blockID, Block.wood.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 4, 0, 3, 4, Block.wood.blockID, Block.wood.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 4, 3, 3, 4, Block.wood.blockID, Block.wood.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 3, Block.planks.blockID, Block.planks.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 1, 3, 3, 3, Block.planks.blockID, Block.planks.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 0, 2, 3, 0, Block.planks.blockID, Block.planks.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 4, 2, 3, 4, Block.planks.blockID, Block.planks.blockID, false);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 2, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 3, 2, 2, par3StructureBoundingBox);

        if (tablePosition > 0)
        {
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, tablePosition, 1, 3, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.pressurePlatePlanks.blockID, 0, tablePosition, 2, 3, par3StructureBoundingBox);
        }

        placeBlockAtCurrentPosition(par1World, 0, 0, 1, 1, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, 0, 0, 1, 2, 0, par3StructureBoundingBox);
        placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 1, 1, 0, getMetadataWithOffset(Block.doorWood.blockID, 1));

        if (getBlockIdAtCurrentPosition(par1World, 1, 0, -1, par3StructureBoundingBox) == 0 && getBlockIdAtCurrentPosition(par1World, 1, -1, -1, par3StructureBoundingBox) != 0)
        {
            placeBlockAtCurrentPosition(par1World, Block.stairCompactCobblestone.blockID, getMetadataWithOffset(Block.stairCompactCobblestone.blockID, 3), 1, 0, -1, par3StructureBoundingBox);
        }

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                clearCurrentPositionBlocksUpwards(par1World, j, 6, i, par3StructureBoundingBox);
                fillCurrentPositionBlocksDownwards(par1World, Block.cobblestone.blockID, 0, j, -1, i, par3StructureBoundingBox);
            }
        }

        spawnVillagers(par1World, par3StructureBoundingBox, 1, 1, 2, 1);
        return true;
    }
}
