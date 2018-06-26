package com.wqs.template.table;

import com.wqs.base.tuple.*;

import java.util.ArrayList;

/**
 * @author wangqingshuai
 */

public class TableTemplate {

        private String tableName;
        private int keyNum;
        private int totalNum;
        private ArrayList<TupleKind> values;

        private TableTemplate(String tableName,
                              int intNum,int sqlDoubleNum,int sqlCharNum,int dateNum,
                              int keyNum)
        {
            int totalNum=intNum+sqlCharNum+sqlDoubleNum+dateNum;
            this.tableName=tableName;
            this.totalNum=totalNum;
            this.keyNum=keyNum;

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

            for(int i =keyNum-1;i<totalNum;i++)
            {
                int randomIndex=i+(int)(Math.random()*(totalNum-i));
                TupleKind temp= values.get(i);
                values.set(i,values.get(randomIndex));
                values.set(randomIndex,temp);
            }


        }

        public String toSql()
        {
            StringBuilder sql= new StringBuilder("CREATE TABLE" + tableName + "{ ");
            for(int i=0;i<totalNum;i++)
            {
                sql.append("tv").append(String.valueOf(i)).append(" ")
                        .append(values.get(i).getKindSql()).append(",\n");
            }
            if(keyNum>0)
            {
                sql.append("PRIMARY KEY ( tv0");
                for(int i=1;i<keyNum;i++)
                {
                    sql.append(",tv").append(String.valueOf(i));
                }
            }
            else
            {
                sql.substring(0,sql.length()-1);
            }
            sql.append(")");
            return sql.toString();
        }

}
