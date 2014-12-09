package doublej.bobtudy.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Jun on 2014-12-01.
 */
public class ISODate extends Date {
    private TimeZone tz = TimeZone.getTimeZone("UTC");
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

    public String toISOString() {
        df.setTimeZone(tz);
        return df.format(this);
    }
}
