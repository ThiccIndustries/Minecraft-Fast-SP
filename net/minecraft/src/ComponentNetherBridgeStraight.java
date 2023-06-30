package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentNetherBridgeStraight extends ComponentNetherBridgePiece
{
    public ComponentNetherBridgeStraight(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
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
        getNextComponentNormal((ComponentNetherBridgeStartPiece)par1StructureComponent, par2List, par3Random, 1, 3, false);
    }

    /**
     * Creates and returns a new component piece. Or null if it could not find enough room to place it.
     */
    public static ComponentNetherBridgeStraight createValidComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -3, 0, 5, 10, 19, par5);

        if (!isAboveGround(structureboundingbox) || StructureComponent.findIntersecting(par0List, structureboundingbox) != null)
        {
            return null;
        }
        else
        {
            return new ComponentNetherBridgeStraight(par6, par1Random, structureboundingbox, par5);
        }
    }

    /**
     * 'second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...'
     */
    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 3, 0, 4, 4, 18, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 5, 0, 3, 7, 18, 0, 0, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 5, 0, 0, 5, 18, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 4, 5, 0, 4, 5, 18, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 2, 5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 13, 4, 2, 18, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 3, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 15, 4, 1, 18, Block.netherBrick.blockID, Block.netherBrick.blockID, false);

        for (int i = 0; i <= 4; i++)
        {
            for (int j = 0; j <= 2; j++)
            {
                fillCurrentPositionBlocksDownwards(par1World, Block.netherBrick.blockID, 0, i, -1, j, par3StructureBoundingBox);
                fillCurrentPositionBlocksDownwards(par1World, Block.netherBrick.blockID, 0, i, -1, 18 - j, par3StructureBoundingBox);
            }
        }

        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 4, 1, Block.netherFence.blockID, Block.netherFence.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 3, 4, 0, 4, 4, Block.netherFence.blockID, Block.netherFence.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 3, 14, 0, 4, 14, Block.netherFence.blockID, Block.netherFence.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 17, 0, 4, 17, Block.netherFence.blockID, Block.netherFence.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 4, 1, Block.netherFence.blockID, Block.netherFence.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 4, 3, 4, 4, 4, 4, Block.netherFence.blockID, Block.netherFence.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 4, 3, 14, 4, 4, 14, Block.netherFence.blockID, Block.netherFence.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 17, 4, 4, 17, Block.netherFence.blockID, Block.netherFence.blockID, false);
        return true;
    }
}
