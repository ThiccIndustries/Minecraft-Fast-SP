package net.minecraft.src;

import java.util.*;

public class ThreadedFileIOBase implements Runnable
{
    /** Instance of ThreadedFileIOBase */
    public static final ThreadedFileIOBase threadedIOInstance = new ThreadedFileIOBase();
    private List threadedIOQueue;
    private volatile long writeQueuedCounter;
    private volatile long savedIOCounter;
    private volatile boolean isThreadWaiting;

    private ThreadedFileIOBase()
    {
        threadedIOQueue = Collections.synchronizedList(new ArrayList());
        writeQueuedCounter = 0L;
        savedIOCounter = 0L;
        isThreadWaiting = false;
        Thread thread = new Thread(this, "File IO Thread");
        thread.setPriority(1);
        thread.start();
    }

    public void run()
    {
        do
        {
            processQueue();
        }
        while (true);
    }

    /**
     * Process the items that are in the queue
     */
    private void processQueue()
    {
        for (int i = 0; i < threadedIOQueue.size(); i++)
        {
            IThreadedFileIO ithreadedfileio = (IThreadedFileIO)threadedIOQueue.get(i);
            boolean flag = ithreadedfileio.writeNextIO();

            if (!flag)
            {
                threadedIOQueue.remove(i--);
                savedIOCounter++;
            }

            try
            {
                if (!isThreadWaiting)
                {
                    Thread.sleep(10L);
                }
                else
                {
                    Thread.sleep(0L);
                }
            }
            catch (InterruptedException interruptedexception1)
            {
                interruptedexception1.printStackTrace();
            }
        }

        if (threadedIOQueue.isEmpty())
        {
            try
            {
                Thread.sleep(25L);
            }
            catch (InterruptedException interruptedexception)
            {
                interruptedexception.printStackTrace();
            }
        }
    }

    /**
     * threaded io
     */
    public void queueIO(IThreadedFileIO par1IThreadedFileIO)
    {
        if (threadedIOQueue.contains(par1IThreadedFileIO))
        {
            return;
        }
        else
        {
            writeQueuedCounter++;
            threadedIOQueue.add(par1IThreadedFileIO);
            return;
        }
    }

    public void waitForFinish() throws InterruptedException
    {
        isThreadWaiting = true;

        while (writeQueuedCounter != savedIOCounter)
        {
            Thread.sleep(10L);
        }

        isThreadWaiting = false;
    }
}
