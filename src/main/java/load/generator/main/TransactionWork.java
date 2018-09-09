package load.generator.main;

import load.generator.generator.RandomGenerateSqlAttributesValue;
import load.generator.template.TableTemplate;
import load.generator.template.Transaction;
import load.generator.utils.MultiThreadSql;

import java.util.ArrayList;

class TransactionWork {

    private Transaction[] tt = new Transaction[RandomGenerateSqlAttributesValue.tranNum()];

    TransactionWork(TableTemplate[] tables) {
        for (int i = 0; i < tt.length; i++) {
            tt[i] = new Transaction(tables);
        }
        System.out.println("事务生成完成，事务数为"+tt.length);
        int index=0;
        for(Transaction t:tt)
        {
            System.out.println("第"+index+++"个事务为：");
            t.printSqls();
        }
    }

    void run(int threadsNum, int runTimes) {
        System.out.println("开始执行，线程数为"+String.valueOf(threadsNum)+"，执行次数为"+String.valueOf(runTimes));
        Thread[] threads = new Thread[threadsNum];
        for (int i = 0; i < threadsNum; i++) {
            threads[i] = new MultiThreadSql(i, tt, runTimes);
        }
        for (int i = 0; i < threadsNum; i++) {
            threads[i].start();
        }
    }
}
