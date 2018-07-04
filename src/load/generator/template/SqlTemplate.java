package load.generator.template;

import load.generator.base.format.GenerateSqlListFromArray;

/**
 * @author wangqingshuai
 */

public class SqlTemplate {
    public String selectTemplate(String[] selectAttributes) {
        return String.format("select %s \n", GenerateSqlListFromArray.generateSelectListFromArray(selectAttributes));
    }

    public String deleteTemplate() {
        return "delete \n";
    }

    public String updateTemplate(String tableName, String[] updateAttributes) {
        return String.format("update %s \nset %s \n", tableName,
                GenerateSqlListFromArray.generateUpdateListFromArray(updateAttributes));
    }

    public String insertTemplate(String tableName, String[] insertAttributes, int keyNum) {
        StringBuilder sqlMiddle = new StringBuilder();
        StringBuilder sqlSelect = new StringBuilder();
        for (int i = 0; i < keyNum; i++) {
            sqlSelect.append("tv").append(String.valueOf(i)).append(",");
        }
        sqlMiddle.append("%s");
        for (int i = 1; i < insertAttributes.length + keyNum; i++) {
            sqlMiddle.append(",%s");
        }
        return String.format("insert into %s (%s%s) values (%s);", tableName,
                sqlSelect, GenerateSqlListFromArray.generateSelectListFromArray(insertAttributes), sqlMiddle);
    }
}
