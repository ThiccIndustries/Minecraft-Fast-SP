package net.minecraft.src;

import java.util.Comparator;

class SorterStatsBlock implements Comparator
{
    final GuiStats statsGUI;
    final GuiSlotStatsBlock slotStatsBlockGUI;

    SorterStatsBlock(GuiSlotStatsBlock par1GuiSlotStatsBlock, GuiStats par2GuiStats)
    {
        slotStatsBlockGUI = par1GuiSlotStatsBlock;
        statsGUI = par2GuiStats;
    }

    public int func_27297_a(StatCrafting par1StatCrafting, StatCrafting par2StatCrafting)
    {
        int i = par1StatCrafting.getItemID();
        int j = par2StatCrafting.getItemID();
        StatBase statbase = null;
        StatBase statbase1 = null;

        if (slotStatsBlockGUI.field_27271_e == 2)
        {
            statbase = StatList.mineBlockStatArray[i];
            statbase1 = StatList.mineBlockStatArray[j];
        }
        else if (slotStatsBlockGUI.field_27271_e == 0)
        {
            statbase = StatList.objectCraftStats[i];
            statbase1 = StatList.objectCraftStats[j];
        }
        else if (slotStatsBlockGUI.field_27271_e == 1)
        {
            statbase = StatList.objectUseStats[i];
            statbase1 = StatList.objectUseStats[j];
        }

        if (statbase != null || statbase1 != null)
        {
            if (statbase == null)
            {
                return 1;
            }

            if (statbase1 == null)
            {
                return -1;
            }

            int k = GuiStats.getStatsFileWriter(slotStatsBlockGUI.field_27274_a).writeStat(statbase);
            int l = GuiStats.getStatsFileWriter(slotStatsBlockGUI.field_27274_a).writeStat(statbase1);

            if (k != l)
            {
                return (k - l) * slotStatsBlockGUI.field_27270_f;
            }
        }

        return i - j;
    }

    public int compare(Object par1Obj, Object par2Obj)
    {
        return func_27297_a((StatCrafting)par1Obj, (StatCrafting)par2Obj);
    }
}
