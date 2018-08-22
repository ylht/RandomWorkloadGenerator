package load.generator.generator;

import load.generator.template.TableTemplate;
import load.generator.template.tuple.TupleForeign;
import load.generator.template.tuple.TupleType;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.min;

/**
 * @author wangqingshuai
 */
public class GeneratorTable {
    Random r = new Random();
    private TableTemplate[] tables;

    public GeneratorTable() {
        int tableNum = RandomGenerateTableAttributesVaule1.tableNum();
        tables = new TableTemplate[tableNum];
        Random r = new Random();
        for (int i = 0; i < tableNum; i++) {
            int intNum = RandomGenerateTableAttributesVaule1.intNum();
            int keyNum = 0;
            if (i > 0) {
                keyNum = r.nextInt(min(min(intNum, i), 4)) + 1;
            } else {
                keyNum = 1;
            }
            int foreignKeyNum = keyNum - 1;
            ArrayList<TupleForeign> tfs = new ArrayList<TupleForeign>();
            if (foreignKeyNum > 0) {
                int total = i;
                ArrayList<Integer> allTableFront = new ArrayList<Integer>();
                for (int j = 0; j < total; j++) {
                    allTableFront.add(j);
                }

                int index1 = r.nextInt(total);
                int index2 = -1;
                allTableFront.remove(index1);
                total--;
                int[] arr = new int[min(tables[index1].getKeyNum(), foreignKeyNum)];
                int[] self = new int[arr.length];
                int[] rmin = new int[arr.length];
                int[] rmax = new int[arr.length];
                ArrayList<TupleType> tt = tables[index1].getTuples();
                for (int j = 0; j < arr.length; j++) {
                    arr[j] = j;
                    self[j] = j + 1;
                    rmin[j] = (int) tt.get(j).getMin();
                    rmax[j] = (int) tt.get(j).getMax();
                }
                TupleForeign tempf = new TupleForeign(index1, self, arr, rmin, rmax);
                tfs.add(tempf);
                if (tables[index1].getKeyNum() < foreignKeyNum) {

                    ArrayList<TupleForeign> tf = tables[index1].getTf();
                    if (tf.size() > 0) {
                        for (TupleForeign aTf : tf) {
                            allTableFront.remove((Object) aTf.getTableLoc());
                            total--;
                        }
                    }

                    index2 = r.nextInt(total);
                    int[] arr2 = new int[min(foreignKeyNum - arr.length, tables[allTableFront.get(index2)].getKeyNum())];
                    int[] self2 = new int[arr2.length];
                    int[] rmin2 = new int[arr2.length];
                    int[] rmax2 = new int[arr2.length];
                    ArrayList<TupleType> tt2 = tables[allTableFront.get(index2)].getTuples();
                    for (int j = 0; j < arr2.length; j++) {
                        arr2[j] = j;
                        self2[j] = arr2[j] + arr.length + 1;
                        rmin2[j] = (int) tt2.get(j).getMin();
                        rmax2[j] = (int) tt2.get(j).getMax();
                    }
                    tfs.add(new TupleForeign(allTableFront.get(index2), self2, arr2, rmin2, rmax2));
                }
            }
            tables[i] = new TableTemplate("t" + String.valueOf(i), intNum,
                    RandomGenerateTableAttributesVaule1.decimalNum(),
                    RandomGenerateTableAttributesVaule1.charNum(),
                    RandomGenerateTableAttributesVaule1.dateNum(),
                    RandomGenerateTableAttributesVaule1.varcharNum(),
                    RandomGenerateTableAttributesVaule1.floatNum(),
                    keyNum, tfs
            );
        }

    }


    public TableTemplate[] getTables() {
        return tables;
    }

    public String[] getAllTable() {
        String[] tableSql = new String[tables.length];
        int num = 0;
        for (TableTemplate i : tables) {
            tableSql[num++] = i.toSql();
        }
        return tableSql;
    }
}
