package load.generator.utils;

import load.generator.template.Transaction;

import java.util.Random;

public class MultiThreadSql extends Thread {
    private Transaction[] tts;
    private int runTimes;
    private int indexThread;
    private Random r = new Random();

    public MultiThreadSql(int indexThread, Transaction[] tts, int runTimes) {
        this.tts = tts.clone();
        this.runTimes = runTimes;
        this.indexThread = indexThread;
    }

    @Override
    public void run() {
        int num = tts.length;
        for (int i = 0; i < runTimes; i++) {
            if (i % 100 == 0) {
                System.out.println("第"+String.valueOf(indexThread) + "个线程执行到" + String.valueOf(i)+"次");
            }
            int index = r.nextInt(num);
            tts[index].executeSql();
        }
    }
}
