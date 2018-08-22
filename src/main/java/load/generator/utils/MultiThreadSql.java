package load.generator.utils;

import load.generator.template.TransactionTemplate;

import java.util.Random;

public class MultiThreadSql extends Thread {
    private TransactionTemplate[] tts;
    private int runTimes;
    private int indexThread;
    private Random r = new Random();

    public MultiThreadSql(int indexThread, TransactionTemplate[] tts, int runTimes) {
        this.tts = tts.clone();
        this.runTimes = runTimes;
        this.indexThread = indexThread;
    }

    @Override
    public void run() {
        int num = tts.length;
        for (int i = 0; i < runTimes; i++) {
            int index = r.nextInt(num);
            tts[index].executeSql();
            if (i % 100 == 0) {
                System.out.println(String.valueOf(indexThread) + ',' + String.valueOf(i));
            }

        }
    }
}
