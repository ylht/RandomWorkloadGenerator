package load.generator.template.tuple;

/**
 * @author wangqingshuai
 */


public class TupleInt extends TupleType {
    private int min;
    private int max;

    public TupleInt(int min, int range) {
        super("int");
        this.min = min;
        this.max = min + range;
    }

    @Override
    public String getValueType() {
        return "int";
    }

    @Override
    public String getTupleType() {
        return "INT ";
    }

    @Override
    public Object getMin() {
        return min;
    }

    @Override
    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public Object getMax() {
        return max;
    }

    @Override
    public void setMax(int max) {
        this.max = max;
    }
}
