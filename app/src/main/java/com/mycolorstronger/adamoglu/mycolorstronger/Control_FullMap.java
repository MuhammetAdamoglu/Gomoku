package com.mycolorstronger.adamoglu.mycolorstronger;

import android.content.Context;



public class Control_FullMap {
    //Mapın dolup dolmadıgını kontrol eder
    private static int count=0;//Yapılan hamle sayısı
    private static int mapSize=0;//Mapın boyutu

    static boolean DoIt(Context context,Map map){
        count++;
        return mapSize == count;//Yapılan hamle sayısı, mapın boyutuna eşitmi diye bakılır
    }

    static void Reset(Map map){
        //Yeni bir oyunda resetlemek için kullanılır
        count=0;
        mapSize=map.getMap()[1].length * map.getMap().length;
    }

}
