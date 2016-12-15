package de.tu_clausthal.in.meclab.verkehrssimulation.desktop;

import java.util.Random;

/**
 * @author Ehsan Tatasadi
 */
public class Test {
    public static void main (String[] arg) {
        Random random = new Random();
        for(int i=0; i < 100; i++) {
            int x = random.nextInt(5);
            System.out.println(x);
        }
    }
}
