package argo.format;

import argo.jdom.*;
import java.io.*;
import java.util.*;

public final class CompactJsonFormatter implements JsonFormatter
{
    public CompactJsonFormatter()
    {
    }

    public String format(JsonRootNode par1JsonRootNode)
    {
        StringWriter stringwriter = new StringWriter();

        try
        {
            format(par1JsonRootNode, stringwriter);
        }
        catch (IOException ioexception)
        {
            throw new RuntimeException("Coding failure in Argo:  StringWriter gave an IOException", ioexception);
        }

        return stringwriter.toString();
    }

    public void format(JsonRootNode par1JsonRootNode, Writer par2Writer) throws IOException
    {
        formatJsonNode(par1JsonRootNode, par2Writer);
    }

    private void formatJsonNode(JsonNode par1JsonNode, Writer par2Writer) throws IOException
    {
        boolean flag = true;

        switch (CompactJsonFormatter_JsonNodeType.enumJsonNodeTypeMappingArray[par1JsonNode.getType().ordinal()])
        {
            case 1:
                par2Writer.append('[');
                JsonNode jsonnode;

                for (Iterator iterator = par1JsonNode.getElements().iterator(); iterator.hasNext(); formatJsonNode(jsonnode, par2Writer))
                {
                    jsonnode = (JsonNode)iterator.next();

                    if (!flag)
                    {
                        par2Writer.append(',');
                    }

                    flag = false;
                }

                par2Writer.append(']');
                break;

            case 2:
                par2Writer.append('{');
                JsonStringNode jsonstringnode;

                for (Iterator iterator1 = (new TreeSet(par1JsonNode.getFields().keySet())).iterator(); iterator1.hasNext(); formatJsonNode((JsonNode)par1JsonNode.getFields().get(jsonstringnode), par2Writer))
                {
                    jsonstringnode = (JsonStringNode)iterator1.next();

                    if (!flag)
                    {
                        par2Writer.append(',');
                    }

                    flag = false;
                    formatJsonNode(((JsonNode)(jsonstringnode)), par2Writer);
                    par2Writer.append(':');
                }

                par2Writer.append('}');
                break;

            case 3:
                par2Writer.append('"').append((new JsonEscapedString(par1JsonNode.getText())).toString()).append('"');
                break;

            case 4:
                par2Writer.append(par1JsonNode.getText());
                break;

            case 5:
                par2Writer.append("false");
                break;

            case 6:
                par2Writer.append("true");
                break;

            case 7:
                par2Writer.append("null");
                break;

            default:
                throw new RuntimeException((new StringBuilder()).append("Coding failure in Argo:  Attempt to format a JsonNode of unknown type [").append(par1JsonNode.getType()).append("];").toString());
        }
    }
}
