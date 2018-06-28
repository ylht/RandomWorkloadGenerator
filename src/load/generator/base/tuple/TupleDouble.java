package load.generator.base.tuple;

import load.generator.base.random.TableRandom;

/**
 * @author wangqingshuai
 */

public class TupleDouble extends TupleType {
    private Boolean signedOrNot;
    private int intNum;
    private int pointNum;

    public TupleDouble() {
        super("double");
        signedOrNot = TableRandom.getDoubleSignedOrNot();
        intNum = TableRandom.getTupleDoubleIntNum();
        pointNum = TableRandom.getTupleDoublePointNum();
    }

    @Override
    public String getKindSql() {
        String sql = "decimal(" + String.valueOf(intNum) + "," + String.valueOf(pointNum) + ") ";
        if (!signedOrNot) {
            sql += "UN";
        }
        sql += "SIGNED";
        return sql;
    }
}
