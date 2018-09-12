package load.generator.main;

import load.generator.generator.RandomGenerateTableAttributesVaule;
import load.generator.template.TableTemplate;
import load.generator.template.tuple.TupleForeign;
import load.generator.template.tuple.TupleType;
import load.generator.utils.MysqlConnector;
import load.generator.utils.TableRefList;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.min;

class CreateTable {
    private MysqlConnector mc = new MysqlConnector();

    private TableTemplate[] tables;

    CreateTable() {
        RandomGenerateTableAttributesVaule rgta = RandomGenerateTableAttributesVaule.getInstance();
        int tableNum = rgta.tableNum();
        tables = new TableTemplate[tableNum];
        TableRefList trList = TableRefList.getInstance();
        for (int tableIndex = 0; tableIndex < tableNum; tableIndex++) {
            int intNum = rgta.intNum();
            int keyNum = rgta.keyNum(intNum, tableIndex);
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
            int level = trList.getLevel(tableIndex);
            int tableLineRate = rgta.tableLineRate(level);
            tables[tableIndex] = new TableTemplate("t" + String.valueOf(tableIndex), intNum,
                    rgta.decimalNum(),
                    rgta.charNum(),
                    rgta.dateNum(),
                    rgta.varcharNum(),
                    rgta.floatNum(),
                    keyNum, tfs, tableLineRate
            );

        }

    }

    TableTemplate[] getTables() {
        return tables;
    }

    boolean work() {
        String drop = "DROP TABLE IF EXISTS t";
        for (int i = 9; i > -1; i--) {
            mc.excuteSql(drop + String.valueOf(i));
        }
        String[] tableSql = new String[tables.length];
        int num = 0;
        for (TableTemplate i : tables) {
            tableSql[num++] = i.toSql();
        }
        for (String i : tableSql) {
            if (!mc.excuteSql(i)) {
                return false;
            }
        }
        return true;
    }

}
