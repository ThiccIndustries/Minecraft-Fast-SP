package net.minecraft.src;

import java.util.Random;

public class NoiseGeneratorPerlin extends NoiseGenerator
{
    private int permutations[];
    public double xCoord;
    public double yCoord;
    public double zCoord;

    public NoiseGeneratorPerlin()
    {
        this(new Random());
    }

    public NoiseGeneratorPerlin(Random par1Random)
    {
        permutations = new int[512];
        xCoord = par1Random.nextDouble() * 256D;
        yCoord = par1Random.nextDouble() * 256D;
        zCoord = par1Random.nextDouble() * 256D;

        for (int i = 0; i < 256; i++)
        {
            permutations[i] = i;
        }

        for (int j = 0; j < 256; j++)
        {
            int k = par1Random.nextInt(256 - j) + j;
            int l = permutations[j];
            permutations[j] = permutations[k];
            permutations[k] = l;
            permutations[j + 256] = permutations[j];
        }
    }

    public final double lerp(double par1, double par3, double par5)
    {
        return par3 + par1 * (par5 - par3);
    }

    public final double func_4110_a(int par1, double par2, double par4)
    {
        int i = par1 & 0xf;
        double d = (double)(1 - ((i & 8) >> 3)) * par2;
        double d1 = i >= 4 ? i != 12 && i != 14 ? par4 : par2 : 0.0D;
        return ((i & 1) != 0 ? -d : d) + ((i & 2) != 0 ? -d1 : d1);
    }

    public final double grad(int par1, double par2, double par4, double par6)
    {
        int i = par1 & 0xf;
        double d = i >= 8 ? par4 : par2;
        double d1 = i >= 4 ? i != 12 && i != 14 ? par6 : par2 : par4;
        return ((i & 1) != 0 ? -d : d) + ((i & 2) != 0 ? -d1 : d1);
    }

    public void func_805_a(double par1ArrayOfDouble[], double par2, double par4, double par6, int par8, int par9, int par10, double par11, double par13, double par15, double par17)
    {
        if (par9 == 1)
        {
            boolean flag = false;
            boolean flag1 = false;
            boolean flag2 = false;
            boolean flag3 = false;
            double d1 = 0.0D;
            double d3 = 0.0D;
            int k2 = 0;
            double d5 = 1.0D / par17;

            for (int j3 = 0; j3 < par8; j3++)
            {
                double d7 = par2 + (double)j3 * par11 + xCoord;
                int k3 = (int)d7;

                if (d7 < (double)k3)
                {
                    k3--;
                }

                int l3 = k3 & 0xff;
                d7 -= k3;
                double d10 = d7 * d7 * d7 * (d7 * (d7 * 6D - 15D) + 10D);

                for (int i4 = 0; i4 < par10; i4++)
                {
                    double d12 = par6 + (double)i4 * par15 + zCoord;
                    int k4 = (int)d12;

                    if (d12 < (double)k4)
                    {
                        k4--;
                    }

                    int i5 = k4 & 0xff;
                    d12 -= k4;
                    double d14 = d12 * d12 * d12 * (d12 * (d12 * 6D - 15D) + 10D);
                    int i = permutations[l3] + 0;
                    int k = permutations[i] + i5;
                    int l = permutations[l3 + 1] + 0;
                    int i1 = permutations[l] + i5;
                    double d2 = lerp(d10, func_4110_a(permutations[k], d7, d12), grad(permutations[i1], d7 - 1.0D, 0.0D, d12));
                    double d4 = lerp(d10, grad(permutations[k + 1], d7, 0.0D, d12 - 1.0D), grad(permutations[i1 + 1], d7 - 1.0D, 0.0D, d12 - 1.0D));
                    double d16 = lerp(d14, d2, d4);
                    par1ArrayOfDouble[k2++] += d16 * d5;
                }
            }

            return;
        }

        int j = 0;
        double d = 1.0D / par17;
        int j1 = -1;
        boolean flag4 = false;
        boolean flag5 = false;
        boolean flag6 = false;
        boolean flag7 = false;
        boolean flag8 = false;
        boolean flag9 = false;
        double d6 = 0.0D;
        double d8 = 0.0D;
        double d9 = 0.0D;
        double d11 = 0.0D;

        for (int j4 = 0; j4 < par8; j4++)
        {
            double d13 = par2 + (double)j4 * par11 + xCoord;
            int l4 = (int)d13;

            if (d13 < (double)l4)
            {
                l4--;
            }

            int j5 = l4 & 0xff;
            d13 -= l4;
            double d15 = d13 * d13 * d13 * (d13 * (d13 * 6D - 15D) + 10D);

            for (int k5 = 0; k5 < par10; k5++)
            {
                double d17 = par6 + (double)k5 * par15 + zCoord;
                int l5 = (int)d17;

                if (d17 < (double)l5)
                {
                    l5--;
                }

                int i6 = l5 & 0xff;
                d17 -= l5;
                double d18 = d17 * d17 * d17 * (d17 * (d17 * 6D - 15D) + 10D);

                for (int j6 = 0; j6 < par9; j6++)
                {
                    double d19 = par4 + (double)j6 * par13 + yCoord;
                    int k6 = (int)d19;

                    if (d19 < (double)k6)
                    {
                        k6--;
                    }

                    int l6 = k6 & 0xff;
                    d19 -= k6;
                    double d20 = d19 * d19 * d19 * (d19 * (d19 * 6D - 15D) + 10D);

                    if (j6 == 0 || l6 != j1)
                    {
                        j1 = l6;
                        int k1 = permutations[j5] + l6;
                        int l1 = permutations[k1] + i6;
                        int i2 = permutations[k1 + 1] + i6;
                        int j2 = permutations[j5 + 1] + l6;
                        int l2 = permutations[j2] + i6;
                        int i3 = permutations[j2 + 1] + i6;
                        d6 = lerp(d15, grad(permutations[l1], d13, d19, d17), grad(permutations[l2], d13 - 1.0D, d19, d17));
                        d8 = lerp(d15, grad(permutations[i2], d13, d19 - 1.0D, d17), grad(permutations[i3], d13 - 1.0D, d19 - 1.0D, d17));
                        d9 = lerp(d15, grad(permutations[l1 + 1], d13, d19, d17 - 1.0D), grad(permutations[l2 + 1], d13 - 1.0D, d19, d17 - 1.0D));
                        d11 = lerp(d15, grad(permutations[i2 + 1], d13, d19 - 1.0D, d17 - 1.0D), grad(permutations[i3 + 1], d13 - 1.0D, d19 - 1.0D, d17 - 1.0D));
                    }

                    double d21 = lerp(d20, d6, d8);
                    double d22 = lerp(d20, d9, d11);
                    double d23 = lerp(d18, d21, d22);
                    par1ArrayOfDouble[j++] += d23 * d;
                }
            }
        }
    }
}
