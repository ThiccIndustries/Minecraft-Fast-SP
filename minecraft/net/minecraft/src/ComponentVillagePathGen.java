package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillagePathGen extends ComponentVillageRoadPiece
{
    private int averageGroundLevel;

    public ComponentVillagePathGen(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        coordBaseMode = par4;
        boundingBox = par3StructureBoundingBox;
        averageGroundLevel = Math.max(par3StructureBoundingBox.getXSize(), par3StructureBoundingBox.getZSize());
    }

    /**
     * 'Initiates construction of the Structure Component picked, at the current Location of StructGen'
     */
    public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
    {
        boolean flag = false;

        for (int i = par3Random.nextInt(5); i < averageGroundLevel - 8; i += 2 + par3Random.nextInt(5))
        {
            StructureComponent structurecomponent = getNextComponentNN((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, 0, i);

            if (structurecomponent != null)
            {
                i += Math.max(structurecomponent.boundingBox.getXSize(), structurecomponent.boundingBox.getZSize());
                flag = true;
            }
        }

        for (int j = par3Random.nextInt(5); j < averageGroundLevel - 8; j += 2 + par3Random.nextInt(5))
        {
            StructureComponent structurecomponent1 = getNextComponentPP((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, 0, j);

            if (structurecomponent1 != null)
            {
                j += Math.max(structurecomponent1.boundingBox.getXSize(), structurecomponent1.boundingBox.getZSize());
                flag = true;
            }
        }

        if (flag && par3Random.nextInt(3) > 0)
        {
            switch (coordBaseMode)
            {
                case 2:
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ, 1, getComponentType());
                    break;

                case 0:
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.maxZ - 2, 1, getComponentType());
                    break;

                case 3:
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, boundingBox.maxX - 2, boundingBox.minY, boundingBox.minZ - 1, 2, getComponentType());
                    break;

                case 1:
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, boundingBox.minX, boundingBox.minY, boundingBox.minZ - 1, 2, getComponentType());
                    break;
            }
        }

        if (flag && par3Random.nextInt(3) > 0)
        {
            switch (coordBaseMode)
            {
                case 2:
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ, 3, getComponentType());
                    break;

                case 0:
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.maxZ - 2, 3, getComponentType());
                    break;

                case 3:
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, boundingBox.maxX - 2, boundingBox.minY, boundingBox.maxZ + 1, 0, getComponentType());
                    break;

                case 1:
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, boundingBox.minX, boundingBox.minY, boundingBox.maxZ + 1, 0, getComponentType());
                    break;
            }
        }
    }

    public static StructureBoundingBox func_35087_a(ComponentVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6)
    {
        for (int i = 7 * MathHelper.getRandomIntegerInRange(par2Random, 3, 5); i >= 7; i -= 7)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 3, 3, i, par6);

            if (StructureComponent.findIntersecting(par1List, structureboundingbox) == null)
            {
                return structureboundingbox;
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
        for (int i = boundingBox.minX; i <= boundingBox.maxX; i++)
        {
            for (int j = boundingBox.minZ; j <= boundingBox.maxZ; j++)
            {
                if (par3StructureBoundingBox.isVecInside(i, 64, j))
                {
                    int k = par1World.getTopSolidOrLiquidBlock(i, j) - 1;
                    par1World.setBlock(i, k, j, Block.gravel.blockID);
                }
            }
        }

        return true;
    }
}
