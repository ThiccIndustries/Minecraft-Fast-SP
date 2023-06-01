package argo.jdom;

import argo.saj.JsonListener;
import java.util.Stack;

final class JsonListenerToJdomAdapter implements JsonListener
{
    private final Stack stack = new Stack();
    private JsonNodeBuilder root;

    JsonListenerToJdomAdapter()
    {
    }

    JsonRootNode getDocument()
    {
        return (JsonRootNode)root.buildNode();
    }

    public void startDocument()
    {
    }

    public void endDocument()
    {
    }

    public void startArray()
    {
        JsonArrayNodeBuilder jsonarraynodebuilder = JsonNodeBuilders.anArrayBuilder();
        addRootNode(jsonarraynodebuilder);
        stack.push(new JsonListenerToJdomAdapter_Array(this, jsonarraynodebuilder));
    }

    public void endArray()
    {
        stack.pop();
    }

    public void startObject()
    {
        JsonObjectNodeBuilder jsonobjectnodebuilder = JsonNodeBuilders.anObjectBuilder();
        addRootNode(jsonobjectnodebuilder);
        stack.push(new JsonListenerToJdomAdapter_Object(this, jsonobjectnodebuilder));
    }

    public void endObject()
    {
        stack.pop();
    }

    public void startField(String par1Str)
    {
        JsonFieldBuilder jsonfieldbuilder = JsonFieldBuilder.aJsonFieldBuilder().withKey(JsonNodeBuilders.func_27254_b(par1Str));
        ((JsonListenerToJdomAdapter_NodeContainer)stack.peek()).addField(jsonfieldbuilder);
        stack.push(new JsonListenerToJdomAdapter_Field(this, jsonfieldbuilder));
    }

    public void endField()
    {
        stack.pop();
    }

    public void numberValue(String par1Str)
    {
        addValue(JsonNodeBuilders.func_27250_a(par1Str));
    }

    public void trueValue()
    {
        addValue(JsonNodeBuilders.func_27251_b());
    }

    public void stringValue(String par1Str)
    {
        addValue(JsonNodeBuilders.func_27254_b(par1Str));
    }

    public void falseValue()
    {
        addValue(JsonNodeBuilders.func_27252_c());
    }

    public void nullValue()
    {
        addValue(JsonNodeBuilders.func_27248_a());
    }

    private void addRootNode(JsonNodeBuilder par1JsonNodeBuilder)
    {
        if (root == null)
        {
            root = par1JsonNodeBuilder;
        }
        else
        {
            addValue(par1JsonNodeBuilder);
        }
    }

    private void addValue(JsonNodeBuilder par1JsonNodeBuilder)
    {
        ((JsonListenerToJdomAdapter_NodeContainer)stack.peek()).addNode(par1JsonNodeBuilder);
    }
}
