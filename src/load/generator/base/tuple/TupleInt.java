package load.generator.base.tuple;

import load.generator.base.random.RandomGenerateTableAttributesVaule;

/**
 * @author wangqingshuai
 */


public class TupleInt extends TupleType {
    private Boolean signedOrNot;

    public TupleInt() {
        super("int");
        signedOrNot = RandomGenerateTableAttributesVaule.intSignedOrNot();
    }

    @Override
    public String getTupleType() {
        String sql = "INT ";
        if (!signedOrNot) {
            sql += "UN";
        }
        sql += "SIGNED";
        return sql;
    }

}
