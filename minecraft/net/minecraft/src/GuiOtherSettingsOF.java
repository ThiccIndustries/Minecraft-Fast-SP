package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiOtherSettingsOF extends GuiScreen
{
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private static EnumOptions enumOptions[];
    private int lastMouseX;
    private int lastMouseY;
    private long mouseStillTime;

    public GuiOtherSettingsOF(GuiScreen guiscreen, GameSettings gamesettings)
    {
        lastMouseX = 0;
        lastMouseY = 0;
        mouseStillTime = 0L;
        title = "Other Settings";
        prevScreen = guiscreen;
        settings = gamesettings;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        int i = 0;
        EnumOptions aenumoptions[] = enumOptions;
        int j = aenumoptions.length;

        for (int k = 0; k < j; k++)
        {
            EnumOptions enumoptions = aenumoptions[k];
            int l = (width / 2 - 155) + (i % 2) * 160;
            int i1 = (height / 6 + 21 * (i / 2)) - 10;

            if (!enumoptions.getEnumFloat())
            {
                controlList.add(new GuiSmallButton(enumoptions.returnEnumOrdinal(), l, i1, enumoptions, settings.getKeyBinding(enumoptions)));
            }
            else
            {
                controlList.add(new GuiSlider(enumoptions.returnEnumOrdinal(), l, i1, enumoptions, settings.getKeyBinding(enumoptions), settings.getOptionFloatValue(enumoptions)));
            }

            i++;
        }

        controlList.add(new GuiButton(210, width / 2 - 100, (height / 6 + 168 + 11) - 22, "Reset Video Settings..."));
        controlList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168 + 11, stringtranslate.translateKey("gui.done")));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton guibutton)
    {
        if (!guibutton.enabled)
        {
            return;
        }

        if (guibutton.id < 100 && (guibutton instanceof GuiSmallButton))
        {
            settings.setOptionValue(((GuiSmallButton)guibutton).returnEnumOptions(), 1);
            guibutton.displayString = settings.getKeyBinding(EnumOptions.getEnumOptions(guibutton.id));
        }

        if (guibutton.id == 200)
        {
            mc.gameSettings.saveOptions();
            mc.displayGuiScreen(prevScreen);
        }

        if (guibutton.id == 210)
        {
            mc.gameSettings.saveOptions();
            GuiYesNo guiyesno = new GuiYesNo(this, "Reset all video settings to their default values?", "", 9999);
            mc.displayGuiScreen(guiyesno);
        }

        if (guibutton.id != EnumOptions.CLOUD_HEIGHT.ordinal())
        {
            ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
            int i = scaledresolution.getScaledWidth();
            int j = scaledresolution.getScaledHeight();
            setWorldAndResolution(mc, i, j);
        }
    }

    public void confirmClicked(boolean flag, int i)
    {
        if (flag)
        {
            mc.gameSettings.resetSettings();
        }

        mc.displayGuiScreen(this);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, title, width / 2, 20, 0xffffff);
        super.drawScreen(i, j, f);

        if (Math.abs(i - lastMouseX) > 5 || Math.abs(j - lastMouseY) > 5)
        {
            lastMouseX = i;
            lastMouseY = j;
            mouseStillTime = System.currentTimeMillis();
            return;
        }

        int k = 700;

        if (System.currentTimeMillis() < mouseStillTime + (long)k)
        {
            return;
        }

        int l = width / 2 - 150;
        int i1 = height / 6 - 5;

        if (j <= i1 + 98)
        {
            i1 += 105;
        }

        int j1 = l + 150 + 150;
        int k1 = i1 + 84 + 10;
        GuiButton guibutton = getSelectedButton(i, j);

        if (guibutton != null)
        {
            String s = getButtonName(guibutton.displayString);
            String as[] = getTooltipLines(s);

            if (as == null)
            {
                return;
            }

            drawGradientRect(l, i1, j1, k1, 0xe0000000, 0xe0000000);

            for (int l1 = 0; l1 < as.length; l1++)
            {
                String s1 = as[l1];
                fontRenderer.drawStringWithShadow(s1, l + 5, i1 + 5 + l1 * 11, 0xdddddd);
            }
        }
    }

    private String[] getTooltipLines(String s)
    {
        if (s.equals("Autosave"))
        {
            return (new String[]
                    {
                        "Autosave interval", "Default autosave interval (2s) is NOT RECOMMENDED.", "Autosave causes the famous Lag Spike of Death."
                    });
        }

        if (s.equals("Fast Debug Info"))
        {
            return (new String[]
                    {
                        "Fast Debug Info", " OFF - default debug info screen, slower", " ON - debug info screen without lagometer, faster", "Removes the lagometer from the debug screen (F3)."
                    });
        }

        if (s.equals("Debug Profiler"))
        {
            return (new String[]
                    {
                        "Debug Profiler", "  ON - debug profiler is active, slower", "  OFF - debug profiler is not active, faster", "The debug profiler collects and shows debug information", "when the debug screen is open"
                    });
        }

        if (s.equals("Time"))
        {
            return (new String[]
                    {
                        "Time", " Default - normal day/night cycles", " Day Only - day only", " Night Only - night only", "The time setting is only effective in CREATIVE mode."
                    });
        }

        if (s.equals("Weather"))
        {
            return (new String[]
                    {
                        "Weather", "  ON - weather is active, slower", "  OFF - weather is not active, faster", "The weather controls rain, snow and thunderstorms."
                    });
        }

        if (s.equals("Fullscreen"))
        {
            return (new String[]
                    {
                        "Fullscreen resolution", "  Default - use desktop screen resolution, slower", "  WxH - use custom screen resolution, may be faster", "The selected resolution is used in fullscreen mode (F11)."
                    });
        }
        else
        {
            return null;
        }
    }

    private String getButtonName(String s)
    {
        int i = s.indexOf(':');

        if (i < 0)
        {
            return s;
        }
        else
        {
            return s.substring(0, i);
        }
    }

    private GuiButton getSelectedButton(int i, int j)
    {
        for (int k = 0; k < controlList.size(); k++)
        {
            GuiButton guibutton = (GuiButton)controlList.get(k);
            boolean flag = i >= guibutton.xPosition && j >= guibutton.yPosition && i < guibutton.xPosition + guibutton.field_52008_a && j < guibutton.yPosition + guibutton.field_52007_b;

            if (flag)
            {
                return guibutton;
            }
        }

        return null;
    }

    static
    {
        enumOptions = (new EnumOptions[]
                {
                    EnumOptions.FAST_DEBUG_INFO, EnumOptions.PROFILER, EnumOptions.WEATHER, EnumOptions.TIME, EnumOptions.FULLSCREEN_MODE, EnumOptions.AUTOSAVE_TICKS
                });
    }
}
