package com.wqs.base.tuple;

/**
 * @author wangqingshuai
 */

public class TupleDate extends TupleKind {
    public TupleDate() {
        super("date");
    }

    @Override
    public String getKindSql()
    {
        return "DATE";
    }
}
