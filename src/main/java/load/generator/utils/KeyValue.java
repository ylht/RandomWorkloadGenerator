package load.generator.utils;


import load.generator.generator.random.RandomInt;
import load.generator.generator.random.RandomValue;
import load.generator.template.tuple.TupleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class KeyValue {
    private AtomicIntegerArray maxRanges;
    private AtomicIntegerArray minRanges;
    private final AtomicIntegerArray currentValues;
    private Random r=new Random();
    private int maxLines=1;
    private Integer currentLine=0;

    private int minLevel;
    private int deleteNum=0;
    private int maxLevel;
    private int insertNum=0;
    private int otherNum;
    public KeyValue(List<RandomValue> randomValues)
    {
        int len=randomValues.size();
        maxRanges=new AtomicIntegerArray(len);
        minRanges=new AtomicIntegerArray(len);
        currentValues=new AtomicIntegerArray(len);
        int index=0;
        for(RandomValue randomValue:randomValues)
        {
            maxRanges.addAndGet(index,((RandomInt)randomValue).getMax());
            minRanges.addAndGet(index,((RandomInt)randomValue).getMin());
            currentValues.addAndGet(index,((RandomInt)randomValue).getMin());
            index++;
        }
        for(int i=0;i<maxRanges.length();i++)
        {
            maxLines*=maxRanges.get(i)-minRanges.get(i);
        }
        minLevel = minRanges.get(0)+1;
        maxLevel = maxRanges.get(0);
        otherNum=maxLines/(maxLevel-minLevel+1);
    }
    public KeyValue(List<TupleType> randomValues,boolean forTransaction)
    {
        int len=randomValues.size();
        maxRanges=new AtomicIntegerArray(len);
        minRanges=new AtomicIntegerArray(len);
        currentValues=new AtomicIntegerArray(len);
        int index=0;
        for(TupleType tuple:randomValues)
        {
            maxRanges.addAndGet(index,(Integer)tuple.getMax());
            minRanges.addAndGet(index,(Integer)tuple.getMin());
            currentValues.addAndGet(index,(Integer)tuple.getMin());
            index++;
        }
        for(int i=0;i<maxRanges.length();i++)
        {
            maxLines*=maxRanges.get(i)-minRanges.get(i);
        }
        minLevel = minRanges.get(0)+1;
        maxLevel = maxRanges.get(0);
        otherNum=maxLines/(maxLevel-minLevel+1);
    }

    private void valuesAdd(AtomicIntegerArray values)
    {
        int i = values.length()-1;
        for(; i>-1; i--)
        {
            if(values.get(i)== maxRanges.get(i)-1)
            {
                if(i==0)
                {
                   values.getAndIncrement(i);
                }
                else
                {
                    values.set(i, minRanges.get(i));
                }
            }
            else
            {
                values.getAndIncrement(i);
                break;
            }
        }
    }
    private AtomicIntegerArray getValue()
    {
        currentLine++;

        if (currentLine>1&&currentLine<=maxLines) {
                valuesAdd(currentValues);
        }

        return currentValues;
    }

    public ArrayList<Integer> getRandomValue()
    {
        ArrayList<Integer> result=new ArrayList<>();
        result.add(r.nextInt(maxLevel-minLevel)+minLevel);
        for(int i=1;i<maxRanges.length();i++)
        {
            result.add(
                    r.nextInt(maxRanges.get(i)-minRanges.get(i))
                    +minRanges.get(i));
        }
        return result;
    }

    public AtomicIntegerArray getDeleteValue()
    {
        minAdd();
        return getValue();
    }
    public AtomicIntegerArray getInsertValue()
    {
        maxAdd();
        return getValue();
    }


    public int getLines()
    {
        return maxLines;
    }
    synchronized private void minAdd()
    {
        deleteNum++;
        if(deleteNum==otherNum)
        {
            deleteNum=0;
            minLevel++;
        }
    }

    

    synchronized private void maxAdd()
    {
        insertNum++;
        maxLines++;
        if(insertNum==otherNum)
        {
            insertNum=0;
            maxLevel++;
        }
    }
}
