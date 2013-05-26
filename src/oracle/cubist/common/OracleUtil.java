package oracle.cubist.common;

import java.io.File;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class OracleUtil {


   public static boolean fileExists(String pathToFile) {
      return new File(pathToFile).exists();
   }

   public static String slashedPath(String s) {
      return s.endsWith("/") ? s : s.concat("/");
   }
}
