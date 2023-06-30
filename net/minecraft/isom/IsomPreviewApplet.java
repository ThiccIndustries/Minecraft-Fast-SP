package net.minecraft.isom;

import java.applet.Applet;
import java.awt.BorderLayout;
import net.minecraft.src.CanvasIsomPreview;

public class IsomPreviewApplet extends Applet
{
    private CanvasIsomPreview isomPreview;

    public IsomPreviewApplet()
    {
        isomPreview = new CanvasIsomPreview();
        setLayout(new BorderLayout());
        add(isomPreview, "Center");
    }

    public void start()
    {
        isomPreview.start();
    }

    public void stop()
    {
        isomPreview.stop();
    }
}
