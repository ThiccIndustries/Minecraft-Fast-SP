package argo.jdom;

class JsonListenerToJdomAdapter_Object implements JsonListenerToJdomAdapter_NodeContainer
{
    final JsonObjectNodeBuilder nodeBuilder;
    final JsonListenerToJdomAdapter listenerToJdomAdapter;

    JsonListenerToJdomAdapter_Object(JsonListenerToJdomAdapter par1JsonListenerToJdomAdapter, JsonObjectNodeBuilder par2JsonObjectNodeBuilder)
    {
        listenerToJdomAdapter = par1JsonListenerToJdomAdapter;
        nodeBuilder = par2JsonObjectNodeBuilder;
    }

    public void addNode(JsonNodeBuilder par1JsonNodeBuilder)
    {
        throw new RuntimeException("Coding failure in Argo:  Attempt to add a node to an object.");
    }

    public void addField(JsonFieldBuilder par1JsonFieldBuilder)
    {
        nodeBuilder.withFieldBuilder(par1JsonFieldBuilder);
    }
}
