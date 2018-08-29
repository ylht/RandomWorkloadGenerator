package load.generator.generator;

import load.generator.template.TableTemplate;
import load.generator.template.tuple.TupleForeign;
import load.generator.template.tuple.TupleType;
import load.generator.utils.TableRefList;

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
        RandomGenerateTableAttributesVaule rgta = RandomGenerateTableAttributesVaule.getInstance();
        int tableNum = rgta.tableNum();
        tables = new TableTemplate[tableNum];
        Random r = new Random();
        TableRefList trList = new TableRefList();
        for (int tableIndex = 0; tableIndex < tableNum; tableIndex++) {
            int intNum = rgta.intNum();
            int keyNum = rgta.keyNum(intNum);
            if (tableIndex == 0) {
                keyNum = 1;
            }
            ArrayList<TupleForeign> tfs = new ArrayList<>();
            int foreignKeyNum = rgta.foreignKeyNum(keyNum, intNum);

            ArrayList<Integer> parent = new ArrayList<>();
            while (foreignKeyNum > 0 && trList.isHasTable()) {
                int index = trList.randomGetNode();
                parent.add(index);
                TableTemplate refTable = tables[index];
                int refKeyNum = refTable.getKeyNum();
                int refNum = min(refKeyNum, foreignKeyNum);
                ArrayList<TupleType> tuples = new ArrayList<>();
                ArrayList<TupleType> reTuples = refTable.getTuples();
                for (int i = 0; i < refNum; i++) {
                    tuples.add(reTuples.get(i));
                }
                TupleForeign tf = new TupleForeign(index, tuples);
                tfs.add(tf);
                foreignKeyNum -= refKeyNum;
            }
            trList.addNode(parent);
            tables[tableIndex] = new TableTemplate("t" + String.valueOf(tableIndex), intNum,
                    rgta.decimalNum(),
                    rgta.charNum(),
                    rgta.dateNum(),
                    rgta.varcharNum(),
                    rgta.floatNum(),
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
