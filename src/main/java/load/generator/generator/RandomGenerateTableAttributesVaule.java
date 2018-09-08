package load.generator.generator;

import load.generator.utils.LoadConfig;
import org.dom4j.Document;
import org.dom4j.Node;

import java.util.List;
import java.util.Random;

import static java.lang.Math.min;

/**
 * 生成用于table随机的各种属性值
 *
 * @author wangqingshuai
 */
public class RandomGenerateTableAttributesVaule {

    private static RandomGenerateTableAttributesVaule instance = new RandomGenerateTableAttributesVaule();
    private static Document dc = LoadConfig.getConfig();
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

    public static void main(String[] args) {
        RandomGenerateTableAttributesVaule rgsav = new RandomGenerateTableAttributesVaule();
        System.out.println(rgsav.tupleDoubleIntNum());
        System.out.println(rgsav.tupleDoublePointNum());
    }

    /**
     * @return 在本次负载中需要随机的表格数量
     */
    public int tableNum() {
        Node tableNum = dc.selectSingleNode("//generator/table/tableNum");
        int maxnum = Integer.valueOf(tableNum.valueOf("max"));
        int minnum = Integer.valueOf(tableNum.valueOf("min"));
        return r.nextInt(maxnum - minnum + 1) + minnum;
    }

    /**
     * @return 在本次负载中需要随机的
     */
    public int keyNum(int tableIntNum, int tableIndex) {
        return min(min(tableIntNum, tableIndex + 1), 4);
    }

    public int foreignKeyNum(int keyNum, int tableIntNum) {
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

    public int tableLineRate(int level) {
        return 5;
    }

    /**
     * @return 在本张表中，int属性的数量
     */
    public int intNum() {
        return tupleNum("int");
    }

    private int tupleNum(String tupleType)
    {
        return getConfigNum("//generator/table/" + tupleType + "/num/range");
    }

    /**
     * 返回对应类型tuple的数值
     *
     * @param location
     * @return
     */
    private int getConfigNum(String location) {
        List<Node> nodeList = dc.selectNodes(location);
        double t = r.nextDouble();
        double sum = 0;
        int result = -1;
        for (Node node : nodeList) {
            sum += Double.valueOf(node.valueOf("probability"));
            assert sum < 1.001;
            if (t < sum) {
                int max = Integer.valueOf(node.valueOf("max"));
                int min = Integer.valueOf(node.valueOf("min"));
                assert max >= min;
                result = r.nextInt(max - min + 1) + min;
                break;
            }
        }
        return result;
    }

    /**
     * @return 在本张表中，double属性的数量
     */
    public int decimalNum() {
        return tupleNum("decimal");
    }

    public int floatNum() {
        return tupleNum("float");
    }

    /**
     * @return 在本张表中，char属性的数量
     */
    public int charNum() {
        return tupleNum("char");
    }

    public int varcharNum() {
        return tupleNum("varchar");
    }

    /**
     * @return 在本张表中，date属性的数量
     */
    public int dateNum() {
        return tupleNum("date");
    }

    public int tupleCharNum() {
        return getConfigNum("//generator/table/char/value/length/range");
    }

    public int tupleVarCharNum() {
        return getConfigNum("//generator/table/varchar/value/length/range");
    }

    /**
     * @return double属性在本tuple中整数部分的位数
     */
    public int tupleDoubleIntNum() {
        return getConfigNum("//generator/table/decimal/value/int/range");
    }

    /**
     * @return double属性在本tuple中小数部分的位数
     */
    public int tupleDoublePointNum() {
        return getConfigNum("//generator/table/decimal/value/point/range");
    }
}
