package Bicing;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.List;
import java.util.Random;

import IA.Bicing.Estacion;

import java.util.ArrayList;

public class BSASuccessorFunction implements SuccessorFunction{
    @Override
    public List getSuccessors(Object state) {
        ArrayList<Successor> retVal = new ArrayList<>(); 
        BState estat = (BState) state;
        Random rand = new Random();
        //BHeuristicFunction B  = new BHeuristicFunction();
        int truck_mod = rand.nextInt(estat.trucks.size());

        Estacion or_mod = estat.trucks.get(truck_mod).orig;
        while (!estat.vist(or_mod)) or_mod = estat.station.get(rand.nextInt(estat.station.size()));

        Estacion L1_mod = estat.trucks.get(truck_mod).L1;
        while (L1_mod == estat.trucks.get(truck_mod).L1 || L1_mod == or_mod) {
            L1_mod = estat.station.get(rand.nextInt(estat.station.size()));
        }

        Estacion L2_mod = estat.trucks.get(truck_mod).L2;
        while (L2_mod == estat.trucks.get(truck_mod).L2 || L2_mod == or_mod || L2_mod == L1_mod) {
            L2_mod = estat.station.get(rand.nextInt(estat.station.size()));
        }

        BState nou_estat = new BState(estat);
        nou_estat.modDist(or_mod, L1_mod, L2_mod, truck_mod);

        String S = "Intercanvi de Estació L1: " + estat.trucks.get(truck_mod).L1 + "per: " + nou_estat.trucks.get(truck_mod).L1 + "\nIntercanvi de Estació L2: " + estat.trucks.get(truck_mod).L2 + "per: " + nou_estat.trucks.get(truck_mod).L2+"\n\n";
        retVal.add(new Successor(S, nou_estat));
        
        return retVal;
    }
}
