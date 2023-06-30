package net.minecraft.src;

import java.nio.*;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class GLAllocation
{
    /**
     * An ArrayList that stores the first index and the length of each display list.
     */
    private static List displayLists = new ArrayList();

    /** An ArrayList that stores all the generated texture names. */
    private static List textureNames = new ArrayList();

    public GLAllocation()
    {
    }

    /**
     * Generates the specified number of display lists and returns the first index.
     */
    public static synchronized int generateDisplayLists(int par0)
    {
        int i = GL11.glGenLists(par0);
        displayLists.add(Integer.valueOf(i));
        displayLists.add(Integer.valueOf(par0));
        return i;
    }

    /**
     * Generates texture names and stores them in the specified buffer.
     */
    public static synchronized void generateTextureNames(IntBuffer par0IntBuffer)
    {
        GL11.glGenTextures(par0IntBuffer);

        for (int i = par0IntBuffer.position(); i < par0IntBuffer.limit(); i++)
        {
            textureNames.add(Integer.valueOf(par0IntBuffer.get(i)));
        }
    }

    public static synchronized void deleteDisplayLists(int par0)
    {
        int i = displayLists.indexOf(Integer.valueOf(par0));
        GL11.glDeleteLists(((Integer)displayLists.get(i)).intValue(), ((Integer)displayLists.get(i + 1)).intValue());
        displayLists.remove(i);
        displayLists.remove(i);
    }

    /**
     * Deletes all textures and display lists. Called when Minecraft is shutdown to free up resources.
     */
    public static synchronized void deleteTexturesAndDisplayLists()
    {
        for (int i = 0; i < displayLists.size(); i += 2)
        {
            GL11.glDeleteLists(((Integer)displayLists.get(i)).intValue(), ((Integer)displayLists.get(i + 1)).intValue());
        }

        IntBuffer intbuffer = createDirectIntBuffer(textureNames.size());
        intbuffer.flip();
        GL11.glDeleteTextures(intbuffer);

        for (int j = 0; j < textureNames.size(); j++)
        {
            intbuffer.put(((Integer)textureNames.get(j)).intValue());
        }

        intbuffer.flip();
        GL11.glDeleteTextures(intbuffer);
        displayLists.clear();
        textureNames.clear();
    }

    /**
     * Creates and returns a direct byte buffer with the specified capacity. Applies native ordering to speed up access.
     */
    public static synchronized ByteBuffer createDirectByteBuffer(int par0)
    {
        ByteBuffer bytebuffer = ByteBuffer.allocateDirect(par0).order(ByteOrder.nativeOrder());
        return bytebuffer;
    }

    /**
     * Creates and returns a direct int buffer with the specified capacity. Applies native ordering to speed up access.
     */
    public static IntBuffer createDirectIntBuffer(int par0)
    {
        return createDirectByteBuffer(par0 << 2).asIntBuffer();
    }

    /**
     * Creates and returns a direct float buffer with the specified capacity. Applies native ordering to speed up
     * access.
     */
    public static FloatBuffer createDirectFloatBuffer(int par0)
    {
        return createDirectByteBuffer(par0 << 2).asFloatBuffer();
    }
}
