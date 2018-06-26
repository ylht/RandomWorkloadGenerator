package com.wqs.base.tuple;

/**
 * @author wangqingshuai
 */
public class TupleKind {
    private String name;

    private Boolean keyOrNot=false;

    TupleKind(String name)
    {
        this.name=name;
    }

    public void makeKey()
    {
        this.keyOrNot=true;
    }

    public String getKindSql()
    {
        return null;
    }

}
