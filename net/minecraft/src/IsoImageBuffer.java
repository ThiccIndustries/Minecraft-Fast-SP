package net.minecraft.src;

import java.awt.image.BufferedImage;

public class IsoImageBuffer
{
    public BufferedImage image;
    public World level;
    public int x;
    public int y;
    public boolean rendered;
    public boolean noContent;
    public int lastVisible;
    public boolean addedToRenderQueue;

    public IsoImageBuffer(World par1World, int par2, int par3)
    {
        rendered = false;
        noContent = false;
        lastVisible = 0;
        addedToRenderQueue = false;
        level = par1World;
        init(par2, par3);
    }

    public void init(int par1, int par2)
    {
        rendered = false;
        x = par1;
        y = par2;
        lastVisible = 0;
        addedToRenderQueue = false;
    }

    public void init(World par1World, int par2, int par3)
    {
        level = par1World;
        init(par2, par3);
    }
}
