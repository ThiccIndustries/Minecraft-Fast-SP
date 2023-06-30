package net.minecraft.src;

import java.util.List;

class GuiSlotStatsGeneral extends GuiSlot
{
    final GuiStats field_27276_a;

    public GuiSlotStatsGeneral(GuiStats par1GuiStats)
    {
        super(GuiStats.getMinecraft(par1GuiStats), par1GuiStats.width, par1GuiStats.height, 32, par1GuiStats.height - 64, 10);
        field_27276_a = par1GuiStats;
        func_27258_a(false);
    }

    /**
     * Gets the size of the current slot list.
     */
    protected int getSize()
    {
        return StatList.generalStats.size();
    }

    /**
     * the element in the slot that was clicked, boolean for wether it was double clicked or not
     */
    protected void elementClicked(int i, boolean flag)
    {
    }

    /**
     * returns true if the element passed in is currently selected
     */
    protected boolean isSelected(int par1)
    {
        return false;
    }

    /**
     * return the height of the content being scrolled
     */
    protected int getContentHeight()
    {
        return getSize() * 10;
    }

    protected void drawBackground()
    {
        field_27276_a.drawDefaultBackground();
    }

    protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
    {
        StatBase statbase = (StatBase)StatList.generalStats.get(par1);
        field_27276_a.drawString(GuiStats.getFontRenderer1(field_27276_a), StatCollector.translateToLocal(statbase.getName()), par2 + 2, par3 + 1, par1 % 2 != 0 ? 0x909090 : 0xffffff);
        String s = statbase.func_27084_a(GuiStats.getStatsFileWriter(field_27276_a).writeStat(statbase));
        field_27276_a.drawString(GuiStats.getFontRenderer2(field_27276_a), s, (par2 + 2 + 213) - GuiStats.getFontRenderer3(field_27276_a).getStringWidth(s), par3 + 1, par1 % 2 != 0 ? 0x909090 : 0xffffff);
    }
}
