package load.generator.generator.random;

import java.util.Random;

public class RandomInt extends RandomValue {
    private int min;
    private int max;
    private int current;
    private boolean getMin = false;
    private Random r = new Random();

    public RandomInt(int min, int max) {
        this.min = min;
        this.max = max;
        this.current = min;
    }

    public String getNext() {
        if (current > max) {
            current = min;
            return String.valueOf(current);
        }
        if (!getMin && current == min) {
            getMin = true;
            return String.valueOf(min);
        }
        return String.valueOf(++current);
    }

    public String getSameValue() {
        getMin = true;
        return String.valueOf(current);
    }

    @Override
    public String getValue() {
        return String.valueOf(r.nextInt(max - min + 1) + min);
    }
}
