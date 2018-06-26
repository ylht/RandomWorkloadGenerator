package com.wqs.base;

/**
 * @author wangqingshuai
 */

public class KeyAndValue {
    private String key;
    private Object value;

    public KeyAndValue(String key,Object value)
    {
        this.key=key;
        this.value=value;
    }

    @Override
    public String toString()
    {
        return String.format("%s = %s",key,value.toString());
    }
}
