package load.generator.utils;

import load.generator.template.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLongArray;

public class MultiThreadSql extends Thread {
    private Transaction[] tts;
    private int runTimes;
    private int indexThread;
    private Random r = new Random();
    private CountDownLatch count;
    private static AtomicInteger runCount=new AtomicInteger(0);
    private static ArrayList<Long> latencyList =new ArrayList<>();
    static {
        Collections.synchronizedList(latencyList);
    }

    public MultiThreadSql(int indexThread, Transaction[] tts,
                          int runTimes,CountDownLatch count) {
        this.tts = tts.clone();
        for (Transaction tt : tts) {
            try {
                tt.setConn((new MysqlConnector()).getConn());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        this.runTimes = runTimes;
        this.indexThread = indexThread;
        this.count=count;

    }

    public int getRunCount(){
        return runCount.get();
    }

    public long latency(int percentage){
        Collections.sort(latencyList);
        int index=(latencyList.size()-1)*percentage/100;
        return latencyList.get(index);
    }

    @Override
    public void run() {
        int num = tts.length;
        for (int i = 0; i < runTimes; i++) {
            if (i % 1000 == 0) {
                System.out.println("第" + indexThread + "个线程执行到" + i + "次");
            }
            int index = r.nextInt(num);
            long start=System.currentTimeMillis();
            if(tts[index].executeSql()==1)
            {
                runCount.incrementAndGet();
                long end=System.currentTimeMillis();
                latencyList.add(end-start);
            }
        }
        count.countDown();
    }
}
