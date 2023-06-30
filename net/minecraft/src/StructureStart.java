package net.minecraft.src;

import java.util.*;

public abstract class StructureStart
{
    /** List of all StructureComponents that are part of this structure */
    protected LinkedList components;
    protected StructureBoundingBox boundingBox;

    protected StructureStart()
    {
        components = new LinkedList();
    }

    public StructureBoundingBox getBoundingBox()
    {
        return boundingBox;
    }

    public LinkedList getComponents()
    {
        return components;
    }

    /**
     * 'Keeps iterating Structure Pieces and spawning them until the checks tell it to stop'
     */
    public void generateStructure(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        Iterator iterator = components.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            StructureComponent structurecomponent = (StructureComponent)iterator.next();

            if (structurecomponent.getBoundingBox().intersectsWith(par3StructureBoundingBox) && !structurecomponent.addComponentParts(par1World, par2Random, par3StructureBoundingBox))
            {
                iterator.remove();
            }
        }
        while (true);
    }

    /**
     * Calculates total bounding box based on components' bounding boxes and saves it to boundingBox
     */
    protected void updateBoundingBox()
    {
        boundingBox = StructureBoundingBox.getNewBoundingBox();
        StructureComponent structurecomponent;

        for (Iterator iterator = components.iterator(); iterator.hasNext(); boundingBox.expandTo(structurecomponent.getBoundingBox()))
        {
            structurecomponent = (StructureComponent)iterator.next();
        }
    }

    /**
     * 'offsets the structure Bounding Boxes up to a certain height, typically 63 - 10'
     */
    protected void markAvailableHeight(World par1World, Random par2Random, int par3)
    {
        int i = 63 - par3;
        int j = boundingBox.getYSize() + 1;

        if (j < i)
        {
            j += par2Random.nextInt(i - j);
        }

        int k = j - boundingBox.maxY;
        boundingBox.offset(0, k, 0);
        StructureComponent structurecomponent;

        for (Iterator iterator = components.iterator(); iterator.hasNext(); structurecomponent.getBoundingBox().offset(0, k, 0))
        {
            structurecomponent = (StructureComponent)iterator.next();
        }
    }

    protected void setRandomHeight(World par1World, Random par2Random, int par3, int par4)
    {
        int i = ((par4 - par3) + 1) - boundingBox.getYSize();
        int j = 1;

        if (i > 1)
        {
            j = par3 + par2Random.nextInt(i);
        }
        else
        {
            j = par3;
        }

        int k = j - boundingBox.minY;
        boundingBox.offset(0, k, 0);
        StructureComponent structurecomponent;

        for (Iterator iterator = components.iterator(); iterator.hasNext(); structurecomponent.getBoundingBox().offset(0, k, 0))
        {
            structurecomponent = (StructureComponent)iterator.next();
        }
    }

    /**
     * 'currently only defined for Villages, returns true if Village has more than 2 non-road components'
     */
    public boolean isSizeableStructure()
    {
        return true;
    }
}
