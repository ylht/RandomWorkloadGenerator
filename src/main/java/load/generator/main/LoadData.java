package load.generator.main;

import load.generator.generator.random.*;
import load.generator.template.TableTemplate;
import load.generator.template.tuple.TupleChar;
import load.generator.template.tuple.TupleType;
import load.generator.utils.KeyValue;
import load.generator.utils.MysqlConnector;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerArray;

import static java.lang.Float.min;

public class LoadData {
    private ArrayList<ArrayList<RandomValue>> randomLists = new ArrayList<ArrayList<RandomValue>>();
    private ArrayList<Integer> keyNums = new ArrayList<Integer>();
    private MysqlConnector mysqlConnector = new MysqlConnector();

    LoadData(TableTemplate[] tables) {
        for (TableTemplate table : tables) {
            ArrayList<TupleType> tt = table.getTuples();
            ArrayList<RandomValue> rv = new ArrayList<RandomValue>();
            for (TupleType t : tt) {
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
                                temprv = new RandomChar((int) t.getMin(), (int) t.getMax(), false);
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
                        temprv = new RandomDate(false);
                        break;
                    default:
                        System.out.println("没有匹配到应生成的随机数据类型");
                        System.exit(-1);
                }
                rv.add(temprv);
            }
            randomLists.add(rv);
            keyNums.add(table.getKeyNum());
        }
    }

    private String getValues(ArrayList<RandomValue> randomList, AtomicIntegerArray keyValues) {
        int keyNum=keyValues.length();
        StringBuilder values = new StringBuilder("(");
        for (int j = 0; j < keyNum; j++) {
            values.append(keyValues.get(j)).append(',');
        }
        for (int j = keyNum; j < randomList.size(); j++) {
            values.append(randomList.get(j).getValue()).append(',');
        }
        values.deleteCharAt(values.length() - 1);
        values.append(')');
        return values.toString();
    }

    public boolean load() {
        for (int current = 0; current < randomLists.size(); current++) {
            System.out.println("开始导入第" + String.valueOf(current) + "张表");
            int tableLineNum = 1;

            ArrayList<RandomValue> randomList = randomLists.get(current);
            KeyValue keys=new KeyValue(randomList.subList(0,keyNums.get(current)));

            tableLineNum *= keys.getLines();

            while (tableLineNum > 0) {
                StringBuilder sql = new StringBuilder("INSERT INTO t" + String.valueOf(current) +
                        " values ");
                int count = 0;
                while (count < min(tableLineNum, 100)) {
                    count++;
                    AtomicIntegerArray keyVaules=keys.getInsertValue();
                    String values = getValues(randomList, keyVaules);
                    sql.append(values).append(',');
                }
                sql.deleteCharAt(sql.length() - 1);
                sql.append(";");
                mysqlConnector.excuteSql(sql.toString());
                tableLineNum -= count;
            }
        }
        return true;
    }
}
