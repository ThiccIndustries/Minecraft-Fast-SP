package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class StructureMineshaftPieces
{
    private static final StructurePieceTreasure lootArray[];

    public StructureMineshaftPieces()
    {
    }

    private static StructureComponent getRandomComponent(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        int i = par1Random.nextInt(100);

        if (i >= 80)
        {
            StructureBoundingBox structureboundingbox = ComponentMineshaftCross.findValidPlacement(par0List, par1Random, par2, par3, par4, par5);

            if (structureboundingbox != null)
            {
                return new ComponentMineshaftCross(par6, par1Random, structureboundingbox, par5);
            }
        }
        else if (i >= 70)
        {
            StructureBoundingBox structureboundingbox1 = ComponentMineshaftStairs.findValidPlacement(par0List, par1Random, par2, par3, par4, par5);

            if (structureboundingbox1 != null)
            {
                return new ComponentMineshaftStairs(par6, par1Random, structureboundingbox1, par5);
            }
        }
        else
        {
            StructureBoundingBox structureboundingbox2 = ComponentMineshaftCorridor.findValidPlacement(par0List, par1Random, par2, par3, par4, par5);

            if (structureboundingbox2 != null)
            {
                return new ComponentMineshaftCorridor(par6, par1Random, structureboundingbox2, par5);
            }
        }

        return null;
    }

    private static StructureComponent getNextMineShaftComponent(StructureComponent par0StructureComponent, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        if (par7 > 8)
        {
            return null;
        }

        if (Math.abs(par3 - par0StructureComponent.getBoundingBox().minX) > 80 || Math.abs(par5 - par0StructureComponent.getBoundingBox().minZ) > 80)
        {
            return null;
        }

        StructureComponent structurecomponent = getRandomComponent(par1List, par2Random, par3, par4, par5, par6, par7 + 1);

        if (structurecomponent != null)
        {
            par1List.add(structurecomponent);
            structurecomponent.buildComponent(par0StructureComponent, par1List, par2Random);
        }

        return structurecomponent;
    }

    static StructureComponent getNextComponent(StructureComponent par0StructureComponent, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        return getNextMineShaftComponent(par0StructureComponent, par1List, par2Random, par3, par4, par5, par6, par7);
    }

    static StructurePieceTreasure[] getTreasurePieces()
    {
        return lootArray;
    }

    static
    {
        lootArray = (new StructurePieceTreasure[]
                {
                    new StructurePieceTreasure(Item.ingotIron.shiftedIndex, 0, 1, 5, 10), new StructurePieceTreasure(Item.ingotGold.shiftedIndex, 0, 1, 3, 5), new StructurePieceTreasure(Item.redstone.shiftedIndex, 0, 4, 9, 5), new StructurePieceTreasure(Item.dyePowder.shiftedIndex, 4, 4, 9, 5), new StructurePieceTreasure(Item.diamond.shiftedIndex, 0, 1, 2, 3), new StructurePieceTreasure(Item.coal.shiftedIndex, 0, 3, 8, 10), new StructurePieceTreasure(Item.bread.shiftedIndex, 0, 1, 3, 15), new StructurePieceTreasure(Item.pickaxeSteel.shiftedIndex, 0, 1, 1, 1), new StructurePieceTreasure(Block.rail.blockID, 0, 4, 8, 1), new StructurePieceTreasure(Item.melonSeeds.shiftedIndex, 0, 2, 4, 10),
                    new StructurePieceTreasure(Item.pumpkinSeeds.shiftedIndex, 0, 2, 4, 10)
                });
    }
}
