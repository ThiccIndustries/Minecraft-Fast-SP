package net.minecraft.src;

public class TextureHDCustomFX extends TextureFX implements TextureHDFX
{
    private TexturePackBase texturePackBase;
    private int tileWidth;

    public TextureHDCustomFX(int i, int j)
    {
        super(i);
        tileWidth = 0;
        tileImage = j;
        tileWidth = 16;
        imageData = null;
    }

    public void setTileWidth(int i)
    {
        tileWidth = i;
    }

    public void setTexturePackBase(TexturePackBase texturepackbase)
    {
        texturePackBase = texturepackbase;
    }

    public void onTick()
    {
    }
}
