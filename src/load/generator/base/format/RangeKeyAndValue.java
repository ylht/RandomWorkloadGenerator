package load.generator.base.format;

/**
 * @author wangqingshuai
 */

public class RangeKeyAndValue {
    private String key;
    private Object leftValue;
    private Object rightValue;

    public RangeKeyAndValue(String key,Object leftValue,Object rightValue)
    {
        this.key=key;
        this.leftValue=leftValue;
        this.rightValue=rightValue;
    }

    @Override
    public String toString()
    {
        return String.format("%s between %s and %s",key,
                leftValue.toString(),rightValue.toString());
    }
}
