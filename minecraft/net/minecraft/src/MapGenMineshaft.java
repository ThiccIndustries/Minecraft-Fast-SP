package net.minecraft.src;

import java.util.Random;

public class MapGenMineshaft extends MapGenStructure
{
    public MapGenMineshaft()
    {
    }

    protected boolean canSpawnStructureAtCoords(int par1, int par2)
    {
        return rand.nextInt(100) == 0 && rand.nextInt(80) < Math.max(Math.abs(par1), Math.abs(par2));
    }

    protected StructureStart getStructureStart(int par1, int par2)
    {
        return new StructureMineshaftStart(worldObj, rand, par1, par2);
    }
}
