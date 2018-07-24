package load.generator.template.tuple;

import load.generator.generator.RandomGenerateTableAttributesVaule;
import load.generator.template.CharTemplate;

import java.util.Map;

/**
 * @author wangqingshuai
 */

public class TupleChar extends TupleType {
    private Boolean fixedOrNot;
    private int charNum;
    private int min;
    private enum charValueType{transFromInt,rangeOfLength,choiceFromTemplate};
    private charValueType cvt;
    private CharTemplate cT;
    private boolean isVarChar;
    private boolean getVauleByInt;
    public TupleChar(boolean isVarChar,boolean getVauleByInt) {
        super("char");
        this.isVarChar=isVarChar;
        this.getVauleByInt=getVauleByInt;
        if(getVauleByInt)
        {
            cvt=charValueType.transFromInt;
            charNum=10;
        }
        else
        {
            cvt=charValueType.rangeOfLength;
            charNum = RandomGenerateTableAttributesVaule.tupleCharNum();
        }
    }

    public TupleChar(boolean isVarChar,int num){
        super("char");
        this.isVarChar=isVarChar;
        cvt=charValueType.choiceFromTemplate;
        cT=new CharTemplate((int)(Math.log(num)/Math.log(6))+1,6);
        charNum=24;
    }

    public int getVauleType()
    {
        return cvt.ordinal();
    }

    public String getValue()
    {
        return cT.getWord();
    }

    @Override
    public String getTupleType() {
        if(isVarChar)
        {
            return "VARCHAR(" + String.valueOf(charNum) + ")";
        }
        else
        {
            return  "CHAR(" + String.valueOf(charNum) + ")";
        }
    }

//    @Override
//    public Object getMin()
//    {
//        return min;
//    }
//
//    @Override
//    public Object getMax()
//    {
//        return charNum;
//    }
}
