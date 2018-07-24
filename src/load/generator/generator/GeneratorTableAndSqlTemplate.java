package load.generator.generator;

import load.generator.template.ConditionTemplate;
import load.generator.template.SqlTemplate;
import load.generator.template.TableTemplate;
import load.generator.template.tuple.TupleForeign;
import load.generator.template.tuple.TupleType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.lang.Math.min;

/**
 * @author wangqingshuai
 */
public class GeneratorTableAndSqlTemplate {
    private TableTemplate[] tables;
    private String[] selectTemplates;
    private String[] updateTemplates;
    private String[] insertTemplates;
    private String[] deleteTemplates;
    Random r =new Random();
    public GeneratorTableAndSqlTemplate() {
        int tableNum = RandomGenerateTableAttributesVaule.tableNum();
        tables = new TableTemplate[tableNum];
        Random r=new Random();
        for (int i = 0; i < tableNum; i++) {
            int intNum = RandomGenerateTableAttributesVaule.intNum();
            int keyNum=0;
            if(i>0){
                keyNum=r.nextInt(min(min(intNum,i),4))+1;
            }
            else {
                keyNum=1;
            }
            int foreignKeyNum=keyNum-1;
            ArrayList<TupleForeign> tfs=new ArrayList<TupleForeign>();
            if(foreignKeyNum>0)
            {
                int total=i;
                ArrayList<Integer> allTableFront=new ArrayList<Integer>();
                for(int j=0;j<total;j++)
                {
                    allTableFront.add(j);
                }

                    int index1=r.nextInt(total);
                    int index2=-1;
                    allTableFront.remove(index1);
                    total--;
                    int arr[]=new int[tables[index1].getKeyNum()];
                    int self[]=new int[arr.length];
                    int rmin[]=new int[arr.length];
                    int rmax[]=new int[arr.length];
                    ArrayList<TupleType> tt=tables[index1].getTuples();
                    for(int j=0;j<arr.length;j++)
                    {
                        arr[j]=j;
                        self[j]=j+1;
                        rmin[j]=(int)tt.get(j).getMin();
                        rmax[j]=(int)tt.get(j).getMax();
                    }
                    TupleForeign tempf=new TupleForeign(index1,self,arr,rmin,rmax);
                    tfs.add(tempf);
                    if(tables[index1].getKeyNum()<foreignKeyNum)
                    {

                        ArrayList<TupleForeign> tf=tables[index1].getTf();
                        if(tf.size()>0)
                        {
                            for (TupleForeign aTf : tf) {
                                allTableFront.remove((Object) aTf.getTableLoc());
                                total--;
                            }
                        }

                        index2=r.nextInt(total);
                        int arr2[]=new int[min(foreignKeyNum-arr.length,tables[allTableFront.get(index2)].getKeyNum())];
                        int self2[]=new int[arr2.length];
                        int rmin2[]=new int[arr2.length];
                        int rmax2[]=new int[arr2.length];
                        ArrayList<TupleType> tt2=tables[index2].getTuples();
                        for(int j=0;j<arr2.length;j++)
                        {
                            arr2[j]=j;
                            self2[j]=arr2[j]+arr.length+1;
                            rmin2[j]=(int)tt2.get(j).getMin();
                            rmax2[j]=(int)tt2.get(j).getMax();
                        }
                        tfs.add(new TupleForeign(index2,self2,arr2,rmin2,rmax2));


                    }
            }
            tables[i] = new TableTemplate("t" + String.valueOf(i), intNum,
                    RandomGenerateTableAttributesVaule.decimalNum(),
                    RandomGenerateTableAttributesVaule.charNum(),
                    RandomGenerateTableAttributesVaule.dateNum(),
                    RandomGenerateTableAttributesVaule.varcharNum(),
                    RandomGenerateTableAttributesVaule.floatNum(),
                    keyNum,tfs
                    );
        }
        SqlTemplate st = new SqlTemplate();
        ConditionTemplate ct = new ConditionTemplate();
        selectTemplates = new String[RandomGenerateSqlAttributesValue.selectNum()];
        for (int i = 0; i < selectTemplates.length; i++) {
            int randomTableIndex = RandomGenerateSqlAttributesValue.randomTable(tables.length);
            TableTemplate randomTable = tables[randomTableIndex];
            String[] randomSelectAtt = getRandomAllAttributes(randomTable, RandomGenerateSqlAttributesValue.selectAttributesNum(randomTable.getTableAttNum()));
            String randomTableName = randomTable.getTableName();
            String[] randomConditionAtt = getRandomKeyAttributes(randomTable, randomTable.getKeyNum());
            selectTemplates[i] = st.selectTemplate(randomSelectAtt) +
                    ct.singleTable(randomTableName)
                    + ct.singleCondition(randomConditionAtt);
        }
        updateTemplates = new String[RandomGenerateSqlAttributesValue.updateNum()];
        for (int i = 0; i < updateTemplates.length; i++) {
            int randomTableIndex = RandomGenerateSqlAttributesValue.randomTable(tables.length);
            TableTemplate randomTable = tables[randomTableIndex];
            String[] randomUpdateAtt = getRandomAllAttributes(randomTable, randomTable.getTableAttNum());
            String randomTableName = randomTable.getTableName();
            String[] randomConditionAtt = getRandomKeyAttributes(randomTable, randomTable.getKeyNum());
            updateTemplates[i] = st.updateTemplate(randomTableName, randomUpdateAtt) + "where " +
                    ct.singleCondition(randomConditionAtt);
        }
        deleteTemplates = new String[RandomGenerateSqlAttributesValue.deleteNum()];
        for (int i = 0; i < deleteTemplates.length; i++) {
            int randomTableIndex = RandomGenerateSqlAttributesValue.randomTable(tables.length);
            TableTemplate randomTable = tables[randomTableIndex];
            String randomTableName = randomTable.getTableName();
            String[] randomConditionAtt = getRandomKeyAttributes(randomTable,randomTable.getKeyNum());
            deleteTemplates[i] = st.deleteTemplate() +
                    ct.singleTable(randomTableName) +
                    ct.singleCondition(randomConditionAtt);
        }
        insertTemplates = new String[RandomGenerateSqlAttributesValue.insertNum()];
        for (int i = 0; i < insertTemplates.length; i++) {
            int randomTableIndex = RandomGenerateSqlAttributesValue.randomTable(tables.length);
            TableTemplate randomTable = tables[randomTableIndex];
            String randomTableName = randomTable.getTableName();
            String[] randomInsertAtt = getRandomSecondIndexAttributes(randomTable, randomTable.getSecondIndexNum());
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

    public String[] getAllTable() {
        String[] tableSql=new String[tables.length];
        int num=0;
        for (TableTemplate i : tables) {
            tableSql[num++]=i.toSql();
        }
        return tableSql;
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
