package load.generator.generator;

import java.util.Random;

import static java.lang.Math.min;

/**
 * @author wangqingshuai
 */

public class RandomGenerateSqlAttributesValue {


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
        double selectIntNum0Per = 0.32;
        double selectIntNum1Per = 0.75;
        double selectIntNum2Per = 0.86;
        double selectIntNum3Per = 0.895;
        double selectIntNumMoreThan3Per = 1;

        double selectDoubleNum0Per = 0.59;
        double selectDoubleNum1Per = 0.93;
        double selectDoubleNum2Per = 0.965;
        double selectDoubleNum4Per = 1;

        double selectCharNum0Per = 0.57;
        double selectCharNum1Per = 0.77;
        double selectCharNum2Per = 0.82;
        double selectCharNumMoreThan2Per = 1;

        double selectDateNum0Per = 0.91;
        double selectDateNum1Per = 0.98;
        double selectDateNum2Per = 1;
        int[] attNum = new int[4];
        double t = r.nextDouble();
        if (t < selectIntNum0Per) {
            attNum[0] = 0;
        } else if (t < selectIntNum1Per) {
            attNum[0] = min(maxNum[0], 1);
        } else if (t < selectIntNum2Per) {
            attNum[0] = min(maxNum[0], 2);
        } else if (t < selectIntNum3Per) {
            attNum[0] = min(maxNum[0], 3);
        } else if (t < selectIntNumMoreThan3Per) {
            attNum[0] = min(maxNum[0], r.nextInt(20) + 3);
        }
        t = r.nextDouble();
        if (t < selectDoubleNum0Per) {
            attNum[1] = 0;
        } else if (t < selectDoubleNum1Per) {
            attNum[1] = min(attNum[1], 1);
        } else if (t < selectDoubleNum2Per) {
            attNum[1] = min(attNum[1], 2);
        } else if (t < selectDoubleNum4Per) {
            attNum[1] = min(attNum[1], 4);
        }
        t = r.nextDouble();
        if (t < selectCharNum0Per) {
            attNum[2] = 0;
        } else if (t < selectCharNum1Per) {
            attNum[2] = min(1, maxNum[2]);
        } else if (t < selectCharNum2Per) {
            attNum[2] = min(2, maxNum[2]);
        } else if (t < selectCharNumMoreThan2Per) {
            attNum[2] = min(r.nextInt(10) + 3, maxNum[2]);
        }
        t = r.nextDouble();
        if (t < selectDateNum0Per) {
            attNum[3] = 0;
        } else if (t < selectDateNum1Per) {
            attNum[3] = min(maxNum[3], 1);
        } else if (t < selectDateNum2Per) {
            attNum[3] = min(maxNum[3], 2);
        }
        int total = 0;
        for (int i = 0; i < 4; i++) {
            total += attNum[0];
        }
        if (total == 0) {
            t = r.nextDouble();
            if (t < 0.5) {
                attNum[0] = 1;
            } else {
                attNum[1] = 1;
            }
        }
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
        int total = 0;
        for (int i = 0; i < 4; i++) {
            total += attNum[0];
        }
        if (total == 0) {
            t = r.nextDouble();
            if (t < 0.5) {
                attNum[0] = 1;
            } else {
                attNum[1] = 1;
            }
        }
        return attNum;
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
