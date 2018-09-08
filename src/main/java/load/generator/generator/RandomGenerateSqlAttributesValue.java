package load.generator.generator;

import load.generator.utils.LoadConfig;
import org.dom4j.Document;
import org.dom4j.Node;

import java.util.List;
import java.util.Random;

import static java.lang.Math.min;

/**
 * @author wangqingshuai
 */

public class RandomGenerateSqlAttributesValue {
    private static Random r = new Random();
    private static Document dc = LoadConfig.getConfig();
    public static int tranNum() {
        Node transaction=dc.selectSingleNode("//generator/transaction/num");
        int max=Integer.valueOf(transaction.valueOf("max"));
        int min=Integer.valueOf(transaction.valueOf("min"));
        assert max>min;
        return r.nextInt(max-min+1) + min;
    }

    public int selectNum() {
        return getSqlNum("select");
    }

    public int randomTable(int totalNum) {
        return r.nextInt(totalNum);
    }


    public int[] selectAttributesNum(int[] maxNum) {
        int[] attNum = new int[4];
        int total = 0;


        return attNum;
    }

    public Boolean selectWithJoin() {
        return Math.random() < 0.5;
    }


    public int updateNum() {
        return getSqlNum("update");
    }

    public int[] updateAttributesNum(int[] maxNum) {
        int[] attNum = new int[4];

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
        return getSqlNum("insert");
    }

    public int[] insertAttributesNum(int[] maxNum) {
        return maxNum;
    }

    public int deleteNum() {
        return getSqlNum("delete");
    }

    private int getSqlAttNum(String sqlType,String tupleType)
    {
        List<Node> nodeList=dc.selectNodes("//generator/transaction/sql/"+sqlType+"value"+tupleType+"range");
        double t = r.nextDouble();
        double sum = 0;
        int result = -1;
        int old=-1;
        for (Node node : nodeList) {
            sum += Double.valueOf(node.valueOf("probability"));
            assert sum < 1.001;
            if (t < sum) {
               if("inf".equals(node.valueOf("value")))
               {
                   return old+r.nextInt();
               }
               else
               {
                   return Integer.valueOf(node.valueOf("value"));
               }
            }
            else
            {
                old=Integer.valueOf(node.valueOf("value"));
            }
        }
        return result;
    }

    private int getSqlNum(String sqlType)
    {
        return getConfigNum("//generator/transaction/sql/"+sqlType+"/num/range");
    }

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


}
