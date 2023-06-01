package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiVideoSettings extends GuiScreen
{
    private GuiScreen parentGuiScreen;

    /** The title string that is displayed in the top-center of the screen. */
    protected String screenTitle;

    /** GUI game settings */
    private GameSettings guiGameSettings;

    /**
     * True if the system is 64-bit (using a simple indexOf test on a system property)
     */
    private boolean is64bit;
    private static EnumOptions videoOptions[];
    private int lastMouseX;
    private int lastMouseY;
    private long mouseStillTime;

    public GuiVideoSettings(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
    {
        lastMouseX = 0;
        lastMouseY = 0;
        mouseStillTime = 0L;
        screenTitle = "Video Settings";
        is64bit = false;
        parentGuiScreen = par1GuiScreen;
        guiGameSettings = par2GameSettings;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        screenTitle = stringtranslate.translateKey("options.videoTitle");
        int i = 0;
        EnumOptions aenumoptions[] = videoOptions;
        int j = aenumoptions.length;

        for (int k = 0; k < j; k++)
        {
            EnumOptions enumoptions = aenumoptions[k];
            int j1 = (width / 2 - 155) + (i % 2) * 160;
            int k1 = (height / 6 + 21 * (i / 2)) - 10;

            if (!enumoptions.getEnumFloat())
            {
                controlList.add(new GuiSmallButton(enumoptions.returnEnumOrdinal(), j1, k1, enumoptions, guiGameSettings.getKeyBinding(enumoptions)));
            }
            else
            {
                controlList.add(new GuiSlider(enumoptions.returnEnumOrdinal(), j1, k1, enumoptions, guiGameSettings.getKeyBinding(enumoptions), guiGameSettings.getOptionFloatValue(enumoptions)));
            }

            i++;
        }

        int l = (height / 6 + 21 * (i / 2)) - 10;
        int i1 = 0;
        i1 = (width / 2 - 155) + 0;
        controlList.add(new GuiSmallButton(101, i1, l, "Details..."));
        i1 = (width / 2 - 155) + 160;
        controlList.add(new GuiSmallButton(102, i1, l, "Quality..."));
        l += 21;
        i1 = (width / 2 - 155) + 0;
        controlList.add(new GuiSmallButton(111, i1, l, "Animations..."));
        i1 = (width / 2 - 155) + 160;
        //controlList.add(new GuiSmallButton(112, i1, l, "Performance..."));
        //l += 21;
        //i1 = (width / 2 - 155) + 0;
        controlList.add(new GuiSmallButton(121, i1, l, "Texture Packs..."));
        i1 = (width / 2 - 155) + 160;
        //controlList.add(new GuiSmallButton(122, i1, l, "Other..."));
        controlList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168 + 11, stringtranslate.translateKey("gui.done")));
        is64bit = false;
        String as[] =
        {
            "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"
        };
        String as1[] = (String[])as;
        int l1 = as1.length;
        int i2 = 0;

        do
        {
            if (i2 >= l1)
            {
                break;
            }

            String s = as1[i2];
            String s1 = System.getProperty(s);

            if (s1 != null && s1.indexOf("64") >= 0)
            {
                is64bit = true;
                break;
            }

            i2++;
        }
        while (true);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (!par1GuiButton.enabled)
        {
            return;
        }

        int i = guiGameSettings.guiScale;

        if (par1GuiButton.id < 100 && (par1GuiButton instanceof GuiSmallButton))
        {
            guiGameSettings.setOptionValue(((GuiSmallButton)par1GuiButton).returnEnumOptions(), 1);
            par1GuiButton.displayString = guiGameSettings.getKeyBinding(EnumOptions.getEnumOptions(par1GuiButton.id));
        }

        if (par1GuiButton.id == 200)
        {
            mc.gameSettings.saveOptions();
            mc.displayGuiScreen(parentGuiScreen);
        }

        if (par1GuiButton.id == 101)
        {
            mc.gameSettings.saveOptions();
            GuiDetailSettingsOF guidetailsettingsof = new GuiDetailSettingsOF(this, guiGameSettings);
            mc.displayGuiScreen(guidetailsettingsof);
        }

        if (par1GuiButton.id == 102)
        {
            mc.gameSettings.saveOptions();
            GuiQualitySettingsOF guiqualitysettingsof = new GuiQualitySettingsOF(this, guiGameSettings);
            mc.displayGuiScreen(guiqualitysettingsof);
        }

        if (par1GuiButton.id == 111)
        {
            mc.gameSettings.saveOptions();
            GuiAnimationSettingsOF guianimationsettingsof = new GuiAnimationSettingsOF(this, guiGameSettings);
            mc.displayGuiScreen(guianimationsettingsof);
        }

        if (par1GuiButton.id == 112)
        {
            mc.gameSettings.saveOptions();
            GuiPerformanceSettingsOF guiperformancesettingsof = new GuiPerformanceSettingsOF(this, guiGameSettings);
            mc.displayGuiScreen(guiperformancesettingsof);
        }

        if (par1GuiButton.id == 121)
        {
            mc.gameSettings.saveOptions();
            GuiTexturePacks guitexturepacks = new GuiTexturePacks(this);
            mc.displayGuiScreen(guitexturepacks);
        }

        if (par1GuiButton.id == 122)
        {
            mc.gameSettings.saveOptions();
            GuiOtherSettingsOF guiothersettingsof = new GuiOtherSettingsOF(this, guiGameSettings);
            mc.displayGuiScreen(guiothersettingsof);
        }

        if (par1GuiButton.id == EnumOptions.AO_LEVEL.ordinal())
        {
            return;
        }

        if (guiGameSettings.guiScale != i)
        {
            ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
            int j = scaledresolution.getScaledWidth();
            int k = scaledresolution.getScaledHeight();
            setWorldAndResolution(mc, j, k);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, screenTitle, width / 2, 20, 0xffffff);

        if (!is64bit)
        {
            if (guiGameSettings.renderDistance != 0);
        }

        super.drawScreen(par1, par2, par3);

        if (Math.abs(par1 - lastMouseX) > 5 || Math.abs(par2 - lastMouseY) > 5)
        {
            lastMouseX = par1;
            lastMouseY = par2;
            mouseStillTime = System.currentTimeMillis();
            return;
        }

        int i = 700;

        if (System.currentTimeMillis() < mouseStillTime + (long)i)
        {
            return;
        }

        int j = width / 2 - 150;
        int k = height / 6 - 5;

        if (par2 <= k + 98)
        {
            k += 105;
        }

        int l = j + 150 + 150;
        int i1 = k + 84 + 10;
        GuiButton guibutton = getSelectedButton(par1, par2);

        if (guibutton != null)
        {
            String s = getButtonName(guibutton.displayString);
            String as[] = getTooltipLines(s);

            if (as == null)
            {
                return;
            }

            drawGradientRect(j, k, l, i1, 0xe0000000, 0xe0000000);

            for (int j1 = 0; j1 < as.length; j1++)
            {
                String s1 = as[j1];
                fontRenderer.drawStringWithShadow(s1, j + 5, k + 5 + j1 * 11, 0xdddddd);
            }
        }
    }

    private String[] getTooltipLines(String s)
    {
        if (s.equals("Graphics"))
        {
            return (new String[]
                    {
                        "Visual quality", "  Fast  - lower quality, faster", "  Fancy - higher quality, slower", "Changes the appearance of clouds, leaves, water,", "shadows and grass sides."
                    });
        }

        if (s.equals("Render Distance"))
        {
            return (new String[]
                    {
                        "Visible distance", "  Tiny - 32m (fastest)", "  Short - 64m (faster)", "  Normal - 128m", "  Far - 256m (slower)", "  Extreme - 512m (slowest!)", "The Extreme view distance is very resource demanding!"
                    });
        }

        if (s.equals("Smooth Lighting"))
        {
            return (new String[]
                    {
                        "Smooth lighting", "  OFF - no smooth lighting (faster)", "  1% - light smooth lighting (slower)", "  100% - dark smooth lighting (slower)"
                    });
        }

        if (s.equals("Performance"))
        {
            return (new String[]
                    {
                        "FPS Limit", "  Max FPS - no limit (fastest)", "  Balanced - limit 120 FPS (slower)", "  Power saver - limit 40 FPS (slowest)", "  VSync - limit to monitor framerate (60, 30, 20)", "Balanced and Power saver decrease the FPS even if", "the limit value is not reached."
                    });
        }

        if (s.equals("3D Anaglyph"))
        {
            return (new String[]
                    {
                        "3D mode used with red-cyan 3D glasses."
                    });
        }

        if (s.equals("View Bobbing"))
        {
            return (new String[]
                    {
                        "More realistic movement.", "When using mipmaps set it to OFF for best results."
                    });
        }

        if (s.equals("GUI Scale"))
        {
            return (new String[]
                    {
                        "GUI Scale", "Smaller GUI might be faster"
                    });
        }

        if (s.equals("Advanced OpenGL"))
        {
            return (new String[]
                    {
                        "Detect and render only visible geometry", "  OFF - all geometry is rendered (slower)", "  Fast - only visible geometry is rendered (fastest)", "  Fancy - conservative, avoids visual artifacts (faster)", "The option is available only if it is supported by the ", "graphic card."
                    });
        }

        if (s.equals("Fog"))
        {
            return (new String[]
                    {
                        "Fog type", "  Fast - faster fog", "  Fancy - slower fog, looks better", "  OFF - no fog, fastest", "The fancy fog is available only if it is supported by the ", "graphic card."
                    });
        }

        if (s.equals("Fog Start"))
        {
            return (new String[]
                    {
                        "Fog start", "  0.2 - the fog starts near the player", "  0.8 - the fog starts far from the player", "This option usually does not affect the performance."
                    });
        }

        if (s.equals("Brightness"))
        {
            return (new String[]
                    {
                        "Increases the brightness of darker objects", "  OFF - standard brightness", "  100% - maximum brightness for darker objects", "This options does not change the brightness of ", "fully black objects"
                    });
        }

        if (s.equals("Clouds"))
        {
            return (new String[]
                    {
                        "Rendering of clouds", "  ON - standard clouds (slower)", "  OFF - no clouds (faster)"
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
        videoOptions = (new EnumOptions[]
                {
                    EnumOptions.GRAPHICS, EnumOptions.RENDER_DISTANCE_FINE, EnumOptions.AO_LEVEL, EnumOptions.FRAMERATE_LIMIT, EnumOptions.ANAGLYPH, EnumOptions.VIEW_BOBBING, EnumOptions.GUI_SCALE, EnumOptions.ADVANCED_OPENGL, EnumOptions.GAMMA, EnumOptions.RENDER_CLOUDS,
                    EnumOptions.FOG_FANCY, EnumOptions.FOG_START
                });
    }
}
