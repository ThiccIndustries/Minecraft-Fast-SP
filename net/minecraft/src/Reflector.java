package net.minecraft.src;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class Reflector
{
    private static Class classes[] = new Class[16];
    private static boolean classesChecked[] = new boolean[16];
    private static Map classNameMap = null;
    private static Method methods[] = new Method[256];
    private static boolean methodsChecked[] = new boolean[256];
    private static Map methodNameMap = null;
    private static Field fields[] = new Field[256];
    private static boolean fieldsChecked[] = new boolean[256];
    private static Map fieldNameMap = null;
    public static final int ModLoader = 0;
    public static final int ForgeHooksClient = 1;
    public static final int MinecraftForgeClient = 2;
    public static final int LightCache = 3;
    public static final int BlockCoord = 4;
    public static final int ForgeBlock = 5;
    public static final int ItemRenderType = 6;
    public static final int FMLRender = 7;
    public static final int ForgeEffectRenderer = 8;
    public static final int ForgeHooks = 9;
    public static final int Entity = 10;
    public static final int List = 11;
    public static final int ModLoader_renderWorldBlock = 0;
    public static final int ModLoader_renderInvBlock = 1;
    public static final int ModLoader_renderBlockIsItemFull3D = 2;
    public static final int ForgeHooksClient_onBlockHighlight = 10;
    public static final int ForgeHooksClient_canRenderInPass = 11;
    public static final int ForgeHooksClient_getTexture = 12;
    public static final int ForgeHooksClient_beforeRenderPass = 13;
    public static final int ForgeHooksClient_afterRenderPass = 14;
    public static final int ForgeHooksClient_beforeBlockRender = 15;
    public static final int ForgeHooksClient_afterBlockRender = 16;
    public static final int ForgeHooksClient_onRenderWorldLast = 17;
    public static final int ForgeHooksClient_onTextureLoadPre = 18;
    public static final int ForgeHooksClient_onTextureLoad = 19;
    public static final int MinecraftForgeClient_getItemRenderer = 20;
    public static final int LightCache_clear = 30;
    public static final int BlockCoord_resetPool = 40;
    public static final int ForgeBlock_isLadder = 50;
    public static final int ForgeBlock_isBed = 51;
    public static final int FMLRender_setTextureDimensions = 70;
    public static final int FMLRender_preRegisterEffect = 71;
    public static final int FMLRender_onUpdateTextureEffect = 72;
    public static final int FMLRender_onTexturePackChange = 73;
    public static final int ForgeEffectRenderer_addEffect = 80;
    public static final int ForgeHooks_onEntityLivingSetAttackTarget = 90;
    public static final int ForgeHooks_onEntityLivingUpdate = 91;
    public static final int ForgeHooks_onEntityLivingAttacked = 92;
    public static final int ForgeHooks_onEntityLivingHurt = 93;
    public static final int ForgeHooks_onEntityLivingDeath = 94;
    public static final int ForgeHooks_onEntityLivingDrops = 95;
    public static final int ForgeHooks_onEntityLivingFall = 96;
    public static final int ForgeHooks_onEntityLivingJump = 97;
    public static final int List_clear = 110;
    public static final int LightCache_cache = 30;
    public static final int ItemRenderType_EQUIPPED = 60;
    public static final int Entity_captureDrops = 100;
    public static final int Entity_capturedDrops = 101;

    public Reflector()
    {
    }

    private static Map getClassNameMap()
    {
        if (classNameMap == null)
        {
            classNameMap = new HashMap();
            classNameMap.put(Integer.valueOf(0), "ModLoader");
            classNameMap.put(Integer.valueOf(1), "forge.ForgeHooksClient");
            classNameMap.put(Integer.valueOf(2), "forge.MinecraftForgeClient");
            classNameMap.put(Integer.valueOf(3), "LightCache");
            classNameMap.put(Integer.valueOf(4), "BlockCoord");
            classNameMap.put(Integer.valueOf(5), net.minecraft.src.Block.class);
            classNameMap.put(Integer.valueOf(6), "forge.IItemRenderer$ItemRenderType");
            classNameMap.put(Integer.valueOf(7), "FMLRenderAccessLibrary");
            classNameMap.put(Integer.valueOf(8), net.minecraft.src.EffectRenderer.class);
            classNameMap.put(Integer.valueOf(9), "forge.ForgeHooks");
            classNameMap.put(Integer.valueOf(10), net.minecraft.src.Entity.class);
            classNameMap.put(Integer.valueOf(11), java.util.List.class);
        }

        return classNameMap;
    }

    private static Map getMethodNameMap()
    {
        if (methodNameMap == null)
        {
            methodNameMap = new HashMap();
            methodNameMap.put(Integer.valueOf(0), "renderWorldBlock");
            methodNameMap.put(Integer.valueOf(1), "renderInvBlock");
            methodNameMap.put(Integer.valueOf(2), "renderBlockIsItemFull3D");
            methodNameMap.put(Integer.valueOf(10), "onBlockHighlight");
            methodNameMap.put(Integer.valueOf(11), "canRenderInPass");
            methodNameMap.put(Integer.valueOf(12), "getTexture");
            methodNameMap.put(Integer.valueOf(13), "beforeRenderPass");
            methodNameMap.put(Integer.valueOf(14), "afterRenderPass");
            methodNameMap.put(Integer.valueOf(15), "beforeBlockRender");
            methodNameMap.put(Integer.valueOf(16), "afterBlockRender");
            methodNameMap.put(Integer.valueOf(17), "onRenderWorldLast");
            methodNameMap.put(Integer.valueOf(18), "onTextureLoadPre");
            methodNameMap.put(Integer.valueOf(19), "onTextureLoad");
            methodNameMap.put(Integer.valueOf(20), "getItemRenderer");
            methodNameMap.put(Integer.valueOf(30), "clear");
            methodNameMap.put(Integer.valueOf(40), "resetPool");
            methodNameMap.put(Integer.valueOf(50), "isLadder");
            methodNameMap.put(Integer.valueOf(51), "isBed");
            methodNameMap.put(Integer.valueOf(70), "setTextureDimensions");
            methodNameMap.put(Integer.valueOf(71), "preRegisterEffect");
            methodNameMap.put(Integer.valueOf(72), "onUpdateTextureEffect");
            methodNameMap.put(Integer.valueOf(73), "onTexturePackChange");
            methodNameMap.put(Integer.valueOf(80), "addEffect");
            methodNameMap.put(Integer.valueOf(90), "onEntityLivingSetAttackTarget");
            methodNameMap.put(Integer.valueOf(91), "onEntityLivingUpdate");
            methodNameMap.put(Integer.valueOf(92), "onEntityLivingAttacked");
            methodNameMap.put(Integer.valueOf(93), "onEntityLivingHurt");
            methodNameMap.put(Integer.valueOf(94), "onEntityLivingDeath");
            methodNameMap.put(Integer.valueOf(95), "onEntityLivingDrops");
            methodNameMap.put(Integer.valueOf(96), "onEntityLivingFall");
            methodNameMap.put(Integer.valueOf(97), "onEntityLivingJump");
            methodNameMap.put(Integer.valueOf(110), "clear");
        }

        return methodNameMap;
    }

    private static Map getFieldNameMap()
    {
        if (fieldNameMap == null)
        {
            fieldNameMap = new HashMap();
            fieldNameMap.put(Integer.valueOf(30), "cache");
            fieldNameMap.put(Integer.valueOf(60), "EQUIPPED");
            fieldNameMap.put(Integer.valueOf(100), "captureDrops");
            fieldNameMap.put(Integer.valueOf(101), "capturedDrops");
        }

        return fieldNameMap;
    }

    public static void callVoid(int i, Object aobj[])
    {
        try
        {
            Method method = getMethod(i);

            if (method == null)
            {
                return;
            }

            method.invoke(null, aobj);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static int callInt(int i, Object aobj[])
    {
        Integer integer = (Integer)call(i, aobj);
        return integer.intValue();
    }

    public static String callString(int i, Object aobj[])
    {
        return (String)call(i, aobj);
    }

    public static boolean callBoolean(int i, Object aobj[])
    {
        try
        {
            Method method = getMethod(i);

            if (method == null)
            {
                return false;
            }
            else
            {
                Boolean boolean1 = (Boolean)method.invoke(null, aobj);
                return boolean1.booleanValue();
            }
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }

        return false;
    }

    public static boolean callBoolean(Object obj, int i, Object aobj[])
    {
        try
        {
            Method method = getMethod(i);

            if (method == null)
            {
                return false;
            }
            else
            {
                Boolean boolean1 = (Boolean)method.invoke(obj, aobj);
                return boolean1.booleanValue();
            }
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }

        return false;
    }

    public static Object call(int i, Object aobj[])
    {
        try
        {
            Method method = getMethod(i);

            if (method == null)
            {
                return Boolean.valueOf(false);
            }
            else
            {
                Object obj = method.invoke(null, aobj);
                return obj;
            }
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }

        return null;
    }

    public static void callVoid(Object obj, int i, Object aobj[])
    {
        try
        {
            if (obj == null)
            {
                return;
            }

            Method method = getMethod(i);

            if (method == null)
            {
                return;
            }

            method.invoke(obj, aobj);
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }

    private static Method getMethod(int i)
    {
        Method method = methods[i];

        if (method == null)
        {
            if (methodsChecked[i])
            {
                return null;
            }

            methodsChecked[i] = true;
            method = findMethod(i);
            methods[i] = method;
        }

        return method;
    }

    private static Method findMethod(int i)
    {
        int j = i / 10;
        Class class1 = getClass(j);

        if (class1 == null)
        {
            return null;
        }

        String s = (String)getMethodNameMap().get(Integer.valueOf(i));

        if (s == null)
        {
            Config.log((new StringBuilder()).append("Method name not found for id: ").append(i).toString());
            return null;
        }

        Method amethod[] = class1.getMethods();

        for (int k = 0; k < amethod.length; k++)
        {
            Method method = amethod[k];

            if (method.getName().equals(s))
            {
                return method;
            }
        }

        Config.log((new StringBuilder()).append("Method not found: ").append(class1.getName()).append(".").append(s).toString());
        return null;
    }

    private static Field getField(int i)
    {
        Field field = fields[i];

        if (field == null)
        {
            if (fieldsChecked[i])
            {
                return null;
            }

            fieldsChecked[i] = true;
            field = findField(i);
            fields[i] = field;
        }

        return field;
    }

    private static Field findField(int i)
    {
        int j = i / 10;
        Class class1 = getClass(j);

        if (class1 == null)
        {
            return null;
        }

        String s = (String)getFieldNameMap().get(Integer.valueOf(i));

        if (s == null)
        {
            Config.log((new StringBuilder()).append("Field name not found for id: ").append(i).toString());
            return null;
        }

        try
        {
            Field field = class1.getDeclaredField(s);
            return field;
        }
        catch (SecurityException securityexception)
        {
            securityexception.printStackTrace();
        }
        catch (NoSuchFieldException nosuchfieldexception)
        {
            Config.log((new StringBuilder()).append("Field not found: ").append(class1.getName()).append(".").append(s).toString());
        }

        return null;
    }

    private static Class getClass(int i)
    {
        Class class1 = classes[i];

        if (class1 == null)
        {
            if (classesChecked[i])
            {
                return null;
            }

            classesChecked[i] = true;
            Object obj = getClassNameMap().get(Integer.valueOf(i));

            if (obj instanceof Class)
            {
                class1 = (Class)obj;
                classes[i] = class1;
                return class1;
            }

            String s = (String)obj;

            if (s == null)
            {
                Config.log((new StringBuilder()).append("Class name not found for id: ").append(i).toString());
                return null;
            }

            try
            {
                class1 = Class.forName(s);
                classes[i] = class1;
            }
            catch (ClassNotFoundException classnotfoundexception)
            {
                Config.log((new StringBuilder()).append("Class not present: ").append(s).toString());
            }
            catch (Throwable throwable)
            {
                throwable.printStackTrace();
            }
        }

        return class1;
    }

    public static boolean hasClass(int i)
    {
        Class class1 = getClass(i);
        return class1 != null;
    }

    public static boolean hasMethod(int i)
    {
        Method method = getMethod(i);
        return method != null;
    }

    public static Object getFieldValue(int i)
    {
        return getFieldValue(null, i);
    }

    public static Object getFieldValue(Object obj, int i)
    {
        try
        {
            Field field = getField(i);

            if (field == null)
            {
                return null;
            }
            else
            {
                Object obj1 = field.get(obj);
                return obj1;
            }
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }

        return null;
    }

    public static void setFieldValue(int i, Object obj)
    {
        setFieldValue(null, i, obj);
    }

    public static void setFieldValue(Object obj, int i, Object obj1)
    {
        try
        {
            Field field = getField(i);

            if (field == null)
            {
                return;
            }

            field.set(obj, obj1);
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }
}
