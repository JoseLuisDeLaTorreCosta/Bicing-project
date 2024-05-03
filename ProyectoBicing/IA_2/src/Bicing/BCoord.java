/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bicing;

/**
 *
 * @author ariadna.garcia.lorente
 */
public class BCoord {
        int x, y;

    BCoord(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static double Distance(BCoord c1, BCoord c2)
    {
        //Se necesita Manhattan
        return Math.abs(c1.x - c2.x)+Math.abs(c1.y - c2.y);
    }
}
