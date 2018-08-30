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
    private ArrayList<RandomValue[]> insertRandoms;
    private ArrayList<RandomValue[]> deleteRandoms;
    private RandomGenerateSqlAttributesValue randomSqlAtt = new RandomGenerateSqlAttributesValue();

    public TransactionTemplate(TableTemplate[] tables) {
        SqlTemplate st = new SqlTemplate();
        ConditionTemplate ct = new ConditionTemplate();
        selectTemplates = new String[randomSqlAtt.selectNum()];
        for (int i = 0; i < selectTemplates.length; i++) {
            TableTemplate randomTable;
            do {
                int randomTableIndex = randomSqlAtt.randomTable(tables.length);
                randomTable = tables[randomTableIndex];
            }while (randomTable.getTableAttNum()==randomTable.getKeyNum());
            int[] selectRangeNum = randomSqlAtt.selectAttributesNum(randomTable.getAttNums());
            Integer[] randomSelectAtt = getSelectAttIndex(randomTable, selectRangeNum);
            if (randomSelectAtt.length == 0) {
                System.out.println("err in get select att");
                System.exit(-1);
            }

            int[] conditionAttIndex = getConditionAttIndex(randomTable.getKeyNum(), true);
            ArrayList<TupleType> tuples = randomTable.getTuples();
            RandomValue[] selectRandom = new RandomValue[conditionAttIndex.length];
            for (int j = 0; j < conditionAttIndex.length; j++) {
                TupleType tuple = tuples.get(conditionAttIndex[j]);
                selectRandom[j] = getRandomValue(tuple);
            }
            selectTemplates[i] = st.selectTemplate(randomSelectAtt) +
                    ct.singleTable(randomTable.getTableName())
                    + ct.singleCondition(conditionAttIndex);

            selectRandoms.add(selectRandom);
        }
        updateTemplates = new String[randomSqlAtt.updateNum()];
        for (int i = 0; i < updateTemplates.length; i++) {
            TableTemplate randomTable;
            do {
                int randomTableIndex = randomSqlAtt.randomTable(tables.length);
                randomTable = tables[randomTableIndex];
            }while (randomTable.getTableAttNum()==randomTable.getKeyNum());
            int[] updateRangeNum = randomSqlAtt.updateAttributesNum(randomTable.getAttNums());
            Integer[] updateAttIndex = getSelectAttIndex(randomTable, updateRangeNum);
            if (updateAttIndex.length == 0) {
                System.out.println("err in get update att");
                System.exit(-1);
            }
            String randomTableName = randomTable.getTableName();
            int[] conditionAttIndex = getConditionAttIndex(randomTable.getKeyNum(), true);
            ArrayList<TupleType> tuples = randomTable.getTuples();
            RandomValue[] updateRandom = new RandomValue[updateAttIndex.length + conditionAttIndex.length];
            for (int j = 0; j < updateAttIndex.length; j++) {
                try {
                    TupleType tuple = tuples.get(updateAttIndex[j]);
                    updateRandom[j] = getRandomValue(tuple);
                } catch (Exception e) {
                    e.printStackTrace();
                    for (int z : updateAttIndex) {
                        System.out.println(z);
                    }
                    System.exit(-1);
                }
            }
            for (int j = 0; j < conditionAttIndex.length; j++) {
                TupleType tuple = tuples.get(conditionAttIndex[j]);
                updateRandom[j + updateAttIndex.length] = getRandomValue(tuple);
            }
            updateRandoms.add(updateRandom);
            updateTemplates[i] = st.updateTemplate(randomTableName, updateAttIndex) + "where " +
                    ct.singleCondition(conditionAttIndex);
        }

        deleteTemplates = new String[randomSqlAtt.deleteNum()];
        for (int i = 0; i < deleteTemplates.length; i++) {
            int randomTableIndex = randomSqlAtt.randomTable(tables.length);
            TableTemplate randomTable = tables[randomTableIndex];
            String randomTableName = randomTable.getTableName();
            int[] conditionAttIndex = getConditionAttIndex(randomTable.getKeyNum(), true);
            ArrayList<TupleType> tuples = randomTable.getTuples();
            RandomValue[] deleteRandom = new RandomValue[conditionAttIndex.length];
            for (int j = 0; j < conditionAttIndex.length; j++) {
                TupleType tuple = tuples.get(conditionAttIndex[j]);
                deleteRandom[j] = getRandomValue(tuple);
            }
            deleteRandoms.add(deleteRandom);
            deleteTemplates[i] = st.deleteTemplate() +
                    ct.singleTable(randomTableName) +
                    ct.singleCondition(conditionAttIndex);
        }
        insertTemplates = new String[randomSqlAtt.insertNum()];
        for (int i = 0; i < insertTemplates.length; i++) {
            int randomTableIndex = randomSqlAtt.randomTable(tables.length);
            TableTemplate randomTable = tables[randomTableIndex];
            String randomTableName = randomTable.getTableName();
            Integer[] randomInsertAtt = getNotKeyAttIndex(randomTable, randomTable.getSecondIndexNum());
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


    private int[] getConditionAttIndex(int keyNum, boolean allKey) {
        int conditionKeyNum = randomSqlAtt.conditionKeyNum(keyNum, allKey);
        ArrayList<Integer> randomKeyIndex = getRandomList(0, keyNum);
        int[] result = new int[conditionKeyNum];
        for (int i = 0; i < conditionKeyNum; i++) {
            result[i] = randomKeyIndex.get(i);
        }
        return result;
    }

    private ArrayList<Integer> getRandomList(int begin, int end) {
        ArrayList<Integer> randomIndex = new ArrayList<Integer>();
        for (int i = begin; i < end; i++) {
            randomIndex.add(i);
        }
        Collections.shuffle(randomIndex);
        return randomIndex;
    }


    /**
     * 从table中获取在rangeNum范围内，要操作的各个属性的位置索引，用于select，update等等
     *
     * @param table
     * @param rangeNum
     * @return
     */
    private Integer[] getSelectAttIndex(TableTemplate table, int[] rangeNum) {

        int[] tableAtt = table.getAttNums().clone();
        int[] allAtt = new int[tableAtt.length + 1];

        System.arraycopy(tableAtt, 0, allAtt, 1, allAtt.length - 1);
        allAtt[0]=table.getKeyNum();
        for (int i = 1; i < allAtt.length; i++) {
            allAtt[i] += allAtt[i - 1];
        }
        allAtt[allAtt.length - 1] = table.getTableAttNum();
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < rangeNum.length; i++) {
            for (int j = 0; j < rangeNum[i]; j++) {
                if (allAtt[i] + j >= allAtt[i + 1]) {
                    break;
                }
                result.add(allAtt[i] + j);
            }
        }

        return result.toArray(new Integer[0]);
    }

    private Integer[] getNotKeyAttIndex(TableTemplate table, int rangeNum) {
        ArrayList<Integer> randomIndex = getRandomList(table.getKeyNum(), table.getTableAttNum());
        Integer[] result = new Integer[rangeNum];
        for (int i = 0; i < rangeNum; i++) {
            result[i] = randomIndex.get(i);
        }
        return result;
    }

}
