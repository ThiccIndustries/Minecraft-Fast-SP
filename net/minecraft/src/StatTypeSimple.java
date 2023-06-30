package net.minecraft.src;

import java.text.NumberFormat;

final class StatTypeSimple implements IStatType
{
    StatTypeSimple()
    {
    }

    /**
     * Formats a given stat for human consumption.
     */
    public String format(int par1)
    {
        return StatBase.getNumberFormat().format(par1);
    }
}
