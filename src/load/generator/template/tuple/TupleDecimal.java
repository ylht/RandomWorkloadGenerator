package load.generator.template.tuple;

import load.generator.generator.RandomGenerateTableAttributesVaule;

import java.util.Map;

/**
 * @author wangqingshuai
 */

public class TupleDecimal extends TupleType {
    private int intNum;
    private int pointNum;
    private double min;
    private double max;
    public TupleDecimal(double min, double range) {
        super("double");
        this.min=min;
        this.max=min+range;
        intNum = RandomGenerateTableAttributesVaule.tupleDoubleIntNum();
        pointNum = RandomGenerateTableAttributesVaule.tupleDoublePointNum();
    }

    @Override
    public String getValueType()
    {
        return "double";
    }

    @Override
    public String getTupleType() {
        return "decimal(" + String.valueOf(intNum) + "," + String.valueOf(pointNum) + ") ";
    }

    @Override
    public Object getMin()
    {
        return min;
    }
    @Override
    public Object getMax()
    {
        return max;
    }
}
