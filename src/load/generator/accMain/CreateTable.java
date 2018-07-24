package load.generator.accMain;

import load.generator.utils.MysqlConnector;

import java.sql.Statement;

public class CreateTable {
    private String[] sql;
    private MysqlConnector mc=MysqlConnector.getInstance();
    public CreateTable(String[] sql){
        this.sql=sql;
    }

    public boolean work()
    {
        String drop="DROP TABLE IF EXISTS t";
        for(int i=9;i>-1;i--)
        {
            mc.excuteSql(drop+String.valueOf(i));
        }
        for(String i:sql)
        {
            if(!mc.excuteSql(i))
            {
                return false;
            }
        }
        return true;
    }

}
