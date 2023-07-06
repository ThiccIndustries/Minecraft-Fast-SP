package net.minecraft.src;

public class TexturedQuad
{
    public PositionTextureVertex vertexPositions[];
    public int nVertices;
    private boolean invertNormal;

    public TexturedQuad(PositionTextureVertex par1ArrayOfPositionTextureVertex[])
    {
        nVertices = 0;
        invertNormal = false;
        vertexPositions = par1ArrayOfPositionTextureVertex;
        nVertices = par1ArrayOfPositionTextureVertex.length;
    }

    public TexturedQuad(PositionTextureVertex par1ArrayOfPositionTextureVertex[], int par2, int par3, int par4, int par5, float par6, float par7)
    {
        this(par1ArrayOfPositionTextureVertex);
        float f = 0.0F / par6;
        float f1 = 0.0F / par7;
        par1ArrayOfPositionTextureVertex[0] = par1ArrayOfPositionTextureVertex[0].setTexturePosition((float)par4 / par6 - f, (float)par3 / par7 + f1);
        par1ArrayOfPositionTextureVertex[1] = par1ArrayOfPositionTextureVertex[1].setTexturePosition((float)par2 / par6 + f, (float)par3 / par7 + f1);
        par1ArrayOfPositionTextureVertex[2] = par1ArrayOfPositionTextureVertex[2].setTexturePosition((float)par2 / par6 + f, (float)par5 / par7 - f1);
        par1ArrayOfPositionTextureVertex[3] = par1ArrayOfPositionTextureVertex[3].setTexturePosition((float)par4 / par6 - f, (float)par5 / par7 - f1);
    }

    public void flipFace()
    {
        PositionTextureVertex apositiontexturevertex[] = new PositionTextureVertex[vertexPositions.length];

        for (int i = 0; i < vertexPositions.length; i++)
        {
            apositiontexturevertex[i] = vertexPositions[vertexPositions.length - i - 1];
        }

        vertexPositions = apositiontexturevertex;
    }

    public void draw(Tessellator par1Tessellator, float par2)
    {
        Vec3D vec3d = vertexPositions[1].vector3D.subtract(vertexPositions[0].vector3D);
        Vec3D vec3d1 = vertexPositions[1].vector3D.subtract(vertexPositions[2].vector3D);
        Vec3D vec3d2 = vec3d1.crossProduct(vec3d).normalize();
        par1Tessellator.startDrawingQuads();

        if (invertNormal)
        {
            par1Tessellator.setNormal(-(float)vec3d2.xCoord, -(float)vec3d2.yCoord, -(float)vec3d2.zCoord);
        }
        else
        {
            par1Tessellator.setNormal((float)vec3d2.xCoord, (float)vec3d2.yCoord, (float)vec3d2.zCoord);
        }

        for (int i = 0; i < 4; i++)
        {
            PositionTextureVertex positiontexturevertex = vertexPositions[i];
            par1Tessellator.addVertexWithUV((float)positiontexturevertex.vector3D.xCoord * par2, (float)positiontexturevertex.vector3D.yCoord * par2, (float)positiontexturevertex.vector3D.zCoord * par2, positiontexturevertex.texturePositionX, positiontexturevertex.texturePositionY);
        }

        par1Tessellator.draw();
    }
}
