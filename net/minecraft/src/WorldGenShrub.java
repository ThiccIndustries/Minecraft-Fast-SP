package net.minecraft.src;

import java.util.Random;

public class WorldGenShrub extends WorldGenerator
{
    private int field_48197_a;
    private int field_48196_b;

    public WorldGenShrub(int par1, int par2)
    {
        field_48196_b = par1;
        field_48197_a = par2;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int i = 0; ((i = par1World.getBlockId(par3, par4, par5)) == 0 || i == Block.leaves.blockID) && par4 > 0; par4--) { }

        int j = par1World.getBlockId(par3, par4, par5);

        if (j == Block.dirt.blockID || j == Block.grass.blockID)
        {
            par4++;
            setBlockAndMetadata(par1World, par3, par4, par5, Block.wood.blockID, field_48196_b);

            for (int k = par4; k <= par4 + 2; k++)
            {
                int l = k - par4;
                int i1 = 2 - l;

                for (int j1 = par3 - i1; j1 <= par3 + i1; j1++)
                {
                    int k1 = j1 - par3;

                    for (int l1 = par5 - i1; l1 <= par5 + i1; l1++)
                    {
                        int i2 = l1 - par5;

                        if ((Math.abs(k1) != i1 || Math.abs(i2) != i1 || par2Random.nextInt(2) != 0) && !Block.opaqueCubeLookup[par1World.getBlockId(j1, k, l1)])
                        {
                            setBlockAndMetadata(par1World, j1, k, l1, Block.leaves.blockID, field_48197_a);
                        }
                    }
                }
            }
        }

        return true;
    }
}
