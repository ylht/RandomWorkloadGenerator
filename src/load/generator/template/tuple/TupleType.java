package load.generator.template.tuple;

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

    public String getTupleType() {
        return null;
    }

}
