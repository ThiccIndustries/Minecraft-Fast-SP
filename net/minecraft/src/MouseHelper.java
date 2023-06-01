package net.minecraft.src;

import java.awt.Component;
import java.nio.IntBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

public class MouseHelper
{
    private Component windowComponent;
    private Cursor cursor;

    /** Mouse delta X this frame */
    public int deltaX;

    /** Mouse delta Y this frame */
    public int deltaY;
    private int field_1115_e;

    public MouseHelper(Component par1Component)
    {
        field_1115_e = 10;
        windowComponent = par1Component;
        IntBuffer intbuffer = GLAllocation.createDirectIntBuffer(1);
        intbuffer.put(0);
        intbuffer.flip();
        IntBuffer intbuffer1 = GLAllocation.createDirectIntBuffer(1024);

        try
        {
            cursor = new Cursor(32, 32, 16, 16, 1, intbuffer1, intbuffer);
        }
        catch (LWJGLException lwjglexception)
        {
            lwjglexception.printStackTrace();
        }
    }

    /**
     * Grabs the mouse cursor it doesn't move and isn't seen.
     */
    public void grabMouseCursor()
    {
        Mouse.setGrabbed(true);
        deltaX = 0;
        deltaY = 0;
    }

    /**
     * Ungrabs the mouse cursor so it can be moved and set it to the center of the screen
     */
    public void ungrabMouseCursor()
    {
        Mouse.setCursorPosition(windowComponent.getWidth() / 2, windowComponent.getHeight() / 2);
        Mouse.setGrabbed(false);
    }

    public void mouseXYChange()
    {
        deltaX = Mouse.getDX();
        deltaY = Mouse.getDY();
    }
}
