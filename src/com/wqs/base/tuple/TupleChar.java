package com.wqs.base.tuple;

import com.wqs.base.RandomControl;

import java.util.Random;

/**
 * @author wangqingshuai
 */

public class TupleChar extends TupleKind {
    private Boolean fixedOrNot;
    private int charNum;
    public TupleChar()  {
        super("char");
        fixedOrNot =RandomControl.getFixedOrNot();
        charNum=RandomControl.getTupleCharNum();
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
