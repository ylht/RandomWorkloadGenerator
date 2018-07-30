package load.generator.accMain;

import load.generator.generator.RandomGenerateSqlAttributesValue;
import load.generator.template.TableTemplate;
import load.generator.template.TransactionTemplate;

public class TransactionWork {
    private TransactionTemplate[] tt=new TransactionTemplate[RandomGenerateSqlAttributesValue.tranNum()];
    private TableTemplate[] tables;
    public TransactionWork(TableTemplate[] tables)
    {
        this.tables=tables;
    }
    public TransactionTemplate[] getTran()
    {
        for(int i=0;i<tt.length;i++)
        {
            tt[i]= new TransactionTemplate(tables);
        }
        return tt;
    }

}
