package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdRoomCrossing extends ComponentStronghold
{
    private static final StructurePieceTreasure chestLoot[];
    protected final EnumDoor doorType;
    protected final int roomType;

    public ComponentStrongholdRoomCrossing(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        coordBaseMode = par4;
        doorType = getRandomDoor(par2Random);
        boundingBox = par3StructureBoundingBox;
        roomType = par2Random.nextInt(5);
    }

    /**
     * 'Initiates construction of the Structure Component picked, at the current Location of StructGen'
     */
    public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
    {
        getNextComponentNormal((ComponentStrongholdStairs2)par1StructureComponent, par2List, par3Random, 4, 1);
        getNextComponentX((ComponentStrongholdStairs2)par1StructureComponent, par2List, par3Random, 1, 4);
        getNextComponentZ((ComponentStrongholdStairs2)par1StructureComponent, par2List, par3Random, 1, 4);
    }

    public static ComponentStrongholdRoomCrossing findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -1, 0, 11, 7, 11, par5);

        if (!canStrongholdGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(par0List, structureboundingbox) != null)
        {
            return null;
        }
        else
        {
            return new ComponentStrongholdRoomCrossing(par6, par1Random, structureboundingbox, par5);
        }
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

        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 10, 6, 10, true, par2Random, StructureStrongholdPieces.getStrongholdStones());
        placeDoor(par1World, par2Random, par3StructureBoundingBox, doorType, 4, 1, 0);
        fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 10, 6, 3, 10, 0, 0, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 4, 0, 3, 6, 0, 0, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 10, 1, 4, 10, 3, 6, 0, 0, false);

        switch (roomType)
        {
            default:
                break;

            case 0:
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 5, 1, 5, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 5, 2, 5, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 5, 3, 5, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 4, 3, 5, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 6, 3, 5, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 5, 3, 4, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 5, 3, 6, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stairSingle.blockID, 0, 4, 1, 4, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stairSingle.blockID, 0, 4, 1, 5, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stairSingle.blockID, 0, 4, 1, 6, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stairSingle.blockID, 0, 6, 1, 4, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stairSingle.blockID, 0, 6, 1, 5, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stairSingle.blockID, 0, 6, 1, 6, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stairSingle.blockID, 0, 5, 1, 4, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stairSingle.blockID, 0, 5, 1, 6, par3StructureBoundingBox);
                break;

            case 1:
                for (int i = 0; i < 5; i++)
                {
                    placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 3, 1, 3 + i, par3StructureBoundingBox);
                    placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 7, 1, 3 + i, par3StructureBoundingBox);
                    placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 3 + i, 1, 3, par3StructureBoundingBox);
                    placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 3 + i, 1, 7, par3StructureBoundingBox);
                }

                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 5, 1, 5, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 5, 2, 5, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 5, 3, 5, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.waterMoving.blockID, 0, 5, 4, 5, par3StructureBoundingBox);
                break;

            case 2:
                for (int j = 1; j <= 9; j++)
                {
                    placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 1, 3, j, par3StructureBoundingBox);
                    placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 9, 3, j, par3StructureBoundingBox);
                }

                for (int k = 1; k <= 9; k++)
                {
                    placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, k, 3, 1, par3StructureBoundingBox);
                    placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, k, 3, 9, par3StructureBoundingBox);
                }

                placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 5, 1, 4, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 5, 1, 6, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 5, 3, 4, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 5, 3, 6, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 4, 1, 5, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 6, 1, 5, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 4, 3, 5, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 6, 3, 5, par3StructureBoundingBox);

                for (int l = 1; l <= 3; l++)
                {
                    placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 4, l, 4, par3StructureBoundingBox);
                    placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 6, l, 4, par3StructureBoundingBox);
                    placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 4, l, 6, par3StructureBoundingBox);
                    placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 6, l, 6, par3StructureBoundingBox);
                }

                placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 5, 3, 5, par3StructureBoundingBox);

                for (int i1 = 2; i1 <= 8; i1++)
                {
                    placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 2, 3, i1, par3StructureBoundingBox);
                    placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 3, 3, i1, par3StructureBoundingBox);

                    if (i1 <= 3 || i1 >= 7)
                    {
                        placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 4, 3, i1, par3StructureBoundingBox);
                        placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 5, 3, i1, par3StructureBoundingBox);
                        placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 6, 3, i1, par3StructureBoundingBox);
                    }

                    placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 7, 3, i1, par3StructureBoundingBox);
                    placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 8, 3, i1, par3StructureBoundingBox);
                }

                placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, getMetadataWithOffset(Block.ladder.blockID, 4), 9, 1, 3, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, getMetadataWithOffset(Block.ladder.blockID, 4), 9, 2, 3, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, getMetadataWithOffset(Block.ladder.blockID, 4), 9, 3, 3, par3StructureBoundingBox);
                createTreasureChestAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 3, 4, 8, chestLoot, 1 + par2Random.nextInt(4));
                break;
        }

        return true;
    }

    static
    {
        chestLoot = (new StructurePieceTreasure[]
                {
                    new StructurePieceTreasure(Item.ingotIron.shiftedIndex, 0, 1, 5, 10), new StructurePieceTreasure(Item.ingotGold.shiftedIndex, 0, 1, 3, 5), new StructurePieceTreasure(Item.redstone.shiftedIndex, 0, 4, 9, 5), new StructurePieceTreasure(Item.coal.shiftedIndex, 0, 3, 8, 10), new StructurePieceTreasure(Item.bread.shiftedIndex, 0, 1, 3, 15), new StructurePieceTreasure(Item.appleRed.shiftedIndex, 0, 1, 3, 15), new StructurePieceTreasure(Item.pickaxeSteel.shiftedIndex, 0, 1, 1, 1)
                });
    }
}
