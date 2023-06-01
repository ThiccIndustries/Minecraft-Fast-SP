package net.minecraft.src;

import java.util.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

abstract class GuiSlotStats extends GuiSlot
{
    protected int field_27268_b;
    protected List field_27273_c;
    protected Comparator field_27272_d;
    protected int field_27271_e;
    protected int field_27270_f;
    final GuiStats field_27269_g;

    protected GuiSlotStats(GuiStats par1GuiStats)
    {
        super(GuiStats.getMinecraft1(par1GuiStats), par1GuiStats.width, par1GuiStats.height, 32, par1GuiStats.height - 64, 20);
        field_27269_g = par1GuiStats;
        field_27268_b = -1;
        field_27271_e = -1;
        field_27270_f = 0;
        func_27258_a(false);
        func_27259_a(true, 20);
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

    protected void drawBackground()
    {
        field_27269_g.drawDefaultBackground();
    }

    protected void func_27260_a(int par1, int par2, Tessellator par3Tessellator)
    {
        if (!Mouse.isButtonDown(0))
        {
            field_27268_b = -1;
        }

        if (field_27268_b == 0)
        {
            GuiStats.drawSprite(field_27269_g, (par1 + 115) - 18, par2 + 1, 0, 0);
        }
        else
        {
            GuiStats.drawSprite(field_27269_g, (par1 + 115) - 18, par2 + 1, 0, 18);
        }

        if (field_27268_b == 1)
        {
            GuiStats.drawSprite(field_27269_g, (par1 + 165) - 18, par2 + 1, 0, 0);
        }
        else
        {
            GuiStats.drawSprite(field_27269_g, (par1 + 165) - 18, par2 + 1, 0, 18);
        }

        if (field_27268_b == 2)
        {
            GuiStats.drawSprite(field_27269_g, (par1 + 215) - 18, par2 + 1, 0, 0);
        }
        else
        {
            GuiStats.drawSprite(field_27269_g, (par1 + 215) - 18, par2 + 1, 0, 18);
        }

        if (field_27271_e != -1)
        {
            char c = 'O';
            byte byte0 = 18;

            if (field_27271_e == 1)
            {
                c = '\201';
            }
            else if (field_27271_e == 2)
            {
                c = '\263';
            }

            if (field_27270_f == 1)
            {
                byte0 = 36;
            }

            GuiStats.drawSprite(field_27269_g, par1 + c, par2 + 1, byte0, 0);
        }
    }

    protected void func_27255_a(int par1, int par2)
    {
        field_27268_b = -1;

        if (par1 >= 79 && par1 < 115)
        {
            field_27268_b = 0;
        }
        else if (par1 >= 129 && par1 < 165)
        {
            field_27268_b = 1;
        }
        else if (par1 >= 179 && par1 < 215)
        {
            field_27268_b = 2;
        }

        if (field_27268_b >= 0)
        {
            func_27266_c(field_27268_b);
            GuiStats.getMinecraft2(field_27269_g).sndManager.playSoundFX("random.click", 1.0F, 1.0F);
        }
    }

    /**
     * Gets the size of the current slot list.
     */
    protected final int getSize()
    {
        return field_27273_c.size();
    }

    protected final StatCrafting func_27264_b(int par1)
    {
        return (StatCrafting)field_27273_c.get(par1);
    }

    protected abstract String func_27263_a(int i);

    protected void func_27265_a(StatCrafting par1StatCrafting, int par2, int par3, boolean par4)
    {
        if (par1StatCrafting != null)
        {
            String s = par1StatCrafting.func_27084_a(GuiStats.getStatsFileWriter(field_27269_g).writeStat(par1StatCrafting));
            field_27269_g.drawString(GuiStats.getFontRenderer4(field_27269_g), s, par2 - GuiStats.getFontRenderer5(field_27269_g).getStringWidth(s), par3 + 5, par4 ? 0xffffff : 0x909090);
        }
        else
        {
            String s1 = "-";
            field_27269_g.drawString(GuiStats.getFontRenderer6(field_27269_g), s1, par2 - GuiStats.getFontRenderer7(field_27269_g).getStringWidth(s1), par3 + 5, par4 ? 0xffffff : 0x909090);
        }
    }

    protected void func_27257_b(int par1, int par2)
    {
        if (par2 < top || par2 > bottom)
        {
            return;
        }

        int i = func_27256_c(par1, par2);
        int j = field_27269_g.width / 2 - 92 - 16;

        if (i >= 0)
        {
            if (par1 < j + 40 || par1 > j + 40 + 20)
            {
                return;
            }

            StatCrafting statcrafting = func_27264_b(i);
            func_27267_a(statcrafting, par1, par2);
        }
        else
        {
            String s = "";

            if (par1 >= (j + 115) - 18 && par1 <= j + 115)
            {
                s = func_27263_a(0);
            }
            else if (par1 >= (j + 165) - 18 && par1 <= j + 165)
            {
                s = func_27263_a(1);
            }
            else if (par1 >= (j + 215) - 18 && par1 <= j + 215)
            {
                s = func_27263_a(2);
            }
            else
            {
                return;
            }

            s = (new StringBuilder()).append("").append(StringTranslate.getInstance().translateKey(s)).toString().trim();

            if (s.length() > 0)
            {
                int k = par1 + 12;
                int l = par2 - 12;
                int i1 = GuiStats.getFontRenderer8(field_27269_g).getStringWidth(s);
                GuiStats.drawGradientRect(field_27269_g, k - 3, l - 3, k + i1 + 3, l + 8 + 3, 0xc0000000, 0xc0000000);
                GuiStats.getFontRenderer9(field_27269_g).drawStringWithShadow(s, k, l, -1);
            }
        }
    }

    protected void func_27267_a(StatCrafting par1StatCrafting, int par2, int par3)
    {
        if (par1StatCrafting == null)
        {
            return;
        }

        Item item = Item.itemsList[par1StatCrafting.getItemID()];
        String s = (new StringBuilder()).append("").append(StringTranslate.getInstance().translateNamedKey(item.getItemName())).toString().trim();

        if (s.length() > 0)
        {
            int i = par2 + 12;
            int j = par3 - 12;
            int k = GuiStats.getFontRenderer10(field_27269_g).getStringWidth(s);
            GuiStats.drawGradientRect1(field_27269_g, i - 3, j - 3, i + k + 3, j + 8 + 3, 0xc0000000, 0xc0000000);
            GuiStats.getFontRenderer11(field_27269_g).drawStringWithShadow(s, i, j, -1);
        }
    }

    protected void func_27266_c(int par1)
    {
        if (par1 != field_27271_e)
        {
            field_27271_e = par1;
            field_27270_f = -1;
        }
        else if (field_27270_f == -1)
        {
            field_27270_f = 1;
        }
        else
        {
            field_27271_e = -1;
            field_27270_f = 0;
        }

        Collections.sort(field_27273_c, field_27272_d);
    }
}
