package load.generator.template.tuple;

import load.generator.generator.RandomGenerateTableAttributesVaule;

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
        RandomGenerateTableAttributesVaule rgta = RandomGenerateTableAttributesVaule.getInstance();
        this.min = min;
        this.max = min + range;
        totalNum = rgta.tupleDoubleIntNum();
        pointNum = rgta.tupleDoublePointNum();
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
