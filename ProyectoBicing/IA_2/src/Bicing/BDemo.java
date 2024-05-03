
package Bicing;

import IA.Bicing.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.search.framework.Problem;
import aima.search.framework.Search;   
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import java.util.Random;


public class BDemo {
    static long time;
    public static void main(String[] args) throws IOException {

        System.out.println("Guía parámetros");
        System.out.println("Inicializacón estados: smt | rnd");
        System.out.println("Algoritmo: hc | sa");
        System.out.println("Camiones: 0 < c < estaciones/2");
        System.out.println("Estaciones: c*2 < est < inf");
        System.out.println("Bicicletas: 0 <= bicis < inf");
        System.out.println("Rush hour: \n0:NO\notros valores: SI");
        System.out.println("Seed: 0 <= seed < inf");
        System.out.println();
        //Entrada de datos:
        BufferedReader reader = new BufferedReader((new InputStreamReader((System.in))));
        System.out.print("Inicialización del estado: ");
        String stateIni = reader.readLine();
        BState B = new BState();
        if(!(stateIni.equals("rnd") || stateIni.equals("smt"))) {
            PrintExit("El parámetro ha de ser rnd o smt");
        }
        int c, i, i1, i2, i3;
        System.out.print("Camiones: ");
        c = Integer.parseInt(reader.readLine());
        System.out.print("Estaciones: ");
        i = Integer.parseInt(reader.readLine());
        System.out.print("Bicicletas: ");
        i1 = Integer.parseInt(reader.readLine());
        System.out.print("Rush hour: ");
        i2 = Integer.parseInt(reader.readLine());
        System.out.print("Semilla aleatoria: ");
        i3 = Integer.parseInt(reader.readLine());
        System.out.print("Algoritmo: ");
        String algIni = reader.readLine();
        if(!(algIni.equals("hc") || algIni.equals("sa"))) {
            PrintExit("El parámetro ha de ser hc o sa");
        }

        //Tratamiento
        if(i2 != 0)
            i2 = 1;
        if(c <= 0 || i < 0 || i1 < 0 || i2 < 0 || i3 < 0)
            PrintExit("Ningún valor puede ser negativo");
        if(c > i / 2)
            PrintExit("Los camiones tienen que ser menores a las estaciones / 2");

        //Resumen de la entrada
        System.out.println();
        System.out.println("Estado inicial: " + stateIni);
        System.out.println("Algoritmo: " + algIni);
        System.out.println("Camiones: " + c);
        System.out.println("Estaciones: " + i);
        System.out.println("Bicicletas: " + i1);
        System.out.println("Tipo: " + i2);
        System.out.println("Seed: " + i3);

        time = System.currentTimeMillis();
        if(stateIni.equals("rnd"))
            B = B.iniRand(c, i, i1, i2, i3);
        else
            B = B.iniSmart(c, i, i1, i2, i3);

        if(algIni.equals("hc"))
            BGoodHillClimbingSearch(B);
        else
            BGoodSimulatedAnnealing(B);
    }
    
    private static void BHillClimbingSearch(BState B) {
        System.out.println("\nTSP HillClimbing  -->");
        try {
            Problem problem = new Problem(B, new BSuccessorFunction(), new BGoalTest(), new BHeuristicFunction());
            Search search = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem, search);
            
            //printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
            Total(search.getGoalState());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void BGoodHillClimbingSearch(BState B) {
        System.out.println("\nTSP HillClimbing  -->");
        try {
            Problem problem = new Problem(B, new BGoodSuccessorFunction(), new BGoalTest(), new BGoodHeuristicFunction());
            Search search = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem, search);
            
            //printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
            GoodTotal(search.getGoalState());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void BSimulatedAnnealing(BState B) {
        System.out.println("\nTSP SimulatedAnnealing  -->");
        try {
            Problem problem = new Problem(B, new BSASuccessorFunction(), new BGoalTest(), new BHeuristicFunction());
            SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(50000, 50000, 5, 0.5);
            SearchAgent agent = new SearchAgent(problem, search);
            
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
            Total(search.getGoalState());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void BGoodSimulatedAnnealing(BState B) {
        System.out.println("\nTSP SimulatedAnnealing  -->");
        try {
            Problem problem = new Problem(B, new BSASuccessorFunction(), new BGoalTest(), new BGoodHeuristicFunction());
            SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(500000, 500000, 1, 0.0005);
            SearchAgent agent = new SearchAgent(problem, search);
            
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
            GoodTotal(search.getGoalState());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void Total(Object Best) {
        BState Bi = (BState) Best;
        int Beneficio = 0;
        double M = 0;
        for(int i = 0; i < Bi.trucks.size(); ++i){
            Beneficio += Bi.trucks.get(i).benefici;
            M += Bi.trucks.get(i).m;
        }
        double KM = M/1000;
        System.out.println("---RESULTADOS---");
        System.out.println("Beneficio: " + Beneficio + "$");
        System.out.println("Distancia total furgonetas: " + Double.toString(KM).replace('.', ',') + "km");
        System.out.println("Tiempo total de ejecución: " + (System.currentTimeMillis() - time) + "ms");
       
    }

    private static void GoodTotal(Object Best) {
        BState Bi = (BState) Best;
        int Beneficio = 0;
        double M = 0;
        double Gastos = 0;
        for(int i = 0; i < Bi.trucks.size(); ++i){
            Beneficio += Bi.trucks.get(i).benefici;
            M += Bi.trucks.get(i).m;
            Gastos += Bi.trucks.get(i).despeses;
        }
        Beneficio -= Gastos;
        double KM = M/1000;
        System.out.println("---RESULTADOS---");
        System.out.println("Beneficio: " + Beneficio + "$");
        System.out.println("Distancia total furgonetas: " + Double.toString(KM).replace('.', ',') + "km");
        System.out.println("Tiempo total de ejecución: " + (System.currentTimeMillis() - time) + "ms");
       
    }
    
    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }
        
    }
    
    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = actions.get(i).toString();
            System.out.println(action);
        }
    }

    private static void PrintExit(String msg) {
        System.out.println(msg);
        System.exit(-1);
    }
}
