package load.generator.template.tuple;

import java.util.Map;
import java.util.zip.ZipEntry;

/**
 * @author wangqingshuai
 */
public class TupleType {
    private String name;

    private Boolean keyOrNot = false;

    TupleType(String name) {
        this.name = name;
    }

    public void makeKey() {
        this.keyOrNot = true;
    }

    public String getValueType()
    {
        return null;
    }

    public String getTupleType() {
        return null;
    }

    public Object getMin()
    {
        return null;
    }

    public Object getMax()
    {
        return null;
    }

    public void setMin(int min){}

    public void setMax(int max){}
}
