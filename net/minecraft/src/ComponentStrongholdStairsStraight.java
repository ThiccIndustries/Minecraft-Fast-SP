package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdStairsStraight extends ComponentStronghold
{
    private final EnumDoor doorType;

    public ComponentStrongholdStairsStraight(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        coordBaseMode = par4;
        doorType = getRandomDoor(par2Random);
        boundingBox = par3StructureBoundingBox;
    }

    /**
     * 'Initiates construction of the Structure Component picked, at the current Location of StructGen'
     */
    public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
    {
        getNextComponentNormal((ComponentStrongholdStairs2)par1StructureComponent, par2List, par3Random, 1, 1);
    }

    public static ComponentStrongholdStairsStraight findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -7, 0, 5, 11, 8, par5);

        if (!canStrongholdGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(par0List, structureboundingbox) != null)
        {
            return null;
        }
        else
        {
            return new ComponentStrongholdStairsStraight(par6, par1Random, structureboundingbox, par5);
        }
    }

    /**
     * 'second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...'
     */
    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox))
        {
            return false;
        }

        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 10, 7, true, par2Random, StructureStrongholdPieces.getStrongholdStones());
        placeDoor(par1World, par2Random, par3StructureBoundingBox, doorType, 1, 7, 0);
        placeDoor(par1World, par2Random, par3StructureBoundingBox, EnumDoor.OPENING, 1, 1, 7);
        int i = getMetadataWithOffset(Block.stairCompactCobblestone.blockID, 2);

        for (int j = 0; j < 6; j++)
        {
            placeBlockAtCurrentPosition(par1World, Block.stairCompactCobblestone.blockID, i, 1, 6 - j, 1 + j, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.stairCompactCobblestone.blockID, i, 2, 6 - j, 1 + j, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.stairCompactCobblestone.blockID, i, 3, 6 - j, 1 + j, par3StructureBoundingBox);

            if (j < 5)
            {
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 1, 5 - j, 1 + j, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 2, 5 - j, 1 + j, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 3, 5 - j, 1 + j, par3StructureBoundingBox);
            }
        }

        return true;
    }
}
