package load.generator.base.tuple;

import load.generator.base.random.TableRandom;

/**
 * @author wangqingshuai
 */


public class TupleInt extends TupleType {
    private Boolean signedOrNot;
    public TupleInt() {
        super("int");
        signedOrNot=TableRandom.getIntSignedOrNot();
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
