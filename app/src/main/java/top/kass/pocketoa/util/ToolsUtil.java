package top.kass.pocketoa.util;

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

}
