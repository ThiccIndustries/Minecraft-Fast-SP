package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiQualitySettingsOF extends GuiScreen
{
    private GuiScreen prevScreen;
    protected String title;
    private GameSettings settings;
    private static EnumOptions enumOptions[];
    private int lastMouseX;
    private int lastMouseY;
    private long mouseStillTime;

    public GuiQualitySettingsOF(GuiScreen guiscreen, GameSettings gamesettings)
    {
        lastMouseX = 0;
        lastMouseY = 0;
        mouseStillTime = 0L;
        title = "Quality Settings";
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

        if (guibutton.id != EnumOptions.CLOUD_HEIGHT.ordinal())
        {
            ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
            int i = scaledresolution.getScaledWidth();
            int j = scaledresolution.getScaledHeight();
            setWorldAndResolution(mc, i, j);
        }
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
        if (s.equals("Mipmap Level"))
        {
            return (new String[]
                    {
                        "Visual effect which makes distant objects look better", "by smoothing the texture details", "  OFF - no smoothing", "  1 - minimum smoothing", "  4 - maximum smoothing", "This option usually does not affect the performance."
                    });
        }

        if (s.equals("Mipmap Type"))
        {
            return (new String[]
                    {
                        "Visual effect which makes distant objects look better", "by smoothing the texture details", "  Nearest - rough smoothing", "  Linear - fine smoothing", "This option usually does not affect the performance."
                    });
        }

        if (s.equals("Clear Water"))
        {
            return (new String[]
                    {
                        "Clear Water", "  ON - clear, transparent water", "  OFF - default water"
                    });
        }

        if (s.equals("Better Grass"))
        {
            return (new String[]
                    {
                        "Better Grass", "  OFF - default side grass texture, fastest", "  Fast - full side grass texture, slower", "  Fancy - dynamic side grass texture, slowest"
                    });
        }

        if (s.equals("Better Snow"))
        {
            return (new String[]
                    {
                        "Better Snow", "  OFF - default snow, faster", "  ON - better snow, slower", "Shows snow under transparent blocks (fence, tall grass)", "when bordering with snow blocks"
                    });
        }

        if (s.equals("Random Mobs"))
        {
            return (new String[]
                    {
                        "Random Mobs", "  OFF - no random mobs, faster", "  ON - random mobs, slower", "Random mobs uses random textures for the game creatures.", "It needs a texture pack which has multiple mob textures."
                    });
        }

        if (s.equals("Swamp Colors"))
        {
            return (new String[]
                    {
                        "Swamp Colors", "  ON - use swamp colors (default), slower", "  OFF - do not use swamp colors, faster", "The swamp colors affect grass, leaves, vines and water."
                    });
        }

        if (s.equals("Smooth Biomes"))
        {
            return (new String[]
                    {
                        "Smooth Biomes", "  ON - smoothing of biome borders (default), slower", "  OFF - no smoothing of biome borders, faster", "The smoothing of biome borders is done by sampling and", "averaging the color of all surounding blocks.", "Affected are grass, leaves, vines and water."
                    });
        }

        if (s.equals("Custom Fonts"))
        {
            return (new String[]
                    {
                        "Custom Fonts", "  ON - uses custom fonts (default), slower", "  OFF - uses default font, faster", "The custom fonts are supplied by the current", "texture pack"
                    });
        }

        if (s.equals("Custom Colors"))
        {
            return (new String[]
                    {
                        "Custom Colors", "  ON - uses custom colors (default), slower", "  OFF - uses default colors, faster", "The custom colors are supplied by the current", "texture pack"
                    });
        }

        if (s.equals("Show Capes"))
        {
            return (new String[]
                    {
                        "Show Capes", "  ON - show player capes (default)", "  OFF - do not show player capes"
                    });
        }

        if (s.equals("Connected Textures"))
        {
            return (new String[]
                    {
                        "Connected Textures", "  OFF - no connected textures (default)", "  Fast - fast connected textures", "  Fancy - fancy connected textures", "Connected textures joins the textures of glass,", "sandstone and bookshelves when placed next to", "each other. The connected textures are supplied", "by the current texture pack."
                    });
        }

        if (s.equals("Far View"))
        {
            return (new String[]
                    {
                        "Far View", " OFF - (default) standard view distance", " ON - 3x view distance", "Far View is very resource demanding!", "3x view distance => 9x chunks to be loaded => FPS / 9", "Standard view distances: 32, 64, 128, 256", "Far view distances: 96, 192, 384, 512"
                    });
        }

        if (s.equals("Natural Textures"))
        {
            return (new String[]
                    {
                        "Natural Textures", "  OFF - no natural textures (default)", "  ON - use natural textures", "Natural textures remove the gridlike pattern", "created by repeating blocks of the same type.", "It uses rotated and flipped variants of the base", "block texture. The configuration for the natural", "textures is supplied by the current texture pack"
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
                    EnumOptions.MIPMAP_LEVEL, EnumOptions.MIPMAP_TYPE, EnumOptions.CLEAR_WATER, EnumOptions.RANDOM_MOBS, EnumOptions.BETTER_GRASS, EnumOptions.BETTER_SNOW, EnumOptions.CUSTOM_FONTS, EnumOptions.CUSTOM_COLORS, EnumOptions.SWAMP_COLORS, EnumOptions.SMOOTH_BIOMES,
                    EnumOptions.CONNECTED_TEXTURES, EnumOptions.NATURAL_TEXTURES
                });
    }
}
