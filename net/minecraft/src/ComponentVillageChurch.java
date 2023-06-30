package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageChurch extends ComponentVillage
{
    private int averageGroundLevel;

    public ComponentVillageChurch(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
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
    public static ComponentVillageChurch findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, 0, 0, 0, 5, 12, 9, par5);

        if (!canVillageGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(par0List, structureboundingbox) != null)
        {
            return null;
        }
        else
        {
            return new ComponentVillageChurch(par6, par1Random, structureboundingbox, par5);
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

            boundingBox.offset(0, ((averageGroundLevel - boundingBox.maxY) + 12) - 1, 0);
        }

        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 1, 3, 3, 7, 0, 0, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 5, 1, 3, 9, 3, 0, 0, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 0, 0, 3, 0, 8, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 0, 3, 10, 0, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 10, 3, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 10, 3, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 4, 0, 4, 7, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 4, 0, 4, 4, 4, 7, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 8, 3, 4, 8, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 5, 4, 3, 10, 4, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 5, 5, 3, 5, 7, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 9, 0, 4, 9, 4, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 4, 0, 4, 4, 4, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 0, 11, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 4, 11, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 2, 11, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 2, 11, 4, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 1, 1, 6, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 1, 1, 7, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 2, 1, 7, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 3, 1, 6, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 3, 1, 7, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stairCompactCobblestone.blockID, getMetadataWithOffset(Block.stairCompactCobblestone.blockID, 3), 1, 1, 5, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stairCompactCobblestone.blockID, getMetadataWithOffset(Block.stairCompactCobblestone.blockID, 3), 2, 1, 6, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stairCompactCobblestone.blockID, getMetadataWithOffset(Block.stairCompactCobblestone.blockID, 3), 3, 1, 5, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stairCompactCobblestone.blockID, getMetadataWithOffset(Block.stairCompactCobblestone.blockID, 1), 1, 2, 7, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stairCompactCobblestone.blockID, getMetadataWithOffset(Block.stairCompactCobblestone.blockID, 0), 3, 2, 7, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 2, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 3, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 4, 2, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 4, 3, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 6, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 7, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 4, 6, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 4, 7, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 2, 6, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 2, 7, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 2, 6, 4, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 2, 7, 4, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 3, 6, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 4, 3, 6, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 2, 3, 8, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 2, 4, 7, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 1, 4, 6, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 3, 4, 6, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 2, 4, 5, par3StructureBoundingBox);
        int i = getMetadataWithOffset(Block.ladder.blockID, 4);

        for (int j = 1; j <= 9; j++)
        {
            placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, i, 3, j, 3, par3StructureBoundingBox);
        }

        placeBlockAtCurrentPosition(par1World, 0, 0, 2, 1, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, 0, 0, 2, 2, 0, par3StructureBoundingBox);
        placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 2, 1, 0, getMetadataWithOffset(Block.doorWood.blockID, 1));

        if (getBlockIdAtCurrentPosition(par1World, 2, 0, -1, par3StructureBoundingBox) == 0 && getBlockIdAtCurrentPosition(par1World, 2, -1, -1, par3StructureBoundingBox) != 0)
        {
            placeBlockAtCurrentPosition(par1World, Block.stairCompactCobblestone.blockID, getMetadataWithOffset(Block.stairCompactCobblestone.blockID, 3), 2, 0, -1, par3StructureBoundingBox);
        }

        for (int k = 0; k < 9; k++)
        {
            for (int l = 0; l < 5; l++)
            {
                clearCurrentPositionBlocksUpwards(par1World, l, 12, k, par3StructureBoundingBox);
                fillCurrentPositionBlocksDownwards(par1World, Block.cobblestone.blockID, 0, l, -1, k, par3StructureBoundingBox);
            }
        }

        spawnVillagers(par1World, par3StructureBoundingBox, 2, 1, 2, 1);
        return true;
    }

    /**
     * Returns the villager type to spawn in this component, based on the number of villagers already spawned.
     */
    protected int getVillagerType(int par1)
    {
        return 2;
    }
}
