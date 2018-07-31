package load.generator.generator;

import java.util.Random;
import java.lang.Math;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * @author wangqingshuai
 */

public class RandomGenerateSqlAttributesValue {
    private static Random r = new Random();
    public enum sqlType{SELECT,UPDATE,INSERT};

    public static int tranNum()
    {
        return r.nextInt(5)+4;
    }

    public static int selectNum() {
        return r.nextInt(3) + 2;
    }

    public static int randomTable(int totalNum) {
        return r.nextInt(totalNum);
    }


    public static int[] selectAttributesNum(int[] maxNum) {
        int []attNum=new int[4];
        double t=r.nextDouble();
        if(t<0.32)
        {
            attNum[0]=0;
        }
        else if (t<0.75)
        {
            attNum[0]=min(maxNum[0],1);
        }
        else if(t<0.86)
        {
            attNum[0]=min(maxNum[0],2);
        }
        else if(t<0.895)
        {
            attNum[0]=min(maxNum[0],3);
        }
        else
        {
            attNum[0]=min(maxNum[0],r.nextInt(20)+3);
        }
        t=r.nextDouble();
        if(t<0.59)
        {
            attNum[1]=0;
        }
        else if(t<0.93)
        {
            attNum[1]=min(attNum[1],1);
        }
        else if(t<0.965)
        {
            attNum[1]=min(attNum[1],2);
        }
        else
        {
            attNum[1]=min(attNum[1],4);
        }
        t=r.nextDouble();
        if(t<0.57)
        {
            attNum[2]=0;
        }
        else if(t<0.77)
        {
            attNum[2]=min(1,maxNum[2]);
        }
        else if(t<0.82)
        {
            attNum[2]=min(2,maxNum[2]);
        }
        else
        {
            attNum[2]=min(r.nextInt(10)+3,maxNum[2]);
        }
        t=r.nextDouble();
        if(t<0.91)
        {
            attNum[3]=0;
        }
        else if(t<0.98)
        {
            attNum[3]=min(maxNum[3],1);
        }
        else
        {
            attNum[3]=min(maxNum[3],2);
        }
        return attNum;
    }

    public static Boolean selectWithJoin() {
        return Math.random() < 0.5;
    }


    public static int updateNum() {
        return r.nextInt(3) + 2;
    }

    public static int[] updateAttributesNum(int[] maxNum) {
        double t=r.nextDouble();
        return null;
    }

    public static int insertNum() {
        return 0;
    }

    public static int insertAttributesNum(int maxNum) {
        return r.nextInt(min(maxNum,5));
    }

    public static int deleteNum() {
        return 0;
    }

}
