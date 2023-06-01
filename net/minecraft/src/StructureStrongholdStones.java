package net.minecraft.src;

import java.util.Random;

class StructureStrongholdStones extends StructurePieceBlockSelector
{
    private StructureStrongholdStones()
    {
    }

    /**
     * 'picks Block Ids and Metadata (Silverfish)'
     */
    public void selectBlocks(Random par1Random, int par2, int par3, int par4, boolean par5)
    {
        if (!par5)
        {
            selectedBlockId = 0;
            selectedBlockMetaData = 0;
        }
        else
        {
            selectedBlockId = Block.stoneBrick.blockID;
            float f = par1Random.nextFloat();

            if (f < 0.2F)
            {
                selectedBlockMetaData = 2;
            }
            else if (f < 0.5F)
            {
                selectedBlockMetaData = 1;
            }
            else if (f < 0.55F)
            {
                selectedBlockId = Block.silverfish.blockID;
                selectedBlockMetaData = 2;
            }
            else
            {
                selectedBlockMetaData = 0;
            }
        }
    }

    StructureStrongholdStones(StructureStrongholdPieceWeight2 par1StructureStrongholdPieceWeight2)
    {
        this();
    }
}
