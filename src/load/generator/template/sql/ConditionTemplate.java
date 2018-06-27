package load.generator.template.sql;

/**
 * @author wangqingshuai
 */
public class ConditionTemplate {
        private String keyToSql(String[] keyNames,Boolean isRange)
        {
            StringBuilder sql=new StringBuilder();
            String sqlMiddle=" = %s";
            if(isRange)
            {
                sqlMiddle="between %s and %s";
            }
            for(int i=0;i<keyNames.length;i++)
            {
                sql.append(keyNames[i]).append(sqlMiddle);
                if(i!=keyNames.length-1)
                {
                    sql.append(" and ");
                }
            }
            return sql.toString();
        }

        public String singleTable(String tableName)
        {
            return String.format("from %s \n where", tableName);
        }

        public String joinTable(String tableFirst,String tableSecond,
                                String valueFirst,String valueSecond)
        {
            return String.format("from %s , %s \n where %s.%s=%s.%s", tableFirst,tableSecond,
                    tableFirst,valueFirst,tableSecond,valueSecond);
        }
        public String singleCondition(String[] keyNames)
        {
            return  keyToSql(keyNames,false)+';';
        }

        public String rangeCondition(String[] keyNames)
        {
            return keyToSql(keyNames,true)+';';
        }

        public String complexContion(String[] singleKeyNames,String[] rangeKeyNames)
        {
            return keyToSql(singleKeyNames,false)+" and "+keyToSql(rangeKeyNames,true)+";";
        }


}
