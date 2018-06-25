package com.wqs.base;

/**
 * @author wangqingshuai
 */

public class ArrayToString {
    public static String toString(Object[] values)
    {
        int attributesNum=values.length;
        StringBuilder attributesStr= new StringBuilder(values[0].toString());
        for(int i=1;i<attributesNum;i++)
        {
            attributesStr.append(',').append(values[i].toString());
        }
        return attributesStr.toString();
    }

    public static String toStringWithAnd(Object[] values)
    {
        int attributesNum=values.length;
        StringBuilder attributesStr= new StringBuilder(values[0].toString());
        for(int i=1;i<attributesNum;i++)
        {
            attributesStr.append("and").append(values[i].toString());
        }
        return attributesStr.toString();
    }
}
