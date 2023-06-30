package net.minecraft.src;

import java.util.Random;

public class TileEntityEnchantmentTable extends TileEntity
{
    /** Used by the render to make the book 'bounce' */
    public int tickCount;

    /** Value used for determining how the page flip should look. */
    public float pageFlip;

    /** The last tick's pageFlip value. */
    public float pageFlipPrev;
    public float field_40061_d;
    public float field_40062_e;

    /** The amount that the book is open. */
    public float bookSpread;

    /** The amount that the book is open. */
    public float bookSpreadPrev;
    public float bookRotation2;
    public float bookRotationPrev;
    public float bookRotation;
    private static Random rand = new Random();

    public TileEntityEnchantmentTable()
    {
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        super.updateEntity();
        bookSpreadPrev = bookSpread;
        bookRotationPrev = bookRotation2;
        EntityPlayer entityplayer = worldObj.getClosestPlayer((float)xCoord + 0.5F, (float)yCoord + 0.5F, (float)zCoord + 0.5F, 3D);

        if (entityplayer != null)
        {
            double d = entityplayer.posX - (double)((float)xCoord + 0.5F);
            double d1 = entityplayer.posZ - (double)((float)zCoord + 0.5F);
            bookRotation = (float)Math.atan2(d1, d);
            bookSpread += 0.1F;

            if (bookSpread < 0.5F || rand.nextInt(40) == 0)
            {
                float f3 = field_40061_d;

                do
                {
                    field_40061_d += rand.nextInt(4) - rand.nextInt(4);
                }
                while (f3 == field_40061_d);
            }
        }
        else
        {
            bookRotation += 0.02F;
            bookSpread -= 0.1F;
        }

        for (; bookRotation2 >= (float)Math.PI; bookRotation2 -= ((float)Math.PI * 2F)) { }

        for (; bookRotation2 < -(float)Math.PI; bookRotation2 += ((float)Math.PI * 2F)) { }

        for (; bookRotation >= (float)Math.PI; bookRotation -= ((float)Math.PI * 2F)) { }

        for (; bookRotation < -(float)Math.PI; bookRotation += ((float)Math.PI * 2F)) { }

        float f;

        for (f = bookRotation - bookRotation2; f >= (float)Math.PI; f -= ((float)Math.PI * 2F)) { }

        for (; f < -(float)Math.PI; f += ((float)Math.PI * 2F)) { }

        bookRotation2 += f * 0.4F;

        if (bookSpread < 0.0F)
        {
            bookSpread = 0.0F;
        }

        if (bookSpread > 1.0F)
        {
            bookSpread = 1.0F;
        }

        tickCount++;
        pageFlipPrev = pageFlip;
        float f1 = (field_40061_d - pageFlip) * 0.4F;
        float f2 = 0.2F;

        if (f1 < -f2)
        {
            f1 = -f2;
        }

        if (f1 > f2)
        {
            f1 = f2;
        }

        field_40062_e += (f1 - field_40062_e) * 0.9F;
        pageFlip = pageFlip + field_40062_e;
    }
}
