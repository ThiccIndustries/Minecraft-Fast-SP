package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageTorch extends ComponentVillage
{
    private int averageGroundLevel;

    public ComponentVillageTorch(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
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
    public static StructureBoundingBox findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, 0, 0, 0, 3, 4, 2, par5);

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

        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 2, 3, 1, 0, 0, false);
        placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 1, 0, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 1, 1, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 1, 2, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, 15, 1, 3, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 15, 0, 3, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 15, 1, 3, 1, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 15, 2, 3, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 15, 1, 3, -1, par3StructureBoundingBox);
        return true;
    }
}
