package cn.jater.graduation.forum.utils.time;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatHandler {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Timestamp getNow() {
        Date date = new Date();
        String now = sdf.format(date);
        return Timestamp.valueOf(now);
    }
}
