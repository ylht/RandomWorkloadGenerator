package com.wqs.base.tuple;

import java.util.Random;

/**
 * @author wangqingshuai
 */

public class TupleDouble extends TupleKind{
    private Boolean signedOrNot;
    private int intNum;
    private int pointNum;

    public TupleDouble()
    {
        super("double");
        signedOrNot=Math.random()<0.5;
        Random r = new Random();
        intNum= r.nextInt(20)+1;
        pointNum= r.nextInt(21);
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
