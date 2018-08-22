package load.generator.main;

import load.generator.generator.GeneratorTable;

public class AccMain {
    public static void main(String[] args) {
        GeneratorTable gt = new GeneratorTable();
        CreateTable ct = new CreateTable(gt.getAllTable());
        ct.work();

        LoadData ld = new LoadData(gt.getTables());
        ld.load();

        TransactionWork tw = new TransactionWork(gt.getTables());
        tw.run(10, 100);
    }

}
