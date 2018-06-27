package load.generator.base.format;

/**
 * @author wangqingshuai
 */

public class ArrayToString {

    private static String subToString(Object[] values,Boolean forUpdate)
    {
        String prefix="";
        if(forUpdate)
        {
            prefix="= %s";
        }
        int attributesNum=values.length;
        StringBuilder attributesStr= new StringBuilder(values[0].toString());
        attributesStr.append(prefix);
        for(int i=1;i<attributesNum;i++)
        {
            attributesStr.append(',').append(values[i].toString()).append(prefix);
        }
        return attributesStr.toString();
    }
    public static String toString(Object[] values)
    {
        return subToString(values,false);
    }

    public static String toStringAsUpdate(Object[] values)
    {
       return subToString(values,true);
    }
}
