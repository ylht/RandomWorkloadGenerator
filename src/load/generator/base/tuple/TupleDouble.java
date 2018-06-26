package load.generator.base.tuple;

import load.generator.base.RandomControl;

/**
 * @author wangqingshuai
 */

public class TupleDouble extends TupleType {
    private Boolean signedOrNot;
    private int intNum;
    private int pointNum;

    public TupleDouble()
    {
        super("double");
        signedOrNot=RandomControl.getDoubleSignedOrNot();
        intNum= RandomControl.getTupleDoubleIntNum();
        pointNum= RandomControl.getTupleDoublePointNum();
    }

    @Override
    public String getKindSql()
    {
        String sql="decimal("+String.valueOf(intNum)+","+String.valueOf(pointNum)+") ";
        if (!signedOrNot)
        {
            sql += "UN";
        }
        sql +="SIGNED";
        return sql;
    }
}
