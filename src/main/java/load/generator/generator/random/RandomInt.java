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

    public boolean getNext() {
        current++;
        if (current >= max) {
            current=min;
            return false;
        }
        else
        {
            return true;
        }
    }

    public int getRange()
    {
        return max-min;
    }

    public String getKeyValue() {
        return String.valueOf(current);
    }

    @Override
    public String getValue() {
        return String.valueOf(r.nextInt(max - min) + min);
    }
}
