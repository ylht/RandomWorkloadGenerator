package load.generator.template;

import load.generator.generator.RandomGenerateSqlAttributesValue;
import load.generator.generator.random.*;
import load.generator.template.tuple.TupleChar;
import load.generator.template.tuple.TupleType;
import load.generator.utils.KeyValue;
import load.generator.utils.MysqlConnector;
import load.generator.utils.TableRefList;

import java.util.ArrayList;
import java.util.Collections;

public class Transaction {
    private MysqlConnector msc = new MysqlConnector();
    private ArrayList<TransactionSql> transactionSqls=new ArrayList<>();


    private RandomGenerateSqlAttributesValue randomSqlAtt = new RandomGenerateSqlAttributesValue();
    
    public Transaction(TableTemplate[] tables) {
        //获取sql模板
        SqlTemplate st = new SqlTemplate();

        //获取condition模板
        ConditionTemplate ct = new ConditionTemplate();

        //获取全部表的keys
        KeyValue[] tableKeys=new KeyValue[tables.length];
        for(int i=0;i<tables.length;i++)
        {
            ArrayList<TupleType> tuples = tables[i].getTuples();
            tableKeys[i]=new KeyValue(tuples.subList(0,tables[i].getKeyNum()),true);
        }
        TransactionSql.setTableKeys(tableKeys);

        //生成select模板
        int selectNum = randomSqlAtt.selectNum();
        for (int i = 0; i < selectNum; i++) {
            //获取进行select的table，如果表中只有主键则重新随机
            TableTemplate randomTable;
            int randomTableIndex;
            do {
                randomTableIndex = randomSqlAtt.randomTable(tables.length);
                randomTable = tables[randomTableIndex];
            } while (randomTable.getTableAttNum() == randomTable.getKeyNum());

            //获取需要select的属性
            int[] selectRangeNum = randomSqlAtt.selectAttributesNum(randomTable.getAttNums());
            Integer[] randomSelectAtt = getNotKeyAttIndex(randomTable, selectRangeNum);

            //获取condition上的属性
            int[] conditionAttIndex = getConditionAttIndex(randomTable.getKeyNum(), true);

            //拼接select模板
            String selectTemplate = st.selectTemplate(randomSelectAtt) +
                    ct.singleTable(randomTable.getTableName())
                    + ct.singleCondition(conditionAttIndex);

            transactionSqls.add(new TransactionSql(selectTemplate,null,
                    randomTableIndex, TransactionSql.sqlTypes.SELECT));
        }

        //生成update模板
        int updateNum=  randomSqlAtt.updateNum();
        for (int i = 0; i < updateNum; i++) {
            //获取进行update的table，如果表中只有主键则重新随机
            TableTemplate randomTable;
            int randomTableIndex;
            do {
                randomTableIndex = randomSqlAtt.randomTable(tables.length);
                randomTable = tables[randomTableIndex];
            } while (randomTable.getTableAttNum() == randomTable.getKeyNum());

            //获取需要update的属性
            int[] updateRangeNum = randomSqlAtt.updateAttributesNum(randomTable.getAttNums());
            Integer[] updateAttIndex = getNotKeyAttIndex(randomTable, updateRangeNum);

            //获取condition上的属性
            int[] conditionAttIndex = getConditionAttIndex(randomTable.getKeyNum(), true);

            //拼接update模板
            String  updateTemplate = st.updateTemplate(randomTable.getTableName(), updateAttIndex)
                    + ct.singleCondition(conditionAttIndex);

            //获取update的值的randomvalue
            RandomValue[] updateRandom =
                    getNotKeyRandomValue(randomTable.getTuples(),updateAttIndex) ;

            transactionSqls.add(new TransactionSql(updateTemplate,updateRandom,
                    randomTableIndex, TransactionSql.sqlTypes.UPDATE));
        }

        //获取可以供给delete和insert的表格，主要是一定要不存在其他的表格引用，否则会出现外键约束
        ArrayList<Integer> deleteAndInsertTableIndex= TableRefList.getInstance().getLastLevelIndex();
        int deleteAndInsertTableNum=deleteAndInsertTableIndex.size();

        //生成delete模板
        int deleteNum = randomSqlAtt.deleteNum();
        for (int i = 0; i < deleteNum; i++) {
            //获取需要delete的table
            int randomDAndIIndex = randomSqlAtt.randomTable(deleteAndInsertTableNum);
            int randomTableIndex = deleteAndInsertTableIndex.get(randomDAndIIndex);
            TableTemplate randomTable = tables[randomTableIndex];

            //获取condition上的属性
            int[] conditionAttIndex = getConditionAttIndex(randomTable.getKeyNum(),true);

            //拼接delete模板
            String deleteTemplate = st.deleteTemplate() +
                    ct.singleTable(randomTable.getTableName()) +
                    ct.singleCondition(conditionAttIndex);

            transactionSqls.add(new TransactionSql(deleteTemplate,null,
                    randomTableIndex, TransactionSql.sqlTypes.DELETE));
        }

        //生成insert模板
        int insertNum = randomSqlAtt.insertNum();
        for (int i = 0; i < insertNum; i++) {
            //获取需要insert的table
            int randomDAndIIndex = randomSqlAtt.randomTable(deleteAndInsertTableNum);
            int randomTableIndex = deleteAndInsertTableIndex.get(randomDAndIIndex);
            TableTemplate randomTable = tables[randomTableIndex];

            //获取需要insert的属性
            int[] insertRangeNum = randomSqlAtt.insertAttributesNum(randomTable.getAttNums());
            Integer[] insertAttIndex = getNotKeyAttIndex(randomTable, insertRangeNum);

            //生成insert模板
            String insertTemplate = st.insertTemplate(randomTable.getTableName(),
                    insertAttIndex, randomTable.getKeyNum());

            //insert的randomValue
            RandomValue[] insertRandom =
                    getNotKeyRandomValue(randomTable.getTuples(),insertAttIndex);

            transactionSqls.add(new TransactionSql(insertTemplate,insertRandom,
                    randomTableIndex, TransactionSql.sqlTypes.INSERT));
        }
    }

