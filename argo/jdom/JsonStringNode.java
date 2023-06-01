package argo.jdom;

import java.util.List;
import java.util.Map;

public final class JsonStringNode extends JsonNode implements Comparable
{
    private final String value;

    JsonStringNode(String par1Str)
    {
        if (par1Str == null)
        {
            throw new NullPointerException("Attempt to construct a JsonString with a null value.");
        }
        else
        {
            value = par1Str;
            return;
        }
    }

    public JsonNodeType getType()
    {
        return JsonNodeType.STRING;
    }

    public String getText()
    {
        return value;
    }

    /**
     * return the fields associated with this node
     */
    public Map getFields()
    {
        throw new IllegalStateException("Attempt to get fields on a JsonNode without fields.");
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
            JsonStringNode jsonstringnode = (JsonStringNode)par1Obj;
            return value.equals(jsonstringnode.value);
        }
    }

    public int hashCode()
    {
        return value.hashCode();
    }

    public String toString()
    {
        return (new StringBuilder()).append("JsonStringNode value:[").append(value).append("]").toString();
    }

    public int func_27223_a(JsonStringNode par1JsonStringNode)
    {
        return value.compareTo(par1JsonStringNode.value);
    }

    public int compareTo(Object par1Obj)
    {
        return func_27223_a((JsonStringNode)par1Obj);
    }
}
