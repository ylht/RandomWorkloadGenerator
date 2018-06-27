
package load.generator.template;

import load.generator.base.format.ArrayToString;

/**
 * @author wangqingshuai
 */

public class SqlTemplate {
    public String selectTemplate(String[] selectAttributes)
    {
        return String.format("select %s \n", ArrayToString.toString(selectAttributes));
    }

    public String deleteTemplate()
    {
        return "delete \n";
    }

    public String updateTemplate(String tableName,String[] updateAttributes)
    {
        return String.format("update %s \nset %s \n", tableName,
                ArrayToString.toStringAsUpdate(updateAttributes));
    }
    public String insertTemplate(String tableName,String[] insertAttributes)
    {
        StringBuilder sqlMiddle= new StringBuilder();
        sqlMiddle.append("%s");
        for(int i=1;i<insertAttributes.length;i++)
        {
            sqlMiddle.append(",%s");
        }
        return String.format("insert into %s (%s) values (%s)", tableName,
                ArrayToString.toStringAsUpdate(insertAttributes),sqlMiddle);
    }
}