package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageField2 extends ComponentVillage
{
    private int averageGroundLevel;

    public ComponentVillageField2(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
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
    public static ComponentVillageField2 findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, 0, 0, 0, 7, 4, 9, par5);

        if (!canVillageGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(par0List, structureboundingbox) != null)
        {
            return null;
        }
        else
        {
            return new ComponentVillageField2(par6, par1Random, structureboundingbox, par5);
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

            boundingBox.offset(0, ((averageGroundLevel - boundingBox.maxY) + 4) - 1, 0);
        }

        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 0, 6, 4, 8, 0, 0, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 0, 1, 2, 0, 7, Block.tilledField.blockID, Block.tilledField.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 4, 0, 1, 5, 0, 7, Block.tilledField.blockID, Block.tilledField.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 0, 0, 8, Block.wood.blockID, Block.wood.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 6, 0, 0, 6, 0, 8, Block.wood.blockID, Block.wood.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 0, 0, 5, 0, 0, Block.wood.blockID, Block.wood.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 0, 8, 5, 0, 8, Block.wood.blockID, Block.wood.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 3, 0, 1, 3, 0, 7, Block.waterMoving.blockID, Block.waterMoving.blockID, false);

        for (int i = 1; i <= 7; i++)
        {
            placeBlockAtCurrentPosition(par1World, Block.crops.blockID, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 1, 1, i, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.crops.blockID, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 2, 1, i, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.crops.blockID, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 4, 1, i, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.crops.blockID, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 5, 1, i, par3StructureBoundingBox);
        }

        for (int j = 0; j < 9; j++)
        {
            for (int k = 0; k < 7; k++)
            {
                clearCurrentPositionBlocksUpwards(par1World, k, 4, j, par3StructureBoundingBox);
                fillCurrentPositionBlocksDownwards(par1World, Block.dirt.blockID, 0, k, -1, j, par3StructureBoundingBox);
            }
        }

        return true;
    }
}
