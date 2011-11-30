package util;

/**
 * Created by IntelliJ IDEA.
 * User: Gustavo Sousa
 * Date: 17/10/11
 * Time: 17:13
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {
    public static String methodToString(String name, Object[] args) {
                StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("(");

        if (args != null) {
            for (Object arg : args) {
                sb.append(arg != null ? arg.toString() : "null");
                sb.append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
        }

        sb.append(")");

        return sb.toString();
    }
}
