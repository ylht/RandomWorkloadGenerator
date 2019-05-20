package load.generator.main;

import load.generator.utils.LoadConfig;

public class AccMain {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        CreateTable ct = new CreateTable();
        if (ct.work()) {
            System.out.println("表格建立完成！");
        }
        LoadData ld = new LoadData(ct.getTables());
        ld.load();
        System.out.println("数据导入完成！");
        TransactionWork tw = new TransactionWork(ct.getTables());
        long end=System.currentTimeMillis();
        System.out.println(end-start);
        try {
            tw.run(Integer.valueOf(LoadConfig.getConfig().valueOf("/generator/threadNum")),
                    Integer.valueOf(LoadConfig.getConfig().valueOf("/generator/runCount")));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
