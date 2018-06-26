package load.generator.base;

import java.util.Random;

public class RandomControl {
    private static Random r=new Random();

    public static int getTableNum()
    {
        return r.nextInt(10)+2;
    }

    public static int getTableIntNum()
    {
        return r.nextInt(10)+2;
    }

    public static int getTableDoubleNum()
    {
        return r.nextInt(10)+2;
    }

    public static int getTableCharNum()
    {
        return r.nextInt(10)+2;
    }

    public static int getTableDateNum()
    {
        return r.nextInt(10)+2;
    }

    public static int getTableKeyNum(int max){return r.nextInt(max-1)+1;}
    public static int getTupleCharNum()
    {
        return r.nextInt(10)+2;
    }

    public static int getTupleDoubleIntNum()
    {
        return r.nextInt(10)+2;
    }

    public static int getTupleDoublePointNum()
    {
        return r.nextInt(10)+2;
    }

    public static Boolean getFixedOrNot()
    {
        return Math.random()<0.5;
    }

    public static Boolean getIntSignedOrNot()
    {
        return Math.random()<0.5;
    }

    public static Boolean getDoubleSignedOrNot()
    {
        return Math.random()<0.5;
    }

}
