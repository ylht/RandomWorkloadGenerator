package load.generator.main;

import load.generator.utils.MysqlConnector;

class CreateTable {
    private String[] sql;
    private MysqlConnector mc = new MysqlConnector();

    CreateTable(String[] sql) {
        this.sql = sql;
    }

    boolean work() {
        String drop = "DROP TABLE IF EXISTS t";
        for (int i = 9; i > -1; i--) {
            mc.excuteSql(drop + String.valueOf(i));
        }
        for (String i : sql) {
            if (!mc.excuteSql(i)) {
                return false;
            }
        }
        return true;
    }

}
