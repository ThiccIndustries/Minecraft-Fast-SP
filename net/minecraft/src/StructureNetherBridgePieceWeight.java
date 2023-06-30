package net.minecraft.src;

class StructureNetherBridgePieceWeight
{
    public Class field_40699_a;
    public final int field_40697_b;
    public int field_40698_c;
    public int field_40695_d;
    public boolean field_40696_e;

    public StructureNetherBridgePieceWeight(Class par1Class, int par2, int par3, boolean par4)
    {
        field_40699_a = par1Class;
        field_40697_b = par2;
        field_40695_d = par3;
        field_40696_e = par4;
    }

    public StructureNetherBridgePieceWeight(Class par1Class, int par2, int par3)
    {
        this(par1Class, par2, par3, false);
    }

    public boolean func_40693_a(int par1)
    {
        return field_40695_d == 0 || field_40698_c < field_40695_d;
    }

    public boolean func_40694_a()
    {
        return field_40695_d == 0 || field_40698_c < field_40695_d;
    }
}
