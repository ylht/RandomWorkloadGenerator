package load.generator.generator;

import java.util.Random;

import static java.lang.Math.min;

/**
 * 生成用于table随机的各种属性值
 *
 * @author wangqingshuai
 */
public class RandomGenerateTableAttributesVaule {

    private static final int TABLE_MIN_NUM = 3;

    ;
    private static final int TABLE_MAX_NUM = 10;
    private static final double TABLE_INT_NUM1_TO5_PER = 0.69;
    private static final double TABLE_INT_NUM6_TO10_PER = 0.73;
    private static final double TABLE_INT_NUM11_TO20_PER = 0.88;
    private static final double TABLE_INT_NUM21_TO30_PER = 0.92;
    private static final double TABLE_INT_NUM31_TO40_PER = 1;
    private static RandomGenerateTableAttributesVaule instance = new RandomGenerateTableAttributesVaule();
    private static Random r = new Random();

    private RandomGenerateTableAttributesVaule() {
    }


    public static RandomGenerateTableAttributesVaule getInstance() {
        return instance;
    }

    public static int tupleIntMin() {
        return r.nextInt(100) + 5;
    }

    public static int tupleIntRange() {
        return r.nextInt(50) + 10;
    }

    public static double tupleDoubleMin() {
        return 0;
    }

    public static double tupleDoubleRange() {
        return r.nextDouble() * 10000 + 5000;
    }

    /**
     * @return 在本次负载中需要随机的表格数量
     */
    int tableNum() {
        return r.nextInt(TABLE_MAX_NUM - TABLE_MIN_NUM) + TABLE_MIN_NUM;
    }

    int keyNum(int tableIntNum) {
        return min(tableIntNum, 4) + 1;
    }

    int foreignKeyNum(int keyNum, int tableIntNum) {
        if (keyNum > 1) {
            if (tableIntNum > keyNum) {
                return r.nextInt(tableIntNum - keyNum) + keyNum - 1;
            } else {
                return keyNum - 1;
            }
        } else {
            return 0;
        }
    }

    /**
     * @return 在本张表中，int属性的数量
     */
    int intNum() {
        double temp = r.nextDouble();
        if (temp < TABLE_INT_NUM1_TO5_PER) {
            return r.nextInt(5) + 1;
        } else if (temp < TABLE_INT_NUM6_TO10_PER) {
            return r.nextInt(5) + 6;
        } else if (temp < TABLE_INT_NUM11_TO20_PER) {
            return r.nextInt(10) + 11;
        } else if (temp < TABLE_INT_NUM21_TO30_PER) {
            return r.nextInt(10) + 21;
        } else if (temp < TABLE_INT_NUM31_TO40_PER) {
            return r.nextInt(10) + 31;
        } else {
            return -1;
        }
    }

    /**
     * @return 在本张表中，double属性的数量
     */
    int decimalNum() {
        double t = r.nextDouble() * 100;
        if (t < 69.23) {
            return 0;
        } else {
            return r.nextInt(5) + 1;
        }
    }

    int floatNum() {
        double t = r.nextDouble() * 100;
        if (t < 65.38) {
            return 0;
        } else if (t < 96.15) {
            return 1;
        } else {
            return 3;
        }
    }

    /**
     * @return 在本张表中，char属性的数量
     */
    int charNum() {
        double t = r.nextDouble() * 100;
        if (t < 80.77) {
            return 0;
        } else if (t < 96.15) {
            return 1 + r.nextInt(5);
        } else {
            return 6 + r.nextInt(5);
        }
    }

    int varcharNum() {
        double t = r.nextDouble() * 100;
        if (t < 34.62) {
            return 0;
        } else if (t < 92.31) {
            return r.nextInt(5) + 1;
        } else if (t < 96.15) {
            return r.nextInt(5) + 6;
        } else {
            return r.nextInt(20) + 11;
        }
    }

    /**
     * @return 在本张表中，date属性的数量
     */
    int dateNum() {
        double t = r.nextDouble() * 100;
        if (t < 76.92) {
            return 0;
        } else if (t < 92.31) {
            return 1;
        } else {
            return 2;
        }
    }

    //value

    /**
     * @return char属性在本tuple采用varchar或者char
     */
    public int tupleCharNum() {
        double t = r.nextDouble() * 100;
        if (t < 24) {
            return r.nextInt(4) + 2;
        } else if (t < 38) {
            return r.nextInt(5) + 6;
        } else if (t < 43) {
            return r.nextInt(5) + 16;
        } else {
            return r.nextInt(10) + 21;
        }
    }

    int tupleVarCharNum() {
        double t = r.nextDouble() * 100;
        if (t < 16) {
            return r.nextInt(4) + 2;
        } else if (t < 59) {
            return r.nextInt(5) + 6;
        } else if (t < 65) {
            return r.nextInt(5) + 11;
        } else if (t < 86) {
            return r.nextInt(5) + 16;
        } else if (t < 94) {
            return r.nextInt(60) + 21;
        } else {
            return r.nextInt(200) + 81;
        }
    }

    /**
     * @return double属性在本tuple中整数部分的位数
     */
    public int tupleDoubleIntNum() {
        double t = r.nextDouble() * 100;
        if (t < 63.16) {
            return 4 + r.nextInt(2);
        } else if (t < 78.95) {
            return 6 + r.nextInt(5);
        } else {
            return 11 + r.nextInt(5);
        }
    }

    /**
     * @return double属性在本tuple中小数部分的位数
     */
    public int tupleDoublePointNum() {
        double t = r.nextDouble() * 100;
        if (t < 47.37) {
            return 0;
        } else if (t < 84.21) {
            return 2;
        } else {
            return 4;
        }
    }
}
