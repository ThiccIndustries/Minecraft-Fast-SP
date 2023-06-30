package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageHouse2 extends ComponentVillage
{
    private static final StructurePieceTreasure chestLoot[];
    private int averageGroundLevel;
    private boolean hasMadeChest;

    public ComponentVillageHouse2(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
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

    public static ComponentVillageHouse2 findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, 0, 0, 0, 10, 6, 7, par5);

        if (!canVillageGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(par0List, structureboundingbox) != null)
        {
            return null;
        }
        else
        {
            return new ComponentVillageHouse2(par6, par1Random, structureboundingbox, par5);
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

        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 0, 9, 4, 6, 0, 0, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 9, 0, 6, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 4, 0, 9, 4, 6, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 5, 0, 9, 5, 6, Block.stairSingle.blockID, Block.stairSingle.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 5, 1, 8, 5, 5, 0, 0, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 0, 2, 3, 0, Block.planks.blockID, Block.planks.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 0, 0, 4, 0, Block.wood.blockID, Block.wood.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 0, 3, 4, 0, Block.wood.blockID, Block.wood.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 6, 0, 4, 6, Block.wood.blockID, Block.wood.blockID, false);
        placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 3, 3, 1, par3StructureBoundingBox);
        fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 2, 3, 3, 2, Block.planks.blockID, Block.planks.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 3, 5, 3, 3, Block.planks.blockID, Block.planks.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 5, Block.planks.blockID, Block.planks.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 6, 5, 3, 6, Block.planks.blockID, Block.planks.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 5, 1, 0, 5, 3, 0, Block.fence.blockID, Block.fence.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 9, 1, 0, 9, 3, 0, Block.fence.blockID, Block.fence.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 6, 1, 4, 9, 4, 6, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        placeBlockAtCurrentPosition(par1World, Block.lavaMoving.blockID, 0, 7, 1, 5, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.lavaMoving.blockID, 0, 8, 1, 5, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, 9, 2, 5, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, 9, 2, 4, par3StructureBoundingBox);
        fillWithBlocks(par1World, par3StructureBoundingBox, 7, 2, 4, 8, 2, 5, 0, 0, false);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 6, 1, 3, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stoneOvenIdle.blockID, 0, 6, 2, 3, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stoneOvenIdle.blockID, 0, 6, 3, 3, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stairDouble.blockID, 0, 8, 1, 1, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 2, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 2, 4, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 2, 2, 6, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 4, 2, 6, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 2, 1, 4, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.pressurePlatePlanks.blockID, 0, 2, 2, 4, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 1, 1, 5, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stairCompactPlanks.blockID, getMetadataWithOffset(Block.stairCompactPlanks.blockID, 3), 2, 1, 5, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stairCompactPlanks.blockID, getMetadataWithOffset(Block.stairCompactPlanks.blockID, 1), 1, 1, 4, par3StructureBoundingBox);

        if (!hasMadeChest)
        {
            int i = getYWithOffset(1);
            int l = getXWithOffset(5, 5);
            int j1 = getZWithOffset(5, 5);

            if (par3StructureBoundingBox.isVecInside(l, i, j1))
            {
                hasMadeChest = true;
                createTreasureChestAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 5, 1, 5, chestLoot, 3 + par2Random.nextInt(6));
            }
        }

        for (int j = 6; j <= 8; j++)
        {
            if (getBlockIdAtCurrentPosition(par1World, j, 0, -1, par3StructureBoundingBox) == 0 && getBlockIdAtCurrentPosition(par1World, j, -1, -1, par3StructureBoundingBox) != 0)
            {
                placeBlockAtCurrentPosition(par1World, Block.stairCompactCobblestone.blockID, getMetadataWithOffset(Block.stairCompactCobblestone.blockID, 3), j, 0, -1, par3StructureBoundingBox);
            }
        }

        for (int k = 0; k < 7; k++)
        {
            for (int i1 = 0; i1 < 10; i1++)
            {
                clearCurrentPositionBlocksUpwards(par1World, i1, 6, k, par3StructureBoundingBox);
                fillCurrentPositionBlocksDownwards(par1World, Block.cobblestone.blockID, 0, i1, -1, k, par3StructureBoundingBox);
            }
        }

        spawnVillagers(par1World, par3StructureBoundingBox, 7, 1, 1, 1);
        return true;
    }

    /**
     * Returns the villager type to spawn in this component, based on the number of villagers already spawned.
     */
    protected int getVillagerType(int par1)
    {
        return 3;
    }

    static
    {
        chestLoot = (new StructurePieceTreasure[]
                {
                    new StructurePieceTreasure(Item.diamond.shiftedIndex, 0, 1, 3, 3), new StructurePieceTreasure(Item.ingotIron.shiftedIndex, 0, 1, 5, 10), new StructurePieceTreasure(Item.ingotGold.shiftedIndex, 0, 1, 3, 5), new StructurePieceTreasure(Item.bread.shiftedIndex, 0, 1, 3, 15), new StructurePieceTreasure(Item.appleRed.shiftedIndex, 0, 1, 3, 15), new StructurePieceTreasure(Item.pickaxeSteel.shiftedIndex, 0, 1, 1, 5), new StructurePieceTreasure(Item.swordSteel.shiftedIndex, 0, 1, 1, 5), new StructurePieceTreasure(Item.plateSteel.shiftedIndex, 0, 1, 1, 5), new StructurePieceTreasure(Item.helmetSteel.shiftedIndex, 0, 1, 1, 5), new StructurePieceTreasure(Item.legsSteel.shiftedIndex, 0, 1, 1, 5),
                    new StructurePieceTreasure(Item.bootsSteel.shiftedIndex, 0, 1, 1, 5), new StructurePieceTreasure(Block.obsidian.blockID, 0, 3, 7, 5), new StructurePieceTreasure(Block.sapling.blockID, 0, 3, 7, 5)
                });
    }
}
