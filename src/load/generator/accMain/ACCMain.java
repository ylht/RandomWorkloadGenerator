package load.generator.accMain;

import load.generator.generator.GeneratorTable;
import load.generator.template.TransactionTemplate;

public class ACCMain {
    public static void main(String[] args) {
        GeneratorTable gt = new GeneratorTable();
        CreateTable ct=new CreateTable(gt.getAllTable());
        System.out.println(ct.work());
//        LoadData ld=new LoadData(gt.getTables());
//        ld.load();
        TransactionWork tw=new TransactionWork(gt.getTables());
        TransactionTemplate[] tt=tw.getTran();
        for(TransactionTemplate t :tt)
        {
            t.getSql();
        }
    }

}
