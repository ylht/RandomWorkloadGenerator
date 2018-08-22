package load.generator.generator;

/**
 * @author wangqingshuai
 */

public class GenerateSqlListFromArray {

    private static String generateModule(Object[] values, Boolean forUpdate) {
        String prefix = "";
        if (forUpdate) {
            prefix = "= ?";
        }
        int attributesNum = values.length;
        StringBuilder attributesStr = new StringBuilder(values[0].toString());
        attributesStr.append(prefix);
        for (int i = 1; i < attributesNum; i++) {
            attributesStr.append(',').append(values[i].toString()).append(prefix);
        }
        return attributesStr.toString();
    }

    public static String generateSelectListFromArray(Object[] values) {
        if (values.length == 0) {
            return null;
        }
        return generateModule(values, false);
    }

    public static String generateForeignListFromArray(int[] values) {
        if (values.length == 0) {
            return null;
        }
        int attributesNum = values.length;
        StringBuilder attributesStr = new StringBuilder("tv" + String.valueOf(values[0]));
        for (int i = 1; i < attributesNum; i++) {
            attributesStr.append(',').append("tv").append(String.valueOf(values[i]));
        }
        return attributesStr.toString();
    }

    public static String generateUpdateListFromArray(Object[] values) {
        return generateModule(values, true);
    }
}
