package load.generator.template;

import load.generator.generator.RandomGenerateTableAttributesVaule;
import load.generator.template.tuple.*;

import java.util.ArrayList;

/**
 * @author wangqingshuai
 */

public class TableTemplate {

    private String tableName;

    private ArrayList<TupleForeign> tf;
    private int totalNum;
    private int tableLineRate;
    private int keyNum;
    private int[] attNums = new int[4];
    private ArrayList<TupleType> tuples = new ArrayList<TupleType>();

    public TableTemplate(String tableName,
                         int intNum, int doubleNum, int charNum, int dateNum,
                         int varCharNum, int floatNum,
                         int keyNum,
                         ArrayList<TupleForeign> tableForeign,int tableLineRate) {
        int totalNum = intNum + charNum + doubleNum + dateNum + varCharNum + floatNum;
        this.tableName = tableName;
        this.totalNum = totalNum;
        this.tableLineRate=tableLineRate;
        this.keyNum = keyNum;
        attNums[0] = intNum;
        attNums[1] = doubleNum + floatNum;
        attNums[2] = charNum + varCharNum;
        attNums[3] = dateNum;
        this.tf = tableForeign;
        for (int i = 0; i < intNum; i++) {
            tuples.add(new TupleInt(RandomGenerateTableAttributesVaule.tupleIntMin(),
                    RandomGenerateTableAttributesVaule.tupleIntRange()));
        }
        for (int i = 0; i < keyNum; i++) {
            tuples.get(i).makeKey();
        }

        for (int i = 0; i < doubleNum; i++) {
            tuples.add(new TupleDecimal(RandomGenerateTableAttributesVaule.tupleDoubleMin(),
                    RandomGenerateTableAttributesVaule.tupleDoubleRange()));
        }
        for (int i = 0; i < floatNum; i++) {
            tuples.add(new TupleFloat(RandomGenerateTableAttributesVaule.tupleDoubleMin(),
                    RandomGenerateTableAttributesVaule.tupleDoubleRange()));
        }
        for (int i = 0; i < charNum; i++) {
            tuples.add(new TupleChar(false, false));
        }
        for (int i = 0; i < varCharNum; i++) {
            tuples.add(new TupleChar(true, false));
        }
        for (int i = 0; i < dateNum; i++) {
            tuples.add(new TupleDate());
        }
        int index = 1;
        for (TupleForeign tupleForeign : tf) {
            ArrayList<TupleType> refTuples = tupleForeign.getTupleTypes();
            for (TupleType refTuple : refTuples) {
                tuples.set(index++, refTuple);
            }
        }
        TupleType temptype=tuples.get(0);
        temptype.setMax((int)temptype.getMin()+tableLineRate);

//        for (int i = keyNum - 1; i < totalNum; i++) {
//            int randomIndex = i + (int) (Math.random() * (totalNum - i));
//            TupleType temp = tuples.get(i);
//            tuples.set(i, tuples.get(randomIndex));
//            tuples.set(randomIndex, temp);
//        }
    }

    public String getTableName() {
        return tableName;
    }

    public int getTableLineRate()
    {
        return tableLineRate;
    }

    public ArrayList<TupleType> getTuples() {
        return tuples;
    }

    public int[] getAttNums() {
        return attNums;
    }

    public int getTableAttNum() {
        return totalNum;
    }

    public int getKeyNum() {
        return keyNum;
    }

    public int getSecondIndexNum() {
        return totalNum - keyNum;
    }

    public String toSql() {
        StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName + "(");
        for (int i = 0; i < totalNum; i++) {
            sql.append("tv").append(String.valueOf(i)).append(" ")
                    .append(tuples.get(i).getTupleType()).append(",");
        }
        if (keyNum > 0) {
            sql.append("PRIMARY KEY (tv0");
            for (int i = 1; i < keyNum; i++) {
                sql.append(",tv").append(String.valueOf(i));
            }
            sql.append(")");
        } else {
            sql.substring(0, sql.length() - 1);
        }
        if (tf.size() != 0) {
            int start = 1;
            for (TupleForeign tupleForeign : tf) {
                int tempSize = tupleForeign.getTupleTypes().size();
                sql.append(',');
                sql.append("FOREIGN KEY(");
                sql.append(generateForeignListFromArray(start, tempSize));
                sql.append(")references t").append(String.valueOf(tupleForeign.getTableLoc())).append('(');
                sql.append(generateForeignListFromArray(0, tempSize));
                sql.append(')');
                start += tempSize;
            }
        }
        sql.append(");");
        return sql.toString();
    }

    private String generateForeignListFromArray(int start, int range) {

        StringBuilder attributesStr = new StringBuilder("tv" + String.valueOf(start));
        for (int i = 1; i < range; i++) {
            attributesStr.append(',').append("tv").append(String.valueOf(start + i));
        }
        return attributesStr.toString();
    }

}
