package util;

public class StringUtils {
    public static String methodToString(String name, Object[] args) {
        return name + "(" + getParams(args) + ")";
    }

    private static String getParams(Object[] args) {
        return args == null ? "" : org.apache.commons.lang3.StringUtils.join(args, ", ");
    }
}
