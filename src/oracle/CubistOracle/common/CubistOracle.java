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

package oracle.CubistOracle.common;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @author Diego Didona, didona@gsd.inesc-id.pt Date: 29/10/12
 */
public abstract class CubistOracle implements Oracle {
   protected static final String DATA = ".data";
   protected static final String MODEL = ".model";
   protected String pathToCubist;

   private File trainingSet;
   private PrintWriter trainingSetWriter;


   protected CubistConfig cubistConfig;

   protected final static Log log = LogFactory.getLog(CubistOracle.class);

   public CubistOracle(CubistConfig config, boolean buildModel) throws OracleException {
      this.cubistConfig = config;
      init(buildModel);
   }

   private void init(boolean buildModel) throws OracleException {
      pathToCubist = cubistConfig.getPathToCubist();
      if (!OracleUtil.fileExists(pathToCubist + "/cubist"))
         throw new OracleException(pathToCubist + " not found");
      //If you want to build a new model, you have to prepare a training set first!!
      if (trainingSet == null) {
         trainingSet = new File(pathToCubist + "/" + cubistConfig.getTrainingSet());
         log.trace("setting training set to " + trainingSet.getAbsolutePath());
      }
      if ((!trainingSet.exists() || trainingSet.length() == 0) && buildModel)
         throw new OracleException("You need a training set to build a new model");
      try {
         if (!trainingSet.exists()) {
            log.trace("Trying to create new trainingSet " + trainingSet.getCanonicalPath());
            trainingSet.createNewFile();
         }
         if (trainingSetWriter == null)
            trainingSetWriter = new PrintWriter(trainingSet);
      } catch (Exception e) {
         e.printStackTrace();
         throw new OracleException(e.getMessage());
      }
      String builtModel;
      if (buildModel) {
         builtModel = createCubistModel(cubistConfig.getTargetFeature());
      } else
         builtModel = cubistConfig.getModel();
      if (builtModel != null) {   //if you don't want to build the model yet, do not init it!
         postModelCreation(pathToCubist + "/" + cubistConfig.getTargetFeature());
      }
   }


   private void buildModel() throws OracleException {
      if (cubistConfig.getTrainingSet() == null)
         throw new OracleException("You cannot build a model without training set");
      if (trainingSet == null) {
         trainingSet = new File(pathToCubist + "/" + cubistConfig.getTrainingSet());
         log.trace("setting training set to " + trainingSet.getAbsolutePath());
      }
      if ((!trainingSet.exists() || trainingSet.length() == 0))
         throw new OracleException("You need a non-empty training set to build a new model");

   }


   protected abstract void postModelCreation(String pathToModel) throws OracleException;

   /**
    * @param features
    * @param target
    * @param init     if true, you rebuild the model after having added the point
    * @throws OracleException
    */
   public void addPoint(String features, String target, boolean init) throws OracleException {
      trainingSetWriter.println(features);
      trainingSetWriter.flush();
      if (init)
         init(true);
   }

   public void addPoint(String features, boolean init) throws OracleException {
      addPoint(features, this.cubistConfig.getTargetFeature(), init);
   }

   public void removePoint(String features, String target) {
      throw new RuntimeException("removePoint method not supported yet");
   }

   private String[] buildCommand(String filestem) {
      StringBuilder sb = new StringBuilder();
      sb.append(this.pathToCubist + "/cubist");
      sb.append(";");
      sb.append("-f");
      sb.append(pathToCubist + "/" + filestem);
      int instances = cubistConfig.getInstances();
      int committee = cubistConfig.getCommittee();
      if (instances > 0) {
         sb.append(";");
         sb.append("-n");
         sb.append(instances);
      }
      if (committee > 0) {
         sb.append(";");
         sb.append("-C");
         sb.append(committee);
      }
      return sb.toString().split(";");

   }

   private String createCubistModel(String filestem) {
      try {
         String[] command = buildCommand(filestem);
         log.trace("Invoking " + Arrays.toString(command));
         Process p = Runtime.getRuntime().exec(buildCommand(filestem));
         checkForError(p);
         p.destroy();
      } catch (Exception e) {
         e.printStackTrace();
         throw new RuntimeException("Could not create CubistModel " + e.getMessage());
      }
      return modelString();
   }

   private String modelString() {
      String modelString = cubistConfig.getPathToCubist() + "/" + cubistConfig.getTargetFeature() + MODEL;
      log.trace("Returning new model " + modelString);
      return modelString;
   }

   protected void checkForError(Process p) throws OracleException {
      BufferedReader stderr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
      String read;

      StringBuilder errorString = null;
      try {
         while ((read = stderr.readLine()) != null) {
            if (errorString == null)
               errorString = new StringBuilder();
            errorString.append(read);
            System.out.println(read);
         }
      } catch (IOException e) {
         e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
         throw new OracleException(e.getMessage());
      }
      if (errorString != null)
         throw new OracleException((read));
   }

   @Override
   public final double query(String features) throws OracleException {
      return query(features, cubistConfig.getTargetFeature());
   }


}
