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
        return "TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00'";
    }

    @Override
    public String getValueType()
    {
        return "date";
    }
}
