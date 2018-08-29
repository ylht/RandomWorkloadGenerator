package load.generator.generator;

import java.util.Random;

import static java.lang.Math.min;

/**
 * @author wangqingshuai
 */

public class RandomGenerateSqlAttributesValue {

    private static final double SELECT_INT_NUM0_PER = 0.32;
    private static final double SELECT_INT_NUM1_PER = 0.75;
    private static final double SELECT_INT_NUM2_PER = 0.86;
    private static final double SELECT_INT_NUM3_PER = 0.895;
    private static final double SELECT_INT_NUM_MORE_THAN3_PER = 1;

    private static final double SELECT_DOUBLE_NUM0_PER = 0.59;
    private static final double SELECT_DOUBLE_NUM1_PER = 0.93;
    private static final double SELECT_DOUBLE_NUM2_PER = 0.965;
    private static final double SELECT_DOUBLE_NUM4_PER = 1;

    private static final double SELECT_CHAR_NUM0_PER = 0.57;
    private static final double SELECT_CHAR_NUM1_PER = 0.77;
    private static final double SELECT_CHAR_NUM2_PER = 0.82;
    private static final double SELECT_CHAR_NUM_MORE_THAN2_PER = 1;

    private static final double SELECT_DATE_NUM0_PER = 0.91;
    private static final double SELECT_DATE_NUM1_PER = 0.98;
    private static final double SELECT_DATE_NUM2_PER = 1;

    private static Random r = new Random();

    public static int tranNum() {
        return r.nextInt(5) + 4;
    }

    public int selectNum() {
        return r.nextInt(3) + 2;
    }

    public int randomTable(int totalNum) {
        return r.nextInt(totalNum);
    }


    public int[] selectAttributesNum(int[] maxNum) {
        int[] attNum = new int[4];
        int total = 0;
        do {
            double t = r.nextDouble();
            if (t < SELECT_INT_NUM0_PER) {
                attNum[0] = 0;
            } else if (t < SELECT_INT_NUM1_PER) {
                attNum[0] = min(maxNum[0], 1);
            } else if (t < SELECT_INT_NUM2_PER) {
                attNum[0] = min(maxNum[0], 2);
            } else if (t < SELECT_INT_NUM3_PER) {
                attNum[0] = min(maxNum[0], 3);
            } else if (t < SELECT_INT_NUM_MORE_THAN3_PER) {
                attNum[0] = min(maxNum[0], r.nextInt(20) + 3);
            }
            t = r.nextDouble();
            if (t < SELECT_DOUBLE_NUM0_PER) {
                attNum[1] = 0;
            } else if (t < SELECT_DOUBLE_NUM1_PER) {
                attNum[1] = min(attNum[1], 1);
            } else if (t < SELECT_DOUBLE_NUM2_PER) {
                attNum[1] = min(attNum[1], 2);
            } else if (t < SELECT_DOUBLE_NUM4_PER) {
                attNum[1] = min(attNum[1], 4);
            }
            t = r.nextDouble();
            if (t < SELECT_CHAR_NUM0_PER) {
                attNum[2] = 0;
            } else if (t < SELECT_CHAR_NUM1_PER) {
                attNum[2] = min(1, maxNum[2]);
            } else if (t < SELECT_CHAR_NUM2_PER) {
                attNum[2] = min(2, maxNum[2]);
            } else if (t < SELECT_CHAR_NUM_MORE_THAN2_PER) {
                attNum[2] = min(r.nextInt(10) + 3, maxNum[2]);
            }
            t = r.nextDouble();
            if (t < SELECT_DATE_NUM0_PER) {
                attNum[3] = 0;
            } else if (t < SELECT_DATE_NUM1_PER) {
                attNum[3] = min(maxNum[3], 1);
            } else if (t < SELECT_DATE_NUM2_PER) {
                attNum[3] = min(maxNum[3], 2);
            }

            for (int i = 0; i < 4; i++) {
                total += attNum[0];
            }
        } while (total == 0);
        return attNum;
    }

    public Boolean selectWithJoin() {
        return Math.random() < 0.5;
    }


    public int updateNum() {
        return r.nextInt(1) + 2;
    }

    public int[] updateAttributesNum(int[] maxNum) {
        int[] attNum = new int[4];
        int total = 0;
        do {
            double t = r.nextDouble();
            if (t < 0.33) {
                attNum[0] = 0;
            } else if (t < 0.74) {
                attNum[0] = min(1, maxNum[0]);
            } else if (t < 0.89) {
                attNum[0] = min(2, maxNum[0]);
            } else {
                attNum[0] = min(r.nextInt(4) + 3, maxNum[0]);
            }
            t = r.nextDouble();
            if (t < 0.52) {
                attNum[1] = 0;
            } else if (t < 0.89) {
                attNum[1] = min(1, maxNum[1]);
            } else {
                attNum[1] = min(2, maxNum[1]);
            }
            t = r.nextDouble();
            if (t < 0.96) {
                attNum[2] = 0;
            } else {
                attNum[2] = min(1, maxNum[2]);
            }
            t = r.nextDouble();
            if (t < 0.96) {
                attNum[3] = 0;
            } else {
                attNum[3] = min(1, maxNum[3]);
            }

            for (int i = 0; i < 4; i++) {
                total += attNum[0];
            }
        } while (total == 0);
        return attNum;
    }

    public int conditionKeyNum(int keyNum, boolean allKey) {
        if (allKey) {
            return keyNum;
        } else {
            if (keyNum == 1) {
                return keyNum;
            } else {
                return r.nextInt(keyNum - 1) + 1;
            }
        }
    }

    public int insertNum() {
        return 0;
    }

    public int insertAttributesNum(int maxNum) {
        return r.nextInt(min(maxNum, 5));
    }

    public int deleteNum() {
        return 0;
    }

}
