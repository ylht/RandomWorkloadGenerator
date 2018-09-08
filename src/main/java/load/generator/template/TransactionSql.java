package load.generator.template;

import load.generator.generator.random.RandomValue;
import load.generator.utils.KeyValue;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerArray;

class TransactionSql {

    private static KeyValue[] tableKeys;
    private sqlTypes sqlType;
    private PreparedStatement pstmt;
    private RandomValue[] randomValues;
    private int tableIndex;
    TransactionSql(PreparedStatement pstmt, RandomValue[] randomValues,
                   int tableIndex, sqlTypes sqlType) {
        this.pstmt=pstmt;
        this.randomValues = randomValues;
        this.tableIndex = tableIndex;
        this.sqlType = sqlType;
    }

    static void setTableKeys(KeyValue[] inputTableKeys) {
        tableKeys = inputTableKeys;
    }

    PreparedStatement getPstmt() {
        return pstmt;
    }

    ArrayList<String> getSqlValues() {
        AtomicIntegerArray intValue;
        ArrayList<Integer> randomKeys;
        ArrayList<String> stringValue;
        switch (sqlType) {
            case SELECT:
                randomKeys = tableKeys[tableIndex].getRandomValue();
                stringValue = new ArrayList<>();
                for (Integer randomKey : randomKeys) {
                    stringValue.add(String.valueOf(randomKey));
                }
                return stringValue;
            case UPDATE:
                randomKeys = tableKeys[tableIndex].getRandomValue();
                stringValue = new ArrayList<>();
                for (RandomValue randomValue : randomValues) {
                    stringValue.add(randomValue.getValue());
                }
                for (Integer randomKey : randomKeys) {
                    stringValue.add(String.valueOf(randomKey));
                }
                return stringValue;
            case INSERT:
                intValue = tableKeys[tableIndex].getInsertValue();
                stringValue = new ArrayList<>();
                for (int j = 0; j < intValue.length(); j++) {
                    stringValue.add(String.valueOf(intValue.get(j)));
                }
                for (RandomValue randomValue : randomValues) {
                    stringValue.add(randomValue.getValue());
                }
                return stringValue;
            case DELETE:
                intValue = tableKeys[tableIndex].getDeleteValue();
                stringValue = new ArrayList<>();
                for (int j = 0; j < intValue.length(); j++) {
                    stringValue.add(String.valueOf(intValue.get(j)));
                }
                return stringValue;
            default:
                return null;
        }

    }

    public enum sqlTypes {
        //四种对应的语句类型
        SELECT, UPDATE, DELETE, INSERT
    }
}
