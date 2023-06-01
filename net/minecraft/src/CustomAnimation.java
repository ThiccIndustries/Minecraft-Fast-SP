package net.minecraft.src;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Properties;

public class CustomAnimation
{
    private String imagePath;
    public byte imageBytes[];
    public int frameWidth;
    public int frameHeight;
    public CustomAnimationFrame frames[];
    public int activeFrame;
    public String destTexture;
    public int destX;
    public int destY;

    public CustomAnimation(String s, byte abyte0[], int i, int j, Properties properties, int k)
    {
        imagePath = null;
        imageBytes = null;
        frameWidth = 0;
        frameHeight = 0;
        frames = null;
        activeFrame = 0;
        destTexture = null;
        destX = 0;
        destY = 0;
        imagePath = s;
        imageBytes = abyte0;
        frameWidth = i;
        frameHeight = j;
        int l = i * j * 4;

        if (abyte0.length % l != 0)
        {
            Config.dbg((new StringBuilder()).append("Invalid animated texture length: ").append(abyte0.length).append(", frameWidth: ").append(j).append(", frameHeight: ").append(j).toString());
        }

        int i1 = abyte0.length / l;

        if (properties.get("tile.0") != null)
        {
            for (int j1 = 0; properties.get((new StringBuilder()).append("tile.").append(j1).toString()) != null; j1++)
            {
                i1 = j1 + 1;
            }
        }

        String s1 = (String)properties.get("duration");
        int k1 = Config.parseInt(s1, k);
        frames = new CustomAnimationFrame[i1];

        for (int l1 = 0; l1 < frames.length; l1++)
        {
            String s2 = (String)properties.get((new StringBuilder()).append("tile.").append(l1).toString());
            int i2 = Config.parseInt(s2, l1);
            String s3 = (String)properties.get((new StringBuilder()).append("duration.").append(l1).toString());
            int j2 = Config.parseInt(s3, k1);
            CustomAnimationFrame customanimationframe = new CustomAnimationFrame(i2, j2);
            frames[l1] = customanimationframe;
        }
    }

    public boolean nextFrame()
    {
        if (frames.length <= 0)
        {
            return false;
        }

        if (activeFrame >= frames.length)
        {
            activeFrame = 0;
        }

        CustomAnimationFrame customanimationframe = frames[activeFrame];
        customanimationframe.counter++;

        if (customanimationframe.counter < customanimationframe.duration)
        {
            return false;
        }

        customanimationframe.counter = 0;
        activeFrame++;

        if (activeFrame >= frames.length)
        {
            activeFrame = 0;
        }

        return true;
    }

    public int getActiveFrameIndex()
    {
        if (frames.length <= 0)
        {
            return 0;
        }

        if (activeFrame >= frames.length)
        {
            activeFrame = 0;
        }

        CustomAnimationFrame customanimationframe = frames[activeFrame];
        return customanimationframe.index;
    }

    public int getFrameCount()
    {
        return frames.length;
    }

    public boolean updateCustomTexture(ByteBuffer bytebuffer, boolean flag, boolean flag1, StringBuffer stringbuffer)
    {
        if (imageBytes == null)
        {
            return false;
        }

        if (!flag && flag1)
        {
            return true;
        }

        if (!nextFrame())
        {
            return true;
        }

        int i = frameWidth * frameHeight * 4;

        if (imageBytes.length < i)
        {
            return false;
        }

        int j = getFrameCount();
        int k = getActiveFrameIndex();
        int l = 0;

        if (flag)
        {
            l = i * k;
        }

        bytebuffer.clear();
        bytebuffer.put(imageBytes, l, i);
        bytebuffer.position(0).limit(i);
        stringbuffer.append(imagePath);
        stringbuffer.append(":");
        stringbuffer.append(k);
        return true;
    }
}
