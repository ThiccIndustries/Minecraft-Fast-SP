package net.minecraft.src;

public class ModelBook extends ModelBase
{
    /** Right cover renderer (when facing the book) */
    public ModelRenderer coverRight;

    /** Left cover renderer (when facing the book) */
    public ModelRenderer coverLeft;

    /** The right pages renderer (when facing the book) */
    public ModelRenderer pagesRight;

    /** The left pages renderer (when facing the book) */
    public ModelRenderer pagesLeft;

    /** Right cover renderer (when facing the book) */
    public ModelRenderer flippingPageRight;

    /** Right cover renderer (when facing the book) */
    public ModelRenderer flippingPageLeft;

    /** The renderer of spine of the book */
    public ModelRenderer bookSpine;

    public ModelBook()
    {
        coverRight = (new ModelRenderer(this)).setTextureOffset(0, 0).addBox(-6F, -5F, 0.0F, 6, 10, 0);
        coverLeft = (new ModelRenderer(this)).setTextureOffset(16, 0).addBox(0.0F, -5F, 0.0F, 6, 10, 0);
        bookSpine = (new ModelRenderer(this)).setTextureOffset(12, 0).addBox(-1F, -5F, 0.0F, 2, 10, 0);
        pagesRight = (new ModelRenderer(this)).setTextureOffset(0, 10).addBox(0.0F, -4F, -0.99F, 5, 8, 1);
        pagesLeft = (new ModelRenderer(this)).setTextureOffset(12, 10).addBox(0.0F, -4F, -0.01F, 5, 8, 1);
        flippingPageRight = (new ModelRenderer(this)).setTextureOffset(24, 10).addBox(0.0F, -4F, 0.0F, 5, 8, 0);
        flippingPageLeft = (new ModelRenderer(this)).setTextureOffset(24, 10).addBox(0.0F, -4F, 0.0F, 5, 8, 0);
        coverRight.setRotationPoint(0.0F, 0.0F, -1F);
        coverLeft.setRotationPoint(0.0F, 0.0F, 1.0F);
        bookSpine.rotateAngleY = ((float)Math.PI / 2F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        setRotationAngles(par2, par3, par4, par5, par6, par7);
        coverRight.render(par7);
        coverLeft.render(par7);
        bookSpine.render(par7);
        pagesRight.render(par7);
        pagesLeft.render(par7);
        flippingPageRight.render(par7);
        flippingPageLeft.render(par7);
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
    {
        float f = (MathHelper.sin(par1 * 0.02F) * 0.1F + 1.25F) * par4;
        coverRight.rotateAngleY = (float)Math.PI + f;
        coverLeft.rotateAngleY = -f;
        pagesRight.rotateAngleY = f;
        pagesLeft.rotateAngleY = -f;
        flippingPageRight.rotateAngleY = f - f * 2.0F * par2;
        flippingPageLeft.rotateAngleY = f - f * 2.0F * par3;
        pagesRight.rotationPointX = MathHelper.sin(f);
        pagesLeft.rotationPointX = MathHelper.sin(f);
        flippingPageRight.rotationPointX = MathHelper.sin(f);
        flippingPageLeft.rotationPointX = MathHelper.sin(f);
    }
}
