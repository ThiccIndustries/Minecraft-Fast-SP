package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdCorridor extends ComponentStronghold
{
    private final int field_35052_a;

    public ComponentStrongholdCorridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        coordBaseMode = par4;
        boundingBox = par3StructureBoundingBox;
        field_35052_a = par4 != 2 && par4 != 0 ? par3StructureBoundingBox.getXSize() : par3StructureBoundingBox.getZSize();
    }

    /**
     * 'Initiates construction of the Structure Component picked, at the current Location of StructGen'
     */
    public void buildComponent(StructureComponent structurecomponent, List list, Random random)
    {
    }

    public static StructureBoundingBox func_35051_a(List par0List, Random par1Random, int par2, int par3, int par4, int par5)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, 4, par5);
        StructureComponent structurecomponent = StructureComponent.findIntersecting(par0List, structureboundingbox);

        if (structurecomponent == null)
        {
            return null;
        }

        if (structurecomponent.getBoundingBox().minY == structureboundingbox.minY)
        {
            for (int i = 3; i >= 1; i--)
            {
                StructureBoundingBox structureboundingbox1 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, i - 1, par5);

                if (!structurecomponent.getBoundingBox().intersectsWith(structureboundingbox1))
                {
                    return StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, i, par5);
                }
            }
        }

        return null;
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

        for (int i = 0; i < field_35052_a; i++)
        {
            placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 0, 0, i, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 1, 0, i, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 2, 0, i, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 3, 0, i, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 4, 0, i, par3StructureBoundingBox);

            for (int j = 1; j <= 3; j++)
            {
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 0, j, i, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, 0, 0, 1, j, i, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, 0, 0, 2, j, i, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, 0, 0, 3, j, i, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 4, j, i, par3StructureBoundingBox);
            }

            placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 0, 4, i, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 1, 4, i, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 2, 4, i, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 3, 4, i, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 4, 4, i, par3StructureBoundingBox);
        }

        return true;
    }
}
