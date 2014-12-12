package doublej.bobtudy.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ISODate extends Date {
    private static SimpleDateFormat sdf;

    static {
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public ISODate(long milliseconds) {
        super(milliseconds);
    }

    public ISODate(Date date) {
        super(date.getTime());
    }

    public String toISOString() {
        return sdf.format(this);
    }

    public static ISODate getInstanceByIsoString(String isoDateString) {
        Date date = null;

        try {
            date = sdf.parse(isoDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new ISODate(date);
    }
}
