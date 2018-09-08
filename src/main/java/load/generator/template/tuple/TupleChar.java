package load.generator.template.tuple;

import load.generator.generator.RandomGenerateTableAttributesVaule;
import load.generator.template.CharTemplate;

/**
 * @author wangqingshuai
 */

public class TupleChar extends TupleType {
    private Boolean fixedOrNot;
    private int charNum;
    private int min = 1;
    private charValueType cvt;

    private CharTemplate cT;
    private boolean isVarChar;

    public TupleChar(boolean isVarChar) {
        super("char");
        this.isVarChar = isVarChar;

    }

    public TupleChar(boolean isVarChar, int num) {
        super("char");
        this.isVarChar = isVarChar;
        if (num == 0) {
            cvt = charValueType.rangeOfLength;
            RandomGenerateTableAttributesVaule rgta = RandomGenerateTableAttributesVaule.getInstance();
            charNum = rgta.tupleCharNum();
        } else {
            cvt = charValueType.choiceFromTemplate;
            cT = new CharTemplate((int) (Math.log(num) / Math.log(6)) + 1, 6);
            charNum = 24;
        }
    }

    public CharTemplate getcT() {
        return cT;
    }

    public int getCharType() {
        return cvt.ordinal();
    }

    @Override
    public String getTupleType() {
        if (isVarChar) {
            return "VARCHAR(" + String.valueOf(charNum) + ")";
        } else {
            return "CHAR(" + String.valueOf(charNum) + ")";
        }
    }

    @Override
    public String getValueType() {
        return "char";
    }

    @Override
    public Object getMin() {
        return min;
    }

    @Override
    public Object getMax() {
        return charNum;
    }

    /**
     * 转换int为char
     * 根据特定的长度随机char
     */

    private enum charValueType {
        //根据特定的长度随机char
        rangeOfLength,
        //在模版库中选择
        choiceFromTemplate
    }
}
