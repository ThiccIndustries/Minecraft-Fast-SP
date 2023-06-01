package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentNetherBridgeEnd extends ComponentNetherBridgePiece
{
    private int fillSeed;

    public ComponentNetherBridgeEnd(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        coordBaseMode = par4;
        boundingBox = par3StructureBoundingBox;
        fillSeed = par2Random.nextInt();
    }

    /**
     * 'Initiates construction of the Structure Component picked, at the current Location of StructGen'
     */
    public void buildComponent(StructureComponent structurecomponent, List list, Random random)
    {
    }

    public static ComponentNetherBridgeEnd func_40023_a(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -3, 0, 5, 10, 8, par5);

        if (!isAboveGround(structureboundingbox) || StructureComponent.findIntersecting(par0List, structureboundingbox) != null)
        {
            return null;
        }
        else
        {
            return new ComponentNetherBridgeEnd(par6, par1Random, structureboundingbox, par5);
        }
    }

    /**
     * 'second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...'
     */
    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        Random random = new Random(fillSeed);

        for (int i = 0; i <= 4; i++)
        {
            for (int i1 = 3; i1 <= 4; i1++)
            {
                int l1 = random.nextInt(8);
                fillWithBlocks(par1World, par3StructureBoundingBox, i, i1, 0, i, i1, l1, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
            }
        }

        int j = random.nextInt(8);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 5, 0, 0, 5, j, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        j = random.nextInt(8);
        fillWithBlocks(par1World, par3StructureBoundingBox, 4, 5, 0, 4, 5, j, Block.netherBrick.blockID, Block.netherBrick.blockID, false);

        for (int k = 0; k <= 4; k++)
        {
            int j1 = random.nextInt(5);
            fillWithBlocks(par1World, par3StructureBoundingBox, k, 2, 0, k, 2, j1, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        }

        for (int l = 0; l <= 4; l++)
        {
            for (int k1 = 0; k1 <= 1; k1++)
            {
                int i2 = random.nextInt(3);
                fillWithBlocks(par1World, par3StructureBoundingBox, l, k1, 0, l, k1, i2, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
            }
        }

        return true;
    }
}
