package load.generator.accMain;

import load.generator.generator.GeneratorTableAndSqlTemplate;
import load.generator.generator.random.*;
import load.generator.template.TableTemplate;
import load.generator.template.tuple.TupleChar;
import load.generator.template.tuple.TupleType;

import java.util.ArrayList;

public class ACCMain {
    public static void main(String[] args) {
        // write your code here
        GeneratorTableAndSqlTemplate gt = new GeneratorTableAndSqlTemplate();
//        for(String i:gt.getAllTable())
//        {
//            System.out.println(i);
//        }
        CreateTable ct=new CreateTable(gt.getAllTable());
        System.out.println(ct.work());
        ArrayList<ArrayList<randomValue>>randomLists=new ArrayList<ArrayList<randomValue>>();
        ArrayList<Integer>keyNums= new ArrayList<>();
        for(TableTemplate table:gt.getTables())
        {
            ArrayList<TupleType> tt=table.getTuples();
            ArrayList<randomValue> rv=new ArrayList<randomValue>();
            for(TupleType t:tt)
            {
                randomValue temprv=new randomValue();
                switch (t.getValueType()){
                    case  "int":
                        temprv=new randomInt((int)t.getMin(),(int)t.getMax());
                        break;
                    case "double":
                        temprv=new randomDemical((double)t.getMin(),(double)t.getMax());
                        break;
                    case "char":
                        TupleChar chart=(TupleChar)t;
                        int l=chart.getCharType();
                        switch (l)
                        {
                            case 0:
                                temprv=new randomChar((int)t.getMax(),(randomInt)rv.get(0));
                                break;
                            case 1:
                                temprv=new randomChar((int)t.getMin(),(int)t.getMax());
                                break;
                            case 2:
                                temprv=new randomChar(chart.getcT());
                                break;
                            default:
                                System.out.println("没有匹配到应生成的随机数据类型");
                                break;
                        }
                        break;
                    case "date":
                        temprv=new randomDate();
                        break;
                    default:
                        System.out.println("没有匹配到应生成的随机数据类型");
                        System.exit(-1);
                }
                rv.add(temprv);
            }
            randomLists.add(rv);
            keyNums.add(table.getKeyNum());
        }
        LoadData ld=new LoadData(randomLists,keyNums);
    }
}
