package net.minecraft.src;

class PlayerUsageSnooperThread extends Thread
{
    final PlayerUsageSnooper field_52012_a;

    PlayerUsageSnooperThread(PlayerUsageSnooper par1PlayerUsageSnooper, String par2Str)
    {
        super(par2Str);
        field_52012_a = par1PlayerUsageSnooper;
    }

    public void run()
    {
        PostHttp.func_52018_a(PlayerUsageSnooper.func_52023_a(field_52012_a), PlayerUsageSnooper.func_52020_b(field_52012_a), true);
    }
}
