package load.generator.template;

/**
 * @author wangqingshuai
 */
public class ConditionTemplate {
    private String keyToSql(int[] keyNames, Boolean isRange) {
        StringBuilder sql = new StringBuilder(" where ");
        String sqlMiddle = " = ? ";
        if (isRange) {
            sqlMiddle = "between ? and ?";
        }
        for (int i = 0; i < keyNames.length; i++) {
            sql.append("tv" + String.valueOf(keyNames[i])).append(sqlMiddle);
            if (i != keyNames.length - 1) {
                sql.append(" and ");
            }
        }
        return sql.toString();
    }

    public String singleTable(String tableName) {
        return String.format("from %s", tableName);
    }

    public String joinTable(String tableFirst, String tableSecond,
                            String valueFirst, String valueSecond) {
        return String.format("from %s , %s  where %s.%s=%s.%s", tableFirst, tableSecond,
                tableFirst, valueFirst, tableSecond, valueSecond);
    }

    public String singleCondition(int[] keyNames) {
        return keyToSql(keyNames, false) + ';';
    }

    public String rangeCondition(int[] keyNames) {
        return keyToSql(keyNames, true) + ';';
    }

    public String complexContion(int[] singleKeyNames, int[] rangeKeyNames) {
        return keyToSql(singleKeyNames, false) + " and " + keyToSql(rangeKeyNames, true) + ";";
    }


}
