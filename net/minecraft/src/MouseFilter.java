package net.minecraft.src;

public class MouseFilter
{
    private float field_22388_a;
    private float field_22387_b;
    private float field_22389_c;

    public MouseFilter()
    {
    }

    public float func_22386_a(float par1, float par2)
    {
        field_22388_a += par1;
        par1 = (field_22388_a - field_22387_b) * par2;
        field_22389_c = field_22389_c + (par1 - field_22389_c) * 0.5F;

        if (par1 > 0.0F && par1 > field_22389_c || par1 < 0.0F && par1 < field_22389_c)
        {
            par1 = field_22389_c;
        }

        field_22387_b += par1;
        return par1;
    }
}