    private RandomValue[] getNotKeyRandomValue(ArrayList<TupleType> tuples ,Integer[] loc)
    {
        RandomValue[] result=new RandomValue[loc.length];
        for (int j = 0; j < loc.length; j++) {

                TupleType tuple = tuples.get(loc[j]);
                result[j] = getRandomValue(tuple);
        }
        return result;
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
                        temprv = new RandomChar((int) t.getMin(), (int) t.getMax(), true);
                        break;
                    case 1:
                        temprv = new RandomChar(chart.getcT());
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

    public ArrayList<String>getSql()
    {
        ArrayList<String> result=new ArrayList<>();
        for (TransactionSql transactionSql : transactionSqls) {
            result.add(transactionSql.getSqlTemplate());
        }
        return result;
    }

    public void executeSql() {
        for (TransactionSql transactionSql : transactionSqls) {
            msc.excuteSql(transactionSql.getSqlTemplate(),
                    transactionSql.getSqlValues());
        }
    }

    private int[] getConditionAttIndex(int keyNum, boolean allKey) {
        int conditionKeyNum = randomSqlAtt.conditionKeyNum(keyNum, allKey);
        ArrayList<Integer> randomKeyIndex = getRandomList(0, keyNum,allKey);
        int[] result = new int[conditionKeyNum];
        for (int i = 0; i < conditionKeyNum; i++) {
            result[i] = randomKeyIndex.get(i);
        }
        return result;
    }

    private ArrayList<Integer> getRandomList(int begin, int end,boolean allkey) {
        var randomIndex = new ArrayList<Integer>();
        for (int i = begin; i < end; i++) {
            randomIndex.add(i);
        }
        if(!allkey)
        {
            Collections.shuffle(randomIndex);
        }
        return randomIndex;
    }


    /**
     * 从table中获取在rangeNum范围内，要操作的各个属性的位置索引，用于select，update等等
     */
    private Integer[] getNotKeyAttIndex(TableTemplate table, int[] rangeNum) {

        int[] tableAtt = table.getAttNums().clone();
        int[] allAtt = new int[tableAtt.length + 1];

        System.arraycopy(tableAtt, 0, allAtt, 1, allAtt.length - 1);
        allAtt[0] = table.getKeyNum();
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

}
