package load.generator.template;

import load.generator.generator.GenerateSqlListFromArray;
import load.generator.generator.RandomGenerateTableAttributesVaule;
import load.generator.template.tuple.*;
import load.generator.utils.RandomGenerator;

import java.util.ArrayList;

/**
 * @author wangqingshuai
 */

public class TableTemplate {

    private String tableName;

    private ArrayList<TupleForeign> tf;
    private int totalNum;
    private int keyNum;

    private ArrayList<TupleType> tuples = new ArrayList<TupleType>();

    public String getTableName() {
        return tableName;
    }

    public ArrayList<TupleType> getTuples() {
        return tuples;
    }

    public TableTemplate(String tableName,
                         int intNum, int doubleNum, int charNum, int dateNum,
                         int varCharNum,int floatNum,
                         int keyNum,
                         ArrayList<TupleForeign> tableForeign) {
        int totalNum = intNum + charNum + doubleNum + dateNum+varCharNum+floatNum;
        this.tableName = tableName;
        this.totalNum = totalNum;
        this.keyNum = keyNum;
        this.tf=tableForeign;
        for (int i = 0; i < intNum; i++) {
            tuples.add(new TupleInt(RandomGenerateTableAttributesVaule.tupleIntMin(),
                    RandomGenerateTableAttributesVaule.tupleIntRange()));
        }
        for (int i = 0; i < keyNum; i++) {
            if(i>=intNum)
            {
                break;
            }
            tuples.get(i).makeKey();
        }

        for (int i = 0; i < doubleNum; i++) {
            tuples.add(new TupleDecimal(RandomGenerateTableAttributesVaule.tupleDoubleMin(),
                    RandomGenerateTableAttributesVaule.tupleDoubleRange()));
        }
        for (int i=0;i<floatNum;i++)
        {
            tuples.add(new TupleFloat(RandomGenerateTableAttributesVaule.tupleDoubleMin(),
                    RandomGenerateTableAttributesVaule.tupleDoubleRange()));
        }
        for (int i = 0; i < charNum; i++) {
            tuples.add(new TupleChar(false,true));
        }
        for (int i =0 ;i<varCharNum;i++ )
        {
            tuples.add(new TupleChar(true,true));
        }
        for (int i = 0; i < dateNum; i++) {
            tuples.add(new TupleDate());
        }

        for(TupleForeign i:tf)
        {
            int num=0;
            for(Object j:i.getSelfTVLoc())
            {
                int temp=(int)j;
                tuples.get(temp).setMin(i.getRangeMin(num));
                tuples.get(temp).setMax(i.getRangeMax(num++));
            }
        }

//        for (int i = keyNum - 1; i < totalNum; i++) {
//            int randomIndex = i + (int) (Math.random() * (totalNum - i));
//            TupleType temp = tuples.get(i);
//            tuples.set(i, tuples.get(randomIndex));
//            tuples.set(randomIndex, temp);
//        }
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

    public ArrayList<TupleForeign> getTf() {
        return tf;
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
        if(tf.size()!=0)
        {
            int num=0;
            for(TupleForeign i:tf)
            {
                sql.append(',');
                sql.append("FOREIGN KEY(");
                sql.append(GenerateSqlListFromArray.generateForeignListFromArray(i.getSelfTVLoc()));
                sql.append(")references t").append(String.valueOf(i.getTableLoc())).append('(');
                sql.append(GenerateSqlListFromArray.generateForeignListFromArray(i.getRefTVLoc()));
                sql.append(')');
            }
        }
        sql.append(");");
        return sql.toString();
    }

}
