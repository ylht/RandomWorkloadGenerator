package load.generator.generator.random;

import java.util.Random;

public class RandomInt extends RandomValue {
    private int min;
    private int max;
    private Random r = new Random();

    public RandomInt(int min, int max) {
        this.min = min;
        this.max = max;
    }


    public int getMax() {
        return max;
    }


    public int getMin(){return min;}

    @Override
    public String getValue() {
        return String.valueOf(r.nextInt(max - min) + min);
    }
}
