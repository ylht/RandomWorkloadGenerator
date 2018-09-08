package load.generator.utils;


import load.generator.generator.random.RandomInt;
import load.generator.generator.random.RandomValue;
import load.generator.template.tuple.TupleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class KeyValue {
    private final AtomicIntegerArray deleteValues;
    private final AtomicIntegerArray insertValues;
    private AtomicIntegerArray maxRanges;
    private AtomicIntegerArray minRanges;
    private Random r = new Random();
    private int maxLines = 1;
    private Integer minLine = 0;

    private int minLevel;
    private int deleteNum = 0;
    private int maxLevel;
    private int insertNum = 0;
    private int otherNum;

    public KeyValue(List<RandomValue> randomValues) {
        int len = randomValues.size();
        maxRanges = new AtomicIntegerArray(len);
        minRanges = new AtomicIntegerArray(len);
        deleteValues = new AtomicIntegerArray(len);
        insertValues = new AtomicIntegerArray(len);
        int index = 0;
        for (RandomValue randomValue : randomValues) {
            maxRanges.addAndGet(index, ((RandomInt) randomValue).getMax());
            minRanges.addAndGet(index, ((RandomInt) randomValue).getMin());
            deleteValues.addAndGet(index, ((RandomInt) randomValue).getMin());
            insertValues.addAndGet(index, ((RandomInt) randomValue).getMax() - 1);
            index++;
        }
        for (int i = 0; i < maxRanges.length(); i++) {
            maxLines *= maxRanges.get(i) - minRanges.get(i);
        }
        minLevel = minRanges.get(0) + 1;
        maxLevel = maxRanges.get(0);
        otherNum = maxLines / (maxLevel - minLevel + 1);
    }

    public KeyValue(List<TupleType> randomValues, boolean forTransaction) {
        int len = randomValues.size();
        maxRanges = new AtomicIntegerArray(len);
        minRanges = new AtomicIntegerArray(len);
        deleteValues = new AtomicIntegerArray(len);
        insertValues = new AtomicIntegerArray(len);
        int index = 0;
        for (TupleType tuple : randomValues) {
            maxRanges.addAndGet(index, (Integer) tuple.getMax());
            minRanges.addAndGet(index, (Integer) tuple.getMin());
            deleteValues.addAndGet(index, (Integer) tuple.getMin());
            insertValues.addAndGet(index, (Integer) tuple.getMax() - 1);
            index++;
        }
        for (int i = 0; i < maxRanges.length(); i++) {
            maxLines *= maxRanges.get(i) - minRanges.get(i);
        }
        minLevel = minRanges.get(0) + 1;
        maxLevel = maxRanges.get(0);
        otherNum = maxLines / (maxLevel - minLevel + 1);
    }

    private void valuesAdd(AtomicIntegerArray values) {
        int i = values.length() - 1;
        for (; i > -1; i--) {
            if (values.get(i) == maxRanges.get(i) - 1) {
                if (i == 0) {
                    values.getAndIncrement(i);
                } else {
                    values.set(i, minRanges.get(i));
                }
            } else {
                values.getAndIncrement(i);
                break;
            }
        }
    }


    public ArrayList<Integer> getRandomValue() {
        ArrayList<Integer> result = new ArrayList<>();
        result.add(r.nextInt(maxLevel - minLevel) + minLevel);
        for (int i = 1; i < maxRanges.length(); i++) {
            result.add(
                    r.nextInt(maxRanges.get(i) - minRanges.get(i))
                            + minRanges.get(i));
        }
        return result;
    }

    synchronized public AtomicIntegerArray getDeleteValue() {

        AtomicIntegerArray result = new AtomicIntegerArray(deleteValues.length());
        for (int i = 0; i < deleteValues.length(); i++) {
            result.set(i, deleteValues.get(i));
        }
        if (minLine < maxLines) {
            deleteNum++;
            if (deleteNum == otherNum) {
                deleteNum = 0;
                minLevel++;
            }
            minLine++;
            valuesAdd(deleteValues);
        }
        return result;
    }

    synchronized public AtomicIntegerArray getInsertValue() {
        insertNum++;
        maxLines++;
        if (insertNum == otherNum) {
            insertNum = 0;
            maxLevel++;
        }

        valuesAdd(insertValues);
        AtomicIntegerArray result = new AtomicIntegerArray(insertValues.length());
        for (int i = 0; i < insertValues.length(); i++) {
            result.set(i, insertValues.get(i));
        }

        return result;
    }


    public int getLines() {
        return maxLines;
    }
}
