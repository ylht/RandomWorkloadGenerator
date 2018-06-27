package load.generator.base.random;

import java.util.Random;

public class SqlRandom {
    private static Random r=new Random();

    public static int getSelectNum()
    {
        return r.nextInt(10)+2;
    }

    public static int getSelectAttributesNum(int maxNum)
    {
        return r.nextInt(maxNum-1)+1;
    }

    public static Boolean getSelectWithJoin()
    {
        return Math.random()<0.5;
    }

    public static int getConditionNum(int maxNum)
    {
        if(maxNum==1)
        {
            return 1;
        }
        else{
            return r.nextInt(maxNum-1)+1;
        }

    }

    public static int getUpdateNum()
    {
        return r.nextInt(10)+2;
    }

    public static int getUpdateAttributesNum()
    {
        return r.nextInt(10)+2;
    }

    public static int getInsertNum()
    {
        return r.nextInt(10)+2;
    }

    public static int getInsertAttributesNum()
    {
        return r.nextInt(10)+2;
    }

    public static int getDeleteNum()
    {
        return r.nextInt(10)+2;
    }

}
