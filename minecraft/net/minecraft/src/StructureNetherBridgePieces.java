package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class StructureNetherBridgePieces
{
    private static final StructureNetherBridgePieceWeight primaryComponents[];
    private static final StructureNetherBridgePieceWeight secondaryComponents[];

    public StructureNetherBridgePieces()
    {
    }

    private static ComponentNetherBridgePiece createNextComponentRandom(StructureNetherBridgePieceWeight par0StructureNetherBridgePieceWeight, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        Class class1 = par0StructureNetherBridgePieceWeight.field_40699_a;
        Object obj = null;

        if (class1 == (net.minecraft.src.ComponentNetherBridgeStraight.class))
        {
            obj = ComponentNetherBridgeStraight.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (class1 == (net.minecraft.src.ComponentNetherBridgeCrossing3.class))
        {
            obj = ComponentNetherBridgeCrossing3.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (class1 == (net.minecraft.src.ComponentNetherBridgeCrossing.class))
        {
            obj = ComponentNetherBridgeCrossing.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (class1 == (net.minecraft.src.ComponentNetherBridgeStairs.class))
        {
            obj = ComponentNetherBridgeStairs.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (class1 == (net.minecraft.src.ComponentNetherBridgeThrone.class))
        {
            obj = ComponentNetherBridgeThrone.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (class1 == (net.minecraft.src.ComponentNetherBridgeEntrance.class))
        {
            obj = ComponentNetherBridgeEntrance.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (class1 == (net.minecraft.src.ComponentNetherBridgeCorridor5.class))
        {
            obj = ComponentNetherBridgeCorridor5.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (class1 == (net.minecraft.src.ComponentNetherBridgeCorridor2.class))
        {
            obj = ComponentNetherBridgeCorridor2.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (class1 == (net.minecraft.src.ComponentNetherBridgeCorridor.class))
        {
            obj = ComponentNetherBridgeCorridor.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (class1 == (net.minecraft.src.ComponentNetherBridgeCorridor3.class))
        {
            obj = ComponentNetherBridgeCorridor3.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (class1 == (net.minecraft.src.ComponentNetherBridgeCorridor4.class))
        {
            obj = ComponentNetherBridgeCorridor4.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (class1 == (net.minecraft.src.ComponentNetherBridgeCrossing2.class))
        {
            obj = ComponentNetherBridgeCrossing2.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }
        else if (class1 == (net.minecraft.src.ComponentNetherBridgeNetherStalkRoom.class))
        {
            obj = ComponentNetherBridgeNetherStalkRoom.createValidComponent(par1List, par2Random, par3, par4, par5, par6, par7);
        }

        return ((ComponentNetherBridgePiece)(obj));
    }

    static ComponentNetherBridgePiece createNextComponent(StructureNetherBridgePieceWeight par0StructureNetherBridgePieceWeight, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        return createNextComponentRandom(par0StructureNetherBridgePieceWeight, par1List, par2Random, par3, par4, par5, par6, par7);
    }

    static StructureNetherBridgePieceWeight[] getPrimaryComponents()
    {
        return primaryComponents;
    }

    static StructureNetherBridgePieceWeight[] getSecondaryComponents()
    {
        return secondaryComponents;
    }

    static
    {
        primaryComponents = (new StructureNetherBridgePieceWeight[]
                {
                    new StructureNetherBridgePieceWeight(net.minecraft.src.ComponentNetherBridgeStraight.class, 30, 0, true), new StructureNetherBridgePieceWeight(net.minecraft.src.ComponentNetherBridgeCrossing3.class, 10, 4), new StructureNetherBridgePieceWeight(net.minecraft.src.ComponentNetherBridgeCrossing.class, 10, 4), new StructureNetherBridgePieceWeight(net.minecraft.src.ComponentNetherBridgeStairs.class, 10, 3), new StructureNetherBridgePieceWeight(net.minecraft.src.ComponentNetherBridgeThrone.class, 5, 2), new StructureNetherBridgePieceWeight(net.minecraft.src.ComponentNetherBridgeEntrance.class, 5, 1)
                });
        secondaryComponents = (new StructureNetherBridgePieceWeight[]
                {
                    new StructureNetherBridgePieceWeight(net.minecraft.src.ComponentNetherBridgeCorridor5.class, 25, 0, true), new StructureNetherBridgePieceWeight(net.minecraft.src.ComponentNetherBridgeCrossing2.class, 15, 5), new StructureNetherBridgePieceWeight(net.minecraft.src.ComponentNetherBridgeCorridor2.class, 5, 10), new StructureNetherBridgePieceWeight(net.minecraft.src.ComponentNetherBridgeCorridor.class, 5, 10), new StructureNetherBridgePieceWeight(net.minecraft.src.ComponentNetherBridgeCorridor3.class, 10, 3, true), new StructureNetherBridgePieceWeight(net.minecraft.src.ComponentNetherBridgeCorridor4.class, 7, 2), new StructureNetherBridgePieceWeight(net.minecraft.src.ComponentNetherBridgeNetherStalkRoom.class, 5, 2)
                });
    }
}
