package net.minecraft.src;

import java.util.Comparator;

class SorterStatsItem implements Comparator
{
    final GuiStats statsGUI;
    final GuiSlotStatsItem slotStatsItemGUI;

    SorterStatsItem(GuiSlotStatsItem par1GuiSlotStatsItem, GuiStats par2GuiStats)
    {
        slotStatsItemGUI = par1GuiSlotStatsItem;
        statsGUI = par2GuiStats;
    }

    public int func_27371_a(StatCrafting par1StatCrafting, StatCrafting par2StatCrafting)
    {
        int i = par1StatCrafting.getItemID();
        int j = par2StatCrafting.getItemID();
        StatBase statbase = null;
        StatBase statbase1 = null;

        if (slotStatsItemGUI.field_27271_e == 0)
        {
            statbase = StatList.objectBreakStats[i];
            statbase1 = StatList.objectBreakStats[j];
        }
        else if (slotStatsItemGUI.field_27271_e == 1)
        {
            statbase = StatList.objectCraftStats[i];
            statbase1 = StatList.objectCraftStats[j];
        }
        else if (slotStatsItemGUI.field_27271_e == 2)
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

            int k = GuiStats.getStatsFileWriter(slotStatsItemGUI.field_27275_a).writeStat(statbase);
            int l = GuiStats.getStatsFileWriter(slotStatsItemGUI.field_27275_a).writeStat(statbase1);

            if (k != l)
            {
                return (k - l) * slotStatsItemGUI.field_27270_f;
            }
        }

        return i - j;
    }

    public int compare(Object par1Obj, Object par2Obj)
    {
        return func_27371_a((StatCrafting)par1Obj, (StatCrafting)par2Obj);
    }
}
