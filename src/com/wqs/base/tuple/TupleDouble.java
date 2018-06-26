package com.wqs.base.tuple;

import com.wqs.base.RandomControl;

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
        signedOrNot=RandomControl.getDoubleSignedOrNot();
        intNum= RandomControl.getTupleDoubleIntNum();
        pointNum= RandomControl.getTupleDoublePointNum();
    }

    @Override
    public String getKindSql()
    {
        String sql="decimal("+String.valueOf(intNum)+","+String.valueOf(pointNum)+") ";
        if (!signedOrNot)
        {
            sql += "UN";
        }
        sql +="SIGNED";
        return sql;
    }
}
