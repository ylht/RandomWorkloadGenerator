package com.wqs.base.tuple;

public class TupleDouble extends TupleKind{
    private Boolean signedOrNot;
    private int intNum;
    private int pointNum;

    public TupleDouble()
    {
        super("double");
        signedOrNot=!(Math.random()<0.5);
        intNum=(int)(Math.random()*20);
        pointNum=(int)(Math.random()*10);
    }

    @Override
    public String getKindSql()
    {
        String sql="decimal("+String.valueOf(intNum)+","+String.valueOf(pointNum)+")";
        if (!signedOrNot)
        {
            sql += "UN";
        }
        sql +="UNSIGNED";
        return sql;
    }
}
