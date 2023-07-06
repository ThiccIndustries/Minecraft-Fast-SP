package argo.jdom;

import java.util.*;

final class JsonArray extends JsonRootNode
{
    private final List elements;

    JsonArray(Iterable par1Iterable)
    {
        elements = asList(par1Iterable);
    }

    public JsonNodeType getType()
    {
        return JsonNodeType.ARRAY;
    }

    public List getElements()
    {
        return new ArrayList(elements);
    }

    public String getText()
    {
        throw new IllegalStateException("Attempt to get text on a JsonNode without text.");
    }

    /**
     * return the fields associated with this node
     */
    public Map getFields()
    {
        throw new IllegalStateException("Attempt to get fields on a JsonNode without fields.");
    }

    public boolean equals(Object par1Obj)
    {
        if (this == par1Obj)
        {
            return true;
        }

        if (par1Obj == null || getClass() != par1Obj.getClass())
        {
            return false;
        }
        else
        {
            JsonArray jsonarray = (JsonArray)par1Obj;
            return elements.equals(jsonarray.elements);
        }
    }

    public int hashCode()
    {
        return elements.hashCode();
    }

    public String toString()
    {
        return (new StringBuilder()).append("JsonArray elements:[").append(elements).append("]").toString();
    }

    private static List asList(Iterable par0Iterable)
    {
        return new JsonArray_NodeList(par0Iterable);
    }
}
