package net.minecraft.src;

import java.io.DataOutputStream;
import java.io.IOException;

class NetworkWriterThread extends Thread
{
    /** Reference to the NetworkManager object. */
    final NetworkManager netManager;

    NetworkWriterThread(NetworkManager par1NetworkManager, String par2Str)
    {
        super(par2Str);
        netManager = par1NetworkManager;
    }

    public void run()
    {
        synchronized (NetworkManager.threadSyncObject)
        {
            NetworkManager.numWriteThreads++;
        }

        try
        {
            while (NetworkManager.isRunning(netManager))
            {
                while (NetworkManager.sendNetworkPacket(netManager)) ;

                try
                {
                    if (NetworkManager.getOutputStream(netManager) != null)
                    {
                        NetworkManager.getOutputStream(netManager).flush();
                    }
                }
                catch (IOException ioexception)
                {
                    if (!NetworkManager.isTerminating(netManager))
                    {
                        NetworkManager.sendError(netManager, ioexception);
                    }

                    ioexception.printStackTrace();
                }

                try
                {
                    sleep(2L);
                }
                catch (InterruptedException interruptedexception) { }
            }
        }
        finally
        {
            synchronized (NetworkManager.threadSyncObject)
            {
                NetworkManager.numWriteThreads--;
            }
        }
    }
}
