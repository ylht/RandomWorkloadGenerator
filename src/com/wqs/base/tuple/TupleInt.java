package com.wqs.base.tuple;

public class TupleInt extends TupleKind {
    private Boolean signedOrNot;
    public TupleInt() {
        super("int");
        signedOrNot=!(Math.random()<0.5);
    }

    @Override
    public String getKindSql()
    {
        String sql="INT ";
        if (!signedOrNot)
        {
            sql += "UN";
        }
        sql +="UNSIGNED";
        return sql;
    }

}
