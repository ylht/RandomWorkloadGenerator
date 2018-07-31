package load.generator.template;

import load.generator.generator.RandomGenerateSqlAttributesValue;
import load.generator.generator.random.*;
import load.generator.template.tuple.TupleChar;
import load.generator.template.tuple.TupleType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TransactionTemplate {
    private ArrayList<ArrayList<randomValue>>rvs;
    private String[] selectTemplates;
    private String[] updateTemplates;
    private String[] insertTemplates;
    private String[] deleteTemplates;
    private ArrayList<randomValue[]> selectRandoms=new ArrayList<>();
    private ArrayList<randomValue[]> updateRandoms=new ArrayList<>();
    private randomValue[] insertRandom;
    private randomValue[] deleteRandom;
    public TransactionTemplate(TableTemplate[] tables) {
        SqlTemplate st = new SqlTemplate();
        ConditionTemplate ct = new ConditionTemplate();
        int sNum=RandomGenerateSqlAttributesValue.selectNum();
        selectTemplates = new String[sNum];
        for (int i = 0; i < selectTemplates.length; i++) {
            int randomTableIndex = RandomGenerateSqlAttributesValue.randomTable(tables.length);
            TableTemplate randomTable = tables[randomTableIndex];
            String[] randomSelectAtt = getRandomAllAttributes(randomTable, RandomGenerateSqlAttributesValue.selectAttributesNum(randomTable.getAttNums()));
            String randomTableName = randomTable.getTableName();
            String[] randomConditionAtt = getRandomKeyAttributes(randomTable, randomTable.getKeyNum());
            int[] rCA=getLoction(randomConditionAtt);
            ArrayList<TupleType> tuples= randomTable.getTuples();
            randomValue[] selectRandom=new randomValue[rCA.length];
            for(int j=0;j<rCA.length;j++)
            {
                TupleType tuple=tuples.get(rCA[j]);
                selectRandom[j]=getRandomValue(tuple);
            }
            selectTemplates[i] = st.selectTemplate(randomSelectAtt) +
                    ct.singleTable(randomTableName)
                    + ct.singleCondition(randomConditionAtt);
            selectRandoms.add(selectRandom);
        }
        updateTemplates = new String[RandomGenerateSqlAttributesValue.updateNum()];
        for (int i = 0; i < updateTemplates.length; i++) {
            int randomTableIndex = RandomGenerateSqlAttributesValue.randomTable(tables.length);
            TableTemplate randomTable = tables[randomTableIndex];
            String[] randomUpdateAtt = getRandomAllAttributes(randomTable, randomTable.getTableAttNum());
            int[] rUA=getLoction(randomUpdateAtt);
            String randomTableName = randomTable.getTableName();
            String[] randomConditionAtt = getRandomKeyAttributes(randomTable, randomTable.getKeyNum());
            int[] rCA=getLoction(randomConditionAtt);
            ArrayList<TupleType> tuples= randomTable.getTuples();
            randomValue[] updateRandom=new randomValue[rUA.length+rCA.length];
            for(int j=0;j<rUA.length;j++)
            {
                TupleType tuple=tuples.get(rUA[j]);
                updateRandom[j]=getRandomValue(tuple);
            }
            for(int j=0;j<rCA.length;j++)
            {
                TupleType tuple=tuples.get(rCA[j]);
                updateRandom[j+rUA.length]=getRandomValue(tuple);
            }
            updateRandoms.add(updateRandom);
            updateTemplates[i] = st.updateTemplate(randomTableName, randomUpdateAtt) + "where " +
                    ct.singleCondition(randomConditionAtt);
        }
        deleteTemplates = new String[RandomGenerateSqlAttributesValue.deleteNum()];
        for (int i = 0; i < deleteTemplates.length; i++) {
            int randomTableIndex = RandomGenerateSqlAttributesValue.randomTable(tables.length);
            TableTemplate randomTable = tables[randomTableIndex];
            String randomTableName = randomTable.getTableName();
            String[] randomConditionAtt = getRandomKeyAttributes(randomTable,randomTable.getKeyNum());
            deleteTemplates[i] = st.deleteTemplate() +
                    ct.singleTable(randomTableName) +
                    ct.singleCondition(randomConditionAtt);
        }
        insertTemplates = new String[RandomGenerateSqlAttributesValue.insertNum()];
        for (int i = 0; i < insertTemplates.length; i++) {
            int randomTableIndex = RandomGenerateSqlAttributesValue.randomTable(tables.length);
            TableTemplate randomTable = tables[randomTableIndex];
            String randomTableName = randomTable.getTableName();
            String[] randomInsertAtt = getRandomSecondIndexAttributes(randomTable, randomTable.getSecondIndexNum());
            insertTemplates[i] = st.insertTemplate(randomTableName, randomInsertAtt, randomTable.getKeyNum());
        }
    }

    private randomValue getRandomValue(TupleType t)
    {
        randomValue temprv=new randomValue();
        switch (t.getValueType()){
            case  "int":
                temprv=new randomInt((int)t.getMin(),(int)t.getMax());
                break;
            case "double":
                temprv=new randomDemical((double)t.getMin(),(double)t.getMax());
                break;
            case "char":
                TupleChar chart=(TupleChar)t;
                int l=chart.getCharType();
                switch (l)
                {
                    case 0:
                    case 1:
                        temprv=new randomChar((int)t.getMin(),(int)t.getMax());
                        break;
                    case 2:
                        temprv=new randomChar(chart.getcT());
                        break;
                    default:
                        System.out.println("没有匹配到应生成的随机数据类型");
                        break;
                }
                break;
            case "date":
                temprv=new randomDate();
                break;
            default:
                System.out.println("没有匹配到应生成的随机数据类型");
                System.exit(-1);
        }
        return temprv;
    }

    public int[] getLoction(String[] loc)
    {
        int[] il=new int[loc.length];
        int count=0;
        for(;count<loc.length;count++)
        {
            il[count]=Integer.valueOf(loc[count].substring(2));
        }
        return il;
    }

    public ArrayList<String> getSql()
    {
        for(int i=0;i<selectTemplates.length;i++)
        {
            randomValue[] rv= selectRandoms.get(i);
            ArrayList<String> v=new ArrayList<>();
            for (randomValue aRv : rv) {
                v.add(aRv.getValue());
            }
            System.out.println(v);
            System.out.println(selectTemplates[i]);
        }
        for(int i=0;i<updateTemplates.length;i++)
        {
            randomValue[] rv= updateRandoms.get(i);
            ArrayList<String> v=new ArrayList<>();
            for (randomValue aRv : rv) {
                v.add(aRv.getValue());
            }
            System.out.println(v);
            System.out.println(updateTemplates[i]);
        }
        ArrayList<String> sqls = new ArrayList<>(Arrays.asList(selectTemplates));
        sqls.addAll(Arrays.asList(updateTemplates));
        sqls.addAll(Arrays.asList(insertTemplates));
        sqls.addAll(Arrays.asList(deleteTemplates));

        return sqls;
    }

    private ArrayList<Integer> getRandomList(int begin, int end) {
        ArrayList<Integer> randomIndex = new ArrayList<Integer>();
        for (int i = begin; i < end; i++) {
            randomIndex.add(i);
        }
        Collections.shuffle(randomIndex);
        return randomIndex;
    }

    private String[] getAttributes(ArrayList<Integer> al, int totalNum) {
        String[] result = new String[totalNum];
        for (int i = 0; i < totalNum; i++) {
            result[i] = "tv" + String.valueOf(al.get(i));
        }
        return result;
    }

    private String[] getRandomAllAttributes(TableTemplate table, int[] randomNum){
        int[] allAtt=table.getAttNums().clone();
        for(int i=allAtt.length-1;i>0;i--)
        {
            allAtt[i]=allAtt[i-1];
        }
        allAtt[0]=0;
        ArrayList<String> result=new ArrayList<>();
        for(int i=0;i<randomNum.length;i++)
        {
            for(int j=0;j<randomNum[i];j++)
            {
                result.add("tv"+String.valueOf(allAtt[i]+j));
            }
        }
        String[] stockArr;
        stockArr = new String[result.size()];
        stockArr = result.toArray(stockArr);
        return stockArr;
    }

    private String[] getRandomAllAttributes(TableTemplate table, int randomNum) {
        ArrayList<Integer> randomIndex = getRandomList(0, table.getTableAttNum());
        return getAttributes(randomIndex, randomNum);
    }

    private String[] getRandomKeyAttributes(TableTemplate table, int randomNum) {
        ArrayList<Integer> randomIndex = getRandomList(0, table.getKeyNum());
        return getAttributes(randomIndex, randomNum);
    }

    private String[] getRandomSecondIndexAttributes(TableTemplate table, int randomNum) {
        ArrayList<Integer> randomIndex = getRandomList(table.getKeyNum(), table.getTableAttNum());
        return getAttributes(randomIndex, randomNum);
    }

}