package argo.jdom;

class JsonListenerToJdomAdapter_Array implements JsonListenerToJdomAdapter_NodeContainer
{
    final JsonArrayNodeBuilder nodeBuilder;
    final JsonListenerToJdomAdapter listenerToJdomAdapter;

    JsonListenerToJdomAdapter_Array(JsonListenerToJdomAdapter par1JsonListenerToJdomAdapter, JsonArrayNodeBuilder par2JsonArrayNodeBuilder)
    {
        listenerToJdomAdapter = par1JsonListenerToJdomAdapter;
        nodeBuilder = par2JsonArrayNodeBuilder;
    }

    public void addNode(JsonNodeBuilder par1JsonNodeBuilder)
    {
        nodeBuilder.withElement(par1JsonNodeBuilder);
    }

    public void addField(JsonFieldBuilder par1JsonFieldBuilder)
    {
        throw new RuntimeException("Coding failure in Argo:  Attempt to add a field to an array.");
    }
}
