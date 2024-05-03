package Bicing;

import java.util.*;
import java.lang.*;

import IA.Bicing.*;

public class BState {
       public class Truck {
        int biL1, biL2, benefici;
        double despeses, m;
        Estacion L1, L2, orig;
         public Truck(){
         biL1 = 0;
         biL2 = 0;
         m = 0;
         benefici = 0;
         despeses = 0;
         L1 = L2 = orig = new Estacion(0,0);
         }
       }
       

    public ArrayList<Truck> trucks;
    public static Estaciones station;
    
    public BState(){}
    
    public BState(BState og){
        this.trucks = new ArrayList<Truck>(og.trucks.size());
        for(int j = 0; j < og.trucks.size(); ++j){
           Truck t = new Truck();
           //trucks.get(i) = new Truck();
           t.biL1 = og.trucks.get(j).biL1;
           t.biL2 = og.trucks.get(j).biL2;
           t.benefici = og.trucks.get(j).benefici;
           t.despeses = og.trucks.get(j).despeses;
           t.m = og.trucks.get(j).m;
           t.L1 = og.trucks.get(j).L1;
           t.L2 = og.trucks.get(j).L2;
           t.orig = og.trucks.get(j).orig;
           trucks.add(t);
        }
        //this.station = og.station;
    }
    
    
    //implementar constructora con C camiones, Trucks vacios
    public BState(int C, int i, int i1, int i2, int i3 ){
        trucks = new ArrayList<Truck>(C);
        for(int j = 0; j <C; ++j){
           Truck t = new Truck();
           //trucks.get(i) = new Truck();
           t.biL1 = 0;
           t.biL2 = 0;
           t.m = 0;
           t.benefici = 0;
           t.despeses = 0;
           t.L1 = t.L2 = t.orig = new Estacion(0,0);
           trucks.add(t);
        }
        station = new Estaciones(i, i1, i2, i3);
        
    }
            
    
    //implementar todo con rand, C camiones con Trucks llenos
    public BState iniRand(int C, int i, int i1, int i2, int i3){
        BState a = new BState(C, i, i1, i2, i3);
        ArrayList<Boolean> visitades = new ArrayList<Boolean>();
        for(int z = 0; z < i; ++z) visitades.add(false);
        Random num = new Random(i3);
        for (int j = 0; j < C; ++j) {

            a.trucks.get(j).biL1 = 0;
            a.trucks.get(j).biL2 = 0;
            a.trucks.get(j).benefici = 0;
            a.trucks.get(j).despeses = 0;
            
            
            int stati = num.nextInt(a.station.size());
            while (visitades.get(stati)) stati = num.nextInt(BState.station.size());
            a.trucks.get(j).orig = a.station.get(stati);
            visitades.set(stati,true);
            int stati2 = num.nextInt(a.station.size());
            while (stati2 == stati) stati2 = num.nextInt(a.station.size());
            a.trucks.get(j).L1 = a.station.get(stati2);
            int stati3 = num.nextInt(a.station.size());
            while (stati2 == stati3 || stati == stati3) stati3 = num.nextInt(a.station.size());
            a.trucks.get(j).L2 = a.station.get(stati3);
            a.trucks.get(j).m = Distance(a.trucks.get(j).orig, a.trucks.get(j).L1)+ Distance(a.trucks.get(j).L1,a.trucks.get(j).L2);
            a.modBicis(j);
        }
        return a;
    }

    public BState iniSmart(int C, int i0, int i1, int i2, int i3) {

        //Estaciones S = new Estaciones(i0, i1, i2, i3);
        BState state = new BState(C, i0, i1, i2, i3);
        PriorityQueue<Estacion> queue = new PriorityQueue<>(new EstacionComparator());

        queue.addAll(station);

       for(int i = 0; i < state.trucks.size() && !queue.isEmpty(); ++i) {
           state.trucks.get(i).orig = queue.poll();
           //System.out.println(state.trucks.get(i).orig.getCoordX() + " " + state.trucks.get(i).orig.getCoordY() + " " + (state.trucks.get(i).orig.getNumBicicletasNoUsadas()-state.trucks.get(i).orig.getDemanda()));
       }

       PriorityQueue<Estacion> objQueue = new PriorityQueue<>(new EstacionComparator2());
        objQueue.addAll(station);
        for(int i = 0; i < state.trucks.size() && !queue.isEmpty(); ++i) {
            state.trucks.get(i).L1 = objQueue.poll();
            state.trucks.get(i).L2 = objQueue.poll();
            state.modBicis(i);
            //System.out.println(state.trucks.get(i).orig.getCoordX() + " " + state.trucks.get(i).orig.getCoordY() + " " + (state.trucks.get(i).orig.getNumBicicletasNoUsadas()-state.trucks.get(i).orig.getDemanda()));
        }

       return state;
    }
    
