package net.minecraft.src;

public class CustomAnimationFrame
{
    public int index;
    public int duration;
    public int counter;

    public CustomAnimationFrame(int i, int j)
    {
        index = 0;
        duration = 0;
        counter = 0;
        index = i;
        duration = j;
    }
}
