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
    }

    void run(int threadsNum, int runTimes) {
        int index = 0;
        for (Transaction t : tt) {
            System.out.println(index++);
            ArrayList<String> sqls = t.getSql();
            for (String sql : sqls) {
                System.out.println(sql);
            }
        }
        Thread[] threads = new Thread[threadsNum];
        for (int i = 0; i < threadsNum; i++) {
            threads[i] = new MultiThreadSql(i, tt, runTimes);
        }
        for (int i = 0; i < threadsNum; i++) {
            threads[i].start();
        }
    }
}
