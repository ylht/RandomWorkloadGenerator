package load.generator.generator.random;

import load.generator.template.CharTemplate;
import load.generator.utils.RandomGenerator;

public class RandomChar extends RandomValue {
    private int min;
    private int max;
    private boolean forTransaction;
    private CharTemplate cvt;
    private RandomInt ri;
    private RandomGenerator rg = new RandomGenerator(20);

    public RandomChar(CharTemplate cvt, boolean forTransaction) {
        this.cvt = cvt;
    }

    public RandomChar(int min, int max, boolean forTransaction) {
        this.min = min;
        this.max = max;
        this.forTransaction = forTransaction;
    }

    public RandomChar(int len, RandomInt ri) {
        this.ri = ri;
        this.max = len;
    }


    @Override
    public String getValue() {
        if (cvt != null) {
            return cvt.getWord();
        } else if (ri != null) {
            String t = String.valueOf(ri.getSameValue());
            if (t.length() > max) {
                return t.substring(0, t.length() - max);
            } else {
                StringBuilder str = new StringBuilder();
                for (int i = 0; i < max - t.length(); i++) {
                    str.append('0');
                }
                return str.toString() + ri.getSameValue();
            }
        } else {
            if (forTransaction) {
                return rg.astring(min, max);
            } else {
                return '\'' + rg.astring(min, max) + '\'';
            }
        }
    }
}
