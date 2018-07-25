package load.generator.accMain;

import load.generator.generator.random.randomInt;
import load.generator.generator.random.randomValue;

import java.util.ArrayList;

public class LoadData {
    private ArrayList<ArrayList<randomValue>>randomLists;
    private ArrayList<Integer> keyNums;
    private int [] temp=new int[200];
    public LoadData(ArrayList<ArrayList<randomValue>>randomLists,ArrayList<Integer>keyNums)
    {
        this.randomLists=randomLists;
        this.keyNums=keyNums;
    }
    private String getValues(ArrayList<randomValue> randomList,int keyNum)
    {
        int length=0;
        StringBuilder values= new StringBuilder("(");
        for(int i=keyNum;i>-1;i--)
        {
            randomInt ri=(randomInt)(randomList.get(i));
            String nv=ri.getNext();
            values.append(nv).append(',');
            if(temp[i]>Integer.valueOf (nv))
            {
                temp[i]=Integer.valueOf (nv);
            }
            else
            {
                break;
            }
        }

        for(int j=keyNum;j<randomList.size();j++)
        {
            values.append(',').append(randomList.get(j).getValue());
        }
        values.deleteCharAt(values.length()-1);
        values.append(')');
        return values.toString();
    }
    public boolean load()
    {
        int current=0;
        for (ArrayList<randomValue> randomList : randomLists) {
            StringBuilder sql = new StringBuilder("INSERT INTO tv" + String.valueOf(current) + "values" + getValues(randomList,keyNums.get(current)));
            int num=1;
            for(int i=1;i<2;i++)
            {
                sql.append(',').append(getValues(randomList,keyNums.get(current)));
            }
            sql.append(')');
            System.out.println(sql);
        }
        return true;
    }
}
