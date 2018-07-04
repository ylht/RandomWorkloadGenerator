package load.generator.base.tuple;

/**
 * @author wangqingshuai
 */

public class TupleDate extends TupleType {
    public TupleDate() {
        super("date");
    }

    @Override
    public String getTupleType() {
        return "DATE";
    }
}
