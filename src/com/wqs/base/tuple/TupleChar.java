package com.wqs.base.tuple;

import java.util.Random;

/**
 * @author wangqingshuai
 */

public class TupleChar extends TupleKind {
    private Boolean fixedOrNot;
    private int charNum;
    public TupleChar()  {
        super("char");
        fixedOrNot =Math.random()<0.5;
        Random r=new Random();
        charNum=r.nextInt(129)+1;
    }

    @Override
    public String getKindSql()
    {
        String sql="";
        if(!fixedOrNot)
        {
            sql+="VAR";
        }
        sql+="CHAR("+String.valueOf(charNum)+")";
        return sql;
    }
}
