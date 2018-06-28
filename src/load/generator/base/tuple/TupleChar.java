package load.generator.base.tuple;

import load.generator.base.random.TableRandom;

/**
 * @author wangqingshuai
 */

public class TupleChar extends TupleType {
    private Boolean fixedOrNot;
    private int charNum;

    public TupleChar() {
        super("char");
        fixedOrNot = TableRandom.getFixedOrNot();
        charNum = TableRandom.getTupleCharNum();
    }

    @Override
    public String getKindSql() {
        String sql = "";
        if (!fixedOrNot) {
            sql += "VAR";
        }
        sql += "CHAR(" + String.valueOf(charNum) + ")";
        return sql;
    }
}
