package load.generator.template;

/**
 * @author wangqingshuai
 */

class SqlTemplate {
    String selectTemplate(Integer[] selectAttributes) {
        return String.format("select %s ", generateSelectListFromArray(selectAttributes));
    }

    String deleteTemplate() {
        return "delete ";
    }

    String updateTemplate(String tableName, Integer[] updateAttributes) {
        return String.format("update %s set %s ", tableName,
                generateUpdateListFromArray(updateAttributes));
    }

    String insertTemplate(String tableName, Integer[] insertAttributes, int keyNum) {
        StringBuilder sqlMiddle = new StringBuilder();
        StringBuilder sqlSelect = new StringBuilder();
        for (int i = 0; i < keyNum; i++) {
            sqlSelect.append("tv").append(String.valueOf(i)).append(",");
        }
        sqlMiddle.append("?");
        for (int i = 1; i < insertAttributes.length + keyNum; i++) {
            sqlMiddle.append(",?");
        }
        if (insertAttributes.length == 0) {
            return String.format("insert into %s (%s) values (%s);", tableName,
                    sqlSelect, sqlMiddle);
        } else {
            return String.format("insert into %s (%s%s) values (%s);", tableName,
                    sqlSelect, generateSelectListFromArray(insertAttributes), sqlMiddle);
        }

    }

    private String generateModule(Integer[] values, Boolean forUpdate) {
        String prefix = "";
        if (forUpdate) {
            prefix = "= ?";
        }
        int attributesNum = values.length;
        StringBuilder attributesStr = new StringBuilder("tv" + String.valueOf(values[0]));
        attributesStr.append(prefix);
        for (int i = 1; i < attributesNum; i++) {
            attributesStr.append(',').append("tv").append(String.valueOf(values[i])).append(prefix);
        }
        return attributesStr.toString();
    }

    private String generateSelectListFromArray(Integer[] values) {
        if (values.length == 0) {
            return null;
        }
        return generateModule(values, false);
    }


    private String generateUpdateListFromArray(Integer[] values) {
        return generateModule(values, true);
    }

}
