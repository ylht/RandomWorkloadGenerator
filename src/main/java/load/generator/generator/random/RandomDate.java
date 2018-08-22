package load.generator.generator.random;

import java.text.SimpleDateFormat;

public class RandomDate extends RandomValue {
    private boolean forTransaction;

    public RandomDate(boolean forTransaction) {
        this.forTransaction = forTransaction;
    }

    @Override
    public String getValue() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (forTransaction) {
            return df.format(System.currentTimeMillis());
        } else {
            return '\'' + df.format(System.currentTimeMillis()) + '\'';
        }

    }
}

