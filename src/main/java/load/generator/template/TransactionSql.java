package load.generator.template;

import load.generator.generator.random.RandomValue;
import load.generator.utils.KeyValue;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerArray;

class TransactionSql {

    public enum sqlTypes{
        //四种对应的语句类型
        SELECT, UPDATE, DELETE, INSERT}
    private sqlTypes sqlType;
    private static KeyValue[] tableKeys;
    private String sqlTemplate;
    private RandomValue[] randomValues;
    private int tableIndex;
    static void setTableKeys(KeyValue[] inputTableKeys)
    {
        tableKeys=inputTableKeys;
    }

    TransactionSql(String sqlTemplate, RandomValue[] randomValues,
                   int tableIndex, sqlTypes sqlType) {
        this.sqlTemplate = sqlTemplate;
        this.randomValues = randomValues;
        this.tableIndex = tableIndex;
        this.sqlType=sqlType;
    }

    String getSqlTemplate()
    {
        return sqlTemplate;
    }

    ArrayList<String> getSqlValues()
    {
        AtomicIntegerArray intValue;
        ArrayList<Integer> randomKeys;
        ArrayList<String>stringValue;
        switch (sqlType)
        {
            case SELECT:
                randomKeys = tableKeys[tableIndex].getRandomValue();
                stringValue=new ArrayList<>();
                for (Integer randomKey : randomKeys) {
                    stringValue.add(String.valueOf(randomKey));
                }
                return stringValue;
            case UPDATE:
                randomKeys = tableKeys[tableIndex].getRandomValue();
                stringValue=new ArrayList<>();
                for (RandomValue randomValue : randomValues) {
                    stringValue.add(randomValue.getValue());
                }
                for (Integer randomKey : randomKeys) {
                    stringValue.add(String.valueOf(randomKey));
                }
                return stringValue;
            case INSERT:
                intValue = tableKeys[tableIndex].getInsertValue();
                stringValue=new ArrayList<>();
                for(int j=0;j<intValue.length();j++)
                {
                    stringValue.add(String.valueOf(intValue.get(j)));
                }
                for (RandomValue randomValue : randomValues) {
                    stringValue.add(randomValue.getValue());
                }
                return stringValue;
            case DELETE:
                intValue = tableKeys[tableIndex].getDeleteValue();
                stringValue=new ArrayList<>();
                for(int j=0;j<intValue.length();j++)
                {
                    stringValue.add(String.valueOf(intValue.get(j)));
                }
                return stringValue;
            default:
                return null;
        }

    }
}
