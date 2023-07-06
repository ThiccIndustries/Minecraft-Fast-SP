package argo.jdom;

import java.util.Arrays;

public final class JsonNodeSelectors
{
    private JsonNodeSelectors()
    {
    }

    public static JsonNodeSelector func_27349_a(Object par0ArrayOfObj[])
    {
        return chainOn(par0ArrayOfObj, new JsonNodeSelector(new JsonNodeSelectors_String()));
    }

    public static JsonNodeSelector func_27346_b(Object par0ArrayOfObj[])
    {
        return chainOn(par0ArrayOfObj, new JsonNodeSelector(new JsonNodeSelectors_Array()));
    }

    public static JsonNodeSelector func_27353_c(Object par0ArrayOfObj[])
    {
        return chainOn(par0ArrayOfObj, new JsonNodeSelector(new JsonNodeSelectors_Object()));
    }

    public static JsonNodeSelector func_27348_a(String par0Str)
    {
        return func_27350_a(JsonNodeFactories.aJsonString(par0Str));
    }

    public static JsonNodeSelector func_27350_a(JsonStringNode par0JsonStringNode)
    {
        return new JsonNodeSelector(new JsonNodeSelectors_Field(par0JsonStringNode));
    }

    public static JsonNodeSelector func_27351_b(String par0Str)
    {
        return func_27353_c(new Object[0]).with(func_27348_a(par0Str));
    }

    public static JsonNodeSelector func_27347_a(int par0)
    {
        return new JsonNodeSelector(new JsonNodeSelectors_Element(par0));
    }

    public static JsonNodeSelector func_27354_b(int par0)
    {
        return func_27346_b(new Object[0]).with(func_27347_a(par0));
    }

    private static JsonNodeSelector chainOn(Object par0ArrayOfObj[], JsonNodeSelector par1JsonNodeSelector)
    {
        JsonNodeSelector jsonnodeselector = par1JsonNodeSelector;

        for (int i = par0ArrayOfObj.length - 1; i >= 0; i--)
        {
            if (par0ArrayOfObj[i] instanceof Integer)
            {
                jsonnodeselector = chainedJsonNodeSelector(func_27354_b(((Integer)par0ArrayOfObj[i]).intValue()), jsonnodeselector);
                continue;
            }

            if (par0ArrayOfObj[i] instanceof String)
            {
                jsonnodeselector = chainedJsonNodeSelector(func_27351_b((String)par0ArrayOfObj[i]), jsonnodeselector);
            }
            else
            {
                throw new IllegalArgumentException((new StringBuilder()).append("Element [").append(par0ArrayOfObj[i]).append("] of path elements").append(" [").append(Arrays.toString(par0ArrayOfObj)).append("] was of illegal type [").append(par0ArrayOfObj[i].getClass().getCanonicalName()).append("]; only Integer and String are valid.").toString());
            }
        }

        return jsonnodeselector;
    }

    private static JsonNodeSelector chainedJsonNodeSelector(JsonNodeSelector par0JsonNodeSelector, JsonNodeSelector par1JsonNodeSelector)
    {
        return new JsonNodeSelector(new ChainedFunctor(par0JsonNodeSelector, par1JsonNodeSelector));
    }
}
