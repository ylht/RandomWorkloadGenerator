package load.generator.template.tuple;

import load.generator.generator.RandomGenerateTableAttributesVaule1;

/**
 * @author wangqingshuai
 */

public class TupleDecimal extends TupleType {
    private int totalNum;
    private int pointNum;
    private double min;
    private double max;

    public TupleDecimal(double min, double range) {
        super("double");
        this.min = min;
        this.max = min + range;
        totalNum = RandomGenerateTableAttributesVaule1.tupleDoubleIntNum();
        pointNum = RandomGenerateTableAttributesVaule1.tupleDoublePointNum();
        if (pointNum > totalNum) {
            totalNum = pointNum;
        }
    }

    @Override
    public String getValueType() {
        return "double";
    }

    @Override
    public String getTupleType() {
        return "decimal(" + String.valueOf(totalNum) + "," + String.valueOf(pointNum) + ") ";
    }

    @Override
    public Object getMin() {
        return min;
    }

    @Override
    public Object getMax() {
        double t = Math.pow(10, totalNum - pointNum) - 1;
        if (max > t) {
            return t;
        }
        return max;
    }
}
