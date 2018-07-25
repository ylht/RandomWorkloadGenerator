package load.generator.generator.random;

import java.util.Random;

public class randomInt extends randomValue{
    private int min;
    private int max;
    private int current;
    private Random r=new Random();

    public randomInt(int min,int max)
    {
        this.min=min;
        this.max=max;
    }

    public String getNext()
    {
        if(current>max)
        {
            current=min;
            return String.valueOf(current);
        }
        return String.valueOf(current++);
    }

    public String getSameValue()
    {
        if(current-1>min)
        {
            return String.valueOf(current-1);
        }
        return String.valueOf(min);
    }
    @Override
    public String getValue()
    {
        return String.valueOf(r.nextInt(max-min+1)+min);
    }
}
