package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentNetherBridgeNetherStalkRoom extends ComponentNetherBridgePiece
{
    public ComponentNetherBridgeNetherStalkRoom(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
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
        getNextComponentNormal((ComponentNetherBridgeStartPiece)par1StructureComponent, par2List, par3Random, 5, 3, true);
        getNextComponentNormal((ComponentNetherBridgeStartPiece)par1StructureComponent, par2List, par3Random, 5, 11, true);
    }

    /**
     * Creates and returns a new component piece. Or null if it could not find enough room to place it.
     */
    public static ComponentNetherBridgeNetherStalkRoom createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -5, -3, 0, 13, 14, 13, par5);

        if (!isAboveGround(structureboundingbox) || StructureComponent.findIntersecting(par0List, structureboundingbox) != null)
        {
            return null;
        }
        else
        {
            return new ComponentNetherBridgeNetherStalkRoom(par6, par1Random, structureboundingbox, par5);
        }
    }

    /**
     * 'second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...'
     */
    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 3, 0, 12, 4, 12, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 5, 0, 12, 13, 12, 0, 0, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 5, 0, 1, 12, 12, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 11, 5, 0, 12, 12, 12, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 2, 5, 11, 4, 12, 12, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 8, 5, 11, 10, 12, 12, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 5, 9, 11, 7, 12, 12, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 2, 5, 0, 4, 12, 1, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 8, 5, 0, 10, 12, 1, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 5, 9, 0, 7, 12, 1, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 2, 11, 2, 10, 12, 10, Block.netherBrick.blockID, Block.netherBrick.blockID, false);

        for (int i = 1; i <= 11; i += 2)
        {
            fillWithBlocks(par1World, par3StructureBoundingBox, i, 10, 0, i, 11, 0, Block.netherFence.blockID, Block.netherFence.blockID, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, i, 10, 12, i, 11, 12, Block.netherFence.blockID, Block.netherFence.blockID, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, 0, 10, i, 0, 11, i, Block.netherFence.blockID, Block.netherFence.blockID, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, 12, 10, i, 12, 11, i, Block.netherFence.blockID, Block.netherFence.blockID, false);
            placeBlockAtCurrentPosition(par1World, Block.netherBrick.blockID, 0, i, 13, 0, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.netherBrick.blockID, 0, i, 13, 12, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.netherBrick.blockID, 0, 0, 13, i, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.netherBrick.blockID, 0, 12, 13, i, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.netherFence.blockID, 0, i + 1, 13, 0, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.netherFence.blockID, 0, i + 1, 13, 12, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.netherFence.blockID, 0, 0, 13, i + 1, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.netherFence.blockID, 0, 12, 13, i + 1, par3StructureBoundingBox);
        }

        placeBlockAtCurrentPosition(par1World, Block.netherFence.blockID, 0, 0, 13, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.netherFence.blockID, 0, 0, 13, 12, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.netherFence.blockID, 0, 0, 13, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.netherFence.blockID, 0, 12, 13, 0, par3StructureBoundingBox);

        for (int j = 3; j <= 9; j += 2)
        {
            fillWithBlocks(par1World, par3StructureBoundingBox, 1, 7, j, 1, 8, j, Block.netherFence.blockID, Block.netherFence.blockID, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, 11, 7, j, 11, 8, j, Block.netherFence.blockID, Block.netherFence.blockID, false);
        }

        int k = getMetadataWithOffset(Block.stairsNetherBrick.blockID, 3);

        for (int l = 0; l <= 6; l++)
        {
            int k1 = l + 4;

            for (int i2 = 5; i2 <= 7; i2++)
            {
                placeBlockAtCurrentPosition(par1World, Block.stairsNetherBrick.blockID, k, i2, 5 + l, k1, par3StructureBoundingBox);
            }

            if (k1 >= 5 && k1 <= 8)
            {
                fillWithBlocks(par1World, par3StructureBoundingBox, 5, 5, k1, 7, l + 4, k1, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
            }
            else if (k1 >= 9 && k1 <= 10)
            {
                fillWithBlocks(par1World, par3StructureBoundingBox, 5, 8, k1, 7, l + 4, k1, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
            }

            if (l >= 1)
            {
                fillWithBlocks(par1World, par3StructureBoundingBox, 5, 6 + l, k1, 7, 9 + l, k1, 0, 0, false);
            }
        }

        for (int i1 = 5; i1 <= 7; i1++)
        {
            placeBlockAtCurrentPosition(par1World, Block.stairsNetherBrick.blockID, k, i1, 12, 11, par3StructureBoundingBox);
        }

        fillWithBlocks(par1World, par3StructureBoundingBox, 5, 6, 7, 5, 7, 7, Block.netherFence.blockID, Block.netherFence.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 7, 6, 7, 7, 7, 7, Block.netherFence.blockID, Block.netherFence.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 5, 13, 12, 7, 13, 12, 0, 0, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 2, 5, 2, 3, 5, 3, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 2, 5, 9, 3, 5, 10, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 2, 5, 4, 2, 5, 8, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 9, 5, 2, 10, 5, 3, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 9, 5, 9, 10, 5, 10, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 10, 5, 4, 10, 5, 8, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        int j1 = getMetadataWithOffset(Block.stairsNetherBrick.blockID, 0);
        int l1 = getMetadataWithOffset(Block.stairsNetherBrick.blockID, 1);
        placeBlockAtCurrentPosition(par1World, Block.stairsNetherBrick.blockID, l1, 4, 5, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stairsNetherBrick.blockID, l1, 4, 5, 3, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stairsNetherBrick.blockID, l1, 4, 5, 9, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stairsNetherBrick.blockID, l1, 4, 5, 10, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stairsNetherBrick.blockID, j1, 8, 5, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stairsNetherBrick.blockID, j1, 8, 5, 3, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stairsNetherBrick.blockID, j1, 8, 5, 9, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.stairsNetherBrick.blockID, j1, 8, 5, 10, par3StructureBoundingBox);
        fillWithBlocks(par1World, par3StructureBoundingBox, 3, 4, 4, 4, 4, 8, Block.slowSand.blockID, Block.slowSand.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 8, 4, 4, 9, 4, 8, Block.slowSand.blockID, Block.slowSand.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 3, 5, 4, 4, 5, 8, Block.netherStalk.blockID, Block.netherStalk.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 8, 5, 4, 9, 5, 8, Block.netherStalk.blockID, Block.netherStalk.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 4, 2, 0, 8, 2, 12, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 4, 12, 2, 8, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 4, 0, 0, 8, 1, 3, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 4, 0, 9, 8, 1, 12, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 4, 3, 1, 8, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 9, 0, 4, 12, 1, 8, Block.netherBrick.blockID, Block.netherBrick.blockID, false);

        for (int j2 = 4; j2 <= 8; j2++)
        {
            for (int l2 = 0; l2 <= 2; l2++)
            {
                fillCurrentPositionBlocksDownwards(par1World, Block.netherBrick.blockID, 0, j2, -1, l2, par3StructureBoundingBox);
                fillCurrentPositionBlocksDownwards(par1World, Block.netherBrick.blockID, 0, j2, -1, 12 - l2, par3StructureBoundingBox);
            }
        }

        for (int k2 = 0; k2 <= 2; k2++)
        {
            for (int i3 = 4; i3 <= 8; i3++)
            {
                fillCurrentPositionBlocksDownwards(par1World, Block.netherBrick.blockID, 0, k2, -1, i3, par3StructureBoundingBox);
                fillCurrentPositionBlocksDownwards(par1World, Block.netherBrick.blockID, 0, 12 - k2, -1, i3, par3StructureBoundingBox);
            }
        }

        return true;
    }
}
