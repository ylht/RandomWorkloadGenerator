package load.generator.template;

import load.generator.utils.RandomGenerator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class CharTemplate {
    private ArrayList<String> words;
    private int counts;
    private int current=0;
    private int[] wordCount;
    private Random cR=new Random();
    public CharTemplate(int wordNum,int counts)
    {
        RandomGenerator cg=new RandomGenerator(20);
        this.counts=counts;
        for(int i=0;i<wordNum;i++)
        {
            words.add(cg.astring(1,3));
        }
        wordCount=new int[counts];
    }

    public String getWord()
    {
        StringBuilder str= new StringBuilder();
        int len=words.size();
        if(current+1!=counts)
        {
            for(int i=0;i<counts;i++)
            {
                str.append(words.get(wordCount[i]));
            }
            for(int i=0;i<counts;i++)
            {
                wordCount[i]+=1;
                if(wordCount[i]>=len)
                {
                    wordCount[i+1]+=len-wordCount[i];
                }
                else
                {
                    break;
                }
            }
            current++;
        }
        else
        {
            for(int i=0;i<counts;i++)
            {
                str.append(words.get(cR.nextInt(len)));
            }
        }
        return str.toString();
    }
}
