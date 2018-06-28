package load.generator.generator;

import load.generator.base.random.SqlRandom;
import load.generator.base.random.TableRandom;
import load.generator.template.ConditionTemplate;
import load.generator.template.SqlTemplate;
import load.generator.template.TableTemplate;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author wangqingshuai
 */
public class Generator {
    private TableTemplate[] tables;
    private String[] selectTemplates;
    private String[] updateTemplates;
    private String[] insertTemplates;
    private String[] deleteTemplates;

    public Generator() {
        int tableNum = TableRandom.getTableNum();
        tables = new TableTemplate[tableNum];
        for (int i = 0; i < tableNum; i++) {
            int intNum = TableRandom.getTableIntNum();
            tables[i] = new TableTemplate("t" + String.valueOf(i), intNum,
                    TableRandom.getTableDoubleNum(),
                    TableRandom.getTableCharNum(),
                    TableRandom.getTableDateNum(),
                    TableRandom.getTableKeyNum(intNum));
        }
        SqlTemplate st = new SqlTemplate();
        ConditionTemplate ct = new ConditionTemplate();
        selectTemplates = new String[SqlRandom.getSelectNum()];
        for (int i = 0; i < selectTemplates.length; i++) {
            int randomTableIndex = SqlRandom.getRandomTable(tables.length);
            TableTemplate randomTable = tables[randomTableIndex];
            String[] randomSelectAtt = getRandomAllAttributes(randomTable, SqlRandom.getSelectAttributesNum(randomTable.getTableAttNum()));
            String randomTableName = "t" + String.valueOf(randomTableIndex);
            String[] randomConditionAtt = getRandomKeyAttributes(randomTable, SqlRandom.getConditionNum(randomTable.getKeyNum()));
            selectTemplates[i] = st.selectTemplate(randomSelectAtt) +
                    ct.singleTable(randomTableName)
                    + ct.singleCondition(randomConditionAtt);
        }
        updateTemplates = new String[SqlRandom.getUpdateNum()];
        for (int i = 0; i < updateTemplates.length; i++) {
            int randomTableIndex = SqlRandom.getRandomTable(tables.length);
            TableTemplate randomTable = tables[randomTableIndex];
            String[] randomUpdateAtt = getRandomAllAttributes(randomTable, SqlRandom.getUpdateAttributesNum(randomTable.getTableAttNum()));
            String randomTableName = "t" + String.valueOf(randomTableIndex);
            String[] randomConditionAtt = getRandomKeyAttributes(randomTable, SqlRandom.getConditionNum(randomTable.getKeyNum()));
            updateTemplates[i] = st.updateTemplate(randomTableName, randomUpdateAtt) + "where " +
                    ct.singleCondition(randomConditionAtt);
        }
        deleteTemplates = new String[SqlRandom.getDeleteNum()];
        for (int i = 0; i < deleteTemplates.length; i++) {
            int randomTableIndex = SqlRandom.getRandomTable(tables.length);
            TableTemplate randomTable = tables[randomTableIndex];
            String randomTableName = "t" + String.valueOf(randomTableIndex);
            String[] randomConditionAtt = getRandomKeyAttributes(randomTable, SqlRandom.getConditionNum(randomTable.getKeyNum()));
            deleteTemplates[i] = st.deleteTemplate() +
                    ct.singleTable(randomTableName) +
                    ct.singleCondition(randomConditionAtt);
        }
        insertTemplates = new String[SqlRandom.getInsertNum()];
        for (int i = 0; i < insertTemplates.length; i++) {
            int randomTableIndex = SqlRandom.getRandomTable(tables.length);
            TableTemplate randomTable = tables[randomTableIndex];
            String randomTableName = "t" + String.valueOf(randomTableIndex);
            String[] randomInsertAtt = getRandomSecondIndexAttributes(randomTable, SqlRandom.getInsertAttributesNum
                    (randomTable.getSecondIndexNum()));
            insertTemplates[i] = st.insertTemplate(randomTableName, randomInsertAtt, randomTable.getKeyNum());
        }
    }

    private ArrayList<Integer> getRandomList(int begin, int end) {
        ArrayList<Integer> randomIndex = new ArrayList<Integer>();
        for (int i = begin; i < end; i++) {
            randomIndex.add(i);
        }
        Collections.shuffle(randomIndex);
        return randomIndex;
    }

    private String[] getAttributes(ArrayList<Integer> al, int totalNum) {
        String[] result = new String[totalNum];
        for (int i = 0; i < totalNum; i++) {
            result[i] = "tv" + String.valueOf(al.get(i));
        }
        return result;
    }

    private String[] getRandomAllAttributes(TableTemplate table, int randomNum) {
        ArrayList<Integer> randomIndex = getRandomList(0, table.getTableAttNum());
        return getAttributes(randomIndex, randomNum);
    }

    private String[] getRandomKeyAttributes(TableTemplate table, int randomNum) {
        ArrayList<Integer> randomIndex = getRandomList(0, table.getKeyNum());
        return getAttributes(randomIndex, randomNum);
    }

    private String[] getRandomSecondIndexAttributes(TableTemplate table, int randomNum) {
        ArrayList<Integer> randomIndex = getRandomList(table.getKeyNum(), table.getTableAttNum());
        return getAttributes(randomIndex, randomNum);
    }

    public TableTemplate[] getTables() {
        return tables;
    }

    public void printAllTable() {
        for (TableTemplate i : tables) {
            System.out.println(i.toSql());
        }
    }

    public void printAllSelectSql() {
        for (String i : selectTemplates) {
            System.out.println(i);
        }
    }

    public void printAllUpdateSql() {
        for (String i : updateTemplates) {
            System.out.println(i);
        }
    }

    public void printAllInsertSql() {
        for (String i : insertTemplates) {
            System.out.println(i);
        }
    }

    public void printAllDeleteSql() {
        for (String i : deleteTemplates) {
            System.out.println(i);
        }
    }
}
