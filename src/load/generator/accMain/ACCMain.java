package load.generator.accMain;

import load.generator.generator.GeneratorTableAndSqlTemplate;

public class ACCMain {
    public static void main(String[] args) {
        // write your code here
        GeneratorTableAndSqlTemplate gt = new GeneratorTableAndSqlTemplate();
//        for(String i:gt.getAllTable())
//        {
//            System.out.println(i);
//        }
        CreateTable ct=new CreateTable(gt.getAllTable());
        System.out.println(ct.work());

    }
}
