package net.minecraft.src;

import java.util.Random;
import org.lwjgl.opengl.GL11;

public class RenderDragon extends RenderLiving
{
    /**
     * The entity instance of the dragon. Note: This is a static field in RenderDragon because there is only supposed to
     * be one dragon
     */
    public static EntityDragon entityDragon;
    private static int field_40284_d = 0;

    /** An instance of the dragon model in RenderDragon */
    protected ModelDragon modelDragon;

    public RenderDragon()
    {
        super(new ModelDragon(0.0F), 0.5F);
        modelDragon = (ModelDragon)mainModel;
        setRenderPassModel(mainModel);
    }

    /**
     * Used to rotate the dragon as a whole in RenderDragon. It's called in the rotateCorpse method.
     */
    protected void rotateDragonBody(EntityDragon par1EntityDragon, float par2, float par3, float par4)
    {
        float f = (float)par1EntityDragon.func_40160_a(7, par4)[0];
        float f1 = (float)(par1EntityDragon.func_40160_a(5, par4)[1] - par1EntityDragon.func_40160_a(10, par4)[1]);
        GL11.glRotatef(-f, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(f1 * 10F, 1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(0.0F, 0.0F, 1.0F);

        if (par1EntityDragon.deathTime > 0)
        {
            float f2 = ((((float)par1EntityDragon.deathTime + par4) - 1.0F) / 20F) * 1.6F;
            f2 = MathHelper.sqrt_float(f2);

            if (f2 > 1.0F)
            {
                f2 = 1.0F;
            }

            GL11.glRotatef(f2 * getDeathMaxRotation(par1EntityDragon), 0.0F, 0.0F, 1.0F);
        }
    }

    protected void func_40280_a(EntityDragon par1EntityDragon, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        if (par1EntityDragon.field_40178_aA > 0)
        {
            float f = (float)par1EntityDragon.field_40178_aA / 200F;
            GL11.glDepthFunc(GL11.GL_LEQUAL);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glAlphaFunc(GL11.GL_GREATER, f);
            loadDownloadableImageTexture(par1EntityDragon.skinUrl, "/mob/enderdragon/shuffle.png");
            mainModel.render(par1EntityDragon, par2, par3, par4, par5, par6, par7);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            GL11.glDepthFunc(GL11.GL_EQUAL);
        }

        loadDownloadableImageTexture(par1EntityDragon.skinUrl, par1EntityDragon.getTexture());
        mainModel.render(par1EntityDragon, par2, par3, par4, par5, par6, par7);

        if (par1EntityDragon.hurtTime > 0)
        {
            GL11.glDepthFunc(GL11.GL_EQUAL);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.5F);
            mainModel.render(par1EntityDragon, par2, par3, par4, par5, par6, par7);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
        }
    }

    /**
     * Renders the dragon, along with its dying animation
     */
    public void renderDragon(EntityDragon par1EntityDragon, double par2, double par4, double par6, float par8, float par9)
    {
        entityDragon = par1EntityDragon;

        if (field_40284_d != 4)
        {
            mainModel = new ModelDragon(0.0F);
            field_40284_d = 4;
        }

        super.doRenderLiving(par1EntityDragon, par2, par4, par6, par8, par9);

        if (par1EntityDragon.healingEnderCrystal != null)
        {
            float f = (float)par1EntityDragon.healingEnderCrystal.innerRotation + par9;
            float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
            f1 = (f1 * f1 + f1) * 0.2F;
            float f2 = (float)(par1EntityDragon.healingEnderCrystal.posX - par1EntityDragon.posX - (par1EntityDragon.prevPosX - par1EntityDragon.posX) * (double)(1.0F - par9));
            float f3 = (float)(((double)f1 + par1EntityDragon.healingEnderCrystal.posY) - 1.0D - par1EntityDragon.posY - (par1EntityDragon.prevPosY - par1EntityDragon.posY) * (double)(1.0F - par9));
            float f4 = (float)(par1EntityDragon.healingEnderCrystal.posZ - par1EntityDragon.posZ - (par1EntityDragon.prevPosZ - par1EntityDragon.posZ) * (double)(1.0F - par9));
            float f5 = MathHelper.sqrt_float(f2 * f2 + f4 * f4);
            float f6 = MathHelper.sqrt_float(f2 * f2 + f3 * f3 + f4 * f4);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6);
            GL11.glRotatef(((float)(-Math.atan2(f4, f2)) * 180F) / (float)Math.PI - 90F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(((float)(-Math.atan2(f5, f3)) * 180F) / (float)Math.PI - 90F, 1.0F, 0.0F, 0.0F);
            Tessellator tessellator = Tessellator.instance;
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_CULL_FACE);
            loadTexture("/mob/enderdragon/beam.png");
            GL11.glShadeModel(GL11.GL_SMOOTH);
            float f7 = 0.0F - ((float)par1EntityDragon.ticksExisted + par9) * 0.01F;
            float f8 = MathHelper.sqrt_float(f2 * f2 + f3 * f3 + f4 * f4) / 32F - ((float)par1EntityDragon.ticksExisted + par9) * 0.01F;
            tessellator.startDrawing(5);
            int i = 8;

            for (int j = 0; j <= i; j++)
            {
                float f9 = MathHelper.sin(((float)(j % i) * (float)Math.PI * 2.0F) / (float)i) * 0.75F;
                float f10 = MathHelper.cos(((float)(j % i) * (float)Math.PI * 2.0F) / (float)i) * 0.75F;
                float f11 = ((float)(j % i) * 1.0F) / (float)i;
                tessellator.setColorOpaque_I(0);
                tessellator.addVertexWithUV(f9 * 0.2F, f10 * 0.2F, 0.0D, f11, f8);
                tessellator.setColorOpaque_I(0xffffff);
                tessellator.addVertexWithUV(f9, f10, f6, f11, f7);
            }

            tessellator.draw();
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glShadeModel(GL11.GL_FLAT);
            RenderHelper.enableStandardItemLighting();
            GL11.glPopMatrix();
        }
    }

