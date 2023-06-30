package net.minecraft.src;

public class ModelBox
{
    private PositionTextureVertex vertexPositions[];
    private TexturedQuad quadList[];

    /** X vertex coordinate of lower box corner */
    public final float posX1;

    /** Y vertex coordinate of lower box corner */
    public final float posY1;

    /** Z vertex coordinate of lower box corner */
    public final float posZ1;

    /** X vertex coordinate of upper box corner */
    public final float posX2;

    /** Y vertex coordinate of upper box corner */
    public final float posY2;

    /** Z vertex coordinate of upper box corner */
    public final float posZ2;
    public String field_40673_g;

    public ModelBox(ModelRenderer par1ModelRenderer, int par2, int par3, float par4, float par5, float par6, int par7, int par8, int par9, float par10)
    {
        posX1 = par4;
        posY1 = par5;
        posZ1 = par6;
        posX2 = par4 + (float)par7;
        posY2 = par5 + (float)par8;
        posZ2 = par6 + (float)par9;
        vertexPositions = new PositionTextureVertex[8];
        quadList = new TexturedQuad[6];
        float f = par4 + (float)par7;
        float f1 = par5 + (float)par8;
        float f2 = par6 + (float)par9;
        par4 -= par10;
        par5 -= par10;
        par6 -= par10;
        f += par10;
        f1 += par10;
        f2 += par10;

        if (par1ModelRenderer.mirror)
        {
            float f3 = f;
            f = par4;
            par4 = f3;
        }

        PositionTextureVertex positiontexturevertex = new PositionTextureVertex(par4, par5, par6, 0.0F, 0.0F);
        PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(f, par5, par6, 0.0F, 8F);
        PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(f, f1, par6, 8F, 8F);
        PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(par4, f1, par6, 8F, 0.0F);
        PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(par4, par5, f2, 0.0F, 0.0F);
        PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(f, par5, f2, 0.0F, 8F);
        PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(f, f1, f2, 8F, 8F);
        PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(par4, f1, f2, 8F, 0.0F);
        vertexPositions[0] = positiontexturevertex;
        vertexPositions[1] = positiontexturevertex1;
        vertexPositions[2] = positiontexturevertex2;
        vertexPositions[3] = positiontexturevertex3;
        vertexPositions[4] = positiontexturevertex4;
        vertexPositions[5] = positiontexturevertex5;
        vertexPositions[6] = positiontexturevertex6;
        vertexPositions[7] = positiontexturevertex7;
        quadList[0] = new TexturedQuad(new PositionTextureVertex[]
                {
                    positiontexturevertex5, positiontexturevertex1, positiontexturevertex2, positiontexturevertex6
                }, par2 + par9 + par7, par3 + par9, par2 + par9 + par7 + par9, par3 + par9 + par8, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
        quadList[1] = new TexturedQuad(new PositionTextureVertex[]
                {
                    positiontexturevertex, positiontexturevertex4, positiontexturevertex7, positiontexturevertex3
                }, par2, par3 + par9, par2 + par9, par3 + par9 + par8, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
        quadList[2] = new TexturedQuad(new PositionTextureVertex[]
                {
                    positiontexturevertex5, positiontexturevertex4, positiontexturevertex, positiontexturevertex1
                }, par2 + par9, par3, par2 + par9 + par7, par3 + par9, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
        quadList[3] = new TexturedQuad(new PositionTextureVertex[]
                {
                    positiontexturevertex2, positiontexturevertex3, positiontexturevertex7, positiontexturevertex6
                }, par2 + par9 + par7, par3 + par9, par2 + par9 + par7 + par7, par3, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
        quadList[4] = new TexturedQuad(new PositionTextureVertex[]
                {
                    positiontexturevertex1, positiontexturevertex, positiontexturevertex3, positiontexturevertex2
                }, par2 + par9, par3 + par9, par2 + par9 + par7, par3 + par9 + par8, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);
        quadList[5] = new TexturedQuad(new PositionTextureVertex[]
                {
                    positiontexturevertex4, positiontexturevertex5, positiontexturevertex6, positiontexturevertex7
                }, par2 + par9 + par7 + par9, par3 + par9, par2 + par9 + par7 + par9 + par7, par3 + par9 + par8, par1ModelRenderer.textureWidth, par1ModelRenderer.textureHeight);

        if (par1ModelRenderer.mirror)
        {
            for (int i = 0; i < quadList.length; i++)
            {
                quadList[i].flipFace();
            }
        }
    }

    /**
     * Draw the six sided box defined by this ModelBox
     */
    public void render(Tessellator par1Tessellator, float par2)
    {
        for (int i = 0; i < quadList.length; i++)
        {
            quadList[i].draw(par1Tessellator, par2);
        }
    }

    public ModelBox func_40671_a(String par1Str)
    {
        field_40673_g = par1Str;
        return this;
    }
}
