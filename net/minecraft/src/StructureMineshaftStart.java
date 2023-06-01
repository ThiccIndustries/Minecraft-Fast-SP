package net.minecraft.src;

import java.util.LinkedList;
import java.util.Random;

public class StructureMineshaftStart extends StructureStart
{
    public StructureMineshaftStart(World par1World, Random par2Random, int par3, int par4)
    {
        ComponentMineshaftRoom componentmineshaftroom = new ComponentMineshaftRoom(0, par2Random, (par3 << 4) + 2, (par4 << 4) + 2);
        components.add(componentmineshaftroom);
        componentmineshaftroom.buildComponent(componentmineshaftroom, components, par2Random);
        updateBoundingBox();
        markAvailableHeight(par1World, par2Random, 10);
    }
}
