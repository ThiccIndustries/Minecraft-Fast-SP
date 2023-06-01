package net.minecraft.src;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiChat extends GuiScreen
{
    private String field_50062_b;
    private int field_50063_c;
    private boolean field_50060_d;
    private String field_50061_e;
    private String field_50059_f;
    private int field_50067_h;
    private List field_50068_i;
    private URI field_50065_j;
    protected GuiTextField field_50064_a;
    private String field_50066_k;

    public GuiChat()
    {
        field_50062_b = "";
        field_50063_c = -1;
        field_50060_d = false;
        field_50061_e = "";
        field_50059_f = "";
        field_50067_h = 0;
        field_50068_i = new ArrayList();
        field_50065_j = null;
        field_50066_k = "";
    }

    public GuiChat(String par1Str)
    {
        field_50062_b = "";
        field_50063_c = -1;
        field_50060_d = false;
        field_50061_e = "";
        field_50059_f = "";
        field_50067_h = 0;
        field_50068_i = new ArrayList();
        field_50065_j = null;
        field_50066_k = "";
        field_50066_k = par1Str;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        field_50063_c = mc.ingameGUI.func_50013_c().size();
        field_50064_a = new GuiTextField(fontRenderer, 4, height - 12, width - 4, 12);
        field_50064_a.setMaxStringLength(100);
        field_50064_a.func_50027_a(false);
        field_50064_a.func_50033_b(true);
        field_50064_a.setText(field_50066_k);
        field_50064_a.func_50026_c(false);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        mc.ingameGUI.func_50014_d();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        field_50064_a.updateCursorCounter();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (par2 == 15)
        {
            completePlayerName();
        }
        else
        {
            field_50060_d = false;
        }

        if (par2 == 1)
        {
            mc.displayGuiScreen(null);
        }
        else if (par2 == 28)
        {
            String s = field_50064_a.getText().trim();

            if (s.length() > 0 && !mc.lineIsCommand(s))
            {
                mc.thePlayer.sendChatMessage(s);
            }

            mc.displayGuiScreen(null);
        }
        else if (par2 == 200)
        {
            func_50058_a(-1);
        }
        else if (par2 == 208)
        {
            func_50058_a(1);
        }
        else if (par2 == 201)
        {
            mc.ingameGUI.func_50011_a(19);
        }
        else if (par2 == 209)
        {
            mc.ingameGUI.func_50011_a(-19);
        }
        else
        {
            field_50064_a.func_50037_a(par1, par2);
        }
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput()
    {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();

        if (i != 0)
        {
            if (i > 1)
            {
                i = 1;
            }

            if (i < -1)
            {
                i = -1;
            }

            if (!func_50049_m())
            {
                i *= 7;
            }

            mc.ingameGUI.func_50011_a(i);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        if (par3 == 0)
        {
            ChatClickData chatclickdata = mc.ingameGUI.func_50012_a(Mouse.getX(), Mouse.getY());

            if (chatclickdata != null)
            {
                URI uri = chatclickdata.func_50089_b();

                if (uri != null)
                {
                    field_50065_j = uri;
                    mc.displayGuiScreen(new GuiChatConfirmLink(this, this, chatclickdata.func_50088_a(), 0, chatclickdata));
                    return;
                }
            }
        }

        field_50064_a.mouseClicked(par1, par2, par3);
        super.mouseClicked(par1, par2, par3);
    }

    public void confirmClicked(boolean par1, int par2)
    {
        if (par2 == 0)
        {
            if (par1)
            {
                try
                {
                    Class class1 = Class.forName("java.awt.Desktop");
                    Object obj = class1.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
                    class1.getMethod("browse", new Class[]
                            {
                                java.net.URI.class
                            }).invoke(obj, new Object[]
                                    {
                                        field_50065_j
                                    });
                }
                catch (Throwable throwable)
                {
                    throwable.printStackTrace();
                }
            }

            field_50065_j = null;
            mc.displayGuiScreen(this);
        }
    }

    /**
     * Autocompletes player name
     */
    public void completePlayerName()
    {
        if (field_50060_d)
        {
            field_50064_a.func_50021_a(-1);

            if (field_50067_h >= field_50068_i.size())
            {
                field_50067_h = 0;
            }
        }
        else
        {
            int i = field_50064_a.func_50028_c(-1);

            if (field_50064_a.func_50035_h() - i < 1)
            {
                return;
            }

            field_50068_i.clear();
            field_50061_e = field_50064_a.getText().substring(i);
            field_50059_f = field_50061_e.toLowerCase();
            Iterator iterator = ((EntityClientPlayerMP)mc.thePlayer).sendQueue.playerNames.iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                GuiPlayerInfo guiplayerinfo = (GuiPlayerInfo)iterator.next();

                if (guiplayerinfo.nameStartsWith(field_50059_f))
                {
                    field_50068_i.add(guiplayerinfo);
                }
            }
            while (true);

            if (field_50068_i.size() == 0)
            {
                return;
            }

            field_50060_d = true;
            field_50067_h = 0;
            field_50064_a.func_50020_b(i - field_50064_a.func_50035_h());
        }

        if (field_50068_i.size() > 1)
        {
            StringBuilder stringbuilder = new StringBuilder();
            GuiPlayerInfo guiplayerinfo1;

            for (Iterator iterator1 = field_50068_i.iterator(); iterator1.hasNext(); stringbuilder.append(guiplayerinfo1.name))
            {
                guiplayerinfo1 = (GuiPlayerInfo)iterator1.next();

                if (stringbuilder.length() > 0)
                {
                    stringbuilder.append(", ");
                }
            }

            mc.ingameGUI.addChatMessage(stringbuilder.toString());
        }

        field_50064_a.func_50031_b(((GuiPlayerInfo)field_50068_i.get(field_50067_h++)).name);
    }

    public void func_50058_a(int par1)
    {
        int i = field_50063_c + par1;
        int j = mc.ingameGUI.func_50013_c().size();

        if (i < 0)
        {
            i = 0;
        }

        if (i > j)
        {
            i = j;
        }

        if (i == field_50063_c)
        {
            return;
        }

        if (i == j)
        {
            field_50063_c = j;
            field_50064_a.setText(field_50062_b);
            return;
        }

        if (field_50063_c == j)
        {
            field_50062_b = field_50064_a.getText();
        }

        field_50064_a.setText((String)mc.ingameGUI.func_50013_c().get(i));
        field_50063_c = i;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawRect(2, height - 14, width - 2, height - 2, 0x80000000);
        field_50064_a.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}
