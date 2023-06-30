package net.minecraft.src;

import java.util.*;

public class ComponentMineshaftRoom extends StructureComponent
{
    private LinkedList chidStructures;

    public ComponentMineshaftRoom(int par1, Random par2Random, int par3, int par4)
    {
        super(par1);
        chidStructures = new LinkedList();
        boundingBox = new StructureBoundingBox(par3, 50, par4, par3 + 7 + par2Random.nextInt(6), 54 + par2Random.nextInt(6), par4 + 7 + par2Random.nextInt(6));
    }

    /**
     * 'Initiates construction of the Structure Component picked, at the current Location of StructGen'
     */
    public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
    {
        int i = getComponentType();
        int j1 = boundingBox.getYSize() - 3 - 1;

        if (j1 <= 0)
        {
            j1 = 1;
        }

        for (int j = 0; j < boundingBox.getXSize(); j += 4)
        {
            j += par3Random.nextInt(boundingBox.getXSize());

            if (j + 3 > boundingBox.getXSize())
            {
                break;
            }

            StructureComponent structurecomponent = StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + j, boundingBox.minY + par3Random.nextInt(j1) + 1, boundingBox.minZ - 1, 2, i);

            if (structurecomponent != null)
            {
                StructureBoundingBox structureboundingbox = structurecomponent.getBoundingBox();
                chidStructures.add(new StructureBoundingBox(structureboundingbox.minX, structureboundingbox.minY, boundingBox.minZ, structureboundingbox.maxX, structureboundingbox.maxY, boundingBox.minZ + 1));
            }
        }

        for (int k = 0; k < boundingBox.getXSize(); k += 4)
        {
            k += par3Random.nextInt(boundingBox.getXSize());

            if (k + 3 > boundingBox.getXSize())
            {
                break;
            }

            StructureComponent structurecomponent1 = StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + k, boundingBox.minY + par3Random.nextInt(j1) + 1, boundingBox.maxZ + 1, 0, i);

            if (structurecomponent1 != null)
            {
                StructureBoundingBox structureboundingbox1 = structurecomponent1.getBoundingBox();
                chidStructures.add(new StructureBoundingBox(structureboundingbox1.minX, structureboundingbox1.minY, boundingBox.maxZ - 1, structureboundingbox1.maxX, structureboundingbox1.maxY, boundingBox.maxZ));
            }
        }

        for (int l = 0; l < boundingBox.getZSize(); l += 4)
        {
            l += par3Random.nextInt(boundingBox.getZSize());

            if (l + 3 > boundingBox.getZSize())
            {
                break;
            }

            StructureComponent structurecomponent2 = StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY + par3Random.nextInt(j1) + 1, boundingBox.minZ + l, 1, i);

            if (structurecomponent2 != null)
            {
                StructureBoundingBox structureboundingbox2 = structurecomponent2.getBoundingBox();
                chidStructures.add(new StructureBoundingBox(boundingBox.minX, structureboundingbox2.minY, structureboundingbox2.minZ, boundingBox.minX + 1, structureboundingbox2.maxY, structureboundingbox2.maxZ));
            }
        }

        for (int i1 = 0; i1 < boundingBox.getZSize(); i1 += 4)
        {
            i1 += par3Random.nextInt(boundingBox.getZSize());

            if (i1 + 3 > boundingBox.getZSize())
            {
                break;
            }

            StructureComponent structurecomponent3 = StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY + par3Random.nextInt(j1) + 1, boundingBox.minZ + i1, 3, i);

            if (structurecomponent3 != null)
            {
                StructureBoundingBox structureboundingbox3 = structurecomponent3.getBoundingBox();
                chidStructures.add(new StructureBoundingBox(boundingBox.maxX - 1, structureboundingbox3.minY, structureboundingbox3.minZ, boundingBox.maxX, structureboundingbox3.maxY, structureboundingbox3.maxZ));
            }
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

        fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY, boundingBox.minZ, boundingBox.maxX, boundingBox.minY, boundingBox.maxZ, Block.dirt.blockID, 0, true);
        fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY + 1, boundingBox.minZ, boundingBox.maxX, Math.min(boundingBox.minY + 3, boundingBox.maxY), boundingBox.maxZ, 0, 0, false);
        StructureBoundingBox structureboundingbox;

        for (Iterator iterator = chidStructures.iterator(); iterator.hasNext(); fillWithBlocks(par1World, par3StructureBoundingBox, structureboundingbox.minX, structureboundingbox.maxY - 2, structureboundingbox.minZ, structureboundingbox.maxX, structureboundingbox.maxY, structureboundingbox.maxZ, 0, 0, false))
        {
            structureboundingbox = (StructureBoundingBox)iterator.next();
        }

        randomlyRareFillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY + 4, boundingBox.minZ, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ, 0, false);
        return true;
    }
}
