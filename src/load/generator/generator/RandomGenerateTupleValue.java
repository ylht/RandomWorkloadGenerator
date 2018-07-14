package load.generator.generator;

import load.generator.template.tuple.TupleDouble;
import load.generator.template.tuple.TupleType;

import java.util.Random;

public class RandomGenerateTupleValue {
    private static Random r = new Random();
    public static String randomStaticValue(TupleType tuple) throws Exception
    {
        if("int".equals(tuple.getTupleType()))
        {
            return String.valueOf(r.nextInt());
        }
        else if("double".equals(tuple.getTupleType()))
        {
            TupleDouble td=(TupleDouble)tuple;

        }
        else if("char".equals(tuple.getTupleType()))
        {

        }
        else if("date".equals(tuple.getTupleType()))
        {

        }
        else{
            throw new Exception();
        }

    }
    private static String randomDoubleValue(TupleType tuple,int tableLines)
    {

    }
    private static String randomCharValue()
}
