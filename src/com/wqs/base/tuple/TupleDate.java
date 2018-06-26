package com.wqs.base.tuple;

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
