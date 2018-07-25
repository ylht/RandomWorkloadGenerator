package load.generator.generator.random;

import load.generator.template.CharTemplate;
import load.generator.utils.RandomGenerator;

public class randomChar extends randomValue {
    private int min;
    private int max;
    private CharTemplate cvt;
    private randomInt ri;
    private RandomGenerator rg=new RandomGenerator(20);

    public randomChar(CharTemplate cvt)
    {
        this.cvt=cvt;
    }

    public randomChar(int min,int max)
    {
        this.min=min;
        this.max=max;
    }

    public randomChar(int len,randomInt ri)
    {
        this.ri=ri;
        this.max=len;
    }


    @Override
    public String getValue()
    {
        if(cvt!=null)
        {
            return cvt.getWord();
        }
        else if(ri!=null)
        {
            String t=String.valueOf(ri.getSameValue());
            if(t.length()>max)
            {
                return t.substring(0,t.length()-max);
            }
            else
            {
                StringBuilder str= new StringBuilder();
                for(int i =0;i<max-t.length();i++)
                {
                    str.append('0');
                }
                return str.toString()+ri.getSameValue();
            }
        }
        else
        {
            return rg.astring(min,max);
        }
    }
}
