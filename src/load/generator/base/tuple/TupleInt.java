package load.generator.base.tuple;

import load.generator.base.RandomControl;

/**
 * @author wangqingshuai
 */


public class TupleInt extends TupleType {
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
