package com.mycolorstronger.adamoglu.mycolorstronger;

import java.util.Random;

class setRandom {
    //Verilen iki sayı arasında random atar
    static Random rand;


    static int getRand(int num1, int num2){

        rand = new Random();
        return rand.nextInt(num2) + num1;
    }

}
