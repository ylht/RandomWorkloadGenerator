package com.wqs.base.tuple;

import com.wqs.base.RandomControl;

/**
 * @author wangqingshuai
 */


public class TupleInt extends TupleKind {
    private Boolean signedOrNot;
    public TupleInt() {
        super("int");
        signedOrNot=RandomControl.getIntSignedOrNot();
    }

    @Override
    public String getKindSql()
    {
        String sql="INT ";
        if (!signedOrNot)
        {
            sql += "UN";
        }
        sql +="SIGNED";
        return sql;
    }

}
