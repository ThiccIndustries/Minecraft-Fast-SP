package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentMineshaftCross extends StructureComponent
{
    private final int corridorDirection;
    private final boolean isMultipleFloors;

    public ComponentMineshaftCross(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        corridorDirection = par4;
        boundingBox = par3StructureBoundingBox;
        isMultipleFloors = par3StructureBoundingBox.getYSize() > 3;
    }

    public static StructureBoundingBox findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5)
    {
        StructureBoundingBox structureboundingbox = new StructureBoundingBox(par2, par3, par4, par2, par3 + 2, par4);

        if (par1Random.nextInt(4) == 0)
        {
            structureboundingbox.maxY += 4;
        }

        switch (par5)
        {
            case 2:
                structureboundingbox.minX = par2 - 1;
                structureboundingbox.maxX = par2 + 3;
                structureboundingbox.minZ = par4 - 4;
                break;

            case 0:
                structureboundingbox.minX = par2 - 1;
                structureboundingbox.maxX = par2 + 3;
                structureboundingbox.maxZ = par4 + 4;
                break;

            case 1:
                structureboundingbox.minX = par2 - 4;
                structureboundingbox.minZ = par4 - 1;
                structureboundingbox.maxZ = par4 + 3;
                break;

            case 3:
                structureboundingbox.maxX = par2 + 4;
                structureboundingbox.minZ = par4 - 1;
                structureboundingbox.maxZ = par4 + 3;
                break;
        }

        if (StructureComponent.findIntersecting(par0List, structureboundingbox) != null)
        {
            return null;
        }
        else
        {
            return structureboundingbox;
        }
    }

    /**
     * 'Initiates construction of the Structure Component picked, at the current Location of StructGen'
     */
    public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
    {
        int i = getComponentType();

        switch (corridorDirection)
        {
            case 2:
                StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ - 1, 2, i);
                StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ + 1, 1, i);
                StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ + 1, 3, i);
                break;

            case 0:
                StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ + 1, 0, i);
                StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ + 1, 1, i);
                StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ + 1, 3, i);
                break;

            case 1:
                StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ - 1, 2, i);
                StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ + 1, 0, i);
                StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ + 1, 1, i);
                break;

            case 3:
                StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ - 1, 2, i);
                StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ + 1, 0, i);
                StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ + 1, 3, i);
                break;
        }

        if (isMultipleFloors)
        {
            if (par3Random.nextBoolean())
            {
                StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY + 3 + 1, boundingBox.minZ - 1, 2, i);
            }

            if (par3Random.nextBoolean())
            {
                StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY + 3 + 1, boundingBox.minZ + 1, 1, i);
            }

            if (par3Random.nextBoolean())
            {
                StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY + 3 + 1, boundingBox.minZ + 1, 3, i);
            }

            if (par3Random.nextBoolean())
            {
                StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY + 3 + 1, boundingBox.maxZ + 1, 0, i);
            }
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

        if (isMultipleFloors)
        {
            fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ, boundingBox.maxX - 1, (boundingBox.minY + 3) - 1, boundingBox.maxZ, 0, 0, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY, boundingBox.minZ + 1, boundingBox.maxX, (boundingBox.minY + 3) - 1, boundingBox.maxZ - 1, 0, 0, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.maxY - 2, boundingBox.minZ, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ, 0, 0, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.maxY - 2, boundingBox.minZ + 1, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ - 1, 0, 0, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY + 3, boundingBox.minZ + 1, boundingBox.maxX - 1, boundingBox.minY + 3, boundingBox.maxZ - 1, 0, 0, false);
        }
        else
        {
            fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ, 0, 0, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY, boundingBox.minZ + 1, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ - 1, 0, 0, false);
        }

        fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ + 1, boundingBox.minX + 1, boundingBox.maxY, boundingBox.minZ + 1, Block.planks.blockID, 0, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ - 1, boundingBox.minX + 1, boundingBox.maxY, boundingBox.maxZ - 1, Block.planks.blockID, 0, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.maxX - 1, boundingBox.minY, boundingBox.minZ + 1, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.minZ + 1, Block.planks.blockID, 0, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.maxX - 1, boundingBox.minY, boundingBox.maxZ - 1, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ - 1, Block.planks.blockID, 0, false);

        for (int i = boundingBox.minX; i <= boundingBox.maxX; i++)
        {
            for (int j = boundingBox.minZ; j <= boundingBox.maxZ; j++)
            {
                int k = getBlockIdAtCurrentPosition(par1World, i, boundingBox.minY - 1, j, par3StructureBoundingBox);

                if (k == 0)
                {
                    placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, i, boundingBox.minY - 1, j, par3StructureBoundingBox);
                }
            }
        }

        return true;
    }
}
