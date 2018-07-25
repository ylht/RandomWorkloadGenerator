package load.generator.template.tuple;

/**
 * @author wangqingshuai
 */

public class TupleDate extends TupleType {
    public TupleDate() {
        super("date");
    }

    @Override
    public String getTupleType() {
        return "TIMESTAMP";
    }

    @Override
    public String getValueType()
    {
        return "date";
    }
}
