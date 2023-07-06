package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentNetherBridgeCorridor3 extends ComponentNetherBridgePiece
{
    public ComponentNetherBridgeCorridor3(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        coordBaseMode = par4;
        boundingBox = par3StructureBoundingBox;
    }

    /**
     * 'Initiates construction of the Structure Component picked, at the current Location of StructGen'
     */
    public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
    {
        getNextComponentNormal((ComponentNetherBridgeStartPiece)par1StructureComponent, par2List, par3Random, 1, 0, true);
    }

    /**
     * Creates and returns a new component piece. Or null if it could not find enough room to place it.
     */
    public static ComponentNetherBridgeCorridor3 createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -7, 0, 5, 14, 10, par5);

        if (!isAboveGround(structureboundingbox) || StructureComponent.findIntersecting(par0List, structureboundingbox) != null)
        {
            return null;
        }
        else
        {
            return new ComponentNetherBridgeCorridor3(par6, par1Random, structureboundingbox, par5);
        }
    }

    /**
     * 'second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...'
     */
    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        int i = getMetadataWithOffset(Block.stairsNetherBrick.blockID, 2);

        for (int j = 0; j <= 9; j++)
        {
            int k = Math.max(1, 7 - j);
            int l = Math.min(Math.max(k + 5, 14 - j), 13);
            int i1 = j;
            fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, i1, 4, k, i1, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, 1, k + 1, i1, 3, l - 1, i1, 0, 0, false);

            if (j <= 6)
            {
                placeBlockAtCurrentPosition(par1World, Block.stairsNetherBrick.blockID, i, 1, k + 1, i1, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stairsNetherBrick.blockID, i, 2, k + 1, i1, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stairsNetherBrick.blockID, i, 3, k + 1, i1, par3StructureBoundingBox);
            }

            fillWithBlocks(par1World, par3StructureBoundingBox, 0, l, i1, 4, l, i1, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, 0, k + 1, i1, 0, l - 1, i1, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, 4, k + 1, i1, 4, l - 1, i1, Block.netherBrick.blockID, Block.netherBrick.blockID, false);

            if ((j & 1) == 0)
            {
                fillWithBlocks(par1World, par3StructureBoundingBox, 0, k + 2, i1, 0, k + 3, i1, Block.netherFence.blockID, Block.netherFence.blockID, false);
                fillWithBlocks(par1World, par3StructureBoundingBox, 4, k + 2, i1, 4, k + 3, i1, Block.netherFence.blockID, Block.netherFence.blockID, false);
            }

            for (int j1 = 0; j1 <= 4; j1++)
            {
                fillCurrentPositionBlocksDownwards(par1World, Block.netherBrick.blockID, 0, j1, -1, i1, par3StructureBoundingBox);
            }
        }

        return true;
    }
}
