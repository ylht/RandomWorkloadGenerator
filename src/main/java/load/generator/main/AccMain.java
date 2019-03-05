package load.generator.main;

public class AccMain {
    public static void main(String[] args) {
        CreateTable ct = new CreateTable();
        if (ct.work()) {
            System.out.println("表格建立完成！");
        }
        LoadData ld = new LoadData(ct.getTables());
        ld.load();
        System.out.println("数据导入完成！");
        TransactionWork tw = new TransactionWork(ct.getTables());
        tw.run(50, 50);
    }
}
