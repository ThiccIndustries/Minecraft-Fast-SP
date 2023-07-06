package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdLibrary extends ComponentStronghold
{
    private static final StructurePieceTreasure field_35056_b[];
    protected final EnumDoor doorType;
    private final boolean isLargeRoom;

    public ComponentStrongholdLibrary(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
    {
        super(par1);
        coordBaseMode = par4;
        doorType = getRandomDoor(par2Random);
        boundingBox = par3StructureBoundingBox;
        isLargeRoom = par3StructureBoundingBox.getYSize() > 6;
    }

    /**
     * 'Initiates construction of the Structure Component picked, at the current Location of StructGen'
     */
    public void buildComponent(StructureComponent structurecomponent, List list, Random random)
    {
    }

    public static ComponentStrongholdLibrary findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -1, 0, 14, 11, 15, par5);

        if (!canStrongholdGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(par0List, structureboundingbox) != null)
        {
            structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -1, 0, 14, 6, 15, par5);

            if (!canStrongholdGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(par0List, structureboundingbox) != null)
            {
                return null;
            }
        }

        return new ComponentStrongholdLibrary(par6, par1Random, structureboundingbox, par5);
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

        byte byte0 = 11;

        if (!isLargeRoom)
        {
            byte0 = 6;
        }

        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 13, byte0 - 1, 14, true, par2Random, StructureStrongholdPieces.getStrongholdStones());
        placeDoor(par1World, par2Random, par3StructureBoundingBox, doorType, 4, 1, 0);
        randomlyFillWithBlocks(par1World, par3StructureBoundingBox, par2Random, 0.07F, 2, 1, 1, 11, 4, 13, Block.web.blockID, Block.web.blockID, false);

        for (int i = 1; i <= 13; i++)
        {
            if ((i - 1) % 4 == 0)
            {
                fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, i, 1, 4, i, Block.planks.blockID, Block.planks.blockID, false);
                fillWithBlocks(par1World, par3StructureBoundingBox, 12, 1, i, 12, 4, i, Block.planks.blockID, Block.planks.blockID, false);
                placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 2, 3, i, par3StructureBoundingBox);
                placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 11, 3, i, par3StructureBoundingBox);

                if (isLargeRoom)
                {
                    fillWithBlocks(par1World, par3StructureBoundingBox, 1, 6, i, 1, 9, i, Block.planks.blockID, Block.planks.blockID, false);
                    fillWithBlocks(par1World, par3StructureBoundingBox, 12, 6, i, 12, 9, i, Block.planks.blockID, Block.planks.blockID, false);
                }

                continue;
            }

            fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, i, 1, 4, i, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, 12, 1, i, 12, 4, i, Block.bookShelf.blockID, Block.bookShelf.blockID, false);

            if (isLargeRoom)
            {
                fillWithBlocks(par1World, par3StructureBoundingBox, 1, 6, i, 1, 9, i, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
                fillWithBlocks(par1World, par3StructureBoundingBox, 12, 6, i, 12, 9, i, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
            }
        }

        for (int j = 3; j < 12; j += 2)
        {
            fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, j, 4, 3, j, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, 6, 1, j, 7, 3, j, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, 9, 1, j, 10, 3, j, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
        }

        if (isLargeRoom)
        {
            fillWithBlocks(par1World, par3StructureBoundingBox, 1, 5, 1, 3, 5, 13, Block.planks.blockID, Block.planks.blockID, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, 10, 5, 1, 12, 5, 13, Block.planks.blockID, Block.planks.blockID, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, 4, 5, 1, 9, 5, 2, Block.planks.blockID, Block.planks.blockID, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, 4, 5, 12, 9, 5, 13, Block.planks.blockID, Block.planks.blockID, false);
            placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 9, 5, 11, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 8, 5, 11, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 9, 5, 10, par3StructureBoundingBox);
            fillWithBlocks(par1World, par3StructureBoundingBox, 3, 6, 2, 3, 6, 12, Block.fence.blockID, Block.fence.blockID, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, 10, 6, 2, 10, 6, 10, Block.fence.blockID, Block.fence.blockID, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, 4, 6, 2, 9, 6, 2, Block.fence.blockID, Block.fence.blockID, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, 4, 6, 12, 8, 6, 12, Block.fence.blockID, Block.fence.blockID, false);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 9, 6, 11, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 8, 6, 11, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 9, 6, 10, par3StructureBoundingBox);
            int k = getMetadataWithOffset(Block.ladder.blockID, 3);
            placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, k, 10, 1, 13, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, k, 10, 2, 13, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, k, 10, 3, 13, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, k, 10, 4, 13, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, k, 10, 5, 13, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, k, 10, 6, 13, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, k, 10, 7, 13, par3StructureBoundingBox);
            byte byte1 = 7;
            byte byte2 = 7;
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, byte1 - 1, 9, byte2, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, byte1, 9, byte2, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, byte1 - 1, 8, byte2, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, byte1, 8, byte2, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, byte1 - 1, 7, byte2, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, byte1, 7, byte2, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, byte1 - 2, 7, byte2, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, byte1 + 1, 7, byte2, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, byte1 - 1, 7, byte2 - 1, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, byte1 - 1, 7, byte2 + 1, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, byte1, 7, byte2 - 1, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, byte1, 7, byte2 + 1, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, byte1 - 2, 8, byte2, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, byte1 + 1, 8, byte2, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, byte1 - 1, 8, byte2 - 1, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, byte1 - 1, 8, byte2 + 1, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, byte1, 8, byte2 - 1, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, byte1, 8, byte2 + 1, par3StructureBoundingBox);
        }

        createTreasureChestAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 3, 3, 5, field_35056_b, 1 + par2Random.nextInt(4));

        if (isLargeRoom)
        {
            placeBlockAtCurrentPosition(par1World, 0, 0, 12, 9, 1, par3StructureBoundingBox);
            createTreasureChestAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 12, 8, 1, field_35056_b, 1 + par2Random.nextInt(4));
        }

        return true;
    }

    static
    {
        field_35056_b = (new StructurePieceTreasure[]
                {
                    new StructurePieceTreasure(Item.book.shiftedIndex, 0, 1, 3, 20), new StructurePieceTreasure(Item.paper.shiftedIndex, 0, 2, 7, 20), new StructurePieceTreasure(Item.map.shiftedIndex, 0, 1, 1, 1), new StructurePieceTreasure(Item.compass.shiftedIndex, 0, 1, 1, 1)
                });
    }
}
