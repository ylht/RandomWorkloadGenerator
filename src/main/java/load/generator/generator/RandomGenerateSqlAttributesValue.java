package load.generator.generator;

import load.generator.utils.LoadConfig;
import org.dom4j.Document;
import org.dom4j.Node;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.Math.max;
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
        return getSqlAttNum("select",maxNum);
    }

    public int[] getSqlAttNum(String sqlType,int[] maxNum)
    {
        int[] attNum = new int[4];
        int total = 0;
        do{
            attNum[0]=min(getSqlAttNum(sqlType,"int"),maxNum[0]);
            attNum[1]=min(getSqlAttNum(sqlType,"decimal"),maxNum[1]);
            attNum[2]=min(getSqlAttNum(sqlType,"char"),maxNum[2]);
            attNum[3]=min(getSqlAttNum(sqlType,"date"),maxNum[3]);
            for(int i:attNum)
            {
                total+=i;
            }
        }while (total==0);

        return attNum;
    }

    public Boolean selectWithJoin() {
        return Math.random() < 0.5;
    }


    public int updateNum() {
        return getSqlNum("update");
    }

    public int[] updateAttributesNum(int[] maxNum) {
        return getSqlAttNum("update",maxNum);
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
        return getSqlAttNum("insert",maxNum);
    }

    public int deleteNum() {
        return getSqlNum("delete");
    }

    private int getSqlAttNum(String sqlType,String tupleType)
    {
        List<Node> nodeList=dc.selectNodes("//generator/transaction/sql/"+sqlType+"/value/"+tupleType+"/range");
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
                   return old+r.nextInt(20);
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
                String maxString=node.valueOf("max");
                int max=0;
                if(maxString.equals("inf"))
                {
                    max=500;
                }
                else
                {
                    max = Integer.valueOf(maxString);
                }

                int min = Integer.valueOf(node.valueOf("min"));
                assert max >= min;
                result = r.nextInt(max - min + 1) + min;
                break;
            }
        }
        return result;
    }

    public static void main(String args[])
    {
        RandomGenerateSqlAttributesValue rgqa=new RandomGenerateSqlAttributesValue();
        int p[]={4,4,4,4};
        for(int i:rgqa.updateAttributesNum(p))
        {
            System.out.println(i);
        }

    }

}
