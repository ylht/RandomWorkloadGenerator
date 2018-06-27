package load.generator.generator;

import load.generator.base.RandomControl;
import load.generator.template.table.TableTemplate;

/**
 * @author wangqingshuai
 */
public class GenerateTable {
    private int tableNum;
    private TableTemplate[] tables;

    public GenerateTable()
    {
        tableNum=RandomControl.getTableNum();
        tables=new TableTemplate[tableNum];
        for(int i=0;i<tableNum;i++)
        {
            int intNum= RandomControl.getTableIntNum();
            tables[i]=new TableTemplate("t"+String.valueOf(i), intNum,
                    RandomControl.getTableDoubleNum(),
                    RandomControl.getTableCharNum(),
                    RandomControl.getTableDateNum(),
                    RandomControl.getTableKeyNum(intNum));
        }
    }

    public void printAllTable()
    {
        for(TableTemplate i:tables)
        {
            System.out.println(i.toSql());
        }
    }
}
