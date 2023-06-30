package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdCrossing extends ComponentStronghold
{
    protected final EnumDoor doorType;
    private boolean field_35042_b;
    private boolean field_35043_c;
    private boolean field_35040_d;
    private boolean field_35041_e;

    public ComponentStrongholdCrossing(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        coordBaseMode = par4;
        doorType = getRandomDoor(par2Random);
        boundingBox = par3StructureBoundingBox;
        field_35042_b = par2Random.nextBoolean();
        field_35043_c = par2Random.nextBoolean();
        field_35040_d = par2Random.nextBoolean();
        field_35041_e = par2Random.nextInt(3) > 0;
    }

    /**
     * 'Initiates construction of the Structure Component picked, at the current Location of StructGen'
     */
    public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
    {
        int i = 3;
        int j = 5;

        if (coordBaseMode == 1 || coordBaseMode == 2)
        {
            i = 8 - i;
            j = 8 - j;
        }

        getNextComponentNormal((ComponentStrongholdStairs2)par1StructureComponent, par2List, par3Random, 5, 1);

        if (field_35042_b)
        {
            getNextComponentX((ComponentStrongholdStairs2)par1StructureComponent, par2List, par3Random, i, 1);
        }

        if (field_35043_c)
        {
            getNextComponentX((ComponentStrongholdStairs2)par1StructureComponent, par2List, par3Random, j, 7);
        }

        if (field_35040_d)
        {
            getNextComponentZ((ComponentStrongholdStairs2)par1StructureComponent, par2List, par3Random, i, 1);
        }

        if (field_35041_e)
        {
            getNextComponentZ((ComponentStrongholdStairs2)par1StructureComponent, par2List, par3Random, j, 7);
        }
    }

    public static ComponentStrongholdCrossing findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -3, 0, 10, 9, 11, par5);

        if (!canStrongholdGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(par0List, structureboundingbox) != null)
        {
            return null;
        }
        else
        {
            return new ComponentStrongholdCrossing(par6, par1Random, structureboundingbox, par5);
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

        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 9, 8, 10, true, par2Random, StructureStrongholdPieces.getStrongholdStones());
        placeDoor(par1World, par2Random, par3StructureBoundingBox, doorType, 4, 3, 0);

        if (field_35042_b)
        {
            fillWithBlocks(par1World, par3StructureBoundingBox, 0, 3, 1, 0, 5, 3, 0, 0, false);
        }

        if (field_35040_d)
        {
            fillWithBlocks(par1World, par3StructureBoundingBox, 9, 3, 1, 9, 5, 3, 0, 0, false);
        }

        if (field_35043_c)
        {
            fillWithBlocks(par1World, par3StructureBoundingBox, 0, 5, 7, 0, 7, 9, 0, 0, false);
        }

        if (field_35041_e)
        {
            fillWithBlocks(par1World, par3StructureBoundingBox, 9, 5, 7, 9, 7, 9, 0, 0, false);
        }

        fillWithBlocks(par1World, par3StructureBoundingBox, 5, 1, 10, 7, 3, 10, 0, 0, false);
        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 2, 1, 8, 2, 6, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 5, 4, 4, 9, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 8, 1, 5, 8, 4, 9, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 4, 7, 3, 4, 9, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 3, 5, 3, 3, 6, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 3, 4, 3, 3, 4, Block.stairSingle.blockID, Block.stairSingle.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 4, 6, 3, 4, 6, Block.stairSingle.blockID, Block.stairSingle.blockID, false);
        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 5, 1, 7, 7, 1, 8, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        fillWithBlocks(par1World, par3StructureBoundingBox, 5, 1, 9, 7, 1, 9, Block.stairSingle.blockID, Block.stairSingle.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 5, 2, 7, 7, 2, 7, Block.stairSingle.blockID, Block.stairSingle.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 4, 5, 7, 4, 5, 9, Block.stairSingle.blockID, Block.stairSingle.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 8, 5, 7, 8, 5, 9, Block.stairSingle.blockID, Block.stairSingle.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 5, 5, 7, 7, 5, 9, Block.stairDouble.blockID, Block.stairDouble.blockID, false);
        placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 6, 5, 6, par3StructureBoundingBox);
        return true;
    }
}