    public void modDist(Estacion og, Estacion S1, Estacion S2, int i){
            int cost_per_km = (trucks.get(i).benefici + 9)/10;
            trucks.get(i).despeses -= trucks.get(i).m/1000*cost_per_km;
            trucks.get(i).orig = og;
            trucks.get(i).L1 = S1;
            trucks.get(i).L2 = S2;
            modBicis(i);
            trucks.get(i).m = Distance(og, S1)+ Distance(S1,S2);
            cost_per_km = (trucks.get(i).benefici + 9)/10;
            trucks.get(i).despeses += (trucks.get(i).m/1000)*cost_per_km;
    }
    
    public void modBicis(int i){
        int numero_bicis_disp = 0;
        trucks.get(i).biL1 = 0;
        trucks.get(i).biL2 = 0;
        while (trucks.get(i).orig.getNumBicicletasNext()- trucks.get(i).orig.getDemanda()- numero_bicis_disp > 0 && numero_bicis_disp < 30 && numero_bicis_disp < trucks.get(i).orig.getNumBicicletasNoUsadas())
            ++numero_bicis_disp;
        int num_bicis_L1 = trucks.get(i).L1.getNumBicicletasNext() - trucks.get(i).L1.getDemanda();
        int num_bicis_L2 = trucks.get(i).L2.getNumBicicletasNext() - trucks.get(i).L2.getDemanda();
        for(int j = 0; j < trucks.size(); ++j){
            if (trucks.get(i).L1 == trucks.get(j).L1) num_bicis_L1 +=  trucks.get(j).biL1;
            if (trucks.get(i).L1 == trucks.get(j).L2) num_bicis_L1 +=  trucks.get(j).biL2;
            if (trucks.get(i).L2 == trucks.get(j).L1) num_bicis_L2 +=  trucks.get(j).biL1;
            if (trucks.get(i).L2 == trucks.get(j).L2) num_bicis_L2 +=  trucks.get(j).biL2;
        }
        if (num_bicis_L1 < 0 && numero_bicis_disp > 0){
            if (numero_bicis_disp >= -1*num_bicis_L1){
                trucks.get(i).biL1 = -1*num_bicis_L1;
                //trucks.get(i).L1.setNumBicicletasNext(-1*num_bicis_L1 + trucks.ge());
                numero_bicis_disp += num_bicis_L1;
                
            }
            else {
                trucks.get(i).biL1 = numero_bicis_disp;
                //trucks.get(i).L1.setNumBicicletasNext(numero_bicis_disp + trucks.get(i).L1.getDemanda());
                numero_bicis_disp = 0;
            }
        }
        //else trucks.get(i).L1 = trucks.get(i).orig;
        if (num_bicis_L2 < 0 && numero_bicis_disp > 0){
                if (numero_bicis_disp >= -1*num_bicis_L2){
                trucks.get(i).biL2 = -1*num_bicis_L2;
                //trucks.get(i).L2.setNumBicicletasNext(-1*num_bicis_L2 + trucks.get(i).L2.getDemanda());
                numero_bicis_disp += num_bicis_L2;
            }
            else {
                trucks.get(i).biL2 = numero_bicis_disp;
                //trucks.get(i).L2.setNumBicicletasNext(numero_bicis_disp + trucks.get(i).L2.getDemanda());
                numero_bicis_disp = 0;
            }
        }
        //else trucks.get(i).L2 = trucks.get(i).L1;
        trucks.get(i).benefici = trucks.get(i).biL1 + trucks.get(i).biL2;
    }
    
    public int Distance(Estacion c1, Estacion c2)
    {
        return Math.abs(c1.getCoordX() - c2.getCoordX())+ Math.abs(c1.getCoordY() - c2.getCoordY());
    } 

    
    public boolean vist(Estacion S1){
        boolean found = false;
        for(int j = 0; j < trucks.size() && !found; ++j){
            found = S1 == trucks.get(j).orig;
        }
        return !found;
    }  

}

class EstacionComparator implements Comparator<Estacion> {
    public int compare(Estacion e1, Estacion e2){
        int bicE1 = e1.getNumBicicletasNoUsadas() - e1.getDemanda();
        int bicE2 = e2.getNumBicicletasNoUsadas() - e2.getDemanda();
        if (bicE1 < bicE2)
            return 1;
        else if (bicE1 > bicE2)
            return -1;
        return 0;
    }
}

class EstacionComparator2 implements Comparator<Estacion> {
    public int compare(Estacion e1, Estacion e2){
        int bicE1 = e1.getNumBicicletasNext() - e1.getDemanda();
        int bicE2 = e2.getNumBicicletasNext() - e2.getDemanda();
        if (bicE1 < bicE2)
            return -1;
        else if (bicE1 > bicE2)
            return 1;
        return 0;
    }
}

