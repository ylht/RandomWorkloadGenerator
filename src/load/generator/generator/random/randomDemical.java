package load.generator.generator.random;

import java.util.Random;

public class randomDemical extends randomValue{
    private double min;
    private double max;
    private Random r=new Random();
    public randomDemical(double min,double max)
    {
        this.min=min;
        this.max=max;
    }

    @Override
    public String getValue()
    {
        return String.valueOf(r.nextDouble()*(max-min)+min);
    }

}
