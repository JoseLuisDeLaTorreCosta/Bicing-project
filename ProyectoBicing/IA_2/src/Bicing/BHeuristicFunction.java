package Bicing;

import aima.search.framework.HeuristicFunction;
import IA.Bicing.*;

public class BHeuristicFunction implements HeuristicFunction {
    
  public double getHeuristicValue(Object state) {
      BState estat = (BState) state;
      double sum = 0;
      for(BState.Truck T:estat.trucks){
       //sum = sum + T.Km;
       sum = sum + T.biL1 + T.biL2;
      }
      //System.out.println(sum);
      return -1*sum;
  }

}
