package load.generator.main;

import load.generator.generator.RandomGenerateSqlAttributesValue;
import load.generator.template.TableTemplate;
import load.generator.template.TransactionTemplate;
import load.generator.utils.MultiThreadSql;

class TransactionWork {

    private TransactionTemplate[] tt = new TransactionTemplate[RandomGenerateSqlAttributesValue.tranNum()];

    TransactionWork(TableTemplate[] tables) {
        for (int i = 0; i < tt.length; i++) {
            tt[i] = new TransactionTemplate(tables);
        }
    }

    void run(int threadsNum, int runTimes) {
        int index = 0;
        for (TransactionTemplate t : tt) {
            System.out.println(index++);
            System.out.println(t.getSql());
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