    /**
     * Renders the animation for when an enderdragon dies
     */
    protected void renderDragonDying(EntityDragon par1EntityDragon, float par2)
    {
        super.renderEquippedItems(par1EntityDragon, par2);
        Tessellator tessellator = Tessellator.instance;

        if (par1EntityDragon.field_40178_aA > 0)
        {
            RenderHelper.disableStandardItemLighting();
            float f = ((float)par1EntityDragon.field_40178_aA + par2) / 200F;
            float f1 = 0.0F;

            if (f > 0.8F)
            {
                f1 = (f - 0.8F) / 0.2F;
            }

            Random random = new Random(432L);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDepthMask(false);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, -1F, -2F);

            for (int i = 0; (float)i < ((f + f * f) / 2.0F) * 60F; i++)
            {
                GL11.glRotatef(random.nextFloat() * 360F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(random.nextFloat() * 360F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360F + f * 90F, 0.0F, 0.0F, 1.0F);
                tessellator.startDrawing(6);
                float f2 = random.nextFloat() * 20F + 5F + f1 * 10F;
                float f3 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
                tessellator.setColorRGBA_I(0xffffff, (int)(255F * (1.0F - f1)));
                tessellator.addVertex(0.0D, 0.0D, 0.0D);
                tessellator.setColorRGBA_I(0xff00ff, 0);
                tessellator.addVertex(-0.86599999999999999D * (double)f3, f2, -0.5F * f3);
                tessellator.addVertex(0.86599999999999999D * (double)f3, f2, -0.5F * f3);
                tessellator.addVertex(0.0D, f2, 1.0F * f3);
                tessellator.addVertex(-0.86599999999999999D * (double)f3, f2, -0.5F * f3);
                tessellator.draw();
            }

            GL11.glPopMatrix();
            GL11.glDepthMask(true);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glShadeModel(GL11.GL_FLAT);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            RenderHelper.enableStandardItemLighting();
        }
    }

    protected int func_40283_a(EntityDragon par1EntityDragon, int par2, float par3)
    {
        if (par2 == 1)
        {
            GL11.glDepthFunc(GL11.GL_LEQUAL);
        }

        if (par2 != 0)
        {
            return -1;
        }
        else
        {
            loadTexture("/mob/enderdragon/ender_eyes.png");
            float f = 1.0F;
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDepthFunc(GL11.GL_EQUAL);
            int i = 61680;
            int j = i % 0x10000;
            int k = i / 0x10000;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f);
            return 1;
        }
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
    {
        return func_40283_a((EntityDragon)par1EntityLiving, par2, par3);
    }

    protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2)
    {
        renderDragonDying((EntityDragon)par1EntityLiving, par2);
    }

    protected void rotateCorpse(EntityLiving par1EntityLiving, float par2, float par3, float par4)
    {
        rotateDragonBody((EntityDragon)par1EntityLiving, par2, par3, par4);
    }

    /**
     * Renders the model in RenderLiving
     */
    protected void renderModel(EntityLiving par1EntityLiving, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        func_40280_a((EntityDragon)par1EntityLiving, par2, par3, par4, par5, par6, par7);
    }

    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        renderDragon((EntityDragon)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        renderDragon((EntityDragon)par1Entity, par2, par4, par6, par8, par9);
    }
}
