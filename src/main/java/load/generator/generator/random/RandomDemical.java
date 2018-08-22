package load.generator.generator.random;

import java.util.Random;

public class RandomDemical extends RandomValue {
    private double min;
    private double max;
    private Random r = new Random();

    public RandomDemical(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String getValue() {
        return String.valueOf(r.nextDouble() * (max - min) + min);
    }

}
