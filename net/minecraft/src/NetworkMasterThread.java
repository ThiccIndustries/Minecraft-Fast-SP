package net.minecraft.src;

class NetworkMasterThread extends Thread
{
    /** Reference to the NetworkManager object. */
    final NetworkManager netManager;

    NetworkMasterThread(NetworkManager par1NetworkManager)
    {
        netManager = par1NetworkManager;
    }

    @SuppressWarnings("deprecation")
    public void run()
    {
        try
        {
            Thread.sleep(5000L);

            if (NetworkManager.getReadThread(netManager).isAlive())
            {
                try
                {
                    NetworkManager.getReadThread(netManager).stop();
                }
                catch (Throwable throwable) { }
            }

            if (NetworkManager.getWriteThread(netManager).isAlive())
            {
                try
                {
                    NetworkManager.getWriteThread(netManager).stop();
                }
                catch (Throwable throwable1) { }
            }
        }
        catch (InterruptedException interruptedexception)
        {
            interruptedexception.printStackTrace();
        }
    }
}
