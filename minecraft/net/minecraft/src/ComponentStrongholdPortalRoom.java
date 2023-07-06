package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdPortalRoom extends ComponentStronghold
{
    private boolean hasSpawner;

    public ComponentStrongholdPortalRoom(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
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
        if (par1StructureComponent != null)
        {
            ((ComponentStrongholdStairs2)par1StructureComponent).portalRoom = this;
        }
    }

    public static ComponentStrongholdPortalRoom findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -1, 0, 11, 8, 16, par5);

        if (!canStrongholdGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(par0List, structureboundingbox) != null)
        {
            return null;
        }
        else
        {
            return new ComponentStrongholdPortalRoom(par6, par1Random, structureboundingbox, par5);
        }
    }

    /**
     * 'second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...'
     */
    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 10, 7, 15, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        placeDoor(par1World, par2Random, par3StructureBoundingBox, EnumDoor.GRATES, 4, 1, 0);
        byte byte0 = 6;
        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, byte0, 1, 1, byte0, 14, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 9, byte0, 1, 9, byte0, 14, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 2, byte0, 1, 8, byte0, 2, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 2, byte0, 14, 8, byte0, 14, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 1, 1, 2, 1, 4, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 8, 1, 1, 9, 1, 4, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 1, 1, 1, 3, Block.lavaMoving.blockID, Block.lavaMoving.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 9, 1, 1, 9, 1, 3, Block.lavaMoving.blockID, Block.lavaMoving.blockID, false);
        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 3, 1, 8, 7, 1, 12, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 9, 6, 1, 11, Block.lavaMoving.blockID, Block.lavaMoving.blockID, false);

        for (int j = 3; j < 14; j += 2)
        {
            fillWithBlocks(par1World, par3StructureBoundingBox, 0, 3, j, 0, 4, j, Block.fenceIron.blockID, Block.fenceIron.blockID, false);
            fillWithBlocks(par1World, par3StructureBoundingBox, 10, 3, j, 10, 4, j, Block.fenceIron.blockID, Block.fenceIron.blockID, false);
        }

        for (int k = 2; k < 9; k += 2)
        {
            fillWithBlocks(par1World, par3StructureBoundingBox, k, 3, 15, k, 4, 15, Block.fenceIron.blockID, Block.fenceIron.blockID, false);
        }

        int l = getMetadataWithOffset(Block.stairsStoneBrickSmooth.blockID, 3);
        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 5, 6, 1, 7, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 2, 6, 6, 2, 7, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 3, 7, 6, 3, 7, false, par2Random, StructureStrongholdPieces.getStrongholdStones());

        for (int i1 = 4; i1 <= 6; i1++)
        {
            placeBlockAtCurrentPosition(par1World, Block.stairsStoneBrickSmooth.blockID, l, i1, 1, 4, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.stairsStoneBrickSmooth.blockID, l, i1, 2, 5, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.stairsStoneBrickSmooth.blockID, l, i1, 3, 6, par3StructureBoundingBox);
        }

        byte byte1 = 2;
        byte byte2 = 0;
        byte byte3 = 3;
        byte byte4 = 1;

        switch (coordBaseMode)
        {
            case 0:
                byte1 = 0;
                byte2 = 2;
                break;

            case 3:
                byte1 = 3;
                byte2 = 1;
                byte3 = 0;
                byte4 = 2;
                break;

            case 1:
                byte1 = 1;
                byte2 = 3;
                byte3 = 0;
                byte4 = 2;
                break;
        }

        placeBlockAtCurrentPosition(par1World, Block.endPortalFrame.blockID, byte1 + (par2Random.nextFloat() <= 0.9F ? 0 : 4), 4, 3, 8, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.endPortalFrame.blockID, byte1 + (par2Random.nextFloat() <= 0.9F ? 0 : 4), 5, 3, 8, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.endPortalFrame.blockID, byte1 + (par2Random.nextFloat() <= 0.9F ? 0 : 4), 6, 3, 8, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.endPortalFrame.blockID, byte2 + (par2Random.nextFloat() <= 0.9F ? 0 : 4), 4, 3, 12, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.endPortalFrame.blockID, byte2 + (par2Random.nextFloat() <= 0.9F ? 0 : 4), 5, 3, 12, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.endPortalFrame.blockID, byte2 + (par2Random.nextFloat() <= 0.9F ? 0 : 4), 6, 3, 12, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.endPortalFrame.blockID, byte3 + (par2Random.nextFloat() <= 0.9F ? 0 : 4), 3, 3, 9, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.endPortalFrame.blockID, byte3 + (par2Random.nextFloat() <= 0.9F ? 0 : 4), 3, 3, 10, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.endPortalFrame.blockID, byte3 + (par2Random.nextFloat() <= 0.9F ? 0 : 4), 3, 3, 11, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.endPortalFrame.blockID, byte4 + (par2Random.nextFloat() <= 0.9F ? 0 : 4), 7, 3, 9, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.endPortalFrame.blockID, byte4 + (par2Random.nextFloat() <= 0.9F ? 0 : 4), 7, 3, 10, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.endPortalFrame.blockID, byte4 + (par2Random.nextFloat() <= 0.9F ? 0 : 4), 7, 3, 11, par3StructureBoundingBox);

        if (!hasSpawner)
        {
            int i = getYWithOffset(3);
            int j1 = getXWithOffset(5, 6);
            int k1 = getZWithOffset(5, 6);

            if (par3StructureBoundingBox.isVecInside(j1, i, k1))
            {
                hasSpawner = true;
                par1World.setBlockWithNotify(j1, i, k1, Block.mobSpawner.blockID);
                TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)par1World.getBlockTileEntity(j1, i, k1);

                if (tileentitymobspawner != null)
                {
                    tileentitymobspawner.setMobID("Silverfish");
                }
            }
        }

        return true;
    }
}
