package net.minecraft.src;

import java.util.List;
import java.util.Random;

abstract class ComponentStronghold extends StructureComponent
{
    protected ComponentStronghold(int par1)
    {
        super(par1);
    }

    /**
     * 'builds a door of the enumerated types (empty opening is a door)'
     */
    protected void placeDoor(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox, EnumDoor par4EnumDoor, int par5, int par6, int par7)
    {
        switch (EnumDoorHelper.doorEnum[par4EnumDoor.ordinal()])
        {
            case 1:
            default:
                fillWithBlocks(par1World, par3StructureBoundingBox, par5, par6, par7, (par5 + 3) - 1, (par6 + 3) - 1, par7, 0, 0, false);
                break;

            case 2:
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5, par6, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.doorWood.blockID, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.doorWood.blockID, 8, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
                break;

            case 3:
                placeBlockAtCurrentPosition(par1World, 0, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, 0, 0, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5, par6, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
                break;

            case 4:
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5, par6, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.doorSteel.blockID, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.doorSteel.blockID, 8, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.button.blockID, getMetadataWithOffset(Block.button.blockID, 4), par5 + 2, par6 + 1, par7 + 1, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.button.blockID, getMetadataWithOffset(Block.button.blockID, 3), par5 + 2, par6 + 1, par7 - 1, par3StructureBoundingBox);
                break;
        }
    }

    protected EnumDoor getRandomDoor(Random par1Random)
    {
        int i = par1Random.nextInt(5);

        switch (i)
        {
            case 0:
            case 1:
            default:
                return EnumDoor.OPENING;

            case 2:
                return EnumDoor.WOOD_DOOR;

            case 3:
                return EnumDoor.GRATES;

            case 4:
                return EnumDoor.IRON_DOOR;
        }
    }

    /**
     * Gets the next component in any cardinal direction
     */
    protected StructureComponent getNextComponentNormal(ComponentStrongholdStairs2 par1ComponentStrongholdStairs2, List par2List, Random par3Random, int par4, int par5)
    {
        switch (coordBaseMode)
        {
            case 2:
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.minX + par4, boundingBox.minY + par5, boundingBox.minZ - 1, coordBaseMode, getComponentType());

            case 0:
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.minX + par4, boundingBox.minY + par5, boundingBox.maxZ + 1, coordBaseMode, getComponentType());

            case 1:
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY + par5, boundingBox.minZ + par4, coordBaseMode, getComponentType());

            case 3:
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY + par5, boundingBox.minZ + par4, coordBaseMode, getComponentType());
        }

        return null;
    }

    /**
     * Gets the next component in the +/- X direction
     */
    protected StructureComponent getNextComponentX(ComponentStrongholdStairs2 par1ComponentStrongholdStairs2, List par2List, Random par3Random, int par4, int par5)
    {
        switch (coordBaseMode)
        {
            case 2:
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY + par4, boundingBox.minZ + par5, 1, getComponentType());

            case 0:
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY + par4, boundingBox.minZ + par5, 1, getComponentType());

            case 1:
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.minX + par5, boundingBox.minY + par4, boundingBox.minZ - 1, 2, getComponentType());

            case 3:
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.minX + par5, boundingBox.minY + par4, boundingBox.minZ - 1, 2, getComponentType());
        }

        return null;
    }

    /**
     * Gets the next component in the +/- Z direction
     */
    protected StructureComponent getNextComponentZ(ComponentStrongholdStairs2 par1ComponentStrongholdStairs2, List par2List, Random par3Random, int par4, int par5)
    {
        switch (coordBaseMode)
        {
            case 2:
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY + par4, boundingBox.minZ + par5, 3, getComponentType());

            case 0:
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY + par4, boundingBox.minZ + par5, 3, getComponentType());

            case 1:
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.minX + par5, boundingBox.minY + par4, boundingBox.maxZ + 1, 0, getComponentType());

            case 3:
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.minX + par5, boundingBox.minY + par4, boundingBox.maxZ + 1, 0, getComponentType());
        }

        return null;
    }

    /**
     * returns false if the Structure Bounding Box goes below 10
     */
    protected static boolean canStrongholdGoDeeper(StructureBoundingBox par0StructureBoundingBox)
    {
        return par0StructureBoundingBox != null && par0StructureBoundingBox.minY > 10;
    }
}
