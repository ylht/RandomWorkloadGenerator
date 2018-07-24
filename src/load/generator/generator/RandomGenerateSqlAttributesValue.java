package load.generator.generator;

import java.util.Random;

/**
 * @author wangqingshuai
 */

public class RandomGenerateSqlAttributesValue {
    private static Random r = new Random();

    public static int selectNum() {
        return r.nextInt(10) + 2;
    }

    public static int randomTable(int totalNum) {
        return r.nextInt(totalNum);
    }


    public static int selectAttributesNum(int maxNum) {
        return r.nextInt(maxNum) + 1;
    }

    public static Boolean selectWithJoin() {
        return Math.random() < 0.5;
    }

    public static int conditionNum(int maxNum) {
        if (maxNum == 1) {
            return 1;
        } else {
            return r.nextInt(maxNum+1) + 1;
        }

    }

    public static int updateNum() {
        return r.nextInt(10) + 2;
    }

    public static int updateAttributesNum(int maxNum) {
        return r.nextInt(maxNum - 1) + 1;
    }

    public static int insertNum() {
        return r.nextInt(10) + 2;
    }

    public static int insertAttributesNum(int maxNum) {
        return r.nextInt(maxNum);
    }

    public static int deleteNum() {
        return r.nextInt(10) + 2;
    }

}
