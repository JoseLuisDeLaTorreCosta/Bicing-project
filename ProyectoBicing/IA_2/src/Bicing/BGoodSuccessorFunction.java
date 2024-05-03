package Bicing;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.List;
import java.util.ArrayList;

public class BGoodSuccessorFunction implements SuccessorFunction {
  @Override
  public List getSuccessors(Object state) {
      ArrayList<Successor> retVal = new ArrayList<>(); 
      BState estat = (BState) state;
      BState best = estat;
      BGoodHeuristicFunction B  = new BGoodHeuristicFunction();
      /*ArrayList<Boolean> visitades = new ArrayList<Boolean>();
        for(int z = 0; z < estat.station.size(); ++z) visitades.add(false);
      */
      for(int i = 0; i < estat.trucks.size(); ++i){
          /*for(int j = 0; j < estat.station.size(); ++j){
            BState nou_estat = new BState(estat);
            if (!visitades.get(j)&& nou_estat.modOG(estat.station.get(j))){
                String S = "Intercanvi de ciutat origen: " + estat.trucks.get(i).orig + "per: " + estat.station.get(j);
                retVal.add(new Successor(S, nou_estat));
                visitades.set(i,true);
            }
             }*/
          
          for(int j = 0; j < estat.station.size(); ++j){
              for(int z = 0; z < estat.station.size(); ++z){
                  for(int x = 0; x < estat.station.size(); ++x){
              //System.out.println(estat.vist(estat.station.get(j)) + " "+ estat.station.get(j));
              if (estat.vist(estat.station.get(j)) && z != x && z != j && j != x && estat.station.get(z)!= estat.trucks.get(i).L1 && estat.station.get(x)!= estat.trucks.get(i).L2 && estat.station.get(j)!= estat.trucks.get(i).orig){
                BState nou_estat = new BState(estat);
                nou_estat.modDist(estat.station.get(j), estat.station.get(z), estat.station.get(x), i);
                if(B.getHeuristicValue(best) > B.getHeuristicValue(nou_estat)){
                    String S = "Intercanvi de Estació L1: " + estat.trucks.get(i).L1 + "per: " + nou_estat.station.get(j) + "\nIntercanvi de Estació L2: " + estat.trucks.get(i).L2 + "per: " + nou_estat.station.get(z)+"\n\n";
                    //System.out.println(S);
                    best = nou_estat;
                    retVal.add(new Successor(S, nou_estat));
                    //visitades.set(j,true);
                }
              }
              }
             }
           }
          /*
           BState nou_estat = new BState(estat);
           nou_estat.modBicis(i);
           if(B.getHeuristicValue(estat) > B.getHeuristicValue(nou_estat)){
               String S = "Intercanvi de bicis: ";
               retVal.add(new Successor(S, nou_estat));
           }
           */
      }
      return retVal;
  }
    
}
