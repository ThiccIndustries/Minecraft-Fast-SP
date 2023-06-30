package net.minecraft.src;

import java.awt.image.BufferedImage;

public class ThreadDownloadImageData
{
    /** The image data. */
    public BufferedImage image;

    /** Number of open references to this ThreadDownloadImageData */
    public int referenceCount;

    /**
     * The GL texture name associated with this image, or -1 if the texture has not been allocated
     */
    public int textureName;

    /**
     * True if the texture has been allocated and the image copied to the texture.  This is reset if global rendering
     * settings change, so that setupTexture will be called again.
     */
    public boolean textureSetupComplete;

    public ThreadDownloadImageData(String par1Str, ImageBuffer par2ImageBuffer)
    {
        referenceCount = 1;
        textureName = -1;
        textureSetupComplete = false;
        (new ThreadDownloadImage(this, par1Str, par2ImageBuffer)).start();
    }
}
