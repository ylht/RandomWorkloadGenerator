package com.wqs.template.transaction;

import com.wqs.base.RandomControl;
import com.wqs.template.table.TableTemplate;

/**
 * @author wangqingshuai
 */
public class ProduceTransaction {
    private int tableNum;
    private TableTemplate[] tables;



    public ProduceTransaction()
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
