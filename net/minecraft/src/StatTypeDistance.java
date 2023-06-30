package net.minecraft.src;

import java.text.DecimalFormat;

final class StatTypeDistance implements IStatType
{
    StatTypeDistance()
    {
    }

    /**
     * Formats a given stat for human consumption.
     */
    public String format(int par1)
    {
        int i = par1;
        double d = (double)i / 100D;
        double d1 = d / 1000D;

        if (d1 > 0.5D)
        {
            return (new StringBuilder()).append(StatBase.getDecimalFormat().format(d1)).append(" km").toString();
        }

        if (d > 0.5D)
        {
            return (new StringBuilder()).append(StatBase.getDecimalFormat().format(d)).append(" m").toString();
        }
        else
        {
            return (new StringBuilder()).append(par1).append(" cm").toString();
        }
    }
}
