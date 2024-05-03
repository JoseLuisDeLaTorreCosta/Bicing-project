
package Bicing;

import IA.Bicing.*;

import java.util.Random;

public class Main {
    
public static void main(String[] args) {
        Random rnd = new Random();
        Estaciones s = new Estaciones(20, 75, 0, rnd.nextInt(500));
        /* 
        for (int i = 0; i < 8; ++i){
            System.out.println(s.get(i).getCoordX());
        }*/
        BState i = new BState();
        i = i.iniRand(4, 20, 75, 0, rnd.nextInt(500));
        for (int j = 0; j < 4; ++j){
            System.out.println("Truck nº: " + j);
            System.out.println(i.trucks.get(j).orig.getCoordX());
            System.out.println(i.trucks.get(j).orig.getCoordY());
        }
    }

}
