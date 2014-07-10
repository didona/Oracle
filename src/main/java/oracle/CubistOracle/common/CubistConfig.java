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

/**
 * @author Diego Didona, didona@gsd.inesc-id.pt Date: 30/10/12
 */
public class CubistConfig {


   private String pathToCubist;
   private String trainingSet;
   private String model;
   private String targetFeature;
   private int numInstances = 0;
   private int committee = 0;
   private boolean printModelOnBuild = false;
   private boolean instancesAndRules = false;

   public boolean isInstancesAndRules() {
      return instancesAndRules;
   }

   public void setInstancesAndRules(boolean instancesAndRules) {
      this.instancesAndRules = instancesAndRules;
   }

   public boolean isPrintModelOnBuild() {
      return printModelOnBuild;
   }

   public void setPrintModelOnBuild(String printModelOnBuild) {
      this.printModelOnBuild = Boolean.valueOf(printModelOnBuild);
   }

   public int getNumInstances() {
      return numInstances;
   }

   public void setNumInstances(int numInstances) {
      this.numInstances = numInstances;
   }

   public int getCommittee() {
      return committee;
   }

   public void setCommittee(int committee) {
      this.committee = committee;
   }

   public String getTargetFeature() {
      return targetFeature;
   }

   public void setTargetFeature(String targetFeature) {
      this.targetFeature = targetFeature;
   }

   public String getTrainingSet() {
      return trainingSet;
   }

   public void setTrainingSet(String trainingSet) {
      this.trainingSet = trainingSet;
   }

   public String getPathToCubist() {
      return pathToCubist;
   }

   public void setPathToCubist(String pathToCubist) {
      this.pathToCubist = pathToCubist;
   }

   public String getModel() {
      return model;
   }

   public void setModel(String model) {
      this.model = model;
   }

   @Override
   public String toString() {
      return "CubistConfig{" +
            "pathToCubist='" + pathToCubist + '\'' +
            ", trainingSet='" + trainingSet + '\'' +
            ", model='" + model + '\'' +
            ", targetFeature='" + targetFeature + '\'' +
            ", numInstances=" + numInstances +
            ", committee=" + committee +
            '}';
   }
}

