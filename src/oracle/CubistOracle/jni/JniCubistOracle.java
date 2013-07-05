/*
 *
 *  * INESC-ID, Instituto de Engenharia de Sistemas e Computadores Investigação e Desevolvimento em Lisboa
 *  * Copyright 2013 INESC-ID and/or its affiliates and other
 *  * contributors as indicated by the @author tags. All rights reserved.
 *  * See the copyright.txt in the distribution for a full listing of
 *  * individual contributors.
 *  *
 *  * This is free software; you can redistribute it and/or modify it
 *  * under the terms of the GNU Lesser General Public License as
 *  * published by the Free Software Foundation; either version 3.0 of
 *  * the License, or (at your option) any later version.
 *  *
 *  * This software is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  * Lesser General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU Lesser General Public
 *  * License along with this software; if not, write to the Free
 *  * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 *  * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
 */

package oracle.CubistOracle.jni;


import oracle.CubistOracle.common.CubistConfig;
import oracle.CubistOracle.common.CubistOracle;
import oracle.CubistOracle.common.OracleException;

/**
 * Created with IntelliJ IDEA.
 * User: diego
 * Date: 09/01/13
 * Time: 10:49
 * To change this template use File | Settings | File Templates.
 */
public class JniCubistOracle extends CubistOracle {

   private native void initiateCubist(String filename);

   private native double getPrediction(String att);

   private native double[] getPredictionAndError(String att);

   public JniCubistOracle(CubistConfig config, boolean buildModel) throws OracleException {
      super(config, buildModel);
   }

   static {
      System.loadLibrary("cubistJNI");
      log.trace("cubistJNI lib correctly loaded");
   }

   @Override
   protected void postModelCreation(String model) throws OracleException {
      initiateCubist(model);
   }

   public double query(String features, String target) throws OracleException {
      return getPrediction(features);
   }
}
