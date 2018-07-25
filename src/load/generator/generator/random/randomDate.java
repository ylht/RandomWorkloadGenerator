package load.generator.generator.random;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Timestamp;

public class randomDate extends randomValue{
    @Override
    public String getValue()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(System.currentTimeMillis());
    }
}

