package com.wqs.template.table;

import com.wqs.base.tuple.*;

import java.util.ArrayList;

public class TableTemplate {

        private String tableName;
        private int keyNum;
        private int totalNum;
        private ArrayList<TupleKind> values;

        private TableTemplate(int intNum,int sqlDoubleNum,int sqlCharNum,int dateNum,int keyNum)
        {

            for(int i=0;i<intNum;i++)
            {
                values.add(new TupleInt());
            }
            for(int i=0;i<keyNum;i++)
            {
                values.get(i).makeKey();
            }
            for(int i=0;i<sqlDoubleNum;i++)
            {
                values.add(new TupleDouble());
            }
            for(int i=0;i<sqlCharNum;i++)
            {
                values.add(new TupleChar());
            }
            for(int i=0;i<dateNum;i++)
            {
                values.add(new TupleDate());
            }
            if(keyNum==0)
            {
                keyNum=1;
            }
            int totalNum=intNum+sqlCharNum+sqlDoubleNum+dateNum;
            for(int i =keyNum-1;i<totalNum;i++)
            {
                int randomIndex=i+(int)(Math.random()*(totalNum-i));
                TupleKind temp= values.get(i);
                values.set(i,values.get(randomIndex));
                values.set(randomIndex,temp);
            }

            this.totalNum=totalNum;
            this.keyNum=keyNum;
        }

        
}
