package doublej.bobtudy.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ISODate extends Date {
    private static TimeZone tz = TimeZone.getTimeZone("UTC");
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public ISODate(long milliseconds) {
        super(milliseconds);
    }

    public ISODate(Date date) {
        super(date.getTime());
    }

    public String toISOString() {
        df.setTimeZone(tz);
        return df.format(this);
    }

    public static ISODate getInstanceByIsoString(String IsoDateString) {
        try {
            Date date = df.parse(IsoDateString);
            return new ISODate(date.getTime());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
