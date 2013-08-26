package oracle.CubistOracle.exec;

import oracle.CubistOracle.common.CubistConfig;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class ExecCubistConfig extends CubistConfig {


   private String pathToOracle;

   public String getPathToOracle() {
      return pathToOracle;
   }

   public void setPathToOracle(String pathToOracle) {
      this.pathToOracle = pathToOracle;
   }
}
