package top.kass.pocketoa.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ToolsUtil {

    public static Integer sti(String str) {
        if (str == null || str.equals("null")) return null;
        return Integer.valueOf(str);
    }

    public static Double std(String str) {
        if (str == null || str.equals("null"))  return null;
        return Double.valueOf(str);
    }

    public static String sts(String str) {
        if (str == null || str.equals("null")) return "";
        return str;
    }

    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd", Locale.CHINA);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String getFomartedDate(int year, int monthOfYear, int dayOfMonth) {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(year)+"-");
        if (monthOfYear +1 < 10) {
            sb.append("0" + Integer.toString(monthOfYear+1));
        } else {
            sb.append(Integer.toString(monthOfYear+1));
        }
        sb.append("-");
        if (dayOfMonth < 10) {
            sb.append("0" + Integer.toString(dayOfMonth));
        } else {
            sb.append(Integer.toString(dayOfMonth));
        }
        return sb.toString();
    }

}
