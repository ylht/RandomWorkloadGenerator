package load.generator.template;

import load.generator.template.tuple.*;

import java.util.ArrayList;

/**
 * @author wangqingshuai
 */

public class TableTemplate {

    private String tableName;


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
                         int keyNum) {
        int totalNum = intNum + charNum + doubleNum + dateNum;
        this.tableName = tableName;
        this.totalNum = totalNum;
        this.keyNum = keyNum;

        for (int i = 0; i < intNum; i++) {
            tuples.add(new TupleInt());
        }
        for (int i = 0; i < keyNum; i++) {
            tuples.get(i).makeKey();
        }

        for (int i = 0; i < doubleNum; i++) {
            tuples.add(new TupleDouble());
        }
        for (int i = 0; i < charNum; i++) {
            tuples.add(new TupleChar());
        }
        for (int i = 0; i < dateNum; i++) {
            tuples.add(new TupleDate());
        }
        if (keyNum == 0) {
            keyNum = 1;
        }

        for (int i = keyNum - 1; i < totalNum; i++) {
            int randomIndex = i + (int) (Math.random() * (totalNum - i));
            TupleType temp = tuples.get(i);
            tuples.set(i, tuples.get(randomIndex));
            tuples.set(randomIndex, temp);
        }
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
        StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName + "{\n");
        for (int i = 0; i < totalNum; i++) {
            sql.append("tv").append(String.valueOf(i)).append(" ")
                    .append(tuples.get(i).getTupleType()).append(",\n");
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
        sql.append(")");
        return sql.toString();
    }

}
