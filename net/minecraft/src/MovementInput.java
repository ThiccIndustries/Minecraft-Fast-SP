package net.minecraft.src;

public class MovementInput
{
    /**
     * The speed at which the player is strafing. Postive numbers to the left and negative to the right.
     */
    public float moveStrafe;

    /**
     * The speed at which the player is moving forward. Negative numbers will move backwards.
     */
    public float moveForward;
    public boolean jump;
    public boolean sneak;

    public MovementInput()
    {
        moveStrafe = 0.0F;
        moveForward = 0.0F;
        jump = false;
        sneak = false;
    }

    public void func_52013_a()
    {
    }
}
