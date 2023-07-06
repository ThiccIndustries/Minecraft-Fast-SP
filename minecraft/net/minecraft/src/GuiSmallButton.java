package net.minecraft.src;

public class GuiSmallButton extends GuiButton
{
    private final EnumOptions enumOptions;

    public GuiSmallButton(int par1, int par2, int par3, String par4Str)
    {
        this(par1, par2, par3, null, par4Str);
    }

    public GuiSmallButton(int par1, int par2, int par3, int par4, int par5, String par6Str)
    {
        super(par1, par2, par3, par4, par5, par6Str);
        enumOptions = null;
    }

    public GuiSmallButton(int par1, int par2, int par3, EnumOptions par4EnumOptions, String par5Str)
    {
        super(par1, par2, par3, 150, 20, par5Str);
        enumOptions = par4EnumOptions;
    }

    public EnumOptions returnEnumOptions()
    {
        return enumOptions;
    }
}
