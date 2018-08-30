package load.generator.main;

public class AccMain {
    public static void main(String[] args) {
        for(int i=0;i<100;i++)
        {
            System.out.println("第"+String.valueOf(i)+"次执行");
            CreateTable ct = new CreateTable();
            if(ct.work())
            {
                System.out.println("表格建立完成！");
            }
            LoadData ld = new LoadData(ct.getTables());
            ld.load();
        }

//
//        TransactionWork tw = new TransactionWork(gt.getTables());
//        tw.run(10, 100);
    }

}
