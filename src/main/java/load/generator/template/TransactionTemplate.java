package load.generator.template;

import load.generator.generator.RandomGenerateSqlAttributesValue;
import load.generator.generator.random.*;
import load.generator.template.tuple.TupleChar;
import load.generator.template.tuple.TupleType;
import load.generator.utils.MysqlConnector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TransactionTemplate {
    private ArrayList<ArrayList<RandomValue>> rvs;
    private MysqlConnector msc = new MysqlConnector();
    private String[] selectTemplates;
    private String[] updateTemplates;
    private String[] insertTemplates;
    private String[] deleteTemplates;
    private ArrayList<RandomValue[]> selectRandoms = new ArrayList<RandomValue[]>();
    private ArrayList<RandomValue[]> updateRandoms = new ArrayList<RandomValue[]>();
    private RandomValue[] insertRandom;
    private RandomValue[] deleteRandom;

    public TransactionTemplate(TableTemplate[] tables) {
        SqlTemplate st = new SqlTemplate();
        ConditionTemplate ct = new ConditionTemplate();
        RandomGenerateSqlAttributesValue randomSqlAtt = new RandomGenerateSqlAttributesValue();
        selectTemplates = new String[randomSqlAtt.selectNum()];
        for (int i = 0; i < selectTemplates.length; i++) {
            int randomTableIndex = randomSqlAtt.randomTable(tables.length);
            TableTemplate randomTable = tables[randomTableIndex];
            int[] selectAttLocation= randomSqlAtt.selectAttributesNum(randomTable.getAttNums());
            Integer[] randomSelectAtt = getRandomAllAttributesIndex(randomTable,selectAttLocation);
            if (randomSelectAtt.length == 0) {
                i--;
                System.out.println(-1);
                continue;
            }
            int conditionKeyNum=randomSqlAtt.conditionKeyNum(randomTable.getKeyNum(),true);
            ArrayList<Integer> randomKeyIndex = getRandomList(0, randomTable.getKeyNum());
            String[] randomConditionAtt = getAttributes(randomKeyIndex, randomKeyIndex.size());
            ArrayList<TupleType> tuples = randomTable.getTuples();
            RandomValue[] selectRandom = new RandomValue[randomKeyIndex.size()];
            for (int j = 0; j < randomKeyIndex.size(); j++) {
                TupleType tuple = tuples.get(randomKeyIndex.get(j));
                selectRandom[j] = getRandomValue(tuple);
            }
            selectTemplates[i] = st.selectTemplate(randomSelectAtt) +
                    ct.singleTable(randomTable.getTableName())
                    + ct.singleCondition(randomConditionAtt);
            selectRandoms.add(selectRandom);
        }
        updateTemplates = new String[randomSqlAtt.updateNum()];
        for (int i = 0; i < updateTemplates.length; i++) {
            int randomTableIndex = randomSqlAtt.randomTable(tables.length);
            TableTemplate randomTable = tables[randomTableIndex];
            String[] randomUpdateAtt = getRandomAllAttributes(randomTable, randomSqlAtt.updateAttributesNum(randomTable.getAttNums()));
            int[] rUA = getLoction(randomUpdateAtt);
            if (rUA.length == 0) {
                i--;
                continue;
            }
            String randomTableName = randomTable.getTableName();
            String[] randomConditionAtt = getRandomKeyAttributes(randomTable, randomTable.getKeyNum());
            int[] rCA = getLoction(randomConditionAtt);
            ArrayList<TupleType> tuples = randomTable.getTuples();
            RandomValue[] updateRandom = new RandomValue[rUA.length + rCA.length];
            for (int j = 0; j < rUA.length; j++) {
                try {
                    TupleType tuple = tuples.get(rUA[j]);
                    updateRandom[j] = getRandomValue(tuple);
                } catch (Exception e) {
                    e.printStackTrace();
                    for (int z : rUA) {
                        System.out.println(z);
                    }
                    System.exit(-1);
                }
            }
            for (int j = 0; j < rCA.length; j++) {
                TupleType tuple = tuples.get(rCA[j]);
                updateRandom[j + rUA.length] = getRandomValue(tuple);
            }
            updateRandoms.add(updateRandom);
            updateTemplates[i] = st.updateTemplate(randomTableName, randomUpdateAtt) + "where " +
                    ct.singleCondition(randomConditionAtt);
        }
        deleteTemplates = new String[randomSqlAtt.deleteNum()];
        for (int i = 0; i < deleteTemplates.length; i++) {
            int randomTableIndex = randomSqlAtt.randomTable(tables.length);
            TableTemplate randomTable = tables[randomTableIndex];
            String randomTableName = randomTable.getTableName();
            String[] randomConditionAtt = getRandomKeyAttributes(randomTable, randomTable.getKeyNum());
            deleteTemplates[i] = st.deleteTemplate() +
                    ct.singleTable(randomTableName) +
                    ct.singleCondition(randomConditionAtt);
        }
        insertTemplates = new String[randomSqlAtt.insertNum()];
        for (int i = 0; i < insertTemplates.length; i++) {
            int randomTableIndex = randomSqlAtt.randomTable(tables.length);
            TableTemplate randomTable = tables[randomTableIndex];
            String randomTableName = randomTable.getTableName();
            String[] randomInsertAtt = getRandomNotKeyAttributes(randomTable, randomTable.getSecondIndexNum());
            insertTemplates[i] = st.insertTemplate(randomTableName, randomInsertAtt, randomTable.getKeyNum());
        }
    }

    private RandomValue getRandomValue(TupleType t) {
        RandomValue temprv = new RandomValue();
        switch (t.getValueType()) {
            case "int":
                temprv = new RandomInt((int) t.getMin(), (int) t.getMax());
                break;
            case "double":
                temprv = new RandomDemical((double) t.getMin(), (double) t.getMax());
                break;
            case "char":
                TupleChar chart = (TupleChar) t;
                int l = chart.getCharType();
                switch (l) {
                    case 0:
                    case 1:
                        temprv = new RandomChar((int) t.getMin(), (int) t.getMax(), true);
                        break;
                    case 2:
                        temprv = new RandomChar(chart.getcT(), true);
                        break;
                    default:
                        System.out.println("没有匹配到应生成的随机数据类型");
                        break;
                }
                break;
            case "date":
                temprv = new RandomDate(true);
                break;
            default:
                System.out.println("没有匹配到应生成的随机数据类型");
                System.exit(-1);
        }
        return temprv;
    }


    public ArrayList<String> getSql() {
        ArrayList<String> sqls = new ArrayList<>();
        sqls.addAll(Arrays.asList(selectTemplates));
        sqls.addAll(Arrays.asList(updateTemplates));
        return sqls;
    }

    public void executeSql() {
        excuteSubSql(selectTemplates, selectRandoms);
        excuteSubSql(updateTemplates, updateRandoms);
        ArrayList<String> sqls = new ArrayList<>(Arrays.asList(selectTemplates));
        sqls.addAll(Arrays.asList(updateTemplates));
        sqls.addAll(Arrays.asList(insertTemplates));
        sqls.addAll(Arrays.asList(deleteTemplates));
    }

    private void excuteSubSql(String[] selectTemplates, ArrayList<RandomValue[]> selectRandoms) {
        for (int i = 0; i < selectTemplates.length; i++) {
            RandomValue[] rv = selectRandoms.get(i);
            ArrayList<String> v = new ArrayList<>();
            for (RandomValue aRv : rv) {
                v.add(aRv.getValue());
            }
            msc.excuteSql(selectTemplates[i], v);
        }
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



    private Integer[] getRandomAllAttributesIndex(TableTemplate table, int[] randomNum) {
        int[] allAtt = table.getAttNums().clone();
        if (allAtt.length - 1 >= 0) {
            System.arraycopy(allAtt, 0, allAtt, 1, allAtt.length - 1);
        }
        for (int i = 0; i < allAtt.length - 1; i++) {
            allAtt[i + 1] += allAtt[i];
        }
        allAtt[0] = table.getKeyNum();

        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < randomNum.length; i++) {
            for (int j = 0; j < randomNum[i]; j++) {
                result.add(allAtt[i] + j);
            }
        }
        return result.toArray(new Integer[0]);
    }

    private String[] getRandomNotKeyAttributes(TableTemplate table, int randomNum) {
        ArrayList<Integer> randomIndex = getRandomList(table.getKeyNum(), table.getTableAttNum());
        return getAttributes(randomIndex, randomNum);
    }

}
