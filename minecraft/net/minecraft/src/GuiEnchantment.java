package net.minecraft.src;

import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.GLU;

public class GuiEnchantment extends GuiContainer
{
    /** The book model used on the GUI. */
    private static ModelBook bookModel = new ModelBook();
    private Random field_40230_x;

    /** ContainerEnchantment object associated with this gui */
    private ContainerEnchantment containerEnchantment;
    public int field_40227_h;
    public float field_40229_i;
    public float field_40225_j;
    public float field_40226_k;
    public float field_40223_l;
    public float field_40224_m;
    public float field_40221_n;
    ItemStack field_40222_o;

    public GuiEnchantment(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5)
    {
        super(new ContainerEnchantment(par1InventoryPlayer, par2World, par3, par4, par5));
        field_40230_x = new Random();
        containerEnchantment = (ContainerEnchantment)inventorySlots;
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        super.onGuiClosed();
    }

    /**
     * Draw the foreground layer for the GuiContainer (everythin in front of the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString(StatCollector.translateToLocal("container.enchant"), 12, 6, 0x404040);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, (ySize - 96) + 2, 0x404040);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        func_40219_x_();
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        int i = (width - xSize) / 2;
        int j = (height - ySize) / 2;

        for (int k = 0; k < 3; k++)
        {
            int l = par1 - (i + 60);
            int i1 = par2 - (j + 14 + 19 * k);

            if (l >= 0 && i1 >= 0 && l < 108 && i1 < 19 && containerEnchantment.enchantItem(mc.thePlayer, k))
            {
                mc.playerController.func_40593_a(containerEnchantment.windowId, k);
            }
        }
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        int i = mc.renderEngine.getTexture("/gui/enchant.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
        GL11.glPushMatrix();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        GL11.glViewport(((scaledresolution.getScaledWidth() - 320) / 2) * scaledresolution.scaleFactor, ((scaledresolution.getScaledHeight() - 240) / 2) * scaledresolution.scaleFactor, 320 * scaledresolution.scaleFactor, 240 * scaledresolution.scaleFactor);
        GL11.glTranslatef(-0.34F, 0.23F, 0.0F);
        GLU.gluPerspective(90F, 1.333333F, 9F, 80F);
        float f = 1.0F;
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        RenderHelper.enableStandardItemLighting();
        GL11.glTranslatef(0.0F, 3.3F, -16F);
        GL11.glScalef(f, f, f);
        float f1 = 5F;
        GL11.glScalef(f1, f1, f1);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/item/book.png"));
        GL11.glRotatef(20F, 1.0F, 0.0F, 0.0F);
        float f2 = field_40221_n + (field_40224_m - field_40221_n) * par1;
        GL11.glTranslatef((1.0F - f2) * 0.2F, (1.0F - f2) * 0.1F, (1.0F - f2) * 0.25F);
        GL11.glRotatef(-(1.0F - f2) * 90F - 90F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
        float f3 = field_40225_j + (field_40229_i - field_40225_j) * par1 + 0.25F;
        float f4 = field_40225_j + (field_40229_i - field_40225_j) * par1 + 0.75F;
        f3 = (f3 - (float)MathHelper.func_40346_b(f3)) * 1.6F - 0.3F;
        f4 = (f4 - (float)MathHelper.func_40346_b(f4)) * 1.6F - 0.3F;

        if (f3 < 0.0F)
        {
            f3 = 0.0F;
        }

        if (f4 < 0.0F)
        {
            f4 = 0.0F;
        }

        if (f3 > 1.0F)
        {
            f3 = 1.0F;
        }

        if (f4 > 1.0F)
        {
            f4 = 1.0F;
        }

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        bookModel.render(null, 0.0F, f3, f4, f2, 0.0F, 0.0625F);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        EnchantmentNameParts.instance.setRandSeed(containerEnchantment.nameSeed);

        for (int l = 0; l < 3; l++)
        {
            String s = EnchantmentNameParts.instance.generateRandomEnchantName();
            zLevel = 0.0F;
            mc.renderEngine.bindTexture(i);
            int i1 = containerEnchantment.enchantLevels[l];
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            if (i1 == 0)
            {
                drawTexturedModalRect(j + 60, k + 14 + 19 * l, 0, 185, 108, 19);
                continue;
            }

            String s1 = (new StringBuilder()).append("").append(i1).toString();
            FontRenderer fontrenderer = mc.standardGalacticFontRenderer;
            int j1 = 0x685e4a;

            if (mc.thePlayer.experienceLevel < i1 && !mc.thePlayer.capabilities.isCreativeMode)
            {
                drawTexturedModalRect(j + 60, k + 14 + 19 * l, 0, 185, 108, 19);
                fontrenderer.drawSplitString(s, j + 62, k + 16 + 19 * l, 104, (j1 & 0xfefefe) >> 1);
                fontrenderer = mc.fontRenderer;
                j1 = 0x407f10;
                fontrenderer.drawStringWithShadow(s1, (j + 62 + 104) - fontrenderer.getStringWidth(s1), k + 16 + 19 * l + 7, j1);
                continue;
            }

            int k1 = par2 - (j + 60);
            int l1 = par3 - (k + 14 + 19 * l);

            if (k1 >= 0 && l1 >= 0 && k1 < 108 && l1 < 19)
            {
                drawTexturedModalRect(j + 60, k + 14 + 19 * l, 0, 204, 108, 19);
                j1 = 0xffff80;
            }
            else
            {
                drawTexturedModalRect(j + 60, k + 14 + 19 * l, 0, 166, 108, 19);
            }

            fontrenderer.drawSplitString(s, j + 62, k + 16 + 19 * l, 104, j1);
            fontrenderer = mc.fontRenderer;
            j1 = 0x80ff20;
            fontrenderer.drawStringWithShadow(s1, (j + 62 + 104) - fontrenderer.getStringWidth(s1), k + 16 + 19 * l + 7, j1);
        }
    }

    public void func_40219_x_()
    {
        ItemStack itemstack = inventorySlots.getSlot(0).getStack();

        if (!ItemStack.areItemStacksEqual(itemstack, field_40222_o))
        {
            field_40222_o = itemstack;

            do
            {
                field_40226_k += field_40230_x.nextInt(4) - field_40230_x.nextInt(4);
            }
            while (field_40229_i <= field_40226_k + 1.0F && field_40229_i >= field_40226_k - 1.0F);
        }

        field_40227_h++;
        field_40225_j = field_40229_i;
        field_40221_n = field_40224_m;
        boolean flag = false;

        for (int i = 0; i < 3; i++)
        {
            if (containerEnchantment.enchantLevels[i] != 0)
            {
                flag = true;
            }
        }

        if (flag)
        {
            field_40224_m += 0.2F;
        }
        else
        {
            field_40224_m -= 0.2F;
        }

        if (field_40224_m < 0.0F)
        {
            field_40224_m = 0.0F;
        }

        if (field_40224_m > 1.0F)
        {
            field_40224_m = 1.0F;
        }

        float f = (field_40226_k - field_40229_i) * 0.4F;
        float f1 = 0.2F;

        if (f < -f1)
        {
            f = -f1;
        }

        if (f > f1)
        {
            f = f1;
        }

        field_40223_l += (f - field_40223_l) * 0.9F;
        field_40229_i = field_40229_i + field_40223_l;
    }
}
