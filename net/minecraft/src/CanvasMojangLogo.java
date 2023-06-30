package net.minecraft.src;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

class CanvasMojangLogo extends Canvas
{
    /** BufferedImage object containing the Majong logo. */
    private BufferedImage logo;

    public CanvasMojangLogo()
    {
        try
        {
            logo = ImageIO.read((net.minecraft.src.PanelCrashReport.class).getResource("/gui/crash_logo.png"));
        }
        catch (IOException ioexception) { }

        byte byte0 = 100;
        setPreferredSize(new Dimension(byte0, byte0));
        setMinimumSize(new Dimension(byte0, byte0));
    }

    public void paint(Graphics par1Graphics)
    {
        super.paint(par1Graphics);
        par1Graphics.drawImage(logo, getWidth() / 2 - logo.getWidth() / 2, 32, null);
    }
}
