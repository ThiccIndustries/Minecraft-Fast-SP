package net.minecraft.src;

class ThreadRunIsoClient extends Thread
{
    final CanvasIsomPreview field_1197_a;

    ThreadRunIsoClient(CanvasIsomPreview par1CanvasIsomPreview)
    {
        field_1197_a = par1CanvasIsomPreview;
    }

    public void run()
    {
        while (CanvasIsomPreview.isRunning(field_1197_a))
        {
            field_1197_a.render();

            try
            {
                Thread.sleep(1L);
            }
            catch (Exception exception) { }
        }
    }
}
