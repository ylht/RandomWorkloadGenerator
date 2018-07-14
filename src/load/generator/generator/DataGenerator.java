package load.generator.generator;

import load.generator.template.TableTemplate;
import load.generator.template.tuple.TupleType;

import java.util.ArrayList;
import java.util.Random;

public class DataGenerator {
    private TableTemplate[] allTables;
    public void generateData()
    {
        for (TableTemplate table:allTables) {
            int keyNum=table.getKeyNum();
            ArrayList<TupleType>tuples=table.getTuples();
            int tupleNum=table.getTableAttNum();
            String[] attValues=new String[tuples.size()];
            int tablelines=RandomGenerateTableAttributesVaule.tableLinesNum();
            if(keyNum==1)
            {
                for(int rowIndex=0;rowIndex<tablelines;rowIndex++)
                {
                    for(int colIndex=0;colIndex<tupleNum;colIndex++)
                    {
                        if(colIndex==0)
                        {
                            attValues[colIndex]=String.valueOf(rowIndex);
                        }
                        else
                        {
                            attValues[colIndex]=
                        }
                    }
                }

            }

        }
    }
}
