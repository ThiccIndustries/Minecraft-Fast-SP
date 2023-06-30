package net.minecraft.src;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import net.minecraft.client.Minecraft;

public final class GameWindowListener extends WindowAdapter
{
    /** A reference to the Minecraft object. */
    final Minecraft mc;

    /** A reference to the Minecraft main thread. */
    final Thread mcThread;

    public GameWindowListener(Minecraft par1Minecraft, Thread par2Thread)
    {
        mc = par1Minecraft;
        mcThread = par2Thread;
    }

    public void windowClosing(WindowEvent par1WindowEvent)
    {
        mc.shutdown();

        try
        {
            mcThread.join();
        }
        catch (InterruptedException interruptedexception)
        {
            interruptedexception.printStackTrace();
        }

        System.exit(0);
    }
}
