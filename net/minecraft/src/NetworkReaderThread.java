package net.minecraft.src;

class NetworkReaderThread extends Thread
{
    /** Reference to the NetworkManager object. */
    final NetworkManager netManager;

    NetworkReaderThread(NetworkManager par1NetworkManager, String par2Str)
    {
        super(par2Str);
        netManager = par1NetworkManager;
    }

    public void run()
    {
        synchronized (NetworkManager.threadSyncObject)
        {
            NetworkManager.numReadThreads++;
        }

        try
        {
            while (NetworkManager.isRunning(netManager) && !NetworkManager.isServerTerminating(netManager))
            {
                while (NetworkManager.readNetworkPacket(netManager)) ;

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
                NetworkManager.numReadThreads--;
            }
        }
    }
}
