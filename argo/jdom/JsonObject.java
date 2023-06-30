package argo.jdom;

import java.util.*;

final class JsonObject extends JsonRootNode
{
    private final Map fields;

    JsonObject(Map par1Map)
    {
        fields = new HashMap(par1Map);
    }

    /**
     * return the fields associated with this node
     */
    public Map getFields()
    {
        return new HashMap(fields);
    }

    public JsonNodeType getType()
    {
        return JsonNodeType.OBJECT;
    }

    public String getText()
    {
        throw new IllegalStateException("Attempt to get text on a JsonNode without text.");
    }

    public List getElements()
    {
        throw new IllegalStateException("Attempt to get elements on a JsonNode without elements.");
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
            JsonObject jsonobject = (JsonObject)par1Obj;
            return fields.equals(jsonobject.fields);
        }
    }

    public int hashCode()
    {
        return fields.hashCode();
    }

    public String toString()
    {
        return (new StringBuilder()).append("JsonObject fields:[").append(fields).append("]").toString();
    }
}
