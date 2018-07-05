package load.generator.template.tuple;

import load.generator.generator.RandomGenerateTableAttributesVaule;

/**
 * @author wangqingshuai
 */

public class TupleChar extends TupleType {
    private Boolean fixedOrNot;
    private int charNum;

    public TupleChar() {
        super("char");
        fixedOrNot = RandomGenerateTableAttributesVaule.fixedOrNot();
        charNum = RandomGenerateTableAttributesVaule.tupleCharNum();
    }

    @Override
    public String getTupleType() {
        String sql = "";
        if (!fixedOrNot) {
            sql += "VAR";
        }
        sql += "CHAR(" + String.valueOf(charNum) + ")";
        return sql;
    }
}
