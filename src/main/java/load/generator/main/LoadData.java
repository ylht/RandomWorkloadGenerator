package load.generator.main;

import load.generator.generator.random.*;
import load.generator.template.TableTemplate;
import load.generator.template.tuple.TupleChar;
import load.generator.template.tuple.TupleType;
import load.generator.utils.MysqlConnector;

import java.util.ArrayList;

public class LoadData {
    private ArrayList<ArrayList<RandomValue>> randomLists = new ArrayList<ArrayList<RandomValue>>();
    private ArrayList<Integer> keyNums = new ArrayList<>();
    private int[] temp = new int[200];
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
                                temprv = new RandomChar((int) t.getMax(), (RandomInt) rv.get(0));
                                break;
                            case 1:
                                temprv = new RandomChar((int) t.getMin(), (int) t.getMax(), false);
                                break;
                            case 2:
                                temprv = new RandomChar(chart.getcT(), false);
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

    private String getValues(ArrayList<RandomValue> randomList, int keyNum) {
        StringBuilder values = new StringBuilder("(");
        int i;
        for (i = 0; i < keyNum; i++) {
            RandomInt ri = (RandomInt) (randomList.get(i));
            String nv = ri.getNext();
            values.append(nv).append(',');
            if (temp[i] > Integer.valueOf(nv)) {
                temp[i] = Integer.valueOf(nv);
                if (i == keyNum - 1) {
                    return null;
                }
            } else {
                temp[i++] = Integer.valueOf(nv);
                break;
            }
        }
        for (; i < keyNum; i++) {
            String t = ((RandomInt) (randomList.get(i))).getSameValue();
            temp[i] = Integer.valueOf(t);
            values.append(t).append(',');
        }

        for (int j = keyNum; j < randomList.size(); j++) {
            values.append(randomList.get(j).getValue()).append(',');
        }
        values.deleteCharAt(values.length() - 1);
        values.append(')');
        return values.toString();
    }

    public boolean load() {
        int current = 0;
        for (ArrayList<RandomValue> randomList : randomLists) {
            while (true) {
                StringBuilder sql = new StringBuilder("INSERT INTO t" + String.valueOf(current) +
                        " values");
                int count = 1;
                boolean continueExe = true;
                while (true) {
                    if (count++ > 100) {
                        break;
                    }
                    String values = getValues(randomList, keyNums.get(current));
                    if (values != null) {
                        sql.append(values).append(',');
                    } else {
                        continueExe = false;
                        if (sql.charAt(sql.length() - 1) != ',') {
                            sql = null;
                        } else {
                            sql.deleteCharAt(sql.length() - 1);
                            sql.append(";");
                        }
                        break;
                    }
                }
                if (sql != null) {
                    sql.deleteCharAt(sql.length() - 1);
                    sql.append(";");
                    mysqlConnector.excuteSql(sql.toString());
                }

                if (!continueExe) {
                    break;
                }
            }
            current++;
            for (int j = 0; j < temp.length; j++) {
                temp[j] = 0;
            }
        }
        return true;
    }
}
