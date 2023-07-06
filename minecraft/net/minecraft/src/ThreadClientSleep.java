package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class ThreadClientSleep extends Thread
{
    /** A reference to the Minecraft object. */
    final Minecraft mc;

    public ThreadClientSleep(Minecraft par1Minecraft, String par2Str)
    {
        super(par2Str);
        mc = par1Minecraft;
        setDaemon(true);
        start();
    }

    public void run()
    {
        while (mc.running)
        {
            try
            {
                Thread.sleep(0x7fffffffL);
            }
            catch (InterruptedException interruptedexception) { }
        }
    }
}
