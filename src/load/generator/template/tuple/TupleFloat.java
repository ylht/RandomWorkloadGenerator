package load.generator.template.tuple;

import load.generator.generator.RandomGenerateTableAttributesVaule;

public class TupleFloat extends TupleType{
    private Boolean signedOrNot;
    private double min;
    private double max;
    public TupleFloat(double min,double range) {
        super("float");
        this.min=min;
        this.max=min+range;
    }
    @Override
    public String getValueType()
    {
        return "double";
    }
    @Override
    public String getTupleType() {
        return "FLOAT ";
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
