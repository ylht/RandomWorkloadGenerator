package load.generator.generator.random;

import load.generator.generator.RandomGenerator;
import load.generator.template.CharTemplate;

public class RandomChar extends RandomValue {
    private int min;
    private int max;
    private boolean forTransaction;
    private CharTemplate cvt;
    private RandomGenerator rg = new RandomGenerator(20);

    public RandomChar(CharTemplate cvt) {
        this.cvt = cvt;
    }

    public RandomChar(int min, int max, boolean forTransaction) {
        this.min = min;
        this.max = max;
        this.forTransaction = forTransaction;
    }

    @Override
    public String getValue() {
        if (cvt != null) {
            return cvt.getWord();
        } else {
            if (forTransaction) {
                return rg.astring(min, max);
            } else {
                return '\'' + rg.astring(min, max) + '\'';
            }
        }
    }
}
