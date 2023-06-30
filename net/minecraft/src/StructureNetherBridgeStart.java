package net.minecraft.src;

import java.util.*;

class StructureNetherBridgeStart extends StructureStart
{
    public StructureNetherBridgeStart(World par1World, Random par2Random, int par3, int par4)
    {
        ComponentNetherBridgeStartPiece componentnetherbridgestartpiece = new ComponentNetherBridgeStartPiece(par2Random, (par3 << 4) + 2, (par4 << 4) + 2);
        components.add(componentnetherbridgestartpiece);
        componentnetherbridgestartpiece.buildComponent(componentnetherbridgestartpiece, components, par2Random);
        StructureComponent structurecomponent;

        for (ArrayList arraylist = componentnetherbridgestartpiece.field_40034_d; !arraylist.isEmpty(); structurecomponent.buildComponent(componentnetherbridgestartpiece, components, par2Random))
        {
            int i = par2Random.nextInt(arraylist.size());
            structurecomponent = (StructureComponent)arraylist.remove(i);
        }

        updateBoundingBox();
        setRandomHeight(par1World, par2Random, 48, 70);
    }
}
