package load.generator.generator;

import java.util.Random;

/**
 * 生成用于table随机的各种属性值
 *
 * @author wangqingshuai
 */
public class RandomGenerateTableAttributesVaule {

    private static Random r = new Random();

    /**
     * @return 在本次负载中需要随机的表格数量
     */
    public static int tableNum() {
        return r.nextInt(7) + 3;
    }


    /**
     * @return 在本张表中，int属性的数量
     */
    public static int intNum() {
        return r.nextInt(10) + 2;
    }

    /**
     * @return 在本张表中，double属性的数量
     */
    public static int doubleNum() {
        return r.nextInt(10) + 2;
    }

    /**
     * @return 在本张表中，char属性的数量
     */
    public static int charNum() {
        return r.nextInt(10) + 2;
    }

    /**
     * @return 在本张表中，date属性的数量
     */
    public static int dateNum() {
        return r.nextInt(10) + 2;
    }

    /**
     * @param max 在1到最大值之间进行随机，不包括最大值
     * @return 在本张表中，构成主键的属性数量
     */
    public static int keyNum(int max) {
        return r.nextInt(max - 1) + 1;
    }

    /**
     * @return char属性在本tuple采用varchar或者char
     */
    public static Boolean fixedOrNot() {
        return Math.random() < 0.5;
    }

    /**
     * @return char属性在本tuple采用varchar或者char
     */
    public static int tupleCharNum() {
        return r.nextInt(10) + 2;
    }

    /**
     * @return double属性在本tuple中整数部分的位数
     */
    public static int tupleDoubleIntNum() {
        return r.nextInt(10) + 2;
    }

    /**
     * @return double属性在本tuple中小数部分的位数
     */
    public static int tupleDoublePointNum() {
        return r.nextInt(10) + 2;
    }

    /**
     * @return double属性在本tuple中是否为signned
     */
    public static Boolean doubleSignedOrNot() {
        return Math.random() < 0.5;
    }

    /**
     * @return int属性在本tuple中是否为signned
     */
    public static Boolean intSignedOrNot() {
        return Math.random() < 0.5;
    }

}
