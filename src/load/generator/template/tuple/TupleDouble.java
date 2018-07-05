package load.generator.template.tuple;

import load.generator.generator.RandomGenerateTableAttributesVaule;

/**
 * @author wangqingshuai
 */

public class TupleDouble extends TupleType {
    private Boolean signedOrNot;
    private int intNum;
    private int pointNum;

    public TupleDouble() {
        super("double");
        signedOrNot = RandomGenerateTableAttributesVaule.doubleSignedOrNot();
        intNum = RandomGenerateTableAttributesVaule.tupleDoubleIntNum();
        pointNum = RandomGenerateTableAttributesVaule.tupleDoublePointNum();
    }

    @Override
    public String getTupleType() {
        String sql = "decimal(" + String.valueOf(intNum) + "," + String.valueOf(pointNum) + ") ";
        if (!signedOrNot) {
            sql += "UN";
        }
        sql += "SIGNED";
        return sql;
    }
}
