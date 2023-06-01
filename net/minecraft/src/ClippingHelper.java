package net.minecraft.src;

public class ClippingHelper
{
    public float frustum[][];
    public float projectionMatrix[];
    public float modelviewMatrix[];
    public float clippingMatrix[];

    public ClippingHelper()
    {
        frustum = new float[16][16];
        projectionMatrix = new float[16];
        modelviewMatrix = new float[16];
        clippingMatrix = new float[16];
    }

    /**
     * Returns true if the box is inside all 6 clipping planes, otherwise returns false.
     */
    public boolean isBoxInFrustum(double par1, double par3, double par5, double par7, double par9, double par11)
    {
        for (int i = 0; i < 6; i++)
        {
            float f = (float)par1;
            float f1 = (float)par3;
            float f2 = (float)par5;
            float f3 = (float)par7;
            float f4 = (float)par9;
            float f5 = (float)par11;

            if (frustum[i][0] * f + frustum[i][1] * f1 + frustum[i][2] * f2 + frustum[i][3] <= 0.0F && frustum[i][0] * f3 + frustum[i][1] * f1 + frustum[i][2] * f2 + frustum[i][3] <= 0.0F && frustum[i][0] * f + frustum[i][1] * f4 + frustum[i][2] * f2 + frustum[i][3] <= 0.0F && frustum[i][0] * f3 + frustum[i][1] * f4 + frustum[i][2] * f2 + frustum[i][3] <= 0.0F && frustum[i][0] * f + frustum[i][1] * f1 + frustum[i][2] * f5 + frustum[i][3] <= 0.0F && frustum[i][0] * f3 + frustum[i][1] * f1 + frustum[i][2] * f5 + frustum[i][3] <= 0.0F && frustum[i][0] * f + frustum[i][1] * f4 + frustum[i][2] * f5 + frustum[i][3] <= 0.0F && frustum[i][0] * f3 + frustum[i][1] * f4 + frustum[i][2] * f5 + frustum[i][3] <= 0.0F)
            {
                return false;
            }
        }

        return true;
    }

    public boolean isBoxInFrustumFully(double d, double d1, double d2, double d3, double d4, double d5)
    {
        for (int i = 0; i < 6; i++)
        {
            float f = (float)d;
            float f1 = (float)d1;
            float f2 = (float)d2;
            float f3 = (float)d3;
            float f4 = (float)d4;
            float f5 = (float)d5;

            if (i < 4)
            {
                if (frustum[i][0] * f + frustum[i][1] * f1 + frustum[i][2] * f2 + frustum[i][3] <= 0.0F || frustum[i][0] * f3 + frustum[i][1] * f1 + frustum[i][2] * f2 + frustum[i][3] <= 0.0F || frustum[i][0] * f + frustum[i][1] * f4 + frustum[i][2] * f2 + frustum[i][3] <= 0.0F || frustum[i][0] * f3 + frustum[i][1] * f4 + frustum[i][2] * f2 + frustum[i][3] <= 0.0F || frustum[i][0] * f + frustum[i][1] * f1 + frustum[i][2] * f5 + frustum[i][3] <= 0.0F || frustum[i][0] * f3 + frustum[i][1] * f1 + frustum[i][2] * f5 + frustum[i][3] <= 0.0F || frustum[i][0] * f + frustum[i][1] * f4 + frustum[i][2] * f5 + frustum[i][3] <= 0.0F || frustum[i][0] * f3 + frustum[i][1] * f4 + frustum[i][2] * f5 + frustum[i][3] <= 0.0F)
                {
                    return false;
                }

                continue;
            }

            if (frustum[i][0] * f + frustum[i][1] * f1 + frustum[i][2] * f2 + frustum[i][3] <= 0.0F && frustum[i][0] * f3 + frustum[i][1] * f1 + frustum[i][2] * f2 + frustum[i][3] <= 0.0F && frustum[i][0] * f + frustum[i][1] * f4 + frustum[i][2] * f2 + frustum[i][3] <= 0.0F && frustum[i][0] * f3 + frustum[i][1] * f4 + frustum[i][2] * f2 + frustum[i][3] <= 0.0F && frustum[i][0] * f + frustum[i][1] * f1 + frustum[i][2] * f5 + frustum[i][3] <= 0.0F && frustum[i][0] * f3 + frustum[i][1] * f1 + frustum[i][2] * f5 + frustum[i][3] <= 0.0F && frustum[i][0] * f + frustum[i][1] * f4 + frustum[i][2] * f5 + frustum[i][3] <= 0.0F && frustum[i][0] * f3 + frustum[i][1] * f4 + frustum[i][2] * f5 + frustum[i][3] <= 0.0F)
            {
                return false;
            }
        }

        return true;
    }
}
