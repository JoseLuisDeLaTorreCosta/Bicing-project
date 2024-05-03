package Bicing;

import aima.search.framework.HeuristicFunction;
import IA.Bicing.*;

public class BGoodHeuristicFunction implements HeuristicFunction {

    //Este heurístico se calcula a partir de sumatorio del valor absoluto las bicicletas de cada estación 
    //menos la demanda prevista para esta hora más el sumatorio de los km recorridos
    //multiplicado por el coste de transporte por km de cada furgoneta
    public double getHeuristicValue(Object state) {
        BState estat = (BState) state;
        double sum = 0;

        for (BState.Truck t: estat.trucks) {
            sum = sum - t.benefici;
        }

        //Se suma a sum el sumatorio de los km recorridos multiplicado por el coste de 
        //transporte por km de cada furgoneta
        for(BState.Truck t: estat.trucks) {
            sum = sum + t.despeses;
        }
        
        
        return sum;
    }
}