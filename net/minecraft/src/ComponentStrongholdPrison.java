package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdPrison extends ComponentStronghold
{
    protected final EnumDoor doorType;

    public ComponentStrongholdPrison(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
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

    public static ComponentStrongholdPrison findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 9, 5, 11, par5);

        if (!canStrongholdGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(par0List, structureboundingbox) != null)
        {
            return null;
        }
        else
        {
            return new ComponentStrongholdPrison(par6, par1Random, structureboundingbox, par5);
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
        else
        {
            fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 8, 4, 10, true, par2Random, StructureStrongholdPieces.getStrongholdStones());
            placeDoor(par1World, par2Random, par3StructureBoundingBox, doorType, 1, 1, 0);
            fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 10, 3, 3, 10, 0, 0, false);
            fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 3, 1, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
            fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 3, 4, 3, 3, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
            fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 7, 4, 3, 7, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
            fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 9, 4, 3, 9, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
            fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 4, 4, 3, 6, Block.fenceIron.blockID, Block.fenceIron.blockID, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, 5, 1, 5, 7, 3, 5, Block.fenceIron.blockID, Block.fenceIron.blockID, false);
            placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, 4, 3, 2, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, 4, 3, 8, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.doorSteel.blockID, getMetadataWithOffset(Block.doorSteel.blockID, 3), 4, 1, 2, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.doorSteel.blockID, getMetadataWithOffset(Block.doorSteel.blockID, 3) + 8, 4, 2, 2, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.doorSteel.blockID, getMetadataWithOffset(Block.doorSteel.blockID, 3), 4, 1, 8, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.doorSteel.blockID, getMetadataWithOffset(Block.doorSteel.blockID, 3) + 8, 4, 2, 8, par3StructureBoundingBox);
            return true;
        }
    }
}
