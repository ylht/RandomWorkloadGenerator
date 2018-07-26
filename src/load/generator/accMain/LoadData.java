package load.generator.accMain;

import load.generator.generator.random.randomInt;
import load.generator.generator.random.randomValue;
import load.generator.utils.MysqlConnector;

import java.util.ArrayList;

public class LoadData {
    private ArrayList<ArrayList<randomValue>>randomLists;
    private ArrayList<Integer> keyNums;
    private int [] temp=new int[200];
    private MysqlConnector mysqlConnector=MysqlConnector.getInstance();
    public LoadData(ArrayList<ArrayList<randomValue>>randomLists,ArrayList<Integer>keyNums)
    {
        this.randomLists=randomLists;
        this.keyNums=keyNums;
    }
    private String getValues(ArrayList<randomValue> randomList,int keyNum)
    {
//        if(keyNum>1)
//        {
//            System.out.println(1);
//        }
        StringBuilder values= new StringBuilder("(");
        int i;
        for(i=0;i<keyNum;i++)
        {
            randomInt ri=(randomInt)(randomList.get(i));
            String nv=ri.getNext();
            values.append(nv).append(',');
            if(temp[i]>Integer.valueOf (nv))
            {
                temp[i]=Integer.valueOf (nv);
                if(i==keyNum-1)
                {
                    return null;
                }
            }
            else
            {
                temp[i++]=Integer.valueOf (nv);
                break;
            }
        }
        for(;i<keyNum;i++)
        {
            String t=((randomInt)(randomList.get(i))).getSameValue();
            temp[i]=Integer.valueOf(t);
            values.append(t).append(',');
        }

        for(int j=keyNum;j<randomList.size();j++)
        {
            values.append(randomList.get(j).getValue()).append(',');
        }
        values.deleteCharAt(values.length()-1);
        values.append(')');
        return values.toString();
    }
    public boolean load()
    {
        int current=0;
        for (ArrayList<randomValue> randomList : randomLists) {
            while (true)
            {
                StringBuilder sql = new StringBuilder("INSERT INTO t" + String.valueOf(current) +
                        " values");
                int count=1;
                boolean continueExe=true;
                while (true)
                {
                    if(count++>100)
                    {
                        break;
                    }
                    String values= getValues(randomList,keyNums.get(current));
                    if(values!=null)
                    {
                        sql.append(values).append(',');
                    }
                    else
                    {
                        continueExe=false;
                        if(sql.charAt(sql.length()-1)!=',')
                        {
                            sql=null;
                        }
                        else
                        {
                            sql.deleteCharAt(sql.length()-1);
                            sql.append(";");
                        }
                        break;
                    }
                }
                if(sql!=null)
                {
                    sql.deleteCharAt(sql.length()-1);
                    sql.append(";");
                    mysqlConnector.excuteSql(sql.toString());
                }

                if(!continueExe)
                {
                    break;
                }
              }
            current++;
            for(int j=0;j<temp.length;j++)
            {
                temp[j]=0;
            }
        }
        return true;
    }
}
