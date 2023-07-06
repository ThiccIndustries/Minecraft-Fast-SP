package argo.jdom;

import argo.format.CompactJsonFormatter;
import argo.format.JsonFormatter;

public final class JsonNodeDoesNotMatchPathElementsException extends JsonNodeDoesNotMatchJsonNodeSelectorException
{
    private static final JsonFormatter JSON_FORMATTER = new CompactJsonFormatter();

    static JsonNodeDoesNotMatchPathElementsException jsonNodeDoesNotMatchPathElementsException(JsonNodeDoesNotMatchChainedJsonNodeSelectorException par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException, Object par1ArrayOfObj[], JsonRootNode par2JsonRootNode)
    {
        return new JsonNodeDoesNotMatchPathElementsException(par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException, par1ArrayOfObj, par2JsonRootNode);
    }

    private JsonNodeDoesNotMatchPathElementsException(JsonNodeDoesNotMatchChainedJsonNodeSelectorException par1JsonNodeDoesNotMatchChainedJsonNodeSelectorException, Object par2ArrayOfObj[], JsonRootNode par3JsonRootNode)
    {
        super(formatMessage(par1JsonNodeDoesNotMatchChainedJsonNodeSelectorException, par2ArrayOfObj, par3JsonRootNode));
    }

    private static String formatMessage(JsonNodeDoesNotMatchChainedJsonNodeSelectorException par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException, Object par1ArrayOfObj[], JsonRootNode par2JsonRootNode)
    {
        return (new StringBuilder()).append("Failed to find ").append(par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException.failedNode.toString()).append(" at [").append(JsonNodeDoesNotMatchChainedJsonNodeSelectorException.getShortFormFailPath(par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException.failPath)).append("] while resolving [").append(commaSeparate(par1ArrayOfObj)).append("] in ").append(JSON_FORMATTER.format(par2JsonRootNode)).append(".").toString();
    }

    private static String commaSeparate(Object par0ArrayOfObj[])
    {
        StringBuilder stringbuilder = new StringBuilder();
        boolean flag = true;
        Object aobj[] = par0ArrayOfObj;
        int i = aobj.length;

        for (int j = 0; j < i; j++)
        {
            Object obj = aobj[j];

            if (!flag)
            {
                stringbuilder.append(".");
            }

            flag = false;

            if (obj instanceof String)
            {
                stringbuilder.append("\"").append(obj).append("\"");
            }
            else
            {
                stringbuilder.append(obj);
            }
        }

        return stringbuilder.toString();
    }
}
