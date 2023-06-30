package net.minecraft.src;

import java.util.*;

public class KeyBinding
{
    public static List keybindArray = new ArrayList();
    public static IntHashMap hash = new IntHashMap();
    public String keyDescription;
    public int keyCode;

    /** because _303 wanted me to call it that(Caironater) */
    public boolean pressed;
    public int pressTime;

    public static void onTick(int par0)
    {
        KeyBinding keybinding = (KeyBinding)hash.lookup(par0);

        if (keybinding != null)
        {
            keybinding.pressTime++;
        }
    }

    public static void setKeyBindState(int par0, boolean par1)
    {
        KeyBinding keybinding = (KeyBinding)hash.lookup(par0);

        if (keybinding != null)
        {
            keybinding.pressed = par1;
        }
    }

    public static void unPressAllKeys()
    {
        KeyBinding keybinding;

        for (Iterator iterator = keybindArray.iterator(); iterator.hasNext(); keybinding.unpressKey())
        {
            keybinding = (KeyBinding)iterator.next();
        }
    }

    public static void resetKeyBindingArrayAndHash()
    {
        hash.clearMap();
        KeyBinding keybinding;

        for (Iterator iterator = keybindArray.iterator(); iterator.hasNext(); hash.addKey(keybinding.keyCode, keybinding))
        {
            keybinding = (KeyBinding)iterator.next();
        }
    }

    public KeyBinding(String par1Str, int par2)
    {
        pressTime = 0;
        keyDescription = par1Str;
        keyCode = par2;
        keybindArray.add(this);
        hash.addKey(par2, this);
    }

    public boolean isPressed()
    {
        if (pressTime == 0)
        {
            return false;
        }
        else
        {
            pressTime--;
            return true;
        }
    }

    private void unpressKey()
    {
        pressTime = 0;
        pressed = false;
    }
}
