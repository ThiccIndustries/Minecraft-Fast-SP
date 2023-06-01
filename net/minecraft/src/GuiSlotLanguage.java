package net.minecraft.src;

import java.util.*;
import net.minecraft.client.Minecraft;

class GuiSlotLanguage extends GuiSlot
{
    private ArrayList field_44013_b;
    private TreeMap field_44014_c;
    final GuiLanguage field_44015_a;

    public GuiSlotLanguage(GuiLanguage par1GuiLanguage)
    {
        super(par1GuiLanguage.mc, par1GuiLanguage.width, par1GuiLanguage.height, 32, (par1GuiLanguage.height - 65) + 4, 18);
        field_44015_a = par1GuiLanguage;
        field_44014_c = StringTranslate.getInstance().getLanguageList();
        field_44013_b = new ArrayList();
        String s;

        for (Iterator iterator = field_44014_c.keySet().iterator(); iterator.hasNext(); field_44013_b.add(s))
        {
            s = (String)iterator.next();
        }
    }

    /**
     * Gets the size of the current slot list.
     */
    protected int getSize()
    {
        return field_44013_b.size();
    }

    /**
     * the element in the slot that was clicked, boolean for wether it was double clicked or not
     */
    protected void elementClicked(int par1, boolean par2)
    {
        StringTranslate.getInstance().setLanguage((String)field_44013_b.get(par1));
        field_44015_a.mc.fontRenderer.setUnicodeFlag(StringTranslate.getInstance().isUnicode());
        GuiLanguage.func_44005_a(field_44015_a).language = (String)field_44013_b.get(par1);
        field_44015_a.fontRenderer.setBidiFlag(StringTranslate.isBidrectional(GuiLanguage.func_44005_a(field_44015_a).language));
        GuiLanguage.func_46028_b(field_44015_a).displayString = StringTranslate.getInstance().translateKey("gui.done");
    }

    /**
     * returns true if the element passed in is currently selected
     */
    protected boolean isSelected(int par1)
    {
        return ((String)field_44013_b.get(par1)).equals(StringTranslate.getInstance().getCurrentLanguage());
    }

    /**
     * return the height of the content being scrolled
     */
    protected int getContentHeight()
    {
        return getSize() * 18;
    }

    protected void drawBackground()
    {
        field_44015_a.drawDefaultBackground();
    }

    protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
    {
        field_44015_a.fontRenderer.setBidiFlag(true);
        field_44015_a.drawCenteredString(field_44015_a.fontRenderer, (String)field_44014_c.get(field_44013_b.get(par1)), field_44015_a.width / 2, par3 + 1, 0xffffff);
        field_44015_a.fontRenderer.setBidiFlag(StringTranslate.isBidrectional(GuiLanguage.func_44005_a(field_44015_a).language));
    }
}
