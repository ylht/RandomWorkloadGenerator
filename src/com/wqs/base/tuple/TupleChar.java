package com.wqs.base.tuple;

public class TupleChar extends TupleKind {
    private Boolean fixedOrNot;
    private int charNum;
    public TupleChar()  {
        super("char");
        fixedOrNot = !(Math.random() < 0.5);
        charNum=(int)(Math.random() * 120);
    }
}
