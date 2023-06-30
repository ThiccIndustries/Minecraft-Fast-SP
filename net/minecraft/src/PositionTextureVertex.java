package net.minecraft.src;

public class PositionTextureVertex
{
    public Vec3D vector3D;
    public float texturePositionX;
    public float texturePositionY;

    public PositionTextureVertex(float par1, float par2, float par3, float par4, float par5)
    {
        this(Vec3D.createVectorHelper(par1, par2, par3), par4, par5);
    }

    public PositionTextureVertex setTexturePosition(float par1, float par2)
    {
        return new PositionTextureVertex(this, par1, par2);
    }

    public PositionTextureVertex(PositionTextureVertex par1PositionTextureVertex, float par2, float par3)
    {
        vector3D = par1PositionTextureVertex.vector3D;
        texturePositionX = par2;
        texturePositionY = par3;
    }

    public PositionTextureVertex(Vec3D par1Vec3D, float par2, float par3)
    {
        vector3D = par1Vec3D;
        texturePositionX = par2;
        texturePositionY = par3;
    }
}
