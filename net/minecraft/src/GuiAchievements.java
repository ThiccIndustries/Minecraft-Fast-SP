package net.minecraft.src;

import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiAchievements extends GuiScreen
{
    /** The top x coordinate of the achievement map */
    private static final int guiMapTop;

    /** The left y coordinate of the achievement map */
    private static final int guiMapLeft;

    /** The bottom x coordinate of the achievement map */
    private static final int guiMapBottom;

    /** The right y coordinate of the achievement map */
    private static final int guiMapRight;
    protected int achievementsPaneWidth;
    protected int achievementsPaneHeight;

    /** The current mouse x coordinate */
    protected int mouseX;

    /** The current mouse y coordinate */
    protected int mouseY;
    protected double field_27116_m;
    protected double field_27115_n;

    /** The x position of the achievement map */
    protected double guiMapX;

    /** The y position of the achievement map */
    protected double guiMapY;
    protected double field_27112_q;
    protected double field_27111_r;

    /** Whether the Mouse Button is down or not */
    private int isMouseButtonDown;
    private StatFileWriter statFileWriter;

    public GuiAchievements(StatFileWriter par1StatFileWriter)
    {
        achievementsPaneWidth = 256;
        achievementsPaneHeight = 202;
        mouseX = 0;
        mouseY = 0;
        isMouseButtonDown = 0;
        statFileWriter = par1StatFileWriter;
        char c = '\215';
        char c1 = '\215';
        field_27116_m = guiMapX = field_27112_q = AchievementList.openInventory.displayColumn * 24 - c / 2 - 12;
        field_27115_n = guiMapY = field_27111_r = AchievementList.openInventory.displayRow * 24 - c1 / 2;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        controlList.clear();
        controlList.add(new GuiSmallButton(1, width / 2 + 24, height / 2 + 74, 80, 20, StatCollector.translateToLocal("gui.done")));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 1)
        {
            mc.displayGuiScreen(null);
            mc.setIngameFocus();
        }

        super.actionPerformed(par1GuiButton);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (par2 == mc.gameSettings.keyBindInventory.keyCode)
        {
            mc.displayGuiScreen(null);
            mc.setIngameFocus();
        }
        else
        {
            super.keyTyped(par1, par2);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        if (Mouse.isButtonDown(0))
        {
            int i = (width - achievementsPaneWidth) / 2;
            int j = (height - achievementsPaneHeight) / 2;
            int k = i + 8;
            int l = j + 17;

            if ((isMouseButtonDown == 0 || isMouseButtonDown == 1) && par1 >= k && par1 < k + 224 && par2 >= l && par2 < l + 155)
            {
                if (isMouseButtonDown == 0)
                {
                    isMouseButtonDown = 1;
                }
                else
                {
                    guiMapX -= par1 - mouseX;
                    guiMapY -= par2 - mouseY;
                    field_27112_q = field_27116_m = guiMapX;
                    field_27111_r = field_27115_n = guiMapY;
                }

                mouseX = par1;
                mouseY = par2;
            }

            if (field_27112_q < (double)guiMapTop)
            {
                field_27112_q = guiMapTop;
            }

            if (field_27111_r < (double)guiMapLeft)
            {
                field_27111_r = guiMapLeft;
            }

            if (field_27112_q >= (double)guiMapBottom)
            {
                field_27112_q = guiMapBottom - 1;
            }

            if (field_27111_r >= (double)guiMapRight)
            {
                field_27111_r = guiMapRight - 1;
            }
        }
        else
        {
            isMouseButtonDown = 0;
        }

        drawDefaultBackground();
        genAchievementBackground(par1, par2, par3);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        func_27110_k();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        field_27116_m = guiMapX;
        field_27115_n = guiMapY;
        double d = field_27112_q - guiMapX;
        double d1 = field_27111_r - guiMapY;

        if (d * d + d1 * d1 < 4D)
        {
            guiMapX += d;
            guiMapY += d1;
        }
        else
        {
            guiMapX += d * 0.84999999999999998D;
            guiMapY += d1 * 0.84999999999999998D;
        }
    }

    protected void func_27110_k()
    {
        int i = (width - achievementsPaneWidth) / 2;
        int j = (height - achievementsPaneHeight) / 2;
        fontRenderer.drawString("Achievements", i + 15, j + 5, 0x404040);
    }

    protected void genAchievementBackground(int par1, int par2, float par3)
    {
        int i = MathHelper.floor_double(field_27116_m + (guiMapX - field_27116_m) * (double)par3);
        int j = MathHelper.floor_double(field_27115_n + (guiMapY - field_27115_n) * (double)par3);

        if (i < guiMapTop)
        {
            i = guiMapTop;
        }

        if (j < guiMapLeft)
        {
            j = guiMapLeft;
        }

        if (i >= guiMapBottom)
        {
            i = guiMapBottom - 1;
        }

        if (j >= guiMapRight)
        {
            j = guiMapRight - 1;
        }

        int k = mc.renderEngine.getTexture("/terrain.png");
        int l = mc.renderEngine.getTexture("/achievement/bg.png");
        int i1 = (width - achievementsPaneWidth) / 2;
        int j1 = (height - achievementsPaneHeight) / 2;
        int k1 = i1 + 16;
        int l1 = j1 + 17;
        zLevel = 0.0F;
        GL11.glDepthFunc(GL11.GL_GEQUAL);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, -200F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        mc.renderEngine.bindTexture(k);
        int i2 = i + 288 >> 4;
        int j2 = j + 288 >> 4;
        int k2 = (i + 288) % 16;
        int l2 = (j + 288) % 16;
        Random random = new Random();

        for (int i3 = 0; i3 * 16 - l2 < 155; i3++)
        {
            float f = 0.6F - ((float)(j2 + i3) / 25F) * 0.3F;
            GL11.glColor4f(f, f, f, 1.0F);

            for (int k3 = 0; k3 * 16 - k2 < 224; k3++)
            {
                random.setSeed(1234 + i2 + k3);
                random.nextInt();
                int j4 = random.nextInt(1 + j2 + i3) + (j2 + i3) / 2;
                int l4 = Block.sand.blockIndexInTexture;

                if (j4 > 37 || j2 + i3 == 35)
                {
                    l4 = Block.bedrock.blockIndexInTexture;
                }
                else if (j4 == 22)
                {
                    if (random.nextInt(2) == 0)
                    {
                        l4 = Block.oreDiamond.blockIndexInTexture;
                    }
                    else
                    {
                        l4 = Block.oreRedstone.blockIndexInTexture;
                    }
                }
                else if (j4 == 10)
                {
                    l4 = Block.oreIron.blockIndexInTexture;
                }
                else if (j4 == 8)
                {
                    l4 = Block.oreCoal.blockIndexInTexture;
                }
                else if (j4 > 4)
                {
                    l4 = Block.stone.blockIndexInTexture;
                }
                else if (j4 > 0)
                {
                    l4 = Block.dirt.blockIndexInTexture;
                }

                drawTexturedModalRect((k1 + k3 * 16) - k2, (l1 + i3 * 16) - l2, l4 % 16 << 4, (l4 >> 4) << 4, 16, 16);
            }
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        for (int j3 = 0; j3 < AchievementList.achievementList.size(); j3++)
        {
            Achievement achievement1 = (Achievement)AchievementList.achievementList.get(j3);

            if (achievement1.parentAchievement == null)
            {
                continue;
            }

            int l3 = (achievement1.displayColumn * 24 - i) + 11 + k1;
            int k4 = (achievement1.displayRow * 24 - j) + 11 + l1;
            int i5 = (achievement1.parentAchievement.displayColumn * 24 - i) + 11 + k1;
            int l5 = (achievement1.parentAchievement.displayRow * 24 - j) + 11 + l1;
            boolean flag = statFileWriter.hasAchievementUnlocked(achievement1);
            boolean flag1 = statFileWriter.canUnlockAchievement(achievement1);
            char c = Math.sin(((double)(System.currentTimeMillis() % 600L) / 600D) * Math.PI * 2D) <= 0.59999999999999998D ? '\202' : '\377';
            int i8 = 0xff000000;

            if (flag)
            {
                i8 = 0xff707070;
            }
            else if (flag1)
            {
                i8 = 65280 + (c << 24);
            }

            drawHorizontalLine(l3, i5, k4, i8);
            drawVerticalLine(i5, k4, l5, i8);
        }

        Achievement achievement = null;
        RenderItem renderitem = new RenderItem();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);

        for (int i4 = 0; i4 < AchievementList.achievementList.size(); i4++)
        {
            Achievement achievement2 = (Achievement)AchievementList.achievementList.get(i4);
            int j5 = achievement2.displayColumn * 24 - i;
            int i6 = achievement2.displayRow * 24 - j;

            if (j5 < -24 || i6 < -24 || j5 > 224 || i6 > 155)
            {
                continue;
            }

            if (statFileWriter.hasAchievementUnlocked(achievement2))
            {
                float f1 = 1.0F;
                GL11.glColor4f(f1, f1, f1, 1.0F);
            }
            else if (statFileWriter.canUnlockAchievement(achievement2))
            {
                float f2 = Math.sin(((double)(System.currentTimeMillis() % 600L) / 600D) * Math.PI * 2D) >= 0.59999999999999998D ? 0.8F : 0.6F;
                GL11.glColor4f(f2, f2, f2, 1.0F);
            }
            else
            {
                float f3 = 0.3F;
                GL11.glColor4f(f3, f3, f3, 1.0F);
            }

            mc.renderEngine.bindTexture(l);
            int k6 = k1 + j5;
            int j7 = l1 + i6;

            if (achievement2.getSpecial())
            {
                drawTexturedModalRect(k6 - 2, j7 - 2, 26, 202, 26, 26);
            }
            else
            {
                drawTexturedModalRect(k6 - 2, j7 - 2, 0, 202, 26, 26);
            }

            if (!statFileWriter.canUnlockAchievement(achievement2))
            {
                float f4 = 0.1F;
                GL11.glColor4f(f4, f4, f4, 1.0F);
                renderitem.field_27004_a = false;
            }

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_CULL_FACE);
            renderitem.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, achievement2.theItemStack, k6 + 3, j7 + 3);
            GL11.glDisable(GL11.GL_LIGHTING);

            if (!statFileWriter.canUnlockAchievement(achievement2))
            {
                renderitem.field_27004_a = true;
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            if (par1 >= k1 && par2 >= l1 && par1 < k1 + 224 && par2 < l1 + 155 && par1 >= k6 && par1 <= k6 + 22 && par2 >= j7 && par2 <= j7 + 22)
            {
                achievement = achievement2;
            }
        }

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(l);
        drawTexturedModalRect(i1, j1, 0, 0, achievementsPaneWidth, achievementsPaneHeight);
        GL11.glPopMatrix();
        zLevel = 0.0F;
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        super.drawScreen(par1, par2, par3);

        if (achievement != null)
        {
            String s = StatCollector.translateToLocal(achievement.getName());
            String s1 = achievement.getDescription();
            int k5 = par1 + 12;
            int j6 = par2 - 4;

            if (statFileWriter.canUnlockAchievement(achievement))
            {
                int l6 = Math.max(fontRenderer.getStringWidth(s), 120);
                int k7 = fontRenderer.splitStringWidth(s1, l6);

                if (statFileWriter.hasAchievementUnlocked(achievement))
                {
                    k7 += 12;
                }

                drawGradientRect(k5 - 3, j6 - 3, k5 + l6 + 3, j6 + k7 + 3 + 12, 0xc0000000, 0xc0000000);
                fontRenderer.drawSplitString(s1, k5, j6 + 12, l6, 0xffa0a0a0);

                if (statFileWriter.hasAchievementUnlocked(achievement))
                {
                    fontRenderer.drawStringWithShadow(StatCollector.translateToLocal("achievement.taken"), k5, j6 + k7 + 4, 0xff9090ff);
                }
            }
            else
            {
                int i7 = Math.max(fontRenderer.getStringWidth(s), 120);
                String s2 = StatCollector.translateToLocalFormatted("achievement.requires", new Object[]
                        {
                            StatCollector.translateToLocal(achievement.parentAchievement.getName())
                        });
                int l7 = fontRenderer.splitStringWidth(s2, i7);
                drawGradientRect(k5 - 3, j6 - 3, k5 + i7 + 3, j6 + l7 + 12 + 3, 0xc0000000, 0xc0000000);
                fontRenderer.drawSplitString(s2, k5, j6 + 12, i7, 0xff705050);
            }

            fontRenderer.drawStringWithShadow(s, k5, j6, statFileWriter.canUnlockAchievement(achievement) ? achievement.getSpecial() ? -128 : -1 : achievement.getSpecial() ? 0xff808040 : 0xff808080);
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.disableStandardItemLighting();
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return true;
    }

    static
    {
        guiMapTop = AchievementList.minDisplayColumn * 24 - 112;
        guiMapLeft = AchievementList.minDisplayRow * 24 - 112;
        guiMapBottom = AchievementList.maxDisplayColumn * 24 - 77;
        guiMapRight = AchievementList.maxDisplayRow * 24 - 77;
    }
}
