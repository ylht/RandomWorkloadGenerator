
package load.generator.template.sql;

import load.generator.base.ArrayToString;
import load.generator.base.KeyAndValue;
import load.generator.base.RangeKeyAndValue;

/**
 * @author wangqingshuai
 */

public class SelectTemplate {
    public String singleRead(String[] selectAttributes,
                             String tableName,
                             KeyAndValue[] keyAndValues)
    {
        String selectAttributesStr=ArrayToString.toString(selectAttributes);
        String keyAndValueStr=ArrayToString.toStringWithAnd(keyAndValues);
        return String.format("select %s from %s where %s;",
                selectAttributesStr,tableName,keyAndValueStr);
    }

    public String rangeRead(String[] selectAttributes,
                            String tableName,
                            RangeKeyAndValue[] rangeKeyAndValues,
                            KeyAndValue[] keyAndValues)
    {
        String selectAttributesStr=ArrayToString.toString(selectAttributes);
        String rangeKeyAndValuesStr=ArrayToString.toStringWithAnd(rangeKeyAndValues);
        String result=String.format("select %s from %s where %s",
                selectAttributesStr,tableName,rangeKeyAndValuesStr);
        if(keyAndValues.length>0)
        {
            result+="and "+ArrayToString.toStringWithAnd(keyAndValues);
        }
        return result+';';
    }

    public String joinRead(String[] selectAttributes,
                           String tableFirst,String tableSecond,
                           String valueFirst,String valueSecond,
                           KeyAndValue[] keyAndValues)
    {
        String selectAttributesStr=ArrayToString.toString(selectAttributes);
        String keyAndValueStr=ArrayToString.toStringWithAnd(keyAndValues);
        return String.format("select %s from %s , %s  where %s.%s = %s.%s and %s;",
                selectAttributesStr,tableFirst,tableSecond,
                tableFirst,valueFirst,
                tableSecond,valueSecond,
                keyAndValueStr);
    }

}
